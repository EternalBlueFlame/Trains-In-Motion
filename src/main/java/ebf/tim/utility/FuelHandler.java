package ebf.tim.utility;


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
import net.minecraftforge.common.util.ForgeDirection;
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
	 * TODO: need support for 3rd party water items that contain dynamic amounts.
	 */
	public static boolean isWater(ItemStack item, GenericRailTransport transport){
		switch (transport.getType()){
            case NUCLEAR_STEAM:{/*compensate for coolant*/}
            //cover water
            case TANKER:{return item != null && item.getItem() instanceof ItemBucket;}
            default: {return item != null && item.getItem() == Items.water_bucket;}
		}
	}

	/**
	 * <h2>get value from Item</h2>
	 * this is intended to get a value from items that normally don't have one, for instance the fuel value of redstone, or the mb of water in a bucket.
	 * @return the value int for the item
	 * non-liquids like redstone are managed here because all non-steam trains use a water tank to define their fuel amount.
	 */
	public static int getValue(ItemStack itemStack){
		if (itemStack != null) {
			if (itemStack.getItem() == Items.water_bucket) {
				return 1000;
			} else if (itemStack.getItem() == Items.redstone){
				return 250;
			} else if (itemStack.getItem() == Item.getItemFromBlock(Blocks.redstone_block)){
				return 2250;
			}

		}

		return 0;
	}

	public void manageSteam(EntityTrainCore train){
		//manage solid fuel
		if (isFuel(train.getStackInSlot(0), train) && fuel + TileEntityFurnace.getItemBurnTime(train.getStackInSlot(0)) < train.getMaxFuel()) {
			fuel += TileEntityFurnace.getItemBurnTime(train.getStackInSlot(0));
			if (!train.isCreative) {
				train.decrStackSize(0, 1);
			}
		}
		if (isWater(train.getStackInSlot(1), train) &&
				train.fill(ForgeDirection.UNKNOWN, new FluidStack(FluidRegistry.WATER, getValue(train.getStackInSlot(1))), false) == getValue(train.getStackInSlot(1))) {
			train.fill(ForgeDirection.UNKNOWN, new FluidStack(FluidRegistry.WATER, getValue(train.getStackInSlot(1))), true);
			if (!train.isCreative) {
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
			int steam = Math.round((fuel *0.0025f)/ train.getTankAmount());
			if (train.drain(ForgeDirection.UNKNOWN, steam,true).amount == steam) {
				fuel -= 50; //a lava bucket lasts 1000 seconds, so burnables are processed at 20000*0.05 a second
				steamTank += MathHelper.floor_float(steam*0.9f);
			} else if (!train.isCreative){
				train.worldObj.createExplosion(train, train.posX, train.posY, train.posZ, 5f, false);
				train.dropItem(train.getItem(), 1);
				train.attackEntityFromPart(null, new EntityDamageSource("overheat", train),1);
			}

		}
		if (steamTank >0){
			//steam is expelled through the pistons to push them back and forth, but even when the accelerator is off, a degree of steam is still escaping.
			steamTank -=5+(10*train.accelerator);
		}
	}



	public void manageElectric(EntityTrainCore train){
		//add redstone to the fuel tank
		if (isWater(train.getStackInSlot(0), train) && getValue(train.getStackInSlot(1))+ fuel < train.getMaxFuel() && !train.isCreative) {
			fuel +=getValue(train.getStackInSlot(1));
			train.decrStackSize(0,1);
		}

		//use stored energy
		if (train.isRunning){
			//electric trains run at a generally set rate which is multiplied at the square of speed.
			//TODO: this may need re-balancing to keep it realistic.
			fuel -= 1 + MathHelper.sqrt_float(Math.copySign(train.accelerator, 1)*5);
		}
	}


	public static void manageTanker(GenericRailTransport transport){
		//consume the water, if the second slot contains a water bucket, also places an empty bucket in inventory
		if (FuelHandler.isWater(transport.getStackInSlot(0), transport) && transport.getTankCapacity() - (FuelHandler.getValue(transport.getStackInSlot(0)) + transport.getTankAmount())>0) {
			transport.fill(ForgeDirection.UNKNOWN, new FluidStack(FluidRegistry.WATER, FuelHandler.getValue(transport.getStackInSlot(0))), true);
			if (!transport.isCreative) {
				transport.decrStackSize(0, 1);
				transport.addItem(new ItemStack(Items.bucket));
			}
		}
	}

} 
