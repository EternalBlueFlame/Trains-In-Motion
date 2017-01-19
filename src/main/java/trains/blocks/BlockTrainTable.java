package trains.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import trains.TrainsInMotion;

public class BlockTrainTable extends Block {

    public BlockTrainTable(){
        super(Material.wood);
        setCreativeTab(TrainsInMotion.creativeTab);
    }

    @Override
    public boolean onBlockActivated(World worldOBJ, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_){

        if (player.isSneaking()){
            return false;
        } else {
            player.openGui(TrainsInMotion.instance,0,worldOBJ,x,y,z);
            return true;
        }

    }

}
