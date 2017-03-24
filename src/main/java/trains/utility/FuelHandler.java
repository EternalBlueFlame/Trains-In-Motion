package trains.utility;


import cpw.mods.fml.common.IFuelHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fluids.FluidRegistry;
import trains.entities.EntityTrainCore;
import trains.entities.GenericRailTransport;

/**
 * <h1>Fuel management for trains</h1>
 * used to process fuel addition and consumption.
 *
 * @author Eternal Blue Flame
 */
public class FuelHandler implements IFuelHandler{

	public int fuel=0;


	/**
	 * <h2>Register burnables with minecraft</h2>
	 * use getBurnTime to register a custom burnable that will work with other mods and the base game.
     */
	@Override
	public int getBurnTime(ItemStack fuel) {
		return 0;
	}



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
	 */
	public static boolean isWater(ItemStack item, GenericRailTransport transport){
		switch (transport.getType()){
			case STEAM: case NUCLEAR_STEAM: {return item != null && item.getItem() == Items.water_bucket;}

		}
		return false;
	}

	/**
	 * <h2>get liquid fuel value</h2>
	 * @return the value in millibuckets for the item
	 * non-liquids like redstone are managed here because all non-steam trains use a water tank to define their fuel amount.
	 * TODO: direct use of liquids seems kind of redundant since they can't be extracted and we deal with the types ourselves, perhaps there should be a replacement will less overhead.
	 */
	public static int waterValue(ItemStack itemStack){
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
	 * <h2> Data Syncing and Saving </h2>
	 * this is explained in
	 * @see GenericRailTransport#readSpawnData(ByteBuf)
	 */
	public void readEntityFromNBT(NBTTagCompound tag) {
		this.fuel = tag.getInteger("fuelhandler");
	}
	public void writeEntityToNBT(NBTTagCompound tag) {
		tag.setInteger("fuelhandler", this.fuel);

	}

	/**
	 * <h2>Fuel management</h2>
	 * this function manages the fuel for the train so we can keep it out of the train class to organize code bulk.
	 */
	public void ManageFuel(EntityTrainCore cart){

		switch (cart.getType()) {
			/**
			 * <h3> Steam Fuel Management</h3>
			 *
			 *
			 * first manage fuel slots for water buckets and burnables.
			 * TileEntityFurnace.getItemBurnTime will tell us how much fuel the item gives, assuming it is a valid fuel item
			 * After manage actual fuel consumption.
			 */
			case STEAM: {
				if (isFuel(cart.inventory.getStackInSlot(0), cart) && fuel + TileEntityFurnace.getItemBurnTime(cart.inventory.getStackInSlot(0)) < cart.getMaxFuel()) {
					//if the first inventory slot contains a burnable listed in our supported burnables, then remove it and add it's value to our fuel.
					fuel += TileEntityFurnace.getItemBurnTime(cart.inventory.getStackInSlot(0));
					if (!cart.isCreative) {
						cart.inventory.decrStackSize(0, 1);
					}
				}

				//if the second slot contains a water bucket, add the contents of the water bucket to our tank and then place an empty bucket in the inventory
				if (isWater(cart.inventory.getStackInSlot(1), cart) &&
						cart.getTank().addFluid(FluidRegistry.WATER, waterValue(cart.inventory.getStackInSlot(1)),true, cart) && !cart.isCreative) {
					cart.inventory.decrStackSize(1,1);
					cart.inventory.addItem(new ItemStack(Items.bucket));
				}

				//be sure there is fuel before trying to consume it
				if (fuel > 0) {
					/*
					* add steam from burning to the steam tank.
					* steam is equal to water used, minus a small percentage to compensate for impurities in the water that dont transition to steam,
					* the amount of water used is generated from heat which is one part fuel 2 parts air, with more fuel burning more heat is created, but this only works to a point.
					* steam beyond the max point of storage is considered to be expelled through a failsafe.
					* TODO: this and the following calculations are still incorrect
					*/
					int steam = Math.round((fuel*0.0025f)/ cart.getMaxFuel());
					if (cart.getTank().drainFluid(steam,true, cart)) {
						fuel -= 50; //a lava bucket lasts 1000 seconds, so burnables are processed at 20000*0.05 a second
						cart.getTank().addFluid(FluidRegistry.WATER, MathHelper.floor_float(steam*0.9f), false, cart);
					} else if (!cart.isCreative){
						cart.worldObj.createExplosion(cart, cart.posX, cart.posY, cart.posZ, 5f, false);
						cart.dropItem(cart.getItem(), 1);
						HitboxHandler.destroyTransport(cart);
						break;
					}

				}
				if (cart.getTankFluid(false) >0){
					//steam is expelled through the pistons to push them back and forth, but even when the accelerator is off, a degree of steam is still escaping.
					cart.getTank().drainFluid(5+(10*cart.accelerator),false, cart);
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
			 * this is one of the simpler fuel managements,
			 * We simply check for if the fuel slot is valid, add fluid to the tank, and decrease the itemstack size.
			 * After that we manage fuel consumption at a set rate based on base use and and current accelerator state
			 */
			case ELECTRIC: case MAGLEV: {
				//add redstone to the fuel tank
				if (isWater(cart.inventory.getStackInSlot(0), cart) &&
						cart.getTank().addFluid(FluidRegistry.WATER, waterValue(cart.inventory.getStackInSlot(1)),true, cart) && !cart.isCreative) {
					cart.inventory.decrStackSize(0,1);
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
