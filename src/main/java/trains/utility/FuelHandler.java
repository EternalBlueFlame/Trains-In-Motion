package trains.utility;


import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidStack;
import trains.entities.MinecartExtended;

import java.util.Arrays;
import java.util.List;


public class FuelHandler {
    private static int[] value = new int[]{20, 3, 50, 100, 500, 3};
    private static List<Item> steamFuel = Arrays.asList(Item.getItemFromBlock(Blocks.planks), Items.stick, Items.coal, Items.blaze_rod, Item.getItemFromBlock(Blocks.coal_block), Item.getItemFromBlock(Blocks.sapling));


    public FuelHandler(MinecartExtended cart){
        //manage fuels, no point in doing a check if this should be run or not, due to the overall efficiency of switch statements.
        switch (cart.trainType) {
            //steam
            case 1: {
                //if there is a burnable item in the fuel slot, and it can store more burnable fuel
                if (steamFuel.contains(cart.inventory[0].getItem()) && cart.furnaceFuel + value[steamFuel.indexOf(cart.inventory[0].getItem())+1] < cart.maxFuel){
                    //add the burnable fuel
                    cart.furnaceFuel += value[steamFuel.indexOf(cart.inventory[0].getItem()) + 1];
                    //decrease the stack size, or set slot to null if there will be none left.
                    if (cart.inventory[0].stackSize >1) {
                        cart.inventory[0].stackSize = cart.inventory[0].stackSize - 1;
                    } else{
                        cart.inventory[0] = null;
                    }
                }
                //consume water bucket from second slot
                if (cart.inventory[1].getItem() == Items.water_bucket && cart.tank[0].getFluidAmount() <= cart.tank[0].getCapacity()-1000){
                    cart.tank[0].fill(new FluidStack(cart.tank[1].getFluid().getFluid(), cart.tank[1].getFluidAmount() + 1000), true);
                    //decrease the stack size, or set slot to null if there will be none left.
                    if (cart.inventory[1].stackSize >1) {
                        cart.inventory[1].stackSize = cart.inventory[1].stackSize - 1;
                    } else{
                        cart.inventory[1] = null;
                    }
                }

                //if the cart has fuel, process the fuel
                if (cart.furnaceFuel >=0){
                    //generate the steam based on the fuel in the furnace
                    int steam = Math.round(cart.furnaceFuel *0.1f);
                    //if there is sufficient water to turn into steam, then do so, and consume some fuel.
                    if (cart.tank[0].getFluidAmount() >=steam){
                        cart.furnaceFuel -=5;
                        cart.tank[0].drain(steam, true);
                        cart.tank[1].fill(new FluidStack(cart.tank[1].getFluid().getFluid(), cart.tank[1].getFluidAmount() + steam), true);
                    } else {
                        //if there was not enough water, explode
                        cart.worldObj.createExplosion(cart, cart.posX, cart.posY, cart.posZ, 5f,false);
                        break;
                    }

                    //drain steam based on train speed
                    if(cart.tank[1].getFluidAmount() >= (cart.tank[1].getCapacity()*0.5f)){
                        cart.tank[1].drain(Math.round(cart.maxSpeed), true);
                    }

                    //if train is off, drain steam from it cooling
                    if (cart.tank[1].getFluidAmount() >=5){
                        cart.tank[1].drain(5, true);
                    } else if (cart.tank[1].getFluidAmount() >0){
                        cart.tank[1].drain(cart.tank[1].getFluidAmount(), true);
                    }

                }
                break;
            }
            //diesel
            case 2: {

                break;
            }
            //electric
            case 3: {

                break;
            }
            //hydrogen
            case 4: {

                break;
            }
            //nuclear
            case 5: {

                break;
            }
            //case for it not being a train
            default:{break;}
        }

    }

}
