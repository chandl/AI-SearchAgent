import maeden.*;
import logic.ReactiveLogic;
import maeden.GridClient;
import maeden.SensoryPacket;

public class MaedenClient implements Runnable {

    private GridClient gc;

    public MaedenClient() {
        gc = new GridClient("localhost",7237);
    }

    public static void main(String[] args){
        MaedenClient client = new MaedenClient();
        client.run();
    }

    public void run(){
        ReactiveLogic react = new ReactiveLogic();

        while(true){
            //sense
            SensoryPacket sp = gc.getSensoryPacket();

            //think
            String action = react.think(sp);

            //act
            gc.effectorSend(action);
        }

    }


}
