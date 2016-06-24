package Movement;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

public class Accelerate {
    EntityMinecart minecart;
    World worldObj;
	public Accelerate(EntityMinecart minecart, World worldObj){
		this.minecart = minecart;
		this.worldObj = worldObj;
	}
	
	public void moveMinecartOnRail(double maxmovespeed){
		if(!worldObj.isRemote){
				
	        double moveX = minecart.motionX;
	        double moveZ = minecart.motionZ;
	
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
	
	        minecart.moveEntity(moveX, 0.0D, moveZ);
    
		}
	}
}
