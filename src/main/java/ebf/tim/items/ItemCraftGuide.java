package ebf.tim.items;

import ebf.tim.TrainsInMotion;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.utility.DebugUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.*;

public class ItemCraftGuide extends Item {

    public void init(){
        GenericRailTransport itm;
        String key;
        for(Class c:itemEntries){
            try {
                itm = ((GenericRailTransport)c.getConstructor(UUID.class, World.class, double.class, double.class, double.class).newInstance(null, null,0,0,0));
            } catch (Exception e){
                System.out.println(c.getName() +" failed at class cast for recipe book");
                e.printStackTrace();
                continue;
            }
            key=itm.getItem().delegate.name().split(":")[0];
            if(!processedTransports.containsKey(key)){
                processedTransports.put(key,new ArrayList<GenericRailTransport>());
                processedTransports.get(key).add(itm);
                indexPages.add(key+"\n");
            } else {
                processedTransports.get(key).add(itm);
            }
        }
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        if(world.isRemote){
            player.openGui(TrainsInMotion.MODID, -1, world,0,0,0);
        }
        return false;
    }

    //modid, then info page
    public static Map<String, String> modInfoPages = new HashMap<>();
    public Map<String, List<GenericRailTransport>> processedTransports = new HashMap<>();
    public List<String> indexPages = new ArrayList<>();

    public static List<Class> itemEntries = new ArrayList<>();



}
