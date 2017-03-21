import maeden.GridClient;
import maeden.SensoryPacket;

public class MaedenClient implements Runnable {

    private GridClient gc;
    private static char direction;
    private static Mapped map;


    public MaedenClient() {
        gc = new GridClient("localhost",7237);
        direction = 'w';
    }

    public static void main(String[] args){
        MaedenClient client = new MaedenClient();
        map = Mapped.getInstance();

        client.run();
    }

    public void run(){
        StateMachine sm = StateMachine.getInstance();


        while(true){

            //sense
            SensoryPacket sp = gc.getSensoryPacket();

            //think
            MoveSequence actions = sm.stateAction(sp);

            //act
            String cmd;
            while(actions.movesLeft()){
                cmd = actions.nextMove();
                if(cmd.equals("r")){
                    switch(direction){
                        case 'n':
                            direction = 'e';
                            break;
                        case 'w':
                            direction = 'n';
                            break;
                        case 'e':
                            direction = 's';
                            break;
                        case 's':
                            direction = 'w';
                            break;
                    }
                }else if(cmd.equals("l")){
                    switch(direction){
                        case 'n':
                            direction = 'w';
                            break;
                        case 'w':
                            direction = 's';
                            break;
                        case 'e':
                            direction = 'n';
                            break;
                        case 's':
                            direction = 'e';
                            break;
                    }
                }
                gc.effectorSend(cmd);
            }

        }

    }



    public static char getDirection(){
        return direction;
    }


}
