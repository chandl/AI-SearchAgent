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

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

/**
Enum constants are implicitly public, static and final.
Enums are easy to read and the compiler can catch errors.
Fixed set of constants.
State Blip: Occasionally it will be convenient for an agent to enter a state with the condition that when
the state is exited, the agent returns to its previous state. This behavior is called a state blip.

 @author Chandler & Dawn
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
    private static ExploreLogic explore = new ExploreLogic();

    private static MoveSearch search;
    public static StateMachine sMachine;
    public static State currentState;
    private static Stack<State> goal;

//    private static Point lastDoorObstacle = null;
//    private static Point lastNarrowsObstacle = null;
//    private static Point lastRockObstacle = null;

    private static Stack<Point> lastDoorObstacle;
    private static Stack<Point> lastNarrowsObstacle;
    private static Stack<Point> lastRockObstacle;

    static{
        sMachine = new StateMachine();
        goal = new Stack<>();
        lastDoorObstacle = new Stack<>();
        lastNarrowsObstacle = new Stack<>();
        lastRockObstacle = new Stack<>();
    }

    public static StateMachine getInstance() {
        return sMachine;
    }

    private StateMachine(){}

    public char[][] convertSensesToBoard(ArrayList<ArrayList<Vector<Character>>> visual){
//        ArrayList<ArrayList<Vector<Character>>> visualArray = sp.getVisualArray();
        char[][] out = new char[visual.size()][visual.get(0).size()];

        int i=0, j=0;
        for(ArrayList<Vector<Character>> al : visual){
            j=0;
            for(Vector<Character> v : al){
                if(v.size() > 0){
                    if(v.get(0) == '0' && v.size() > 1){
                        out[i][j] =  v.get(1);
                    }else{
                        out[i][j] = v.get(0);
                    }
                }

                j++;
            }
            i++;
        }

        return out;
    }

    private MoveSequence searchVisual(char[][] board, char object){
        Point dest = null;
        Point origin = null;
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                if(board[i][j] == object){
                    dest = new Point(i,j, new Vector<>(object));
                }
                if(board[i][j] == '0'){
                    origin = new Point(i,j, new Vector<>('0'));
                }
            }
        }
        MoveSearch.ViewGraph graph = new MoveSearch.ViewGraph(dest);
        graph.init(board);

        MoveSearch search = new MoveSearch(origin, dest, graph);
        return search.AStar();
    }

    //sp: current sensory packet
    //Point...: Starting & Ending points (if applicable)
    public MoveSequence stateAction(maeden.SensoryPacket sp, Point... points) {

        //we are trying to navigate to a specific point
        if(points.length > 0){
            search = new MoveSearch(points[0], points[1], Mapped.getInstance().known);//start, goal, graph
            MoveSequence moves = search.AStar();

            if(moves == null){ //no path found, need to explore the frontier
                currentState = State.EXPLORE;
                moves = stateAction(sp);
            }

            if(moves.getDoorObstacle() != null && currentState != State.OPENDOOR){//if the moveSearch had a door obstacle and we were NOT looking to open it
                currentState = State.OBSTACLEDOOR;
                lastDoorObstacle.push(moves.getDoorObstacle());
                moves = stateAction(sp);
            }else if(moves.getNarrowsObstacle() != null && currentState != State.NAVIGATENARROWS) {//if the moveSearch had a narrows obstacle and we were NOT looking to navigate through
                currentState = State.OBSTACLENARROWS;
                lastNarrowsObstacle.push(moves.getNarrowsObstacle());
                moves = stateAction(sp);
            }else if(moves.getRockObstacle() != null && currentState != State.BREAKROCK){//if the moveSearch had a rock obstacle and we were NOT looking to break the rock
                currentState = State.OBSTACLEROCK;
                lastRockObstacle.push(moves.getRockObstacle());
                moves = stateAction(sp);
            }

            switch(currentState){//add appropriate actions to navigate narrows/rocks/doors
                case OPENDOOR:
                case BREAKROCK:
                    goal.pop();//remove obstacle
                    moves.addMove('u');//use the item to open/destroy the door/rock
                    break;
                case NAVIGATENARROWS:
                    for(int i=0; i<sp.getInventory().size(); i++){
                        moves.addMove('d'); //drop all items in the inventory
                    }
                    moves.addMove('f');//go through the narrows
                    goal.pop();//remove obstacle
                    break;
            }

            return moves;
        }
        //TODO in order for this to work, we need to keep track of where the agent is in the Mapped.Graph
        switch (currentState) {
            case EXPLORE://Make one move towards the frontier
                MoveSequence sequence = explore.think(sp);//change to explore.think if Mapped.Graph logic is put in place.
                currentState = goal.pop();//go to the previous state
                return sequence;

            case LOOKFOOD:

                if(sp.getGroundContents().contains('+')){
                    return new MoveSequence("gu");
                }

                //Look for food in sensory packet after reactive search.
                //TODO keep track of food location in Mapped
                for(String s : sp.getRawSenseData()){
                    if(s.contains("\"+\"")){//we found food somewhere!
                        goal.push(State.LOOKFOOD);
                        sequence = searchVisual(convertSensesToBoard(sp.getVisualArray()), '+');
//                        System.out.println("New sequence!");
                        if(sequence.movesLeft()){}//no moves found
                        else return sequence;

//                        return stateAction(sp, startPoint, endPoint);
                    }
                }

                //IF Last action wasn't failure, go REACTIVE. FALLBACK if we can't get other stuff working in time
                if(sp.getLastActionStatus()){
                    return reactive.think(sp);
                }

                //ELSE ->
                goal.push(State.LOOKFOOD);
                currentState = State.EXPLORE;
                return stateAction(sp);
            //==========OBSTACLES==========
            case OBSTACLEDOOR:

                if(sp.getInventory().contains('k')){
                    goal.push(State.OBSTACLEDOOR);
                    currentState = State.OPENDOOR;
                    return stateAction(sp);

                }else{ //set state to LOOKKEY

                    goal.push(State.OBSTACLEDOOR);
                    currentState = State.LOOKKEY;
                    return stateAction(sp);
                }

            case OBSTACLENARROWS:
                currentState = State.NAVIGATENARROWS;
//                return stateAction(sp, startPoint, lastNarrowsObstacle.pop());

            case OBSTACLEROCK:
                //if they have a hammer, go to the rock
                if(sp.getInventory().contains('T')){
                    currentState = State.BREAKROCK;
//                    return stateAction( sp, startPoint, lastRockObstacle.pop());
                }else{ //set state to LOOKHAMMER
                    currentState = State.LOOKHAMMER;
                    return stateAction( sp);
                }
                //break;

            //==========LOOKING FOR STUFF==========
            case LOOKKEY:

                for(String s : sp.getRawSenseData()){//WILL CHECK Mapped.Graph instead.
                    if(s.contains("\"k\"")){
                        //MoveSearch to this location. We need to figure out what it is on the Mapped.Graph first
                        goal.push(State.LOOKKEY);
                        sequence = searchVisual(convertSensesToBoard(sp.getVisualArray()), 'k');
                        if(sequence.movesLeft()){}//no moves found
                        else return sequence;
//                        return stateAction(sp, startPoint, endPoint);
                    }
                }

                //Key not found anywhere
                goal.push(State.LOOKKEY);
                currentState = State.EXPLORE;
                return stateAction(sp);

            case LOOKHAMMER:
                for(String s : sp.getRawSenseData()){//WILL CHECK Mapped.Graph instead.
                    if(s.contains("\"T\"")){
                        //MoveSearch to this location. We need to figure out what it is on the Mapped.Graph first
                        goal.push(State.LOOKHAMMER);
                        sequence = searchVisual(convertSensesToBoard(sp.getVisualArray()), 'T');
                        if(sequence.movesLeft()){}//no moves found
                        else return sequence;
//                        return stateAction(sp, startPoint, endPoint);
                    }
                }

                //Hammer not found anywhere
                goal.push(State.LOOKHAMMER);
                currentState = State.EXPLORE;
                return stateAction(sp);

            default:
                System.out.println("stop breaking our program pls");
                System.exit(-1);
                break;
        }
        return null;
    }
}