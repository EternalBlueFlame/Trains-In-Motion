package trains.items;

import cpw.mods.fml.common.registry.GameRegistry;
import ibxm.Player;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import scala.reflect.internal.Trees.This;
import trains.TrainsInMotion;
import trains.blocks.Oil;

public class OilBucket extends Item{
	public static BlockFluidClassic fluid;
	Fluid Oil = new Oil("Oil").setViscosity(6000);
	public OilBucket() {
		FluidRegistry.registerFluid(Oil);
		fluid = new BlockFluidClassic(Oil, Material.water);
		GameRegistry.registerBlock(fluid, "Test");
	}
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int posX, int posY, int posZ, int blockSide, float pointToRayX, float pointToRayY, float pointToRayZ){
		//TODO Find the face of the block pointed at
		switch(blockSide){
		case 0:
			world.setBlock(posX, posY - 1, posZ, fluid);
			break;
		case 1:
			world.setBlock(posX, posY + 1, posZ, fluid);
			break;
		case 2:
			world.setBlock(posX, posY, posZ - 1, fluid);
			break;
		case 3:
			world.setBlock(posX, posY, posZ + 1, fluid);
		case 4:
			world.setBlock(posX - 1, posY, posZ, fluid);
		case 5:
			world.setBlock(posX + 1, posY, posZ, fluid);
		default:
			world.setBlock(posX, posY, posZ, fluid);
		}
		
		return false;
		
	}
}