package trains.utility;



import net.minecraft.block.BlockTorch;
import net.minecraft.world.World;

public class BlockLight extends BlockTorch {

    public BlockLight() {
        super();
        this.setCreativeTab(null);
    }

    @Override
    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
        return true;
    }



}
