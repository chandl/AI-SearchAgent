import helper.MaedenLog;

import java.util.*;

/**
 * MoveSearch - Search for the Best Move from {@link Point} A to B in a {@link Mapped.Graph}
 *
 * <p>
 *     Using A* Search to find the best path.
 * </p>
 *
 * @author Chandler Severson
 *         3/11/2017
 */
public class MoveSearch {
    private MaedenLog log = MaedenLog.getInstance();
//    private Mapped.Graph searchGraph;
    private GraphTest searchGraph;
    private Point startPoint;
    private Point goalPoint;
    private int totalPoints;

    private static final int TURN_COST    = 3;
    private static final int MOVE_COST    = 5;
    private static final int OBSTACLE_COST= 10;
    private static final int USE_COST     = 10;
    private static final int PICKUP_COST  = 2;
    private static final int DROP_COST    = 1;


    /**
     * Instantiate a MoveSearch.
     * Find the best path with {@see AStar()}
     *
     * @param start The Starting {@link Point}
     * @param goal The Ending {@link Point}
     * @param graph The {@link Mapped.Graph} to traverse.
     */
    public MoveSearch(Point start, Point goal, Mapped.Graph graph) {
//        this.searchGraph = graph;
        this.searchGraph = GraphTest.instance;
        this.startPoint = start;
        this.goalPoint = goal;
        this.totalPoints = 1;
    }

    public MoveSearch() {}

    /**
     *  A* Search - Return the Best Move Sequence from a Starting Point to an Ending Point
     *
     *  <p>https://en.wikipedia.org/wiki/A*_search_algorithm</p>
     *
     * @return a {@link MoveSequence} to get from the starting {@link Point} to the ending {@link Point} on a {@link Mapped.Graph}
     */
    public MoveSequence AStar(){
        List<Point> closedSet = new ArrayList<>();
        Comparator<Point> pointComparator = new Point.PointComparator();
        PriorityQueue<Point> openSet = new PriorityQueue<Point>(totalPoints, pointComparator);

        initDistances(startPoint);

        startPoint.setDistFromSource(0);
        startPoint.setCostToDest(heuristic(startPoint, goalPoint));

        openSet.add(startPoint);

        Point current;

        char direction = MaedenClient.getDirection();
        while(openSet.peek() != null){
            current = openSet.poll();
            if(current.equals(goalPoint)){
//                return reconstructPath(current, direction);
                return reconstructPath(current, 'w');
            }

            closedSet.add(current);


            for(Point neighbor : searchGraph.getNeighbors(current)){
                if(closedSet.contains(neighbor)){continue;}
                int tentativeCost = current.getDistFromSource() + getCostToMove(current, neighbor, isFacing(current, neighbor, getDirection(current, neighbor)));

                if(!openSet.contains(neighbor)){
                    openSet.add(neighbor);
                }else if(tentativeCost >= neighbor.getDistFromSource()){
                    continue;
                }

                neighbor.setPrevious(current);
                neighbor.setDistFromSource(tentativeCost);
                double heuristic = heuristic(neighbor, goalPoint);
                neighbor.setCostToDest(tentativeCost + heuristic);

                //Print out possible paths to take
                //System.out.println(String.format("A* Path Point: From:%s to %s. Dist From Source: %d. Heuristic: %f. Direction: %c", current, neighbor, neighbor.getDistFromSource(), heuristic, neighbor.facing));
            }
        }

        return null;
    }

    /**
     * Gets the direction necessary to get to Point B from Point A.
     *
     * @param a
     * @param b
     * @return 'n, s, l, r' (north, south, left, right)
     */
    public char getDirection(Point a, Point b) {
        if(a.x > b.x){//A is below
            return 'n';
        }else if(a.x < b.x){//A is above
            return 's';
        }

        if(a.y > b.y){//A is to the right
            return 'l';
        }else if(a.y < b.y){//A is to the left
            return 'r';
        }

        return ' ';//this shouldn't happen
    }

    /**
     * Checks if you are facing Point B from Point A.
     * @param a
     * @param b
     * @param direction 'l, r, n, s' (left, right, north, south)
     * @return
     */
    public boolean isFacing(Point a, Point b, char direction){
        if(a.x != b.x){ //above or below
            if(direction == 'n' || direction == 's') return true;
            else return false;
        }

        if(a.y != b.y){ //to the east or west
            if(direction == 'r' || direction == 'l') return true;
            else return false;
        }
        return false;
    }

    /**
     * Reverses a path made from A* into a stack.
     *
     * <p>(So we can go from the starting point to ending point, instead of the other way around)</p>
     *
     * @param end
     * @return
     */
    private static Stack<Point> reversePath(Point end){
        Stack<Point> moveSequence = new Stack<>();

        //Add points to the sequence of Points
        Point current = end;
        while(current != null){
            moveSequence.push(current);
            current = current.getPrevious();
        }
        return moveSequence;
    }

