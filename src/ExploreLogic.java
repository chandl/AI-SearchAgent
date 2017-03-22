import maeden.SensoryPacket;


/**
 * Agent that goes in a random direction to explore.
 */
public class ExploreLogic extends AgentLogic {

    private Mapped.Graph known;
    private SensoryPacket sp;

    @Override
    public MoveSequence think(SensoryPacket sp) {
        this.sp = sp;
        Mapped mapped = Mapped.getInstance();
        known = mapped.known;

        int rand = (int) Math.random() * 4 + 1;
        char random;
        switch(rand){
            case 1:
                random = 'e';
                break;
            case 2:
                random = 'w';
                break;
            case 3:
                random = 'n';
                break;
            case 4:
                random = 's';
                break;
            default:
                random = 'n';
                break;
        }

        Point point = null; //INITIALIZE TO THE CURRENT AGENT POSITION
        MoveSequence out = goInOneDirection(point, random);

        return out;
    }


    public MoveSequence goInOneDirection(Point point, char direction){
        MoveSequence sequence = null;
        while(point != null){
            for(Point p : known.getNeighbors(point)){
                if(MoveSearch.getDirection(point, p) == direction){
                    MoveSearch search = new MoveSearch(point, p, Mapped.getInstance().known);
                    sequence = search.AStar();
                }
            }
        }

        return sequence.movesLeft()? think(sp) : sequence;//if we didn't find a good sequence, call think() again.
    }


}
