package ebf.tim.entities.rollingstock;

import ebf.tim.TrainsInMotion;
import ebf.tim.api.RollingstockBase;
import ebf.tim.entities.trains.EntityBrigadelok080;
import ebf.tim.items.ItemTransport;
import ebf.tim.models.Bogie;
import ebf.tim.models.rollingstock.ModelGATX1300GallonTanker;
import fexcraft.tmt.slim.ModelBase;
import fexcraft.tmt.slim.Vec3d;
import ebf.tim.registry.URIRegistry;
import ebf.tim.utility.FuelHandler;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static ebf.tim.registry.TransportRegistry.GenericCMDBogie;

/**
 * <h1>V.A.T Log Car entity</h1>
 * For more information on the overrides and functions:
 * @see EntityBrigadelok080
 * @author Eternal Blue Flame
 */
public class EntityGTAX13000GallonTanker extends RollingstockBase {

    public static final String[] itemDescription = new String[]{
            "\u00A77" + StatCollector.translateToLocal("menu.item.weight") +": 4" + StatCollector.translateToLocal("menu.item.tons"),
            "\u00A77" + StatCollector.translateToLocal("menu.item.sizeof") +": 13.4" + StatCollector.translateToLocal("gui.buckets")};
    public static final Item thisItem = new ItemTransport(new EntityGTAX13000GallonTanker(null)).setUnlocalizedName("gatx13000gallontanker");

    public EntityGTAX13000GallonTanker(UUID owner, World world, double xPos, double yPos, double zPos) {
        super(owner, world, xPos, yPos, zPos);
    }
    public EntityGTAX13000GallonTanker(World world){
        super(world);
    }

    /*
     * <h1>Variable Overrides</h1>
     */

    @Override
    public List<Double> getRenderBogieOffsets(){return  Arrays.asList(-2.1D, 2.1D);}
    @Override
    public int bogieLengthFromCenter(){return 2;}

    @Override
    public float getRenderScale() {
        return  0.0625f;
    }

    @Override
    public boolean isReinforced() {
        return false;
    }

    @Override
    public TrainsInMotion.inventorySizes getInventorySize(){return TrainsInMotion.inventorySizes.NULL;}
    @Override
    public TrainsInMotion.transportTypes getType(){return TrainsInMotion.transportTypes.TANKER;}
    @Override
    public double[][] getRiderOffsets(){return null;}
    @Override
    public double[][] getHitboxPositions(){return new double[][]{{-3.35d,0.25d,0d},{-2.75d,0.25d,0d},{-1.35d,0.25d,0d},{0d,0.25d,0d},{1.35d,0.25d,0d},{2.75d,0.25d,0d},{3.35d,0.25d,0d}};}
    @Override
    public Vec3d getLampOffset(){return new Vec3d(0,0,0);}

    @Override
    public float getPistonOffset() {
        return 0;
    }

    @Override
    public float[][] getSmokeOffset() {
        return null;
    }

    @Override
    public Item getItem(){
        return thisItem;
    }
    @Override
    public int getTankCapacity(){return 13400;}

    @Override
    public int getRFCapacity() {
        return 0;
    }

    @Override
    public void manageFuel(){
        FuelHandler.manageTanker(this);
    }

    @Override
    public float weightKg() {
        return 1814.3f;
    }

    @Override
    public float getMaxFuel() {
        return 0;
    }

    @Override
    public Bogie[] getBogieModels(){return new Bogie[]{GenericCMDBogie(), GenericCMDBogie()};}
    @Override
    public ResourceLocation getTexture(){return URIRegistry.HD_MODEL_ROLLINGSTOCK_TEXTURE.getResource("gatx_13000_gallon_tanker.png");} //URIRegistry.MODEL_ROLLINGSTOCK_TEXTURE.getResource("null.png");}
    @Override
    public List<? extends ModelBase> getModel(){return Collections.singletonList(new ModelGATX1300GallonTanker());}
}
