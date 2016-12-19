package trains.utility;


import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LiquidManager {
    private List<FluidTank> tanks = new ArrayList<FluidTank>();
    private List<Fluid> blacklistedFluids = new ArrayList<Fluid>();
    private List<Fluid> whitelistedFluids = new ArrayList<Fluid>();


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


    public int tankCapacity(int tank){
        if(tanks.size() >= tank) {
            return tanks.get(tank).getCapacity();
        } else{
            return 0;
        }
    }

    public int tankFluidAmount(int tank){
        if(tanks.size() >= tank && tanks.get(tank).getFluid() != null) {
            return tanks.get(tank).getFluidAmount();
        } else{
            return 0;
        }
    }


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



    public boolean canDrain(int amount, int tank){
        if(tanks.size() >= tank && tanks.get(tank).getFluid() != null) {
            return tanks.get(tank).getFluidAmount() - amount > 0;
        } else{
            return false;
        }
    }

    public void addFluid(Fluid fluid, int amount, int tank){
        if(tanks.size() >= tank && isFluidValid(fluid) && tanks.get(tank).getFluidAmount() + amount < tanks.get(tank).getCapacity()){
            if(tanks.get(tank).getFluid() != null){
                amount += tanks.get(tank).getFluidAmount();
            }
            tanks.get(tank).setFluid(new FluidStack(fluid, amount));
            System.out.println(tanks.get(tank).getFluidAmount());
        }
    }

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
