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

public class FuelHandler implements IFuelHandler{

	public int fuel=0;


	/**
	 * <h2>Register burnables with minecraft</h2>
	 * use getBurnTime to register a burnable that will work with other mods. this will need to be
	 * use getCustom to register a burnable that will only work with TiM
     */
	@Override
	public int getBurnTime(ItemStack fuel) {
		return 0;
	}

	public int getCustom(ItemStack fuel){
		return 0;
	}


	public static boolean isFuel(ItemStack item, GenericRailTransport transport){
		switch (transport.getType()){
			case STEAM: {return item != null && TileEntityFurnace.getItemBurnTime(item) !=0;}
			case ELECTRIC:{return item != null && item.getItem() == Items.redstone;}

		}
		return false;
	}

	public static boolean isWater(ItemStack item, GenericRailTransport transport){
		switch (transport.getType()){
			case STEAM: {return item != null && item.getItem() == Items.water_bucket;}

		}
		return false;
	}

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
	 * this class manages the fuel for the train so we can keep it out of the train class to organize code bulk.
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
						cart.tanks.addFluid(FluidRegistry.WATER, waterValue(cart.inventory.getStackInSlot(1)),true) && !cart.isCreative) {
					cart.inventory.decrStackSize(1,1);
					cart.inventory.addItem(new ItemStack(Items.bucket));
				}

				//be sure there is fuel before trying to consume it
				if (fuel > 0) {
					//add steam from burning to the steam tank.
					//steam is equal to water used, but generated from heat which is one part fuel 2 parts air, with more fuel burning more heat is created, but this only works to a point.
					int steam = Math.round((fuel*0.025f)/ cart.getMaxFuel());
					if (cart.tanks.drainFluid(steam,true)) {
						fuel -= 50; //a lava bucket lasts 1000 seconds, so burnables are processed at 20000*0.05 a second
						cart.tanks.addFluid(FluidRegistry.WATER, steam, false);
					} else if (!cart.isCreative){
						cart.worldObj.createExplosion(cart, cart.posX, cart.posY, cart.posZ, 5f, false);
						cart.dropItem(cart.getItem(), 1);
						HitboxHandler.destroyTransport(cart);
						break;
					}

				}
				if (cart.tanks.getTank(false).getFluidAmount()>0){
					//steam is expelled through the pistons to push them back and forth, but even when the accelerator is off, a degree of steam is still escaping.
					cart.tanks.drainFluid(5+(10*cart.accelerator),false);
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
			 * <h3> Electric Fuel Management</h3>
			 *
			 *
			 */
			case ELECTRIC: {
				//add redstone to the fuel tank
				if (isWater(cart.inventory.getStackInSlot(1), cart) &&
						cart.tanks.addFluid(FluidRegistry.WATER, waterValue(cart.inventory.getStackInSlot(1)),true) && !cart.isCreative) {
					cart.inventory.decrStackSize(1,1);
					cart.inventory.addItem(new ItemStack(Items.bucket));
				}
				//use stored energy
				if (cart.isRunning){
					//electric trains run at a generally set rate which is multiplied at the square of speed.
					fuel -= 1 + MathHelper.sqrt_float(cart.accelerator*5);
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
			/**
			 *
			 *
			 * <h3> Maglev Fuel Management</h3>
			 *
			 *
			 */
			case MAGLEV: {
				break;
			}
			default:{break;}
		}

	}

} 
