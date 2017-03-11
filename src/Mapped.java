import java.util.*;

/**
 * Created by andres on 3/4/17.
 */

public class Mapped {
    static final int WIDTH = 100;
    static final int HEIGHT = 100;
    public boolean empty;
    public Graph visited;
    public Graph known;

    //internal map of visited and seen parts of the grid
    //use graph to build out with adjacency list.

    class Graph {

        HashMap<Point, List<Point>> adjList;

        public Graph() {
            adjList = new HashMap<>();
//            for (int i = 0; i < nodes.length; ++i) {
//                adj.put(i, new LinkedList<Point>());
//            }
        }

        public int addNext(Point find, Point add) {
            try {
                adjList.get(find).add(add);
                adjList.get(add).add(find);
                return 0;
            } catch (Exception e) {
                return 1;
            }

        }

        public List<Point> getNext(Point find) {
            return adjList.get(find);
        }

    }

    public Mapped() {

        this.empty = true;
        this.visited = new Graph();
        this.known = new Graph();

    }

    //constructor of singleton cause we only need 1 map
    private static class SingleMap {
        private static final Mapped SINGLETON = new Mapped();

    }

    public static Mapped getInstance() {

        return SingleMap.SINGLETON;
    }

    public void addFOV(/*field of view, /*/) {
        //take in a field of view and insert/update accordingly

        if(empty) {
            for (int x = -2; x < 3; x++ ){
                for (int y = -5; y < 2; y++) {
                    if (x == 0 && y == 0) {

                    } else {
                        known.put(new Point, 1 );

                    }
                }
            }
            empty = false;

        } else {

        }

    }

    //wishful classes
    public void setPoint(){

        //maybe set a flag on the map to return to

    }

    public AgentKnowledge agentFoodDirectionGuess() {
        return null;
    }
    public AgentKnowledge agentSeenHammer() {
        return null;

    }
    public AgentKnowledge agentSeenRobot() {
        //has the agent seen a robot
        return null;

    }
    public AgentKnowledge agentSeenKey() {
        //has the agent seen a key
        return null;

    }
    public AgentKnowledge agentSeenDoor() {
        //has the agent seen a door
        return null;

    }
    public AgentKnowledge agentInRoom() {
        //are we in a room right now?
        return null;

    }

    public AgentKnowledge agentSeenFood() {
        //has the agent seen food
        return null;

    }

    public class AgentKnowledge {

        public boolean affirmation;
        public  Point location;

    }

}
