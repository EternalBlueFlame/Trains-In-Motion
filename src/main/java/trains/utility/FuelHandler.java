package trains.utility;


import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import trains.entities.EntityTrainCore;
import trains.entities.MinecartExtended;

import java.util.Arrays;
import java.util.List;


public class FuelHandler {
    private static final int[] value = new int[]{20, 3, 50, 100, 500, 3};
    private static final List<Item> steamFuel = Arrays.asList(Item.getItemFromBlock(Blocks.planks), Items.stick, Items.coal, Items.blaze_rod, Item.getItemFromBlock(Blocks.coal_block), Item.getItemFromBlock(Blocks.sapling));

    public static void ManageFuel(EntityTrainCore cart){

        switch (cart.trainType) {
            /**
             *
             *
             * <h1> Steam Fuel Management</h1>
             *
             *
             * first manage fuel slots for water buckets and burnables.
             * After manage actual fuel consumption.
             */
            case 1: {
                //if the first inventory slot contains a burnable listed in our supported burnables, then remove it and add it's value to our fuel.
                if (steamFuel.contains(cart.inventory.get(0).getItem()) && cart.furnaceFuel + value[steamFuel.indexOf(cart.inventory.get(0).getItem())+1] < cart.maxFuel){
                    cart.furnaceFuel += value[steamFuel.indexOf(cart.inventory.get(0).getItem()) + 1];
                    if (cart.inventory.get(0).stackSize >1) {
                        cart.inventory.get(0).stackSize = cart.inventory.get(0).stackSize - 1;
                    } else{
                        cart.inventory.set(0, null);
                    }
                }
                //if the second slot contains a water bucket, add the contents of the water bucket to our tank and then place an empty bucket in the inventory
                if (cart.inventory.get(1).getItem() == Items.water_bucket && cart.tank[0].getFluidAmount() <= cart.tank[0].getCapacity()-1000){
                    cart.tank[0].fill(new FluidStack(cart.tank[1].getFluid().getFluid(), cart.tank[1].getFluidAmount() + 1000), true);
                    if (cart.inventory.get(1).stackSize >1) {
                        cart.inventory.get(1).stackSize = cart.inventory.get(1).stackSize - 1;
                    } else{
                        cart.inventory.set(1, null);
                    }
                    cart.addItems(0, new ItemStack(Items.water_bucket));
                }

                //be sure there is fuel before trying to consume it
                if (cart.furnaceFuel >=0){
                    //add steam from burning to the steam tank.
                    int steam = Math.round(cart.furnaceFuel *0.1f);
                    if (cart.tank[0].getFluidAmount() >=steam){
                        cart.furnaceFuel -=5;
                        cart.tank[0].drain(steam, true);
                        cart.tank[1].fill(new FluidStack(cart.tank[1].getFluid().getFluid(), cart.tank[1].getFluidAmount() + steam), true);
                    } else {
                        cart.worldObj.createExplosion(cart, cart.posX, cart.posY, cart.posZ, 5f,false);
                        break;
                    }

                    //drain used steam from the tank TODO define amount used based on the current accelerator of the train
                    if(cart.tank[1].getFluidAmount() >= (cart.tank[1].getCapacity()*0.5f)){
                        cart.tank[1].drain(1, true);
                    }

                }
                break;
            }
            /**
             *
             *
             * <h1> Diesel Fuel Management</h1>
             *
             *
             */
            case 2: {
                break;
            }
            /**
             *
             *
             * <h1> HydroElectric Fuel Management</h1>
             *
             *
             */
            case 3: {
                break;
            }
            /**
             *
             *
             * <h1> Electric Fuel Management</h1>
             *
             *
             */
            case 4: {
                break;
            }
            /**
             *
             *
             * <h1> NuclearSteam Fuel Management</h1>
             *
             *
             */
            case 5: {
                break;
            }
            /**
             *
             *
             * <h1> Nuclear Electric Fuel Management</h1>
             *
             *
             */
            case 6: {
                break;
            }
            /**
             *
             *
             * <h1> Maglev Fuel Management</h1>
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
