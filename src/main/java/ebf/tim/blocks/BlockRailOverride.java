package ebf.tim.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.BlockRail;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author Eternal Blue Flame
 */
public class BlockRailOverride extends BlockRail implements ITileEntityProvider {

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return RenderingRegistry.getNextAvailableRenderId();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta){
        return new renderTileEntity();
    }



    public class renderTileEntity extends TileEntity{
        @Override
        public boolean canUpdate(){return true;}

    }
}
