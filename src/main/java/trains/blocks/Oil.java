package trains.blocks;

import net.minecraftforge.fluids.Fluid;
import trains.registry.GenericRegistry;

/**
 * <h2> Oil Block</h2>
 * this bock is the in-world block for our implementation of oil
 * @see GenericRegistry
 */
public class Oil extends Fluid {

	public Oil(String fluidName) {
		super(fluidName);
	}

}
