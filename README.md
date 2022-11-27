###overview of the algorithm

My algorithm uses three methods to break up the problem. 
First the findPaths() method compares the distance between the seperate goals begin and end, 
and then compares and sort the goals to start placing paths at the shortest distance goal first, 
so that paths are less likely to overlap. Next, findPaths() calls paths(), which creates a path by calling bfs().
It takes the path created and reverses that HashMap so that the order is from end to start. Finally, this function
converts the data into a Wire object and then places that Wire to the board. For finding the shortest path, my
algorithm uses Breadth First Search, taking in a begin and end. The board is then traversed and checked for blockages or
if the current or neighbor coordinate is taken or equal to the end coordinate. bfs() is adding to HashMap parents, which 
shows the path from begin to end.

###one or more examples of applying your algorithm to interesting boards
![alt text](https://i.imgur.com/CFojEan.png)
###evaluation of your algorithm with respect to finding and minimizing wire layouts

My algorithm works well with placing the shortest distance goals first before the longer distance goals, which 
eliminates wires blocking other wires on the board. This makes it so that all wires will be placed with respect to
the board.

###evaluation of the time complexity and wall-clock time of your algorithm.
#####time complexity:
- BFS time complexity: O(n)
- paths time complexity: O(n^2)
- findPaths time complexity: O(nlogn)
- Total time complexity: O(n^3)
#####wall clock time:
- Using System.currentTimeMillis(), I got a range from 17 ms to 53 ms for 23 wires and a 5 x 12 board.



