import java.util.Stack;

public class MoveSequence {
    Stack<Character> moves;
    private boolean doorObstacle = false;
    private boolean narrowsObstacle = false;
    private boolean rockObstacle = false;

    public MoveSequence(String sequence){
        moves = new Stack<>();

        for(int i=sequence.length() - 1; i>=0; i--){
            switch(sequence.charAt(i)){
                case 'r':
                    rockObstacle = true;
                    break;
                case 'n':
                    narrowsObstacle = true;
                    break;
                case 'd':
                    doorObstacle = true;
                    break;
            }
            moves.push(sequence.charAt(i));
        }
    }

    public void addMove(char move){
        moves.insertElementAt(move, moves.size()-1);
    }

    public void removeLastMove(){
        moves.remove(moves.size()-1);
    }
    public boolean isDoorObstacle() {
        return doorObstacle;
    }

    public boolean isNarrowsObstacle() {
        return narrowsObstacle;
    }

    public boolean isRockObstacle() {
        return rockObstacle;
    }

    public String nextMove(){
        return Character.toString(moves.pop());
    }

    public boolean movesLeft(){
        return moves.size() > 0;
    }

}
