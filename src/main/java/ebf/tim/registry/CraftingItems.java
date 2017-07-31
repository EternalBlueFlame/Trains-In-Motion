package ebf.tim.registry;

import ebf.tim.TrainsInMotion;
import net.minecraft.item.Item;
import scala.actors.threadpool.Arrays;

import java.util.List;

/**
 * Created by Eternal Blue Flame
 */
public class CraftingItems {

    public static final List<String> itemURIs = Arrays.asList(new String[]{
            "cabin.wood"
    });

    public static Item getCraftingItem(String uri){
        return new Item().setUnlocalizedName(uri).setCreativeTab(TrainsInMotion.creativeTab).setTextureName(uri);
    }

}
