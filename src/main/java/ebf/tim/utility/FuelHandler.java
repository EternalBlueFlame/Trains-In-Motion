package ebf.tim.utility;


import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.ItemEnergyContainer;
import ebf.tim.entities.EntityTrainCore;
import ebf.tim.entities.GenericRailTransport;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * <h1>Fuel management for trains</h1>
 * used to process fuel addition and consumption.
 *
 * @author Eternal Blue Flame
 */
public class FuelHandler{

	/**the main fuel variable used by most trains*/
	public int fuel =0;
	/**the steam tank, used for steam and nuclear steam trains*/
	public int steamTank=0;
	public Item lastFuelConsumed = null;

	/**
	 * <h2>check if an item is a usable fuel</h2>
	 * returns if the train can consume the fuel or not.
	 * TiM only fuels and support for 3rd party/vanilla fuels are managed here
     */
	public static boolean isFuel(ItemStack item, GenericRailTransport transport){
		switch (transport.getType()){
			case STEAM: {return item != null && TileEntityFurnace.getItemBurnTime(item) !=0;}
			case ELECTRIC:{return item != null && item.getItem() == Items.redstone;}

		}
		return false;
	}

	/**
	 * <h2>check if an item is a usable water source item</h2>
	 * @return if the train can add the water to the boiler or not.
	 * TiM only water items and support for 3rd party/vanilla fuels are managed here
	 */
	public static FluidStack isUseableFluid(ItemStack itemStack, GenericRailTransport transport){
		switch (transport.getType()){
			case MAGLEV:case ELECTRIC:{
				if (itemStack.getItem() == Items.redstone){
					return new FluidStack(FluidRegistry.WATER, 250);
				} else if (itemStack.getItem() == Item.getItemFromBlock(Blocks.redstone_block)){
					return new FluidStack(FluidRegistry.WATER,2250);
				}else if (itemStack.getItem() instanceof IEnergyContainerItem){
					return new FluidStack(FluidRegistry.WATER, ((IEnergyContainerItem) itemStack.getItem()).extractEnergy(itemStack, 250, false));
				}
				return null;
			}
			case NUCLEAR_STEAM:{/*compensate for coolant*/}
			case STEAM:{
				//return the fluidstack only if it's water.
				return FluidContainerRegistry.getFluidForFilledItem(itemStack)!= null && FluidContainerRegistry.getFluidForFilledItem(itemStack).getFluidID() == FluidRegistry.WATER.getID()?FluidContainerRegistry.getFluidForFilledItem(itemStack):null;
			}
			default:{
				return FluidContainerRegistry.getFluidForFilledItem(itemStack);
			}
		}
	}


	public void manageSteam(EntityTrainCore train){
		//manage solid fuel
		if (isFuel(train.getStackInSlot(0), train) && fuel + TileEntityFurnace.getItemBurnTime(train.getStackInSlot(0)) < train.getMaxFuel()) {
			fuel += TileEntityFurnace.getItemBurnTime(train.getStackInSlot(0));
			lastFuelConsumed = train.getStackInSlot(0).getItem();
			if (!train.getBoolean(GenericRailTransport.boolValues.CREATIVE)) {
				train.decrStackSize(0, 1);
			}
		}
		//if there's a fluid item in the slot and the train can consume the entire thing
		if (train.getStackInSlot(1) != null &&
				train.fill(null, isUseableFluid(train.getStackInSlot(1), train), false) >= FluidContainerRegistry.getFluidForFilledItem(train.getStackInSlot(1)).amount) {
			train.fill(null, isUseableFluid(train.getStackInSlot(1), train), true);
			if (!train.getBoolean(GenericRailTransport.boolValues.CREATIVE)) {
				train.decrStackSize(1, 1);
				train.addItem(new ItemStack(Items.bucket));
			}
		}

		//be sure there is fuel before trying to consume it
		if (fuel > 0) {
					/*
					* add steam from burning to the steam tank.
					* steam is equal to water used, minus a small percentage to compensate for impurities in the water that don't transition to steam,
					* the amount of water used is generated from heat which is one part fuel 2 parts air, with more fuel burning more heat is created, but this only works to a point.
					* steam beyond the max point of storage is considered to be expelled through a failsafe.
					* NOTE: in TiM we need to remember steam and fluid are housed in the same tank, and calculate based on that. should also throw in calcium buildup for non-distilled water. TC however isn't so advanced
					*/
			int steam = Math.round(
					(fuel*0.00075f) * //calculate heat from fuel
							(train.getTankAmount()*0.005f) //calculate surface area of water
			);
			if (train.drain(null, steam,true)!= null) {
				fuel -= 10*train.getEfficiency(); //a lava bucket lasts 1000 seconds, so burnables are processed at 20000*0.05 a second
				steamTank +=steam;

				if(steamTank>train.getTankCapacity()){//in TiM it needs to be  > getTankCapacity-getTankAmount.
					steamTank= train.getTankCapacity();
				}

			} else if (!train.getBoolean(GenericRailTransport.boolValues.CREATIVE)){
				train.worldObj.createExplosion(train, train.posX, train.posY, train.posZ, 5f, false);
				train.dropItem(train.getItem(), 1);
				train.attackEntityFromPart(null, new EntityDamageSource("overheat", train),100);
			}

		} else if (fuel <0){
			fuel=0;
		}

		if (steamTank >0){
			train.setBoolean(GenericRailTransport.boolValues.RUNNING, true);
			//steam is expelled through the pistons to push them back and forth, but even when the accelerator is off, a degree of steam is still escaping.
			steamTank -=(5*train.getEfficiency())*((train.accelerator)*train.getEfficiency());
		} else {
			train.setBoolean(GenericRailTransport.boolValues.RUNNING, false);
		}
	}



	public void manageElectric(EntityTrainCore train){
		//add redstone to the fuel tank
		if (train.fill(null, isUseableFluid(train.getStackInSlot(1), train), false)>0) {
			train.fill(null, isUseableFluid(train.getStackInSlot(1), train), true);
			lastFuelConsumed = train.getStackInSlot(0).getItem();
			if (!train.getBoolean(GenericRailTransport.boolValues.CREATIVE)) {
				train.decrStackSize(0, 1);
			}
		}

		//use stored energy
		if (train.getBoolean(GenericRailTransport.boolValues.RUNNING)){
			//electric trains run at a generally set rate which is multiplied at the square of speed.
			if (train.drain(null, MathHelper.floor_double((5*train.getEfficiency()) + Math.copySign(train.accelerator, 1)*(15*train.getEfficiency())), false)!= null){
				train.drain(null, MathHelper.floor_double((5*train.getEfficiency()) + Math.copySign(train.accelerator, 1)*(15*train.getEfficiency())), true);
			} else {
				train.setBoolean(GenericRailTransport.boolValues.RUNNING, false);
			}
		}
	}


	public static void manageTanker(GenericRailTransport transport){
		//consume the water, if the second slot contains a water bucket, also places an empty bucket in inventory
		if (transport.getStackInSlot(0) != null &&
				transport.fill(null, isUseableFluid(transport.getStackInSlot(0), transport), false) >= FluidContainerRegistry.getFluidForFilledItem(transport.getStackInSlot(0)).amount) {
			transport.fill(null, isUseableFluid(transport.getStackInSlot(0), transport), true);
			if (!transport.getBoolean(GenericRailTransport.boolValues.CREATIVE)) {
				transport.decrStackSize(0, 1);
				transport.addItem(new ItemStack(Items.bucket));
			}
		}
	}

} 
