package trains.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import trains.TrainsInMotion;
import trains.tileentities.TileEntityStorage;


/**
 * <h1>Train crafting table block</h1>
 * @author Eternal Blue Flame
 */
public class BlockTrainTable extends BlockContainer {

    /**
     * <h2>block initializer</h2>
     *  Defines the material like what is necessary to make it and the creative tab for it, and the block name.
     */
    public BlockTrainTable(){
        super(Material.wood);
        setCreativeTab(TrainsInMotion.creativeTab);
        setBlockName("block.traintable");
    }

    /**
     * <h2>Block use</h2>
     * Defines the functionality on block use.
     * This is used to play the using animation on client and open the GUI on server.
     */
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

    /**
     * <h2>Tile entity spawner</h2>
     * spawns the tile entity related to this block, if there isn't one already.
     */
    public TileEntity createNewTileEntity(World worldObj, int meta){
        return new TileEntityStorage();
    }

}
