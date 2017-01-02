package trains.utility;


import cpw.mods.fml.common.IFuelHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
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
		if (itemStack != null && itemStack.getItem() == Items.water_bucket){
			return 1000;
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
					cart.inventory.decrStackSize(0, 1);
				}

				//if the second slot contains a water bucket, add the contents of the water bucket to our tank and then place an empty bucket in the inventory
				if (isWater(cart.inventory.getStackInSlot(1), cart) &&
						cart.getTank().addFluid(FluidRegistry.WATER, waterValue(cart.inventory.getStackInSlot(1)),true)) {
					cart.inventory.decrStackSize(1,1);
					cart.inventory.addItem(new ItemStack(Items.bucket));
				}

				//be sure there is fuel before trying to consume it
				if (fuel > 0) {
					//add steam from burning to the steam tank.
					int steam = Math.round(fuel * 0.01f);
					if (cart.getTank().drainFluid(steam,true)) {
						fuel -= 5;
						cart.getTank().addFluid(FluidRegistry.WATER, steam/3, false);
					} else {
						cart.worldObj.createExplosion(cart, cart.posX, cart.posY, cart.posZ, 5f, false);
						cart.dropItem(cart.getItem(), 1);
						HitboxHandler.destroyTransport(cart);
						break;
					}

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
