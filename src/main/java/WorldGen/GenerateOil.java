package WorldGen;

import java.util.Random;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import trains.blocks.Oil;
import trains.items.OilBucket;

public class GenerateOil implements IWorldGenerator{

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		// TODO Auto-generated method stub
		switch(world.provider.dimensionId){
		case 1:
			break;
		case 0:
			generateOverworld(world, random, chunkX, chunkZ);
			break;
		case -1:
			break;
		}
	}
	
	public void generateOverworld(World world, Random random, int x, int z){
		
		generateOres(OilBucket.fluid, world, random, x, z, 4, 30, 3, 3, 30, Blocks.stone);
		
	}
	
	public void generateOres(Block block, World world, Random random, int chunkX, int chunkZ,
			int minvienSize, int maxvienSize, int chance, int minY, int maxY, Block Inside){
		int VienSize = minvienSize + random.nextInt(maxvienSize - minvienSize);
		int heightRange = maxY - minY;
		WorldGenMinable minable = new WorldGenMinable(block, VienSize, Inside);
		for(int i = 0; i < chance; i++){
			int xRand = chunkX * 16 + random.nextInt(16);
			int yRand = random.nextInt(heightRange) + minY;
			int zRand = chunkZ * 16 + random.nextInt(16);
			
			minable.generate(world, random, xRand, yRand, zRand);
			
		}
		
	} // Min: 10 Max: 100

}
