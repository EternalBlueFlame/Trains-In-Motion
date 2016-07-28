package trains.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import trains.blocks.LampBlock;
import trains.blocks.Oil;

public class BlockRegistry {
    //initialize the oil
    public static final Material Mat_Oil = new MaterialLiquid(MapColor.blackColor);
    public static final Fluid fluidOil = new Oil("Oil").setUnlocalizedName("Oil");
    public static BlockFluidClassic blockFluidOil;
    //initialize the lamp block
    public static Block lampBlock = new LampBlock();



    public void RegisterBlocks(){
        //register oil
        FluidRegistry.registerFluid(fluidOil);
        blockFluidOil = new BlockFluidClassic(fluidOil, Mat_Oil);
        GameRegistry.registerBlock(blockFluidOil, "OilBlock");
        //register the lamp block
        GameRegistry.registerBlock(lampBlock, "lampblock");
    }

}
