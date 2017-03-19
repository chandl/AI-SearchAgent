import java.util.Stack;

public class MoveSequence {
    Stack<Character> moves;

    public MoveSequence(String sequence){
        moves = new Stack<>();

        for(int i=sequence.length() - 1; i>=0; i--){
            moves.push(sequence.charAt(i));
        }
    }

    public String nextMove(){
        return Character.toString(moves.pop());
    }

    public boolean movesLeft(){
        return moves.size() > 0;
    }

}
