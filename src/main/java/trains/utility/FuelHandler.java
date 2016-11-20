package trains.utility;


import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import trains.entities.EntityTrainCore;

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
			 * GameRegistry.getFuelValue will tell us how much fuel the item gives, assuming it is a valid fuel item
			 * After manage actual fuel consumption.
			 */
			case 1: {
				if (GameRegistry.getFuelValue(cart.inventory.get(0)) > 0) {
					//if the first inventory slot contains a burnable listed in our supported burnables, then remove it and add it's value to our fuel.
					if (cart.furnaceFuel + GameRegistry.getFuelValue(cart.inventory.get(0)) < cart.getMaxFuel()) {
						cart.furnaceFuel += GameRegistry.getFuelValue(cart.inventory.get(0));
						if (cart.inventory.get(0).stackSize > 1) {
							cart.inventory.get(0).stackSize = cart.inventory.get(0).stackSize - 1;
						} else {
							cart.inventory.set(0, null);

						}
					}
					//if the second slot contains a water bucket, add the contents of the water bucket to our tank and then place an empty bucket in the inventory
					if (cart.inventory.get(1).getItem() == Items.water_bucket && cart.tank[0].getFluidAmount() <= cart.tank[0].getCapacity() - 1000) {
						cart.tank[0].fill(new FluidStack(cart.tank[1].getFluid().getFluid(), cart.tank[1].getFluidAmount() + 1000), true);
						if (cart.inventory.get(1).stackSize > 1) {
							cart.inventory.get(1).stackSize = cart.inventory.get(1).stackSize - 1;
						} else {
							cart.inventory.set(1, null);
						}
						cart.addItems(0, new ItemStack(Items.water_bucket));
					}

					//be sure there is fuel before trying to consume it
					if (cart.furnaceFuel >= 0) {
						//add steam from burning to the steam tank.
						int steam = Math.round(cart.furnaceFuel * 0.1f);
						if (cart.tank[0].getFluidAmount() >= steam) {
							cart.furnaceFuel -= 5;
							cart.tank[0].drain(steam, true);
							cart.tank[1].fill(new FluidStack(cart.tank[1].getFluid().getFluid(), cart.tank[1].getFluidAmount() + steam), true);
						} else {
							cart.worldObj.createExplosion(cart, cart.posX, cart.posY, cart.posZ, 5f, false);
							break;
						}

						//drain used steam from the tank TODO define amount used based on the current accelerator of the train
						if (cart.tank[1].getFluidAmount() >= (cart.tank[1].getCapacity() * 0.5f)) {
							cart.tank[1].drain(1, true);
						}

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
