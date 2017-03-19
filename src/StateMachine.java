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
/**
Enum constants are implicitly public, static and final.
Enums are easy to read and the compiler can catch errors.
Fixed set of constants.
State Blip: Occasionally it will be convenient for an agent to enter a state with the condition that when
the state is exited, the agent returns to its previous state. This behavior is called a state blip.
**/

public class StateMachine {

        public enum State {
        LOOKFOOD, FOUNDFOOD
        OBSTACLEDOOR, LOOKKEY, OPENDOOR,
        OBSTACLENARROWS, NAVIGATENARROWS,
        OBSTACLEROCK, LOOKHAMMER, BREAKROCK,
        OBSTACLEROBOT, LOOKGUN, KILLROBOT
    }

    StateMachine sMachine;

    public StateMachine(StateMachine sMachine) {
        this.sMachine = sMachine;
    }

    public void stateAction() {
        switch (sMachine) {
            case LOOKFOOD:
                //do something.
                break;

            case FOUNDFOOD:
                //if (rawSenses[0].equals("h") == true)
                {
                    //Food found. Exit.
                    //System.exit(0);
                }
                break;

            case OBSTACLEDOOR:
                //do something.
                break;

            case LOOKKEY:
                //do something.
                break;

            case OPENDOOR:
                //do something.
                break;

            case OBSTACLENARROWS:
                //do something.
                break;

            case NAVIGATENARROWS:
                //do something.
                break;

            case OBSTACLEROCK:
                //do something.
                break;

            case LOOKHAMMER:
                //do something.
                break;

            case BREAKROCK:
                //do something.
                break;

            case OBSTACLEROBOT:
                //do something.
                break;

            case LOOKGUN:
                //do something.
                break;

            case KILLROBOT:
                //do something.
                break;

            default:
                System.out.println("OK");
                break;
        }
    }
}