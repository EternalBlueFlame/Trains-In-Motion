package ebf.tim.utility;


import ebf.tim.entities.EntityTrainCore;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.registry.NBTKeys;
import io.netty.buffer.ByteBuf;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * <h1>Fuel management for trains</h1>
 * used to process fuel addition and consumption.
 *
 * @author Eternal Blue Flame
 */
public class FuelHandler{

	/**the main fuel variable used by most trains*/
	public int fuel =0;
	/**the steam tank, used for steam and nuclear steam trains*/
	public int steamTank=0;

	/**
	 * <h2>check if an item is a usable fuel</h2>
	 * returns if the train can consume the fuel or not.
	 * TiM only fuels and support for 3rd party/vanilla fuels are managed here
     */
	public static boolean isFuel(ItemStack item, GenericRailTransport transport){
		switch (transport.getType()){
			case STEAM: {return item != null && TileEntityFurnace.getItemBurnTime(item) !=0;}
			case ELECTRIC:{return item != null && item.getItem() == Items.redstone;}

		}
		return false;
	}

	/**
	 * <h2>check if an item is a usable water source item</h2>
	 * @return if the train can add the water to the boiler or not.
	 * TiM only water items and support for 3rd party/vanilla fuels are managed here
	 * TODO: need support for 3rd party water items that contain dynamic amounts.
	 */
	public static boolean isWater(ItemStack item, GenericRailTransport transport){
		switch (transport.getType()){
			case STEAM: case NUCLEAR_STEAM: {return item != null && item.getItem() == Items.water_bucket;}

		}
		return false;
	}

	/**
	 * <h2>get value from Item</h2>
	 * this is intended to get a value from items that normally don't have one, for instance the fuel value of redstone, or the mb of water in a bucket.
	 * @return the value int for the item
	 * non-liquids like redstone are managed here because all non-steam trains use a water tank to define their fuel amount.
	 */
	public static int getValue(ItemStack itemStack){
		if (itemStack != null) {
			if (itemStack.getItem() == Items.water_bucket) {
				return 1000;
			} else if (itemStack.getItem() == Items.redstone){
				return 250;
			}

		}

		return 0;
	}


	/**
	 * <h2>Fuel management</h2>
	 * this function manages the fuel for the train so we can keep it out of the train class to organize code bulk.
	 */
	public void manageFuel(EntityTrainCore cart){

		switch (cart.getType()) {
			/**
			 * <h3> Steam Fuel Management</h3>
			 *
			 *
			 */
			case STEAM: {
				//consume the burnable, if the first slot contains one. we use getItemBurnTime to detect if it's a burnable and get it's value, this allows for support of 3rd party mods.
				if (isFuel(cart.getStackInSlot(0), cart) && fuel + TileEntityFurnace.getItemBurnTime(cart.getStackInSlot(0)) < cart.getMaxFuel()) {
					fuel += TileEntityFurnace.getItemBurnTime(cart.getStackInSlot(0));
					if (!cart.isCreative) {
						cart.decrStackSize(0, 1);
					}
				}

				//consume the water, if the second slot contains a water bucket, also places an empty bucket in inventory
				if (isWater(cart.getStackInSlot(1), cart) &&
						cart.fill(ForgeDirection.UNKNOWN, new FluidStack(FluidRegistry.WATER, getValue(cart.getStackInSlot(1))), false) == getValue(cart.getStackInSlot(1))) {
					cart.fill(ForgeDirection.UNKNOWN, new FluidStack(FluidRegistry.WATER, getValue(cart.getStackInSlot(1))), true);
					if (!cart.isCreative) {
						cart.decrStackSize(1, 1);
						cart.addItem(new ItemStack(Items.bucket));
					}
				}

				//be sure there is fuel before trying to consume it
				if (fuel > 0) {
					/*
					* add steam from burning to the steam tank.
					* steam is equal to water used, minus a small percentage to compensate for impurities in the water that don't transition to steam,
					* the amount of water used is generated from heat which is one part fuel 2 parts air, with more fuel burning more heat is created, but this only works to a point.
					* steam beyond the max point of storage is considered to be expelled through a failsafe.
					* TODO: this and the following calculations are still incorrect
					* NOTE: in TiM we need to remember steam and fluid are housed in the same tank, and calculate based on that. should also throw in calcium buildup for non-distilled water. TC however isn't so advanced
					*/
					int steam = Math.round((fuel *0.0025f)/ cart.getTankAmount());
					if (cart.drain(ForgeDirection.UNKNOWN, steam,true).amount == steam) {
						fuel -= 50; //a lava bucket lasts 1000 seconds, so burnables are processed at 20000*0.05 a second
						steamTank += MathHelper.floor_float(steam*0.9f);
					} else if (!cart.isCreative){
						cart.worldObj.createExplosion(cart, cart.posX, cart.posY, cart.posZ, 5f, false);
						cart.dropItem(cart.getItem(), 1);
						cart.attackEntityFromPart(null, new EntityDamageSource("overheat", cart),1);
						break;
					}

				}
				if (steamTank >0){
					//steam is expelled through the pistons to push them back and forth, but even when the accelerator is off, a degree of steam is still escaping.
					steamTank -=5+(10*cart.accelerator);
				}

				break;
			}
			/**
			 * <h3> Diesel Fuel Management</h3>
			 *
			 *
			 */
			case DIESEL: {
				break;
			}
			/**
			 * <h3> HydroElectric Fuel Management</h3>
			 *
			 *
			 */
			case HYDROGEN_DIESEL: {
				break;
			}
			/**
			 * <h3> Electric and maglev Fuel Management</h3>
			 *
			 *
			 */
			case ELECTRIC: case MAGLEV: {
				//add redstone to the fuel tank
				if (isWater(cart.getStackInSlot(0), cart) &&
						getValue(cart.getStackInSlot(1))+ fuel < cart.getMaxFuel() && !cart.isCreative) {
					fuel +=getValue(cart.getStackInSlot(1));
					cart.decrStackSize(0,1);
				}

				//use stored energy
				if (cart.isRunning){
					//electric trains run at a generally set rate which is multiplied at the square of speed.
					//TODO: this may need re-balancing to keep it realistic.
					fuel -= 1 + MathHelper.sqrt_float(Math.copySign(cart.accelerator, 1)*5);
				}
				break;
			}
			/**
			 * <h3> NuclearSteam Fuel Management</h3>
			 *
			 *
			 */
			case NUCLEAR_STEAM: {
				break;
			}
			/**
			 *
			 *
			 * <h3> Nuclear Electric Fuel Management</h3>
			 *
			 *
			 */
			case NUCLEAR_ELECTRIC: {
				break;
			}
			default:{break;}
		}

	}

} 