    /**
     * Reconstructs a path found from {@see AStar()}.
     *
     * <p>
     *     This method takes an ending {@link Point} that is found during
     *     A* Search {@see AStar()} and from its predecessors, constructs
     *     a {@link MoveSequence} to get from the starting to ending {@link Point}s.
     * </p>
     *
     * @param end The last {@link Point} in the path. (The goal)
     * @return A {@link MoveSequence} that describes the moves needed by Maeden to get from the starting to ending {@link Point}.
     */
    private MoveSequence reconstructPath(Point end, char direction){
        StringBuilder sb = new StringBuilder();
        Stack<Point> moveSequence = reversePath(end);
        Point current;

        //Goes from the start point to the end point, creating a sequence of moves to send.
        Point next;
        while(moveSequence.size() > 1){
            current = moveSequence.pop();
            next = moveSequence.peek();

            if(current.x < next.x){ //Move to the South

                switch(direction){
                    case 'n':
                        sb.append('b');
                        break;
                    case 'e':
                        sb.append("rf");
                        direction = 's';
                        break;
                    case 's':
                        sb.append('f');
                        break;
                    case 'w':
                        sb.append("lf");
                        direction = 's';
                        break;
                }
            } else if(current.x > next.x){ //Move to the North
                switch(direction){
                    case 'n':
                        sb.append('f');
                        break;
                    case 'e':
                        sb.append("lf");
                        direction = 'n';
                        break;
                    case 's':
                        sb.append('b');
                        break;
                    case 'w':
                        sb.append("rf");
                        direction = 'n';
                        break;
                }
            } else if(current.y < next.y){ //Move to the East
                switch(direction){
                    case 'n':
                        sb.append("rf");
                        direction = 'e';
                        break;
                    case 'e':
                        sb.append("f");
                        break;
                    case 's':
                        sb.append("lf");
                        direction = 'e';
                        break;
                    case 'w':
                        sb.append("b");
                        break;
                }
            } else if(current.y > next.y){ //Move to the West
                switch(direction){
                    case 'n':
                        sb.append("lf");
                        direction = 'w';
                        break;
                    case 'e':
                        sb.append('b');
                        break;
                    case 's':
                        sb.append("rf");
                        direction = 'w';
                        break;
                    case 'w':
                        sb.append('f');
                        break;
                }
            }
        }

        return new MoveSequence(sb.toString());
    }

    /**
     * Initializes {@link Point} instance variables according to A* algorithm {@see AStar()}
     *
     * <p>
     *     Sets each {@link Point} in the {@link Mapped.Graph} to have a
     *     distanceFromSource and a costToDest of {@see Integer.MAX_VALUE}
     * </p>
     * @param start
     */
    private void initDistances(Point start){
        for(Point adjacent : searchGraph.adjList.get(start) ){
            if(adjacent == null || adjacent.getDistFromSource() == Integer.MAX_VALUE){continue;} //prevent from initializing again
            adjacent.setDistFromSource(Integer.MAX_VALUE);
            adjacent.setCostToDest(Integer.MAX_VALUE);
            this.totalPoints++;
            adjacent.facing = getDirection(start, adjacent);
            initDistances(adjacent);
        }
    }

    /**
     * Gets the cost to move from {@link Point} A to B.
     *
     * This changes based on the Object in the Points.
     *
     * @param a The starting point
     * @param b The destination point
     * @return
     */
    private int getCostToMove(Point a, Point b, boolean facing){

        for(char type : b.getPointType()){
            switch(type){
                case ' ':
                    return facing? MOVE_COST : TURN_COST + MOVE_COST;
                case '=':
                case '@':
                    return facing? OBSTACLE_COST + USE_COST : TURN_COST + OBSTACLE_COST + USE_COST;
                case 'k':
                case '+':
                default:
                    return facing? MOVE_COST + PICKUP_COST : TURN_COST + MOVE_COST + PICKUP_COST;
            }
        }

        if(b.getPointType().size() == 0){
            return facing? MOVE_COST : TURN_COST + MOVE_COST;
        }

        return Integer.MIN_VALUE;//this shouldn't happen
    }

