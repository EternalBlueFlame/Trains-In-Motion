package ebf.tim.items;

import ebf.tim.entities.GenericRailTransport;
import net.minecraft.world.World;

import java.util.*;

public class ItemCraftGuide {

    public void init(){
        GenericRailTransport itm;
        String key;
        for(Class c:itemEntries){
            try {
                itm = ((GenericRailTransport)c.getConstructor(World.class).newInstance(null));
            } catch (Exception e){
                System.out.println(c.getName() +" failed at class cast for recipe book");
                e.printStackTrace();
                continue;
            }
            key=itm.getItem().delegate.name().split(":")[0];
            if(!processedTransports.containsKey(key)){
                processedTransports.put(key, Collections.singletonList(itm));
            } else {
                processedTransports.get(key).add(itm);
            }
        }

    }


    public static String[] donorsPages(){
        return new String[]{"A special thanks to those that helped support the mod from donations\n" +
                "NightScale5755\n"
        };
    }

    //modid, then info page
    public Map<String, String> modInfoPages = new HashMap<>();
    public Map<String, List<GenericRailTransport>> processedTransports = new HashMap<>();

    public static List<Class> itemEntries = new ArrayList<>();
}
