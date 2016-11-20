package trains.utility;


import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import trains.entities.EntityBogie;
import trains.entities.EntityTrainCore;

import java.util.Arrays;
import java.util.List;


public class Utility {
    /**
     * <h2>Fuel registry</h2>
     * TODO: see if we can get this information from minecraft itself
     * TODO: create a new sub-class to hold both the item value and the fuel use value, to keep it more organized.
     */
    private static final int[] value = new int[]{20, 3, 50, 100, 500, 3};
    private static final List<Item> steamFuel = Arrays.asList(Item.getItemFromBlock(Blocks.planks), Items.stick, Items.coal, Items.blaze_rod, Item.getItemFromBlock(Blocks.coal_block), Item.getItemFromBlock(Blocks.sapling));

    public static final float radian = (float) Math.PI / 180.0F;


    /**
     * <h2>Vanilla Track Overrrides</h2>
     * TODO: need ZND API for ITrackBase
     * TODO: other:
     * @see EntityBogie#minecartMove()
     *
     * we override some vanilla track detection so that way it's more efficient and can support rails from other mods.
     */
    public static boolean isRailBlockAt(World world, int x, int y, int z) { // Can later be substituted for BlockPos
        return (/*world.getTileEntity(x, y, z) instanceof ITrackBase ||*/ world.getBlock(x, y, z) instanceof BlockRailBase);
    }
    public static boolean isRailBlockAt(Block block) { // Can later be substituted for BlockPos
        return (/*world.getTileEntity(x, y, z) instanceof ITrackBase ||*/ block instanceof BlockRailBase);
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
             * After manage actual fuel consumption.
             */
            case 1: {
                //if the first inventory slot contains a burnable listed in our supported burnables, then remove it and add it's value to our fuel.
                if (steamFuel.contains(cart.inventory.get(0).getItem()) && cart.furnaceFuel + value[steamFuel.indexOf(cart.inventory.get(0).getItem())+1] < cart.getMaxFuel()){
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
