package trains.blocks;

import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.block.BlockAir;

/**
 * <h1>Lamp Block</h1>
 * This is more of a tool for instanceof checks, it allows client to manage lights on the trains
 * Even though it's a cosmetic block, it needs to extend block air, rather than just block, so we can't use the BlockDynamic class for this.
 * @see trains.utility.ClientProxy#onTick(TickEvent.ClientTickEvent)
 * @author Eternal Blue Flame
 */
public class LampBlock extends BlockAir {

    public LampBlock(){
    }

}
