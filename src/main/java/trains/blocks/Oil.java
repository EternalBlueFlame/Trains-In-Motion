package trains.blocks;

import net.minecraftforge.fluids.Fluid;
import trains.registry.GenericRegistry;

/**
 * <h1>Oil Block</h1>
 * this bock is the in-world block for our implementation of oil
 * @see GenericRegistry
 * @author Justice
 */
public class Oil extends Fluid {

	public Oil(String fluidName) {
		super(fluidName);
	}

}
