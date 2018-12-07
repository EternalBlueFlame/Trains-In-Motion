package ebf.tim.entities.rollingstock;

import ebf.tim.TrainsInMotion;
import ebf.tim.api.RollingstockBase;
import ebf.tim.api.SkinRegistry;
import ebf.tim.entities.trains.EntityBrigadelok080;
import ebf.tim.items.ItemTransport;
import ebf.tim.models.bogies.CMDBogie;
import ebf.tim.models.rollingstock.VATLogCar;
import fexcraft.tmt.slim.ModelBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.UUID;

import static ebf.tim.utility.RailUtility.DefineStack;


/**
 * <h1>V.A.T Log Car entity</h1>
 * For more information on the overrides and functions:
 * @see EntityBrigadelok080
 * @author Eternal Blue Flame
 */
public class EntityVATLogCar extends RollingstockBase {

    public static final String[] itemDescription = new String[]{
            "\u00A77" + StatCollector.translateToLocal("menu.item.weight") +": 2" + StatCollector.translateToLocal("menu.item.tons"),
            "\u00A77" + StatCollector.translateToLocal("menu.item.sizeof") +": 27" + StatCollector.translateToLocal("menu.item.slots")};
    public static final Item thisItem = new ItemTransport(new EntityVATLogCar(null), TrainsInMotion.MODID, TrainsInMotion.creativeTab);

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
    public float[][] bogieModelOffsets() {
        return new float[][]{{1.3f,0,0}, {-1.3f,0,0}};
    }

    @Override
    public ModelBase[] bogieModels() {
        return new ModelBase[]{new CMDBogie()};
    }

    @Override
    public float[] bogieLengthFromCenter(){return new float[]{1, -1};}

    @Override
    public float getRenderScale() {
        return 0.0625f;
    }

    @Override
    public float[][] modelOffsets() {
        return null;
    }

    @Override
    public void registerSkins() {
        SkinRegistry.addSkin(this.getClass(), TrainsInMotion.MODID, "textures/VATLogCar.png",
                new String[]{"textures/hd/rollingstock/cmd_bogie.png"},
                "V.A.T Logcar",
                "Fictional log car from \"Railroads!\" used for carrying entire logs but would be good for planks too");
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
    public String[] getTankFilters(int tankID) {
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
    public ItemStack[] getRecipie() {
        return new ItemStack[]{
                DefineStack(Blocks.log, 1), null, null,
                null, null, null,
                null, null, null
        };
    }

    @Override
    public String transportName() {
        return "V.A.T Logcar";
    }

    @Override
    public String transportcountry() {
        return "US";
    }

    @Override
    public String transportYear() {
        return "1850-1970";
    }

    @Override
    public String transportEra() {
        return "Steam-Electric";
    }

    @Override
    public float transportTopSpeed() {
        return 0;
    }

    @Override
    public boolean isFictional() {
        return true;
    }

    @Override
    public float transportTractiveEffort() {
        return 0;
    }

    @Override
    public float transportMetricHorsePower() {
        return 0;
    }

    @Override
    public String[] additionalItemText() {
        return null;
    }

    @Override
    public float getMaxFuel() {
        return 0;
    }

    /**
     * <h2>Inventory Size</h2>
     */
    @Override
    public int getInventoryRows(){return 3;}
    /**
     * <h2>Type</h2>
     */
    @Override
    public TrainsInMotion.transportTypes getType(){return TrainsInMotion.transportTypes.LOGCAR;}
    /**
     * <h2>Rider offsets</h2>
     */
    @Override
    public float[][] getRiderOffsets(){return null;}

    @Override
    public float[] getHitboxSize() {
        return new float[]{3.5f,1.5f,1.2f};
    }

    @Override
    public float getPistonOffset() {
        return 0;
    }

    @Override
    public ModelBase[] getModel(){return new ModelBase[]{new VATLogCar()};}

    /**
     * <h2>pre-asigned values</h2>
     */
    @Override
    public Item getItem(){
        return thisItem;
    }
}
