package ebf.tim.utility;


import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;
import ebf.tim.TrainsInMotion;
import ebf.tim.entities.EntityTrainCore;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.registry.TiMFluids;
import mods.railcraft.api.electricity.IElectricGrid;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
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

	public enum DefaultTanks {
		STEAM(new String[][]{{FluidRegistry.WATER.getName()},{TiMFluids.fluidSteam.getName()}}),
		HEAVY_STEAM(new String[][]{{FluidRegistry.WATER.getName()},{TiMFluids.fluidHeavySteam.getName()}}),
		DIESEL(new String[][]{{TiMFluids.fluidDiesel.getName(), TiMFluids.fluidfueloil.getName()}}),
		ELECTRIC(new String[][]{{TiMFluids.fluidRedstone.getName()}}),
		DIESEL_ELECTRIC(new String[][]{{TiMFluids.fluidDiesel.getName(), TiMFluids.fluidfueloil.getName()}, {TiMFluids.fluidRedstone.getName()}});
		private String[][] t;
		DefaultTanks(String[][] type){this.t = type;}

		public String[][] value(){return t;}

	}


	public float maxHeat(GenericRailTransport transport){
		return transport.getMaxFuel() * 750;
	}

	/**
	 * <h2>check if an item is a usable water source item</h2>
	 * @return if the train can add the water to the boiler or not.
	 * TiM only water items and support for 3rd party/vanilla fuels are managed here
	 *
	 */
	@Deprecated
	public static FluidStack isUseableFluid(ItemStack itemStack, GenericRailTransport transport){

		if(transport.getTypes().contains(TrainsInMotion.transportTypes.ELECTRIC)) {
			if (itemStack.getItem() == Items.redstone) {
				return new FluidStack(FluidRegistry.WATER, 250);
			} else if (itemStack.getItem() == Item.getItemFromBlock(Blocks.redstone_block)) {
				return new FluidStack(FluidRegistry.WATER, 2250);
			} else if (itemStack.getItem() instanceof IEnergyContainerItem) {
				return new FluidStack(FluidRegistry.WATER, ((IEnergyContainerItem) itemStack.getItem()).extractEnergy(itemStack, 250, false));
			}
			return null;
		}

		return FluidContainerRegistry.getFluidForFilledItem(itemStack);
	}

	public FluidStack getFluidForSlot(GenericRailTransport train, int slot){
		return FluidContainerRegistry.getFluidForFilledItem(train.getSlotIndexByID(slot).getStack());
	}

	/**
	 * <h2>steam management</h2>
	 *
	 * Credit to zodiacmal for the heat calculation.
	 * @param train
	 */
	public void manageSteam(EntityTrainCore train){
		//manage solid burnHeat
		ItemStackSlot slotId=train.getSlotIndexByID(400);
		if (burnTime <1){
			burnTime=0;
			if (slotId != null && TileEntityFurnace.getItemBurnTime(slotId.getStack())>0) {
				burnHeat = (int) (TileEntityFurnace.getItemBurnTime(slotId.getStack()) * train.getEfficiency());
				burnTime = MathHelper.ceiling_double_int(burnHeat *0.1);
				burnTimeMax = burnTime;
				if (!train.getBoolean(GenericRailTransport.boolValues.CREATIVE)) {
					train.getSlotIndexByID(400).decrStackSize(1);
				}
			} else {
				burnHeat = 0;
				burnTimeMax = 0;
			}
		} else {
			burnTime--;
		}

		//if there's a fluid item in the slot and the train can consume the entire thing
		if (getFluidForSlot(train, 401) !=null &&
				train.fill(null, getFluidForSlot(train, 401), false) == 0) {

			train.fill(null, getFluidForSlot(train, 401), true);
			if (!train.getBoolean(GenericRailTransport.boolValues.CREATIVE)) {
				train.getSlotIndexByID(401).decrStackSize(1);
				train.addItem(new ItemStack(Items.bucket));
			}
		}
		//be sure there is burnHeat before trying to consume it
		if (burnHeat > 1) {
			//calculate the heat increase
			float heat = train.getDataWatcher().getWatchableObjectFloat(16);
			if(heat==0){heat=1;}
			train.getDataWatcher().updateObject(16,
					(heat+
							(float) ((1f- Math.sqrt(heat/maxHeat(train))) * Math.sqrt((heat+burnHeat)/burnHeat))*train.getEfficiency()));

		} else {
			float heat = (((train.worldObj.getBiomeGenForCoords(train.chunkCoordX, train.chunkCoordZ).temperature -0.15f)//biome temperature with offset to compensate for freezing point
							- (0.0014166695f * ((float)train.posY - 64f)))//temperature changes by 0.00166667 for every meter above or below sea level (64)
							*0.368f//convert to celsius*0.01
			);

			//cap the heat to the biome temp
			if((heat >0 && train.getDataWatcher().getWatchableObjectFloat(16)>= heat*100)
			|| (heat <0 && train.getDataWatcher().getWatchableObjectFloat(16)<= heat*100)
			){
				train.getDataWatcher().updateObject(16, heat*100);
			} else {
				train.getDataWatcher().updateObject(16, train.getDataWatcher().getWatchableObjectFloat(16)+heat);
			}
		}
		//if the boiler temp is above the boiling point, start generating steam.
		if (train.getDataWatcher().getWatchableObjectFloat(16) >100){
			int steam = (int)Math.floor(
					((train.getDataWatcher().getWatchableObjectFloat(16)-100)*0.005f) * //calculate heat from burnHeat
							(train.getTankInfo(null)[0].fluid.amount*0.005f) //calculate surface area of water
			);
			//drain fluid
			if (train.drain(null, steam!=0?steam/5:0,true)!= null) {
				float escaping =Math.abs(train.accelerator) * (train.getTankInfo(null)[1].capacity*0.01f);
				escaping+=train.fill(null, new FluidStack(TiMFluids.fluidSteam, (int)(-escaping+steam*0.9f)), true);
				//todo: tell train to render more steam particles based on escaping steam

				//if no fluid left and not creative mode, explode.
			} else if (!train.getBoolean(GenericRailTransport.boolValues.CREATIVE)){
				train.worldObj.createExplosion(train, train.posX, train.posY, train.posZ, 5f, false);
				train.dropItem(train.getItem(), 1);
				train.setDead();
			}
			train.setBoolean(GenericRailTransport.boolValues.RUNNING, true);
		} else {
			train.setBoolean(GenericRailTransport.boolValues.RUNNING, false);
			train.accelerator=0;
		}

		if (train.getTankInfo(null)[1] !=null && train.getTankInfo(null)[1].fluid.amount >0) {
			//steam is expelled through the pistons to push them back and forth, but even when the accelerator is off, a degree of steam is still escaping.
			train.getTankInfo(null)[1].fluid.amount -= (5 * train.getEfficiency()) * ((train.accelerator) * train.getEfficiency()) * 0.55;
		}

		//update the datawatchers so client can display the info on the GUI.
		train.getDataWatcher().updateObject(13, burnTime>0?(int)((burnTime/ burnTimeMax)*18):0);
		train.getDataWatcher().updateObject(15, MathHelper.floor_float(train.getDataWatcher().getWatchableObjectFloat(16) * 100f));
	}




	public void manageElectric(EntityTrainCore train){
		//add redstone to the fuel tank
		ItemStackSlot slotId=train.getSlotIndexByID(400);
		if(slotId !=null && slotId.getStack()!=null) {
			if (slotId.getItem() == Items.redstone) {
				if (train.fill(null, new FluidStack(TiMFluids.fluidRedstone, 100), false) == 0) {
					train.fill(null, new FluidStack(TiMFluids.fluidRedstone, 100), true);
					train.getSlotIndexByID(400).decrStackSize(1);
				}
			} else if (slotId.getItem() == Item.getItemFromBlock(Blocks.redstone_block)) {
				if (train.fill(null, new FluidStack(TiMFluids.fluidRedstone, 1000), false) == 0) {
					train.fill(null, new FluidStack(TiMFluids.fluidRedstone, 1000), true);
					train.getSlotIndexByID(400).decrStackSize(1);
				}
			} else if (slotId.getItem() instanceof IEnergyContainerItem) {
				if (train.fill(null, new FluidStack(TiMFluids.fluidRedstone, 100), false) == 0) {
					train.fill(null, new FluidStack(TiMFluids.fluidRedstone,
							((IEnergyContainerItem) train.getSlotIndexByID(400).getItem())
									.extractEnergy(slotId.getStack(), 100, false)), true);
				}
			}
			if (train.fill(null, new FluidStack(TiMFluids.fluidRedstone, 100), false) == 0) {
				int draw = 0;
				TileEntity te;
				Block b;
				for (int i=-1; i<5;i++) {
					te=train.worldObj.getTileEntity(MathHelper.floor_double(train.posX), MathHelper.floor_double(train.posY + i), MathHelper.floor_double(train.posZ));
					if (te instanceof IEnergyHandler) {
						for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
							draw = ((IEnergyHandler) te).receiveEnergy(direction, 100, true);

							if (draw != 0) {
								((IEnergyHandler) te).receiveEnergy(direction, 100, false);
								train.fill(null, new FluidStack(TiMFluids.fluidRedstone, 100), true);
								break;
							}
						}
					} else{
						b= train.worldObj.getBlock(MathHelper.floor_double(train.posX), MathHelper.floor_double(train.posY + i), MathHelper.floor_double(train.posZ));
						if (b instanceof IElectricGrid && ((IElectricGrid) b).getChargeHandler().getCharge()>=100){
							((IElectricGrid) b).getChargeHandler().removeCharge(100);
							train.fill(null, new FluidStack(TiMFluids.fluidRedstone, 100), true);
						}
					}
					if (draw != 0) {
						break;
					}
				}
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
		if (transport.getStackInSlot(0) != null){
			//fill from top slot
			if (isUseableFluid(transport.getStackInSlot(0), transport) != null &&
					transport.fill(null, isUseableFluid(transport.getStackInSlot(0), transport), false) >= FluidContainerRegistry.getFluidForFilledItem(transport.getStackInSlot(0)).amount) {

				if (!transport.getBoolean(GenericRailTransport.boolValues.CREATIVE) && (transport.getStackInSlot(1) == null || transport.getStackInSlot(1).getItem() == Items.bucket && transport.getStackInSlot(1).stackSize <16)){
					transport.fill(null, isUseableFluid(transport.getStackInSlot(0), transport), true);
					transport.getStackInSlot(0).stackSize--;
					if (transport.getStackInSlot(0) != null && transport.getStackInSlot(0).stackSize <=0){
						transport.setInventorySlotContents(0, null);
					}
					if (transport.getStackInSlot(1) == null ) {
						transport.setInventorySlotContents(1, new ItemStack(Items.bucket, 1));
					} else if (transport.getStackInSlot(1).getItem() == Items.bucket && transport.getStackInSlot(1).stackSize <16) {
						transport.getStackInSlot(1).stackSize++;
					}
				} else if (transport.getBoolean(GenericRailTransport.boolValues.CREATIVE)){
					transport.fill(null, isUseableFluid(transport.getStackInSlot(0), transport), true);
				}
			}
			//drain from top slot
			else if (transport.getStackInSlot(1) == null && isUseableFluid(transport.getStackInSlot(0), transport) == null &&
					transport.drain(null, 1000, false) != null && transport.drain(null, 1000, false).amount >= 1000){
				transport.setInventorySlotContents(1, FluidContainerRegistry.fillFluidContainer(transport.drain(null, 1000, false), transport.getStackInSlot(0)));
				if (transport.getStackInSlot(0).stackSize == 1) {
					transport.setInventorySlotContents(0, null);
				} else {
					transport.getStackInSlot(0).stackSize--;
				}
				if (!transport.getBoolean(GenericRailTransport.boolValues.CREATIVE)) {
					transport.drain(null, 1000, true);
				}
			}
		}
	}

} 
