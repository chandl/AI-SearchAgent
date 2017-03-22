import helper.MaedenLog;
import maeden.SensoryPacket;

public abstract class AgentLogic {
    public static MaedenLog log = MaedenLog.getInstance();
    public abstract MoveSequence think(SensoryPacket sp);
}
