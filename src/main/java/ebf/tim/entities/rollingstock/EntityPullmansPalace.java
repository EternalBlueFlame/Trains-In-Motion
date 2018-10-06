package ebf.tim.entities.rollingstock;

import ebf.tim.TrainsInMotion;
import ebf.tim.api.RollingstockBase;
import ebf.tim.entities.trains.EntityBrigadelok080;
import ebf.tim.items.ItemTransport;
import ebf.tim.models.Bogie;
import ebf.tim.models.rollingstock.PullmansPalace;
import fexcraft.tmt.slim.ModelBase;
import fexcraft.tmt.slim.Vec3d;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * <h1>Pullman's Palace entity</h1>
 * For more information on the overrides and functions:
 * @see EntityBrigadelok080
 * @author Eternal Blue Flame
 */
public class EntityPullmansPalace extends RollingstockBase {
    private static final String[] itemDescription = new String[]{
            "\u00A77" + StatCollector.translateToLocal("menu.item.weight") +": 2 " + StatCollector.translateToLocal("menu.item.tons"),
            "\u00A77" + StatCollector.translateToLocal("menu.item.seats") +": 4 " + StatCollector.translateToLocal("menu.item.players")};
    public static final Item thisItem = new ItemTransport(new EntityPullmansPalace(null)).setUnlocalizedName("pullmanspalace");

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
    public int bogieLengthFromCenter(){return 2;}

    @Override
    public float getRenderScale() {
        return 0.0625f;
    }

    @Override
    public boolean isReinforced() {
        return false;
    }

    @Override
    public int[] getTankCapacity() {
        return null;
    }

    @Override
    public int getRFCapacity() {
        return 0;
    }

    @Override
    public void manageFuel() {

    }

    @Override
    public float weightKg() {
        return 1814.3f;
    }

    @Override
    public float getMaxFuel() {
        return 0;
    }

    /**
     * <h2>Inventory Size</h2>
     */
    @Override
    public int getInventoryRows(){return 0;}
    /**
     * <h2>Type</h2>
     */
    @Override
    public TrainsInMotion.transportTypes getType(){return TrainsInMotion.transportTypes.PASSENGER;}
    /**
     * <h2>Rider offsets</h2>
     */
    @Override
    public double[][] getRiderOffsets(){return new double[][]{{2f,0.5f, 0.2f},{0.75f,0.5f, 0.2f},{-0.75f,0.5f, 0.2f},{-2f,0.5f, 0.2f}};}
    /**
     * <h2>Hitbox offsets</h2>
     */
    @Override
    public double[][] getHitboxPositions(){return new double[][]{{-3.35d,0.25d,0d},{-2.75d,0.25d,0d},{-1.25d,0.25d,0d},{0d,0.25d,0d},{1.35d,0.25d,0d},{2.75d,0.25d,0d},{3.35d,0.25d,0d}};}

    @Override
    public float getPistonOffset() {
        return 0;
    }

    @Override
    public float[][] getSmokeOffset() {
        return null;
    }

    @Override
    public ResourceLocation getTexture(){return null;} //URIRegistry.MODEL_ROLLINGSTOCK_TEXTURE.getResource("null.png");}

    @Override
    public List<? extends ModelBase> getModel(){return Collections.singletonList(new PullmansPalace());}

    @Override
    public Bogie[] getBogieModels(){return null;}

    @Override
    public List<Double> getRenderBogieOffsets() {
        return null;
    }

    /**
     * <h2>pre-asigned values</h2>
     */
    @Override
    public Item getItem(){
        return thisItem;
    }
}
