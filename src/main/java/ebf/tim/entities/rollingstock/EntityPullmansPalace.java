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
 * <h1>Pullman's Palace entity</h1>
 * For more information on the overrides and functions:
 * @see EntityBrigadelok080
 * @author Eternal Blue Flame
 */
public class EntityPullmansPalace extends EntityRollingStockCore {
    private static final LiquidManager tank = null;
    private static final String[] itemDescription = new String[]{
            "\u00A77" + StatCollector.translateToLocal("menu.item.weight") +": 2 " + StatCollector.translateToLocal("menu.item.tons"),
            "\u00A77" + StatCollector.translateToLocal("menu.item.seats") +": 4 " + StatCollector.translateToLocal("menu.item.players")};
    public static final Item thisItem = new ItemTransport(itemDescription, EntityPullmansPalace.class).setUnlocalizedName("pullmanspalace");

    public EntityPullmansPalace(UUID owner, World world, double xPos, double yPos, double zPos) {
        super(owner, world, xPos, yPos, zPos);
    }
    public EntityPullmansPalace(World world){
        super(world);
    }

    /**
     * <h1>Variable Overrides</h1>
     */

    /**
     * <h2>Bogie Offset</h2>
     */
    @Override
    public List<Double> getRenderBogieOffsets(){return  Arrays.asList(-1.75, 1.75);}
    /**
     * <h2>Inventory Size</h2>
     */
    @Override
    public TrainsInMotion.inventorySizes getInventorySize(){return TrainsInMotion.inventorySizes.NULL;}
    /**
     * <h2>Type</h2>
     */
    @Override
    public TrainsInMotion.transportTypes getType(){return TrainsInMotion.transportTypes.PASSENGER;}
    /**
     * <h2>Rider offsets</h2>
     */
    @Override
    public double[][] getRiderOffsets(){return new double[][]{{2.5f,1.5f, 0f},{1f,1.5f, 0f},{-1f,1.5f, 0f},{-2.5f,1.5f, 0f}};}
    /**
     * <h2>Hitbox offsets</h2>
     */
    @Override
    public float[] getHitboxPositions(){return new float[]{-2.75f,-1.25f,0f,1.35f,2.75f};}
    /**
     * <h2>Lamp offset</h2>
     */
    @Override
    public Vec3d getLampOffset(){return new Vec3d(0,2,0);}
    /**
     * <h2>Smoke offset</h2>
     */
    @Override
    public float[][] getSmokeOffset(){return new float[][]{};}



    /**
     * <h2>pre-asigned values</h2>
     */
    @Override
    public LiquidManager getTank(){return tank;}
    @Override
    public Item getItem(){
        return thisItem;
    }
}
