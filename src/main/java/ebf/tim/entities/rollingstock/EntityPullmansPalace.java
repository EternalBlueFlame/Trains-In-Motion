package ebf.tim.entities.rollingstock;

import ebf.tim.TrainsInMotion;
import ebf.tim.api.RollingstockBase;
import ebf.tim.entities.trains.EntityBrigadelok080;
import ebf.tim.items.ItemTransport;
import ebf.tim.models.Bogie;
import ebf.tim.models.rollingstock.PullmansPalace;
import ebf.tim.models.tmt.ModelBase;
import ebf.tim.models.tmt.Vec3d;
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
    public int getLengthFromCenter(){return 2;}
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
    public double[][] getRiderOffsets(){return new double[][]{{2f,0.5f, 0.2f},{0.75f,0.5f, 0.2f},{-0.75f,0.5f, 0.2f},{-2f,0.5f, 0.2f}};}
    /**
     * <h2>Hitbox offsets</h2>
     */
    @Override
    public double[][] getHitboxPositions(){return new double[][]{{-3.35d,0.25d,0d},{-2.75d,0.25d,0d},{-1.25d,0.25d,0d},{0d,0.25d,0d},{1.35d,0.25d,0d},{2.75d,0.25d,0d},{3.35d,0.25d,0d}};}
    /**
     * <h2>Lamp offset</h2>
     */
    @Override
    public Vec3d getLampOffset(){return new Vec3d(0,2,0);}

    @Override
    public ResourceLocation getTexture(){return null;} //URIRegistry.MODEL_ROLLINGSTOCK_TEXTURE.getResource("null.png");}

    @Override
    public List<? extends ModelBase> getModel(){return Collections.singletonList(new PullmansPalace());}

    @Override
    public Bogie[] getBogieModels(){return null;}

    /**
     * <h2>pre-asigned values</h2>
     */
    @Override
    public Item getItem(){
        return thisItem;
    }
}
