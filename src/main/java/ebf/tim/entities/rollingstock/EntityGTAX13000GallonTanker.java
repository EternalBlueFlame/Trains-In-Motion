package ebf.tim.entities.rollingstock;

import ebf.tim.TrainsInMotion;
import ebf.tim.api.RollingstockBase;
import ebf.tim.entities.EntityRollingStockCore;
import ebf.tim.entities.trains.EntityBrigadelok080;
import ebf.tim.items.ItemTransport;
import ebf.tim.models.Bogie;
import ebf.tim.models.rollingstock.ModelGATX1300GallonTanker;
import ebf.tim.models.tmt.ModelBase;
import ebf.tim.models.tmt.Vec3d;
import ebf.tim.models.trains.Brigadelok_080;
import ebf.tim.registry.URIRegistry;
import ebf.tim.utility.FuelHandler;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.Arrays;
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
    public static final Item thisItem = new ItemTransport(itemDescription, EntityGTAX13000GallonTanker.class).setUnlocalizedName("gatx13000gallontanker");

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
    public List<Double> getRenderBogieOffsets(){return  Arrays.asList(-2.4D, 2.4D);}
    @Override
    public double getLengthFromCenter(){return 2.3D;}
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
    public Item getItem(){
        return thisItem;
    }
    @Override
    public int getTankCapacity(){return 13400;}
    @Override
    public void manageFuel(){
        FuelHandler.manageTanker(this);
    }
    @Override
    public Bogie[] getBogieModels(){return new Bogie[]{GenericCMDBogie(), GenericCMDBogie()};}
    @Override
    public ResourceLocation getTexture(){return null;} //URIRegistry.MODEL_ROLLINGSTOCK_TEXTURE.getResource("null.png");}
    @Override
    public ModelBase getModel(){return new ModelGATX1300GallonTanker();}
}
