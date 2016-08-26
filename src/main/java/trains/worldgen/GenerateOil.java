package trains.worldgen;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import trains.registry.BlockRegistry;

public class GenerateOil implements IWorldGenerator{

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		switch(world.provider.dimensionId){
			case 0: {
				WorldGenMinable minable = new WorldGenMinable(BlockRegistry.blockFluidOil, 4 + random.nextInt(30 - 4), Blocks.stone);
				for(int i = 0; i < 3; i++) {
					minable.generate(world, random, chunkX * 16 + random.nextInt(16), random.nextInt(27) + 3, chunkZ * 16 + random.nextInt(16));
				}
				break;
			}
			default: {
				break;
			}
		}
	}

}
