package ebf.tim.registry;

import ebf.tim.TrainsInMotion;
import ebf.tim.items.*;
import net.minecraft.item.Item;

import static ebf.tim.registry.TiMGenericRegistry.RegisterItem;

public class TiMItems {

    //todo
    public static Item adminKey,stake,crowbar;

    public static Item cabinWood,cabinAluminium,cabinCopper,cabinIron,cabinSteel,cabinFiberglass,cabinPlastic;
    public static Item frameWood,frameAluminium,frameCopper,frameIron,frameSteel,frameFiberglass,framePlastic;
    public static Item wheelWood,wheelAluminium,wheelIron,wheelSteel,wheelPlastic;
    public static Item chimneyWood,chimneyAluminium,chimneyCopper,chimneyIron,chimneySteel,chimneyFiberglass,chimneyPlastic;

    public static Item boilerWood,boilerIron,boilerSteel,boilerCopper;
    public static Item petrolEngine,smallDieselEngine,mediumDieselEngine,largeDieselEngine;
    public static Item smallElectricEngine,mediumElectricEngine,largeElectricEngine;

    public static Item seatsWooden,seatsIron,seatsPadded,seatsLuxury;

    public static Item hydraulicTransmission,pnumaticTransmission,transformer,transformerHV,electricControls;

    public static Item piston,cylinder,camshaft,graphite,electronicCircuit,copperWire,goldWire;

    public static Item railItem;


    public static void registerItems(){

        RegisterItem(new ItemAdminBook(),TrainsInMotion.MODID, "adminbook", TrainsInMotion.creativeTab);
        RegisterItem(new ItemCraftGuide(),TrainsInMotion.MODID, "craftbook", TrainsInMotion.creativeTab);

        RegisterItem(new ItemPaintBucket(),TrainsInMotion.MODID, "paintbucket", TrainsInMotion.creativeTab);
        RegisterItem(new ItemKey(),TrainsInMotion.MODID,  "transportkey", TrainsInMotion.creativeTab);
        RegisterItem(new ItemTicket(),TrainsInMotion.MODID,  "transportticket", TrainsInMotion.creativeTab);


        //overides the server registration of the rail item, so the client can have a complex model.
        //   server can't load the CustomItemModel class due to it's reliance on GL imports.
        if(!TrainsInMotion.proxy.isClient()) {
            railItem = RegisterItem( new ItemRail(), TrainsInMotion.MODID, "timrail", TrainsInMotion.creativeTab);
        } else {
            railItem = RegisterItem(new ItemRail(),TrainsInMotion.MODID,  "timrail", null, TrainsInMotion.creativeTab, null, ebf.tim.items.CustomItemModel.instance);
        }



        craftItmShorthand(cabinWood,"cabin.wood");
        craftItmShorthand(cabinAluminium,"cabin.aluminium");

    }

    private static void craftItmShorthand(Item i, String unlocalizedName){
        TiMGenericRegistry.RegisterItem(i=new Item(),TrainsInMotion.MODID,unlocalizedName,TrainsInMotion.creativeTabCrafting);
    }

}
