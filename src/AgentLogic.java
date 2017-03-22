import maeden.SensoryPacket;

/**
 * AgentLogic - Agent's Logic Abstract Class
 * @author Chandler
 */
public abstract class AgentLogic {
    public abstract MoveSequence think(SensoryPacket sp);
}
