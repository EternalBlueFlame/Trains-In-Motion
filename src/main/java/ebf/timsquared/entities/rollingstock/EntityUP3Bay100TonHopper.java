package ebf.timsquared.entities.rollingstock;

import ebf.tim.TrainsInMotion;
import ebf.tim.api.RollingstockBase;
import ebf.tim.api.SkinRegistry;
import ebf.tim.items.ItemTransport;
import ebf.timsquared.TiMSquared;
import ebf.timsquared.entities.trains.EntityBrigadelok080;
import ebf.timsquared.models.bogies.CMDBogie;
import ebf.timsquared.models.rollingstock.UP3Bay100TonHopper;
import fexcraft.tmt.slim.ModelBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

import static ebf.tim.utility.RailUtility.DefineStack;


/**
 * <h1>Union Pacific 3-Bay 100-Ton Hopper entity</h1>
 * For more information on the overrides and functions:
 * @see EntityBrigadelok080
 * @author Eternal Blue Flame
 */
public class EntityUP3Bay100TonHopper extends RollingstockBase {

    /*public static final String[] itemDescription = new String[]{
            "\u00A77" + StatCollector.translateToLocal("menu.item.weight") +": 2" + StatCollector.translateToLocal("menu.item.tons"),
            "\u00A77" + StatCollector.translateToLocal("menu.item.sizeof") +": 27" + StatCollector.translateToLocal("menu.item.slots")};*/
    public static final Item thisItem = new ItemTransport(new EntityUP3Bay100TonHopper(null), TiMSquared.MODID, TiMSquared.creativeTab);

    public EntityUP3Bay100TonHopper(UUID owner, World world, double xPos, double yPos, double zPos) {
        super(owner, world, xPos, yPos, zPos);
    }
    public EntityUP3Bay100TonHopper(World world){
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
        return new float[][]{{1,0,0},{-1,0,0}};
    }

    @Override
    public ModelBase[] bogieModels() {
        return new ModelBase[]{new CMDBogie()};
    }

    @Override
    public float[] bogieLengthFromCenter(){return new float[]{1,-1};}

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
        SkinRegistry.addSkin(this.getClass(), TrainsInMotion.MODID, "textures/UP3Bay100TonHopper.png",
                new String[]{"textures/hd/rollingstock/cmd_bogie.png"},
                "UP 3 Bay hopper", "Primarily used for transporting coal");
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
    public String[][] getTankFilters() {
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
        return 9071.847f;
    }

    @Override
    public ItemStack[] getRecipie() {
        return new ItemStack[]{
                DefineStack(Items.coal, 1), null, null,
                null, null, null,
                null, null, null
        };
    }

    @Override
    public String transportName() {
        return "Union Pacific 3-bay Hopper";
    }

    @Override
    public String transportcountry() {
        return "US";
    }

    @Override
    public String transportYear() {
        return "1960";
    }

    @Override
    public String transportFuelType() {
        return "Steam-Diesel";
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

    /**
     * <h2>Inventory Size</h2>
     */
    @Override
    public int getInventoryRows(){return 3;}
    /**
     * <h2>Type</h2>
     */
    @Override
    public List<TrainsInMotion.transportTypes> getTypes(){return TrainsInMotion.transportTypes.HOPPER.singleton();}
    /**
     * <h2>Rider offsets</h2>
     */
    @Override
    public float[][] getRiderOffsets(){return null;}

    @Override
    public float[] getHitboxSize() {
        return new float[]{4,1.5f, 1.2f};
    }

    @Override
    public float getPistonOffset() {
        return 0;
    }


    @Override
    public ModelBase[] getModel(){return new ModelBase[]{new UP3Bay100TonHopper()};}

    /**
     * <h2>pre-asigned values</h2>
     */
    @Override
    public Item getItem(){
        return thisItem;
    }
}
