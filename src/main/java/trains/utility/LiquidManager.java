package trains.utility;


import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>fluid management</h1>
 * this is more of a replacement for the base game fluid management to better allow us to use multiple tanks of fluids.
 */
public class LiquidManager {
    private List<FluidTank> tanks = new ArrayList<FluidTank>();
    private List<Fluid> blacklistedFluids = new ArrayList<Fluid>();
    private List<Fluid> whitelistedFluids = new ArrayList<Fluid>();


    /**
     * <h2>initialization</h2>
     * sets the initial values of the class.
     * @param capacity the max fluid that can be stored in the respective tank.
     * @param list the list of fluids
     * @param whitelist is the list exclusive to the listed fluids?
     */
    public LiquidManager(int[]capacity, Fluid[] list, boolean whitelist){
        for (int i :capacity) {
            tanks.add(new FluidTank(i));
        }

        if (whitelist){
            whitelistedFluids = Arrays.asList(list);
        } else if (list.length>0){
            blacklistedFluids = Arrays.asList(list);
        }

    }


    /**
     * <h2>get tank capacity</h2>
     * @param tank the tank to check
     * @return the capacity of the tank
     */
    public int tankCapacity(int tank){
        if(tanks.size() >= tank) {
            return tanks.get(tank).getCapacity();
        } else{
            return 0;
        }
    }

    /**
     * <h2>get the current fluid amount</h2>
     * @param tank the tank to check.
     * @return the amount of fluid in the tank.
     */
    public int tankFluidAmount(int tank){
        if(tanks.size() >= tank && tanks.get(tank).getFluid() != null) {
            return tanks.get(tank).getFluidAmount();
        } else{
            return 0;
        }
    }


    /**
     * <h2>can the tank take more fluid</h2>
     * checks the defined tank for if it can store the intended amount of fluuid.
     *
     * @param amount amount of space to check for.
     * @param tank the tank to check.
     * @return if the tank can store the amount specified.
     */
    public boolean canFill(int amount, int tank){
        if(tanks.size() >= tank) {
            if (tanks.get(tank).getFluid() == null){
                return true;
            }
            System.out.println(tanks.get(tank).getFluidAmount());
            return tanks.get(tank).getFluidAmount() + amount < tanks.get(tank).getCapacity();
        } else{
            return false;
        }
    }


    /**
     * <h2>NBT saving</h2>
     * reads/writes the tank data to the world file.
     */
    public void writeToNBT(NBTTagCompound tag){
        for (FluidTank tempTank : tanks){
            tempTank.writeToNBT(tag);
        }
    }
    public void readFromNBT(NBTTagCompound tag){
        for (FluidTank tempTank : tanks){
            tempTank.readFromNBT(tag);
        }
    }


    /**
     * <h2>can the tank drain</h2>
     * checks if the tank can drain the amount specified.
     *
     * @param amount the amount to check.
     * @param tank the tank to check.
     * @return if the tank defined can drain the amount specified.
     */
    public boolean canDrain(int amount, int tank){
        if(tanks.size() >= tank && tanks.get(tank).getFluid() != null) {
            return tanks.get(tank).getFluidAmount() - amount > 0;
        } else{
            return false;
        }
    }

    /**
     * <h2>add fluid to a tank</h2>
     * attempts to add fluid to the tank of the defined amount and type.
     * @param fluid fluid to add to the tank.
     * @param amount the amount of fluid to add.
     * @param tank the tank to add it to.
     */
    public void addFluid(Fluid fluid, int amount, int tank){
        if(tanks.size() >= tank && isFluidValid(fluid) && tanks.get(tank).getFluidAmount() + amount < tanks.get(tank).getCapacity()){
            if(tanks.get(tank).getFluid() != null){
                amount += tanks.get(tank).getFluidAmount();
            }
            tanks.get(tank).setFluid(new FluidStack(fluid, amount));
            System.out.println(tanks.get(tank).getFluidAmount());
        }
    }

    /**
     * <h2>drain fluid from a tank</h2>
     * attempts to drain fluid from the tank of the defined amount and type.
     * @param amount the amount of fluid to drain.
     * @param tank the tank to drain it from.
     */
    public void drainFluid(int amount, int tank){
        if (tanks.size() >= tank && tanks.get(tank).getFluid() != null){
            if (tanks.get(tank).getFluid().amount >= amount){
                tanks.get(tank).getFluid().amount -= amount;
            } else {
                tanks.get(tank).getFluid().amount =0;
            }
            tanks.get(tank).getFluidAmount();
        }
    }

    /**
     * <h2>is fluid valid for tank</h2>
     * checks the tanks can take the fluid (this checks all the tanks).
     * @param fluid the fluid to check for
     * @return if the tanks can take the fluid defined.
     */
    private boolean isFluidValid(Fluid fluid){
        if (whitelistedFluids.size() >0){
            return whitelistedFluids.contains(fluid);
        } else if (blacklistedFluids.size() >0){
            return !blacklistedFluids.contains(fluid);
        } else {
            return true;
        }
    }

}
