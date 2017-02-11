package trains.utility;


import net.minecraft.entity.Entity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>fluid management</h1>
 * this is more of a replacement for the base game fluid management to better allow us to use multiple tanks of fluids.
 * @author Eternal Blue Flame
 */
public class LiquidManager {
    private FluidTank firstTank;
    private FluidTank secondTank;
    private List<Fluid> firstistedFluids = new ArrayList<Fluid>();
    private List<Fluid> secondlistedFluids = new ArrayList<Fluid>();
    private boolean firstIsWhitelist =false;
    private boolean secondIsWhitelist =false;


    /**
     * <h2>initialization</h2>
     * sets the initial values of the class.
     */
    public LiquidManager(int firstCapacity, int secondCapacity, Fluid[] firstList, Fluid[] secondList, boolean firstIsWhitelist, boolean secondIsWhitelist){
        System.out.println(firstCapacity + ":" + secondCapacity + " and whitelists are" + firstCapacity + ":" + secondIsWhitelist);
        this.firstTank = new FluidTank(firstCapacity);
        this.secondTank = new FluidTank(secondCapacity);
        this.firstIsWhitelist = firstIsWhitelist;
        this.secondIsWhitelist = secondIsWhitelist;
        for (Fluid fluid : firstList) {
            this.firstistedFluids.add(fluid);
        }

        for (Fluid fluid : secondList) {
            this.secondlistedFluids.add(fluid);
        }

    }


    /**
     * <h2>get tank</h2>
     * @return the capacity of the tank
     */
    public FluidTank getTank(boolean isFirstTank){
        if (isFirstTank){
            return firstTank;
        } else {
            return secondTank;
        }
    }

    /**
     * <h2>add fluid to a tank</h2>
     * attempts to add fluid to the tank of the defined amount and type.
     * @param fluid fluid to add to the tank.
     * @param amount the amount of fluid to add.
     */
    public boolean addFluid(Fluid fluid, int amount, boolean isFirstTank, Entity host){
        if (isFirstTank){
            System.out.println((firstTank.getFluidAmount() + " : "+ amount) + " : "+ firstTank.getCapacity());
            if (isFluidValid(fluid, true) && firstTank.getFluidAmount() + amount <= firstTank.getCapacity()
                && firstTank.getFluidAmount() + amount >=0){
                firstTank.setFluid(new FluidStack(fluid, firstTank.getFluidAmount() + amount));
                host.getDataWatcher().updateObject(20, firstTank.getFluidAmount());
                return true;
            }
        } else {
            if (isFluidValid(fluid, false) && secondTank.getFluidAmount() + amount <= secondTank.getCapacity()
                    && secondTank.getFluidAmount() + amount >=0){
                secondTank.setFluid(new FluidStack(fluid, firstTank.getFluidAmount() + amount));
                host.getDataWatcher().updateObject(21, secondTank.getFluidAmount());
                return true;
            }
        }
        return false;
    }

    /**
     * <h2>drain fluid from a tank</h2>
     * attempts to drain fluid from the tank of the defined amount and type.
     * @param amount the amount of fluid to drain.
     */
    public boolean drainFluid(int amount, boolean isFirstTank, Entity host){
        if (isFirstTank){
            if (firstTank.getFluidAmount()>amount) {
                firstTank.setFluid(new FluidStack(firstTank.getFluid(), firstTank.getFluidAmount() - amount));
                host.getDataWatcher().updateObject(20, firstTank.getFluidAmount());
                return true;
            } else {
                return false;
            }
        } else {
            if (secondTank.getFluidAmount()>amount) {
                secondTank.setFluid(new FluidStack(secondTank.getFluid(), secondTank.getFluidAmount() - amount));
                host.getDataWatcher().updateObject(21, secondTank.getFluidAmount());
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * <h2>is fluid valid for tank</h2>
     * checks the tanks can take the fluid (this checks all the tanks).
     * @param fluid the fluid to check for
     * @return if the tanks can take the fluid defined.
     */
    private boolean isFluidValid(Fluid fluid, boolean isFirstTank){
        if (isFirstTank){
            if (firstIsWhitelist){
                return firstistedFluids.contains(fluid);
            } else {
                return !firstistedFluids.contains(fluid);
            }
        } else {
            if (secondIsWhitelist){
                return secondlistedFluids.contains(fluid);
            } else {
                return !secondlistedFluids.contains(fluid);
            }
        }
    }

}
