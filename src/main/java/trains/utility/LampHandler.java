package trains.utility;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import trains.TrainsInMotion;
import trains.blocks.LampBlock;

public class LampHandler {
    public int X;
    public int Y;
    public int Z;
    public boolean isOn;

    //functions for quick checking if the lamp should update
    public void ShouldUpdate(World worldObj, double x, double y, double z){
        if(isOn && (X !=MathHelper.floor_double(x) ^ Y != MathHelper.floor_double(y) ^ Z != MathHelper.floor_double(z))){
            //if there was a block placed previously, remove it.
            if (X != 0 && Y != 0 && Z != 0) {
                worldObj.setBlockToAir(X, Y, Z);
            }
            //set the values
            X = MathHelper.floor_double(x);
            Y = MathHelper.floor_double(y);
            Z = MathHelper.floor_double(z);
            //create the block.
            if (!(worldObj.getBlock(X,Y,Z) instanceof LampBlock)) {
                worldObj.setBlock(X,Y,Z, TrainsInMotion.lampBlock);
                worldObj.getBlock(X,Y,Z).setLightLevel(1f);
            }

        }
    }


}
