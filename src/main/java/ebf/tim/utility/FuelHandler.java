package ebf.tim.utility;


import cofh.api.energy.IEnergyContainerItem;
import ebf.tim.entities.EntityTrainCore;
import ebf.tim.entities.GenericRailTransport;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * <h1>Fuel management for trains</h1>
 * used to process burnHeat addition and consumption.
 *
 * @author Eternal Blue Flame
 */
public class FuelHandler{

	/**the main burnHeat variable used by most trains*/
	public int burnHeat =0;
	private float burnTime =0;
	private float burnTimeMax =0;
	/**the steam tank, used for steam and nuclear steam trains*/
	public int steamTank=0;
	public float heatC =21;

	public float maxHeat(GenericRailTransport transport){
		switch (transport.getType()){
			case STEAM:{return transport.getMaxFuel() * 750;}
		}
		return 0;
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


	/**
	 * <h2>steam management</h2>
	 *
	 * Credit to zodiacmal for the heatC calculation.
	 * @param train
	 */
	public void manageSteam(EntityTrainCore train){
		//manage solid burnHeat
		if (burnTime <1){
			burnTime=0;
			if (train.getStackInSlot(0) != null) {
				burnHeat = (int) (TileEntityFurnace.getItemBurnTime(train.getStackInSlot(0)) * train.getEfficiency());
				burnTime = MathHelper.ceiling_double_int(burnHeat *0.1);
				burnTimeMax = burnTime;
				if (!train.getBoolean(GenericRailTransport.boolValues.CREATIVE)) {
					train.decrStackSize(0, 1);
				}
			} else {
				burnHeat = 0;
				burnTimeMax = 0;
			}
		} else {
			burnTime--;
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

		//be sure there is burnHeat before trying to consume it
		if (burnHeat > 1) {
			System.out.println(burnHeat);
			//calculate the heat increase
			heatC += (float) ((1- Math.sqrt(heatC/maxHeat(train))) * Math.sqrt((heatC+burnHeat)/burnHeat))*train.getEfficiency();
		} else {
			//cool down, or heat up the boiler to match the temperature of the biome and height
			heatC += 1- Math.sqrt(heatC/ (
					(((train.worldObj.getBiomeGenForCoords(train.chunkCoordX, train.chunkCoordZ).temperature -0.15)//biome temperature with freezing point (0.15) set to 0
							- (0.0014166695 * (train.posY - 64))) //temperature changes by 0.00166667 for every meter above or below sea level (64), the value is -15% to match up with the change to biome temp
							*36.8)//converts the temp to celsius with compensation for the temp offset
			));
		}
		//if the boiler temp is above the boiling point, start generating steam.
		if (heatC >100){
			int steam = (int)Math.floor(
					((heatC-100)*0.05f) * //calculate heat from burnHeat
							(train.getTankAmount()*0.005f) //calculate surface area of water
			);
			//drain fluid
			if (train.drain(null, steam,true)!= null) {
				steamTank +=steam*0.9f;//compensate for water impurities
				if(steamTank>train.getTankCapacity()){//in TiM it needs to be  > getTankCapacity-getTankAmount.
					steamTank= train.getTankCapacity();
				}
				//if no fluid left and not creative mode, explode.
			} else if (!train.getBoolean(GenericRailTransport.boolValues.CREATIVE)){
				train.worldObj.createExplosion(train, train.posX, train.posY, train.posZ, 5f, false);
				train.dropItem(train.getItem(), 1);
				train.attackEntityFromPart(null, new EntityDamageSource("overheat", train),100);
			}
		}

		if (steamTank >0){
			train.setBoolean(GenericRailTransport.boolValues.RUNNING, true);
			//steam is expelled through the pistons to push them back and forth, but even when the accelerator is off, a degree of steam is still escaping.
			steamTank -=(5*train.getEfficiency())*((train.accelerator)*train.getEfficiency());
		} else {
			train.setBoolean(GenericRailTransport.boolValues.RUNNING, false);
		}

		//update the datawatchers so client can display the info on the GUI.
		train.getDataWatcher().updateObject(13, burnTime>0?(int)((burnTime/ burnTimeMax)*18):0);
		train.getDataWatcher().updateObject(14, steamTank);
		train.getDataWatcher().updateObject(15, MathHelper.floor_float(heatC * 100f));
	}




	public void manageElectric(EntityTrainCore train){
		//add redstone to the fuel tank
		if (train.fill(null, isUseableFluid(train.getStackInSlot(1), train), false)>0) {
			train.fill(null, isUseableFluid(train.getStackInSlot(1), train), true);
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
		//consume fluid if the top slot has a fluid container.
		if (transport.getStackInSlot(0) != null && isUseableFluid(transport.getStackInSlot(0), transport) != null &&
				transport.fill(null, isUseableFluid(transport.getStackInSlot(0), transport), false) >= FluidContainerRegistry.getFluidForFilledItem(transport.getStackInSlot(0)).amount) {
			transport.fill(null, isUseableFluid(transport.getStackInSlot(0), transport), true);
			if (!transport.getBoolean(GenericRailTransport.boolValues.CREATIVE)) {
				transport.decrStackSize(0, 1);
				transport.setInventorySlotContents(0, new ItemStack(Items.bucket,1));
			}
		}
		//empty the tank if the bottom slot contains a bucket.
		if (transport.getStackInSlot(1) != null && transport.getStackInSlot(1).getItem() instanceof ItemBucket &&
				transport.drain(null, 1000, false) != null && transport.drain(null, 1000, false).amount >= 1000) {
			transport.setInventorySlotContents(1, FluidContainerRegistry.fillFluidContainer(transport.drain(null, 1000, false), transport.getStackInSlot(1)));
			if (!transport.getBoolean(GenericRailTransport.boolValues.CREATIVE)) {
				transport.drain(null, 1000, true);
			}
		}
		//todo: add support for dynamically filled non-bucket items? (is that even a thing in 1.9+? if it's not probably not worth the bother)
	}

} 
