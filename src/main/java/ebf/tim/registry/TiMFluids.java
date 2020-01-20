package ebf.tim.registry;

import ebf.tim.TrainsInMotion;
import net.minecraft.block.material.MapColor;
import net.minecraftforge.fluids.Fluid;

import static ebf.tim.registry.TiMGenericRegistry.RegisterFluid;

public class TiMFluids {


    /**the oil fluid*/
    public static Fluid fluidOil = new Fluid("Oil");
    /**the diesel fluid*/
    public static Fluid fluidDiesel = new Fluid("Diesel");
    /**the fuel oil fluid*/
    public static Fluid fluidfueloil = new Fluid("FuelOil");
    /**the steam fluid*/
    public static Fluid fluidSteam = new Fluid("Steam");
    /**the heavy steam fluid*/
    public static Fluid fluidHeavySteam = new Fluid("HeavySteam");
    /**the RF fluid*/
    public static Fluid fluidRedstone = new Fluid("Redstone");




    public static void registerFluids(){

        RegisterFluid(fluidOil, TrainsInMotion.MODID, "oil", false, 700, MapColor.blackColor, TrainsInMotion.creativeTab);
        RegisterFluid(fluidDiesel, TrainsInMotion.MODID, "diesel", false, 500, MapColor.sandColor, TrainsInMotion.creativeTab);
        RegisterFluid(fluidSteam, TrainsInMotion.MODID, "steam", true, 200, MapColor.snowColor, TrainsInMotion.creativeTab);
        RegisterFluid(fluidHeavySteam, TrainsInMotion.MODID, "heavysteam", true, 600, MapColor.snowColor, TrainsInMotion.creativeTab);
        RegisterFluid(fluidfueloil, TrainsInMotion.MODID, "fueloil", false, 600, MapColor.brownColor, TrainsInMotion.creativeTab);
        RegisterFluid(fluidRedstone, TrainsInMotion.MODID, "redstone", false, 100, MapColor.redColor, TrainsInMotion.creativeTab);

    }
}
