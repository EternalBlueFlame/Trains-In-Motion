package trains.blocks;

import net.minecraftforge.fluids.Fluid;
import trains.registry.GenericRegistry;

/**
 * <h1>Oil Block</h1>
 * this bock is the in-world block for our implementation of oil
 * because it's a fluid and not a normal block, it has to extend Fluid and be it's own class, rather than be defined via BlockDynamic.
 * @see GenericRegistry
 * @author Justice
 */
public class Oil extends Fluid {

	public Oil(String fluidName) {
		super(fluidName);
	}

}
