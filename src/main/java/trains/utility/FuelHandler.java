package trains.utility;


import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fluids.FluidRegistry;
import trains.entities.EntityTrainCore;
import trains.entities.GenericRailTransport;

public class FuelHandler implements IFuelHandler{


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
			case 1: {return item != null && TileEntityFurnace.getItemBurnTime(item) !=0;}

		}
		return false;
	}

	public static boolean isWater(ItemStack item, GenericRailTransport transport){
		switch (transport.getType()){
			case 1: {return item != null && item.getItem() == Items.water_bucket;}

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
	 * <h2>Fuel management</h2>
	 * this class manages the fuel for the train so we can keep it out of the train class to organize code bulk.
	 */
	public static void ManageFuel(EntityTrainCore cart){

		switch (cart.getType()) {
			/**
			 * <h3> Steam Fuel Management</h3>
			 *
			 *
			 * first manage fuel slots for water buckets and burnables.
			 * TileEntityFurnace.getItemBurnTime will tell us how much fuel the item gives, assuming it is a valid fuel item
			 * After manage actual fuel consumption.
			 */
			case 1: {
				if (isFuel(cart.inventory.getStackInSlot(0), cart) && cart.furnaceFuel + TileEntityFurnace.getItemBurnTime(cart.inventory.getStackInSlot(0)) < cart.getMaxFuel()) {
					//if the first inventory slot contains a burnable listed in our supported burnables, then remove it and add it's value to our fuel.
					cart.furnaceFuel += TileEntityFurnace.getItemBurnTime(cart.inventory.getStackInSlot(0));
					cart.inventory.decrStackSize(0, 1);
				}

				//if the second slot contains a water bucket, add the contents of the water bucket to our tank and then place an empty bucket in the inventory
				if ( isWater(cart.inventory.getStackInSlot(1), cart) && cart.tank.canFill(waterValue(cart.inventory.getStackInSlot(1)),0)) {
					cart.tank.addFluid(FluidRegistry.WATER, waterValue(cart.inventory.getStackInSlot(1)),0);
					cart.inventory.decrStackSize(1,1);
					cart.inventory.addItem(new ItemStack(Items.bucket));
				}

				//be sure there is fuel before trying to consume it
				if (cart.furnaceFuel > 0) {
					//add steam from burning to the steam tank.
					int steam = Math.round(cart.furnaceFuel * 0.1f);
					if (cart.tank.canDrain(steam,1)) {
						cart.furnaceFuel -= 5;
						cart.tank.drainFluid(steam, 1);
					} else {
						cart.worldObj.createExplosion(cart, cart.posX, cart.posY, cart.posZ, 5f, false);
						cart.dropItem(cart.getItem(), 1);
						cart.worldObj.removeEntity(cart);
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
			case 2: {
				break;
			}
			/**
			 * <h3> HydroElectric Fuel Management</h3>
			 *
			 *
			 */
			case 3: {
				break;
			}
			/**
			 * <h3> Electric Fuel Management</h3>
			 *
			 *
			 */
			case 4: {
				break;
			}
			/**
			 * <h3> NuclearSteam Fuel Management</h3>
			 *
			 *
			 */
			case 5: {
				break;
			}
			/**
			 *
			 *
			 * <h3> Nuclear Electric Fuel Management</h3>
			 *
			 *
			 */
			case 6: {
				break;
			}
			/**
			 *
			 *
			 * <h3> Maglev Fuel Management</h3>
			 *
			 *
			 */
			case 7: {
				break;
			}
			default:{break;}
		}

	}

} 
