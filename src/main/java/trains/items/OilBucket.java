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
		world.setBlock(posX, posY + 1, posZ, fluid);
		
		return false;
		
	}
}