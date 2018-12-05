package ebf.tim.entities.rollingstock;

import ebf.tim.TrainsInMotion;
import ebf.tim.api.RollingstockBase;
import ebf.tim.api.SkinRegistry;
import ebf.tim.entities.trains.EntityBrigadelok080;
import ebf.tim.items.ItemTransport;
import ebf.tim.models.bogies.CMDBogie;
import ebf.tim.models.rollingstock.ModelGATX1300GallonTanker;
import ebf.tim.utility.FuelHandler;
import fexcraft.tmt.slim.ModelBase;
import net.minecraft.init.Items;
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
    public float[][] bogieModelOffsets() {
        return new float[][]{{2.1f,0,0},{-2.1f,0,0}};
    }

    @Override
    public ModelBase[] bogieModels() {
        return new ModelBase[]{new CMDBogie()};
    }

    @Override
    public float[] bogieLengthFromCenter(){return new float[]{2,-2};}

    @Override
    public float getRenderScale() {
        return  0.0625f;
    }

    @Override
    public float[][] modelOffsets() {
        return null;
    }

    @Override
    public void registerSkins() {
        SkinRegistry.addSkin(this.getClass(), TrainsInMotion.MODID, "textures/GATX13000GallonTanker.png",
                new String[]{"textures/hd/rollingstock/cmd_bogie.png"},
                "GATX 13000 Gallon Tanker",
                "One of their smaller tanker cars, usually used for commodities such as molten sulfur, clay slurry, caustic soda and corn syrup.");
    }

    @Override
    public boolean isReinforced() {
        return false;
    }

    @Override
    public int getInventoryRows(){return 0;}
    @Override
    public TrainsInMotion.transportTypes getType(){return TrainsInMotion.transportTypes.TANKER;}
    @Override
    public float[][] getRiderOffsets(){return null;}

    @Override
    public float[] getHitboxSize() {
        return new float[]{4,1.5f,1.2f};
    }

    @Override
    public float getPistonOffset() {
        return 0;
    }

    @Override
    public Item getItem(){
        return thisItem;
    }
    @Override
    public int[] getTankCapacity(){return new int[]{13400};}

    @Override
    public String[] getTankFilters(int tankID) {
        return null;
    }

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
        return 29900f;
    }

    @Override
    public ItemStack[] getRecipie() {
        return new ItemStack[]{
                DefineStack(Items.bucket, 1), null, null,
                null, null, null,
                null, null, null
        };
    }

    @Override
    public String transportName() {
        return "GATX 13000 Gallon Tanker";
    }

    @Override
    public String transportcountry() {
        return "US";
    }

    @Override
    public String transportYear() {
        return "2000";
    }

    @Override
    public String transportEra() {
        return "Diesel";
    }

    @Override
    public float transportTopSpeed() {
        return 0;
    }

    @Override
    public boolean isFictional() {
        return false;
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


    @Override
    public ModelBase[] getModel(){return new ModelBase[]{new ModelGATX1300GallonTanker()};}
}
