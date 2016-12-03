package trains.worldgen;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import trains.registry.GenericRegistry;

import java.util.Random;

public class OreGen implements IWorldGenerator{
	/**
	 * <h2>Ore definitions</h2>
	 * we define the ores here for better performance.
	 * normally generating this every call would be inefficient but having it defined in a static function changes the variable bindings to be significantly more efficient.
     */
	private static WorldGenMinable oilMine(Random random){return new WorldGenMinable(GenericRegistry.blockFluidOil, 4 + random.nextInt(30 - 4), Blocks.stone);}


	/**
	 *<h2>Ore-gen management</h2>
	 * handles generation of ores and fluids in the world.
	 * courtesy of Justice.
     */
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.dimensionId){
			case 0: {
				for(int i = 0; i < 3; i++) {
					oilMine(random).generate(world, random, chunkX * 16 + random.nextInt(16), random.nextInt(27) + 3, chunkZ * 16 + random.nextInt(16));
				}
				break;
			}
			default: {
				break;
			}
		}
	}

}
