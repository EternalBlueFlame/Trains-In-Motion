package ebf.tim.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

/**
 * <h2>Fluid Block</h2>
 * similar to the vanilla fluid block, but allows setting for flammable fluids.
 * TODO: it may be possible to use this to house all the fluid data, meaning bucket, fluid, and block.
 *
 * @author Eternal Blue Flame
 */
public class BlockTrainFluid extends BlockFluidClassic {

    @SideOnly(Side.CLIENT)
    private IIcon[] sidedTexture;

    private boolean flammable;
    private int flammability = 0;

    public BlockTrainFluid(Fluid fluid, Material material) {
        super(fluid, material);
    }

    /**
     * <h3>define texture</h3>
     * uses the sidedTexture to define the texture for the block
     */
    @Override
    public IIcon getIcon(int side, int meta) {
        if (this.sidedTexture == null){
            return null;
        }
        return side != 0 && side != 1 ? this.sidedTexture[1] : this.sidedTexture[0];
    }

    /**
     * <h3>Register iicon</h3>
     * fom my vague understanding this is another way of registering the texture for the block
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.sidedTexture = new IIcon[]{iconRegister.registerIcon(fluidName), iconRegister.registerIcon("_flow")};
    }

    /**
     * <h3>flammability</h3>
     * allows you to set the flammability of the block, this allows for a check if it can be set on fire and if it is the source of the fire.
     * similar to lava.
     * The following functions use this information to define how the fire spreads.
     */
    public BlockTrainFluid setFlammable(boolean flammable, int flammability) {
        this.flammable = flammable;
        this.flammability = flammability;
        return this;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {return flammable ? 300 : 0;}

    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {return flammability;}

    @Override
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return flammable;
    }

    @Override
    public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side) {return flammable && flammability == 0;}
}
