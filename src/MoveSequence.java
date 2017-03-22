import java.util.Stack;

/**
 * MoveSequence - Represents a sequence of moves.
 * @author Chandler
 */
public class MoveSequence {
    Stack<Character> moves;
    private Point doorObstacle = null;
    private Point narrowsObstacle = null;
    private Point rockObstacle = null;

    public MoveSequence(){}

    public MoveSequence(String sequence){
        moves = reverseSequence(sequence);
    }

    private Stack<Character> reverseSequence(String sequence){
        Stack<Character> moves = new Stack<>();

        for(int i=sequence.length() - 1; i>=0; i--){
            moves.push(sequence.charAt(i));
        }

        return moves;
    }

    public void addSequence(String sequence){
        moves = reverseSequence(sequence);
    }

    public void addMove(char move){
        moves.insertElementAt(move, moves.size()-1);
    }

    public void removeLastMove(){
        moves.remove(0);
    }

    public Point getDoorObstacle() {
        return doorObstacle;
    }

    public void setDoorObstacle(Point doorObstacle) {
        this.doorObstacle = doorObstacle;
    }

    public Point getNarrowsObstacle() {
        return narrowsObstacle;
    }

    public void setNarrowsObstacle(Point narrowsObstacle) {
        this.narrowsObstacle = narrowsObstacle;
    }

    public Point getRockObstacle() {
        return rockObstacle;
    }

    public void setRockObstacle(Point rockObstacle) {
        this.rockObstacle = rockObstacle;
    }

    public String nextMove(){
        return Character.toString(moves.pop());
    }

    public boolean movesLeft(){
        return moves.size() > 0;
    }

}
