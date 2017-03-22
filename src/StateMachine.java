/**
 * Created by Dawn on 3/10/2017.
 */
/**
 States - represent with stack:
 -LOOKING_FOOD
 -OBJECT_SEEN (subgoal to pick it up)
 -LOOKING_KEY
 -LOOKING_GUN
 -LOOKING_HAMMER
 -NAVIGATE_NARROWS
 -OPEN_DOOR
 -KILL_ROBOT
 -BREAK_ROCK
 **/

import logic.ObjectFinderLogic;
import logic.ReactiveLogic;

/**
Enum constants are implicitly public, static and final.
Enums are easy to read and the compiler can catch errors.
Fixed set of constants.
State Blip: Occasionally it will be convenient for an agent to enter a state with the condition that when
the state is exited, the agent returns to its previous state. This behavior is called a state blip.
**/

public class StateMachine {

        public enum State {
        LOOKFOOD, FOUNDFOOD,
        OBSTACLEDOOR, LOOKKEY, OPENDOOR,
        OBSTACLENARROWS, NAVIGATENARROWS,
        OBSTACLEROCK, LOOKHAMMER, BREAKROCK,
            EXPLORE
    }

    private static ReactiveLogic reactive = new ReactiveLogic();
    public static ObjectFinderLogic objectFinder;
    private static MoveSearch search;
    public static StateMachine sMachine;
    public static State currentState;
    static{
        sMachine = new StateMachine();
    }

    public static StateMachine getInstance() {
        return sMachine;
    }

    private StateMachine(){}


    //state: current state
    //sp: current sensory packet
    //Point...: Starting & Ending points (if applicable)
    public MoveSequence stateAction(maeden.SensoryPacket sp, Point... points) {

        if(points[0] != null){//we are trying to navigate to a specific point
            search = new MoveSearch(points[0], points[1], Mapped.getInstance().known);//start, goal, graph
            MoveSequence moves = search.AStar();

            if(moves == null){ //no path found, need to explore the frontier
                currentState = State.EXPLORE;
                moves = stateAction(sp);
                //TODO try to navigate to a random? section at end of the Known map
            }

            if(moves.getDoorObstacle() != null ){
                currentState = State.OBSTACLEDOOR;
                moves = stateAction(sp);
            }else if(moves.getNarrowsObstacle() != null ) {
                currentState = State.OBSTACLENARROWS;
                moves = stateAction(sp);
            }else if(moves.getRockObstacle() != null ){
                currentState = State.OBSTACLEROCK;
                moves = stateAction(sp);
            }

            return moves;
        }
        switch (currentState) {
            case LOOKFOOD:
                //Reactive Agent
                return new MoveSequence(reactive.think(sp));

            case OBSTACLEDOOR:
                //if they have a key, go to the door
                if(sp.getInventory().contains('k')){
                    currentState = State.OPENDOOR;
                    return stateAction(sp);

                }else{ //set state to LOOKKEY

                    currentState = State.LOOKKEY;
                    return stateAction(sp);
                }

            case LOOKKEY:
                objectFinder = new ObjectFinderLogic("k");
                String move = objectFinder.think(sp);
                break;

            case OPENDOOR:
                search = new MoveSearch();//start, destination, graph
                return search.AStar();

            case OBSTACLENARROWS:
                if (sp.getInventory().isEmpty()) {
                    search = new MoveSearch(points[0], points[1], Mapped.getInstance().known);
                    MoveSequence movesequence = search.AStar();
                    //moving past narrows
                    movesequence.addMove('f');
                    return movesequence;
                } else {
                    search = new MoveSearch(); // search to come up to the narrows
                    MoveSequence moveSequence = search.AStar();
                    moveSequence.removeLastMove();
                    currentState = State.NAVIGATENARROWS;
                    return stateAction(sp, points[0], points[1]);
                    //drop items
                }
                //break;

            case NAVIGATENARROWS:
                search = new MoveSearch();//start, destination, graph
                return search.AStar();
                //break;

            case OBSTACLEROCK:
                //if they have a hammer, go to the rock
                if(sp.getInventory().contains('T')){
                    currentState = State.BREAKROCK;
                    return stateAction( sp);
                }else{ //set state to LOOKHAMMER
                    currentState = State.LOOKHAMMER;
                    return stateAction( sp);
                }
                //break;

            case LOOKHAMMER:
                objectFinder = new ObjectFinderLogic("T");
                break;

            case BREAKROCK:
                search = new MoveSearch();//start, destination, graph
                return search.AStar();
                //break;

            default:
                //System.out.println("OK");
                break;
        }
        return null;
    }
}