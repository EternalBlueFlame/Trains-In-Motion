package trains.utility;


import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidStack;
import trains.entities.EntityTrainCore;
import trains.entities.MinecartExtended;

import java.util.Arrays;
import java.util.List;


public class FuelHandler {
    private static int[] value = new int[]{20, 3, 50, 100, 500, 3};
    private static List<Item> steamFuel = Arrays.asList(Item.getItemFromBlock(Blocks.planks), Items.stick, Items.coal, Items.blaze_rod, Item.getItemFromBlock(Blocks.coal_block), Item.getItemFromBlock(Blocks.sapling));

    /**
     * defines the fuel handler used by trains
     * this is defined by the train type
     * @see EntityTrainCore
     *
     * @param cart the EntityTrainCore calling the fuel handler.
     */
    public FuelHandler(EntityTrainCore cart){

        switch (cart.trainType) {
            case 1: {
                /**
                 * if there is a burnable item in the fuel slot, and it can store more burnable fuel then aff the fuel based on the item and decrease the stack size.
                 * stack becomes null if size would reduce it to <1
                 *
                 * Next consume a water bucket, similar to the burnable fuel.
                 * TODO water bucket needs to add an empty bucket to inventory
                 *
                 */
                if (steamFuel.contains(cart.inventory[0].getItem()) && cart.furnaceFuel + value[steamFuel.indexOf(cart.inventory[0].getItem())+1] < cart.maxFuel){
                    cart.furnaceFuel += value[steamFuel.indexOf(cart.inventory[0].getItem()) + 1];
                    if (cart.inventory[0].stackSize >1) {
                        cart.inventory[0].stackSize = cart.inventory[0].stackSize - 1;
                    } else{
                        cart.inventory[0] = null;
                    }
                }
                if (cart.inventory[1].getItem() == Items.water_bucket && cart.tank[0].getFluidAmount() <= cart.tank[0].getCapacity()-1000){
                    cart.tank[0].fill(new FluidStack(cart.tank[1].getFluid().getFluid(), cart.tank[1].getFluidAmount() + 1000), true);
                    if (cart.inventory[1].stackSize >1) {
                        cart.inventory[1].stackSize = cart.inventory[1].stackSize - 1;
                    } else{
                        cart.inventory[1] = null;
                    }
                }

                /**
                 * this part we check if the fuel is larger than 0 and generate steam if it is.
                 * if there's enough water to maintain the steam output, then add it to the steam tank, otherwise, blow up the train.
                 *
                 * then drain the steam based on the train's current speed.
                 * if the train is off then drain the steam faster from cooling.
                 */
                if (cart.furnaceFuel >=0){
                    int steam = Math.round(cart.furnaceFuel *0.1f);
                    if (cart.tank[0].getFluidAmount() >=steam){
                        cart.furnaceFuel -=5;
                        cart.tank[0].drain(steam, true);
                        cart.tank[1].fill(new FluidStack(cart.tank[1].getFluid().getFluid(), cart.tank[1].getFluidAmount() + steam), true);
                    } else {
                        cart.worldObj.createExplosion(cart, cart.posX, cart.posY, cart.posZ, 5f,false);
                        break;
                    }

                    if(cart.tank[1].getFluidAmount() >= (cart.tank[1].getCapacity()*0.5f)){
                        cart.tank[1].drain(Math.round(cart.maxSpeed), true);
                    }

                    if (cart.tank[1].getFluidAmount() >=5){
                        cart.tank[1].drain(5, true);
                    } else if (cart.tank[1].getFluidAmount() >0){
                        cart.tank[1].drain(cart.tank[1].getFluidAmount(), true);
                    }

                }
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
            case 4: {
                break;
            }
            case 5: {
                break;
            }
            case 6: {
                break;
            }
            default:{break;}
        }

    }

}