    /**
     * The Heuristic Evaluation function to get from {@link Point} A to B.
     *  (Using Distance Formula for a Straight-Line heuristic)
     *
     * @param a The starting point
     * @param b The destination point
     * @return The straight-line distance between {@link Point} A and B.
     */
    public double heuristic(Point a, Point b){
        return Math.sqrt( Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2) );
    }


    public static void main(String[] args){
        String map =
                "*************************\n" +
                        "*           *           *\n" +
                        "*           *           *\n" +
                        "*           *           *\n" +
                        "*           *           *\n" +
                        "*           *           *\n" +
                        "*        1  * +         *\n" +
                        "*         T *           *\n" +
                        "*           *****@*******\n" +
                        "*           *           *\n" +
                        "*           *           *\n" +
                        "*           *           *\n" +
                        "*           *           *\n" +
                        "*           =           *\n" +
                        "*           *          T*\n" +
                        "*************************";
        String[] map2 = map.split("\n");


        char[][] mapChars = new char[map2.length][map2[0].length()];

        for(int i=0; i<map2.length; i++){
            System.out.println(map2[i]);
            mapChars[i] = map2[i].toCharArray();
        }

        GraphTest gtest = GraphTest.instance;
        gtest.init(mapChars);

        System.out.println("Origin: " + gtest.origin +"\nDestination: "+gtest.dest);

        //Print out every point and their adjacent points
        /*for(Map.Entry<Point, HashSet<Point>> entry : gtest.adjList.entrySet()){
            System.out.print(String.format("\nPoint (%s, %s). Adj: ", entry.getKey().x, entry.getKey().y  ));
            entry.getValue().stream().forEach(System.out::print);
        }*/

        MoveSearch ms = new MoveSearch(gtest.origin, gtest.dest, null);
        MoveSequence seq = ms.AStar();


        //Print out the move sequence
        /*while(seq.movesLeft()){
            System.out.println("Move: "+ seq.nextMove());
        }*/

        //Print the generated path
        gtest.printPath(mapChars);

    }

    /**
     * A testing Graph class for A* Search.
     */
    private static class GraphTest{
        Point origin, dest;
        HashMap<Point, HashSet<Point>> adjList;
        Point[][] internalMap;

        //These are the destination coordinates.
        int destX = 6, destY= 14;

        static GraphTest instance;
        static{
            instance = new GraphTest();
        }
        private GraphTest(){
            origin = new Point(0,0,null);
            adjList = new HashMap<>();
        }

        //Initialize the Graph, creating an internal map & creating adjacency list.
        protected void init(char[][] map){
            internalMap = new Point[map.length][map[0].length];
            initMap(map);
            initAdjacent(origin);
        }

        private void initMap(char[][] map){
            for(int i=0; i<map.length; i++){
                for(int j=0; j<map[0].length; j++){
                    if(map[i][j] == '*'){
                        internalMap[i][j] = null;
                        continue;
                    }

                    Vector<Character> type = new Vector<>();
                    type.add(map[i][j]);
                    internalMap[i][j] = new Point(i, j, type);

                    if(i == destX && j == destY){dest = internalMap[i][j];}

                    if(map[i][j] == '1'){
                        origin = internalMap[i][j];
                    }
                }
            }
        }

        private void initAdjacent(Point start){
            if(start == null){return;}
            if(adjList.get(start) != null){return;}

            HashSet<Point> adjacentSet = new HashSet<>();
            adjList.put(start, adjacentSet);

            //init left
            if(start.x - 1 >= 0){
                adjacentSet.add(internalMap[start.x -1][start.y]);
                initAdjacent(internalMap[start.x -1][start.y]);
            }

            //init right
            if(start.x + 1 < internalMap.length){
                adjacentSet.add(internalMap[start.x + 1][start.y]);
                initAdjacent(internalMap[start.x + 1][start.y]);
            }

            //init top
            if(start.y - 1 >= 0){
                adjacentSet.add(internalMap[start.x][start.y-1]);
                initAdjacent(internalMap[start.x][start.y-1]);
            }

            //init down
            if(start.y + 1 < internalMap[0].length){
                adjacentSet.add(internalMap[start.x][start.y + 1]);
                initAdjacent(internalMap[start.x][start.y+1]);
            }
        }


        private void printPath(char[][] map){
            char[][] mapCopy = new char[map.length][map[0].length];
            for(int i=0; i<map.length; i++){
                mapCopy[i] = map[i].clone();
            }
            mapCopy[origin.x][origin.y] = 'X';
            mapCopy[dest.x][dest.y] = 'X';

            Stack<Point> path = MoveSearch.reversePath(dest);
            path.pop();//remove first
            Point p;
            while(path.size() > 1){
                p = path.pop();
                mapCopy[p.x][p.y] = '.';

                //Print out info about new path point.
                //System.out.println(String.format("New Path Point: %s. Dist From Source: %d. Cost to Dest: %f. Direction: %c", p, p.getDistFromSource(), p.getCostToDest(), p.facing));
            }
            path.clear();

            for(int i=0; i<map.length; i++){
                System.out.println(new String(mapCopy[i]));
            }
        }

        //returns the direct (n,s,e,w) neighbors of a Point, p
        protected List<Point> getNeighbors(Point p){
            ArrayList<Point> out = new ArrayList<>();
            for(Point p1 : adjList.get(p)){
                if(p1 != null) out.add(p1);
            }
            return out;
        }

    }
}
