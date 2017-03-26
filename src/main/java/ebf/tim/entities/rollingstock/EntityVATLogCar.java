package ebf.tim.entities.rollingstock;

import ebf.tim.TrainsInMotion;
import ebf.tim.entities.EntityRollingStockCore;
import ebf.tim.entities.trains.EntityBrigadelok080;
import ebf.tim.items.ItemTransport;
import ebf.tim.models.tmt.Vec3d;
import ebf.tim.utility.LiquidManager;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * <h1>V.A.T Log Car entity</h1>
 * For more information on the overrides and functions:
 * @see EntityBrigadelok080
 * @author Eternal Blue Flame
 */
public class EntityVATLogCar extends EntityRollingStockCore {

    private static final LiquidManager tank = null;
    public static final String[] itemDescription = new String[]{
            "\u00A77" + StatCollector.translateToLocal("menu.item.weight") +": 2" + StatCollector.translateToLocal("menu.item.tons"),
            "\u00A77" + StatCollector.translateToLocal("menu.item.sizeof") +": 27" + StatCollector.translateToLocal("menu.item.slots")};
    public static final Item thisItem = new ItemTransport(itemDescription, EntityVATLogCar.class).setUnlocalizedName("vatlogcar");

    public EntityVATLogCar(UUID owner, World world, double xPos, double yPos, double zPos) {
        super(owner, world, xPos, yPos, zPos);
    }
    public EntityVATLogCar(World world){
        super(world);
    }

    /**
     * <h1>Variable Overrides</h1>
     */

    /**
     * <h2>Bogie Offset</h2>
     */
    @Override
    public List<Double> getRenderBogieOffsets(){return  Arrays.asList(-0.9, 0.9);}
    /**
     * <h2>Inventory Size</h2>
     */
    @Override
    public TrainsInMotion.inventorySizes getInventorySize(){return TrainsInMotion.inventorySizes.NINExTHREE;}
    /**
     * <h2>Type</h2>
     */
    @Override
    public TrainsInMotion.transportTypes getType(){return TrainsInMotion.transportTypes.LOGCAR;}
    /**
     * <h2>Rider offsets</h2>
     */
    @Override
    public double[][] getRiderOffsets(){return null;}
    /**
     * <h2>Hitbox offsets</h2>
     */
    @Override
    public float[] getHitboxPositions(){return new float[]{-1.25f,0f,1.25f};}
    /**
     * <h2>Lamp offset</h2>
     */
    @Override
    public Vec3d getLampOffset(){return new Vec3d(0,0,0);}
    /**
     * <h2>Smoke offset</h2>
     */
    @Override
    public float[][] getSmokeOffset(){return new float[][]{};}



    /**
     * <h2>pre-asigned values</h2>
     */    @Override
    public LiquidManager getTank(){return tank;}
    @Override
    public Item getItem(){
        return thisItem;
    }
}
