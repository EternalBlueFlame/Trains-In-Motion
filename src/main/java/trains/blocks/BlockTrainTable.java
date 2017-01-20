package trains.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import trains.TrainsInMotion;
import trains.crafting.TileEntityStorage;


public class BlockTrainTable extends BlockContainer {


    public BlockTrainTable(){
        super(Material.wood);
        setCreativeTab(TrainsInMotion.creativeTab);
    }

    @Override
    public boolean onBlockActivated(World worldOBJ, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_){

        if (player.isSneaking()){
            return false;
        } else {
            if (worldOBJ.isRemote) {
                return true;
            } else {
                TileEntity entity = worldOBJ.getTileEntity(x,y,z);

                if (entity != null) {
                    player.openGui(TrainsInMotion.instance,0,worldOBJ,x,y,z);
                }

                return true;
            }
        }

    }


    public TileEntity createNewTileEntity(World worldObj, int meta){
        return new TileEntityStorage();
    }

}
