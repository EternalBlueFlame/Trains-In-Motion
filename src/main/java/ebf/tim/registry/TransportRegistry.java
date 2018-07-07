package ebf.tim.registry;


import cpw.mods.fml.common.registry.GameRegistry;
import ebf.tim.TrainsInMotion;
import ebf.tim.api.SkinRegistry;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.entities.rollingstock.EntityGTAX13000GallonTanker;
import ebf.tim.entities.rollingstock.EntityPullmansPalace;
import ebf.tim.entities.rollingstock.EntityUP3Bay100TonHopper;
import ebf.tim.entities.rollingstock.EntityVATLogCar;
import ebf.tim.entities.trains.EntityBrigadelok080;
import ebf.tim.models.Bogie;
import ebf.tim.models.bogies.CMDBogie;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * <h1>Train registry</h1>
 * this class lists the data necessary to register trains and rollingstock.
 * this is not intended to be a way to get specific trains from the mod. (use the unlocalized entity name, or a class instanceof check)
 * @author Eternal Blue Flame
 */
public class TransportRegistry {

    public GenericRailTransport transport;
    public Item[] recipe;

    public TransportRegistry(GenericRailTransport transport, Item[] recipe){
        this.transport = transport;
        this.recipe = recipe;
    }


    /**returns a CMD Bogie with the default texture, just to simplify some code*/
    public static Bogie GenericCMDBogie(){return new Bogie(URIRegistry.HD_MODEL_ROLLINGSTOCK_TEXTURE.getResource("cmd_bogie.png"), new CMDBogie());}

    public static void registerTransports(int entityIDOffset){
        int index =0;
        GenericRailTransport registry = listTrains(0);
        while (registry!=null) {
            cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(
                    registry.getClass(),
                    registry.getItem().getUnlocalizedName().replace("item","entity"),
                    index+entityIDOffset, TrainsInMotion.instance, 60, 1, true);
            GameRegistry.registerItem(registry.getItem(), registry.getItem().getUnlocalizedName().substring(5));
            registry.registerSkins();
            index++;
            registry = listTrains(index);
        }
    }


    @Deprecated
    public static ItemStack[] listRecipies(int index) {
        switch (index) {
            case 0: {return new ItemStack[]{
                        new ItemStack(Items.coal, 1), null, null,
                        null, null, null,
                        null, null, null};
            }
            case 1: {return new ItemStack[]{
                    new ItemStack(Items.coal, 1), null, null,
                    null, null, null,
                    null, null, null};
            }
            case 2: {return new ItemStack[]{
                    new ItemStack(Items.coal, 1), null, null,
                    null, null, null,
                    null, null, null};
            }
            case 3: {return new ItemStack[]{
                    new ItemStack(Items.coal, 1), null, null,
                    null, null, null,
                    null, null, null};
            }
            case 4: {return new ItemStack[]{
                    new ItemStack(Items.coal, 1), null, null,
                    null, null, null,
                    null, null, null};
            }
        }
        return null;
    }



    public static GenericRailTransport listTrains(int index){
        switch (index) {

            /*
             * <h3>Steam Train registry entries</h3>
             */
            case 0: {return new EntityBrigadelok080(null);}

             /*
             * <h3>Passenger Rollingstock registry entries</h3>
             */

            //pullman's palace
            case 1:{return new EntityPullmansPalace(null);}

            /*
             * <h3>Freight Rollingstock registry entries</h3>
             */

            case 2:{return new EntityVATLogCar(null);}

            /*
             * <h3>Hopper Rollingstock registry entries</h3>
             */

            case 3:{return new EntityUP3Bay100TonHopper(null);}


            /*
             * <h3>Tanker Rollingstock registry entries</h3>
             */

            //GATX 13000 Gallon Tanker
            case 4:{return new EntityGTAX13000GallonTanker(null);}


        }

        return null;
    }

}
