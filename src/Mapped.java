/**
 * Created by andres on 3/4/17.
 */

public class Mapped {

    //internal map of visited and seen parts of the grid
    //use graph to build out with adjacency matrix.
    public Mapped() {}

    //constructor of singleton cause we only need 1 map
    private static class SingleMap {
        private static final Mapped SINGLETON = new Mapped();
    }
    public static Mapped getInstance() {
        return SingleMap.SINGLETON;
    }

    public void addFOV(/*field of view*/) {
        //take in a field of view and insert/update accordingly

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
        public MaedenClient.Point location;

    }

}
