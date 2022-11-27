
import java.lang.reflect.Array;
import java.util.*;

public class Routing {

    /**
     * The findPaths function takes a board and a list of goals that contain
     * endpoints that need to be connected. The function returns a list of
     * Paths that connect the points.
     *
     * BFS: graph (board), start (goals (Coord start)), visited (new list of visited Coords), parent (what came before the current Coord)
     * GOAL: BFS --> shortest path from Coord start --> Coord end
     **/

    public static ArrayList<Wire> findPaths(Board board, ArrayList<Endpoints> goals) {
        //create comparator and sort goals
        //Collections.sort(goals, (p1, p2) -> (int) Math.hypot(p1.start.x - p1.end.x, p2.start.y - p2.end.y));

        ArrayList<Endpoints> copyGoals = (ArrayList<Endpoints>) goals.clone();

        Comparator<Endpoints> distance = new Comparator<Endpoints>() {
            @Override
            public int compare(Endpoints pair1, Endpoints pair2) {
                double dist1 = (Math.pow((pair1.start.x - pair1.end.x), 2)) + (Math.pow((pair1.start.y - pair1.end.y), 2));
                double dist2 = (Math.pow((pair2.start.x - pair2.end.x), 2)) + (Math.pow((pair2.start.y - pair2.end.y), 2));
                return Double.compare(dist1, dist2);
            }
        };

        copyGoals.sort(distance);
        return paths(board, copyGoals);
    }


    public static ArrayList<Wire> paths(Board board, ArrayList<Endpoints> goals){

        ArrayList<Wire> path = new ArrayList<Wire>();
        //go through all the goals a path is needed for

        for (Endpoints goal : goals) {
            //get the current goal
            Coord begin = goal.start;
            Coord end = goal.end;
            int num = goal.id;
            //BFS time
            HashMap<Coord, Coord> parents = bfs(board, begin, end);

            List<Coord> points = new ArrayList<>();
            //go through parents backwards to find the path, start at end coord
            Coord current = end;
            //while not at beginning
            //if parent contains end
            if(parents.containsKey(end)){
                while (!current.equals(begin)) {
                    points.add(current);
                    current = parents.get(current); //end might not have parent
                }
            }
            points.add(begin);
            for (int k = 0, j = points.size() - 1; k < j; k++) {
                points.add(k, points.remove(j));
            }

            Wire a = new Wire(num, points);
            board.placeWire(a);
            path.add(a);
        }
        return path;
    }

    public static HashMap<Coord, Coord> bfs(Board board, Coord begin, Coord end) {

        //setting up the search for BFS
        HashSet<Coord> visited = new HashSet<Coord>();
        //priority queue of heuristic that finds the coord closest to end in queued
        //heuristic Euclidean distance
        Queue<Coord> queued = new LinkedList<Coord>();
        HashMap<Coord, Coord> parents = new HashMap<Coord, Coord>();
        queued.add(begin);
        parents.put(begin, begin);
        visited.add(begin);
        Coord current = queued.peek();

        //searching
        while (!queued.isEmpty() && !current.equals(end)) {
            current = queued.remove();
            //all neighbors: north, south, east, west
            for (Coord neighbor : board.adj(current)) {
                //check if they are obstacles or occupied, if not continue and if neighbor was not already looked at
                if ((!board.isOccupied(neighbor) || (neighbor.equals(end))) && !visited.contains(neighbor)) {
                    //if the parent is not already in the list, then add
                    parents.put(neighbor, current);
                    queued.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return parents;
    }
}
