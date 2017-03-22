import maeden.SensoryPacket;

import java.util.ArrayList;
import java.util.Vector;

/**
 * A reactive logic that follows its nose towards the food.
 * Code recycled from the 1st Maeden Lab.
 * @author Chandler Severson
 */
public class ReactiveLogic extends AgentLogic {

    /**
     * Checks if there is a wall in the specified direction
     * (in relation to a SensoryPacket)
     *
     * @param direction The direction to check {@code 'l', 'r', 'f', 'b'}, (left, right, forwards, backwards)
     * @param sp The {@link SensoryPacket} of the agent's location.
     * @return {@code True} if there is a wall in the direction, {@code False} otherwise.
     */
    public boolean checkWall(String direction, SensoryPacket sp){
        ArrayList<ArrayList<Vector<Character>>> visualArray = sp.getVisualArray();

        switch(direction){
            case "l":
                if(visualArray.get(5).get(1).contains('*')){
                    return true;
                }else{
                    return false;
                }
            case "r":
                if(visualArray.get(5).get(3).contains('*')){
                    return true;
                }else{
                    return false;
                }
            case "f":
                if(visualArray.get(4).get(2).contains('*')){
                    return true;
                }else{
                    return false;
                }
            case "b":
                if(visualArray.get(6).get(2).contains('*')){
                    return true;
                }else{
                    return false;
                }
            default:
                return false;
        }
    }

    @Override
    public MoveSequence think(SensoryPacket sp) {
        String action = "";

        //Check if we have food in our inventory
        if(sp.getInventory().contains('+')){
            action = "u"; //use
        }
        //Check if we are standing on a food space
        else if( sp.getGroundContents().contains('+')){
            action = "g"; //grab
        }

        //No Food + Not on food space
        else {
            //Move in the direction of the food
            action = sp.getSmell();

            //Check if we hit a wall
            if(checkWall(action, sp))
            {
                //If we have already turned away from the obstacle
                //go ahead and move forward
                if((action.equals("l")) && (!checkWall("f", sp)))
                {
                    action = "f";
                }
                else if(action.equals("r") && !checkWall("f", sp))
                {
                    action = "f";
                }
                else
                {
                    action = "r";
                }
            }

            if(action.equals("b"))
            {//move forwards instead of backwards
                action="r";
            }
        }

        return new MoveSequence(action);
    }

}
