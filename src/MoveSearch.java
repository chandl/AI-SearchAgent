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
    private Mapped.Graph searchGraph;
    private Point startPoint;
    private Point goalPoint;
    private int totalPoints;


    /**
     * Instantiate a MoveSearch.
     * Find the best path with {@see AStar()}
     *
     * @param start The Starting {@link Point}
     * @param goal The Ending {@link Point}
     * @param graph The {@link Mapped.Graph} to traverse.
     */
    public MoveSearch(Point start, Point goal, Mapped.Graph graph) {
        this.searchGraph = graph;
        this.startPoint = start;
        this.goalPoint = goal;
        this.totalPoints = 1;
    }


    /**
     *  A* Search - Return the Best Move Sequence from a Starting Point to an Ending Point
     *
     *  <p>https://en.wikipedia.org/wiki/A*_search_algorithm</p>
     *
     * @return a {@link MoveSequence} to get from the starting {@link Point} to the ending {@link Point} on a {@link Mapped.Graph}
     */
    private MoveSequence AStar(){
        List<Point> closedSet = new ArrayList<>();
        Comparator<Point> pointComparator = new Point.PointComparator();
        PriorityQueue<Point> openSet = new PriorityQueue<Point>(totalPoints, pointComparator);

        openSet.add(startPoint);

        initDistances(startPoint);

        startPoint.setDistFromSource(0);
        startPoint.setCostToDest(heuristic(startPoint, goalPoint));

        Point current;
        while(openSet.peek() != null){
            current = openSet.poll();
            if(current.equals(goalPoint)){
                return reconstructPath(current);
            }

            closedSet.add(current);

            for(Point neighbor : getNeighbors(current)){
                if(closedSet.contains(neighbor)){continue;}

                int tentativeCost = current.getDistFromSource() + getCostToMove(current, neighbor);

                if(!openSet.contains(neighbor)){
                    openSet.add(neighbor);
                }else if(tentativeCost >= neighbor.getDistFromSource()){
                    continue;
                }

                neighbor.setPrevious(current);
                neighbor.setDistFromSource(tentativeCost);
                neighbor.setCostToDest(tentativeCost + heuristic(neighbor, goalPoint));
            }
        }

        return null;
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
    private MoveSequence reconstructPath(Point end){
        StringBuilder sb = new StringBuilder();
        Stack<Point> moveSequence = new Stack<>();
        char direction = MaedenClient.getDirection();

        //Add points to the sequence of Points
        Point current = end;
        while(current != null){
            moveSequence.push(current);
            current = current.getPrevious();
        }

        //Goes from the start point to the end point, creating a sequence of moves to send.
        Point next;
        for(int i=0; i<moveSequence.size()-1; i++){
            current = moveSequence.pop();
            next = moveSequence.peek();

            if(current.x > next.x){ //Move to the West
                switch(direction){
                    case 'n':
                        sb.append("lf");
                        direction = 'w';
                        break;
                    case 'e':
                        sb.append("b");
                        break;
                    case 's':
                        sb.append("rf");
                        direction = 'w';
                        break;
                    case 'w':
                        sb.append("f");
                        break;
                }
            } else if(current.x < next.x){ //Move to the East
                switch(direction){
                    case 'n':
                        sb.append("rf");
                        direction = 'e';
                        break;
                    case 'e':
                        sb.append('f');
                        break;
                    case 's':
                        sb.append("lf");
                        direction = 'e';
                        break;
                    case 'w':
                        sb.append('b');
                        break;
                }
            } else if(current.y > next.y){ //Move North
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
            } else if(current.y < next.y){ //Move South
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
            }
        }

        return new MoveSequence(sb.toString());
    }

    /**
     * Gets the adjacent {@link Point}s of the specified point.
     *
     * @param p The {@link Point} to analyze.
     * @return A {@link List} of {@link Point}s that are adjacent to p.
     */
    private List<Point> getNeighbors(Point p){
        return searchGraph.adjList.get(p);
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
            adjacent.setDistFromSource(Integer.MAX_VALUE);
            adjacent.setCostToDest(Integer.MAX_VALUE);
            this.totalPoints++;
            initDistances(adjacent);
        }
    }

    /**
     * Gets the cost to move from {@link Point} A to B.
     *
     * This changes based on the Object in the Points.
     *
     * @param a
     * @param b
     * @return
     */
    private int getCostToMove(Point a, Point b){
        //TODO implement distance function
        return 0;
    }

    /**
     * The Heuristic Evaluation function to get from {@link Point} A to B.
     *
     * @param a
     * @param b
     * @return
     */
    public int heuristic(Point a, Point b){
        //TODO implement heuristic function
        //Maybe use a straight-line heuristic function?
        return 0;
    }

}
