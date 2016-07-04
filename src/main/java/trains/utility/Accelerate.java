package trains.utility;

import com.sun.org.apache.xpath.internal.functions.FuncFloor;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import trains.entities.MinecartExtended;

public class Accelerate /*implements IFuelHandler */{
    MinecartExtended minecart;
    World worldObj;
    public static double moveX;
    public static double moveZ;
    public static double x;
    public static double y;
    public static double z;
	public Accelerate(MinecartExtended minecart, World worldObj, double x, double y, double z){
		this.minecart = minecart;
		this.worldObj = worldObj;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void moveMinecartOnRail(double maxmovespeed){
		if(!worldObj.isRemote){
		        moveX = minecart.motionX;
		        moveZ = minecart.motionZ;
		        if (moveX < -maxmovespeed)
		        {
		            moveX = -maxmovespeed;
		        }
		
		        if (moveX > maxmovespeed)
		        {
		            moveX = maxmovespeed;
		        }
		
		        if (moveZ < -maxmovespeed)
		        {
		            moveZ = -maxmovespeed;
		        }
		
		        if (moveZ > maxmovespeed)
		        {
		            moveZ = maxmovespeed;
		        }

		        minecart.moveEntity(moveX, 0, moveZ);
		}
	} // 0.30000001192092896
	/*
	@Override
	public int getBurnTime(ItemStack fuel) {
		// TODO Auto-generated method stub
		MinecartExtended.furnaceFuel = fuel.stackSize;
		return 0;
	}
	For until fuel is fixed.
	*/
	public static void drag(){
		moveX *= 0.5;
        moveZ *= 0.5;
	}
}