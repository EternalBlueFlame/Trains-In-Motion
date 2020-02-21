package ebf.tim.registry;

import ebf.tim.TrainsInMotion;
import ebf.tim.items.*;
import net.minecraft.item.Item;

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

    public static Item enginePiston,cylinder,camshaft,graphite,electronicCircuit,copperWire,goldWire;

    public static Item railItem;


    public static void registerItems(){

        TiMGenericRegistry.RegisterItem(new ItemAdminBook(),TrainsInMotion.MODID, "adminbook", TrainsInMotion.creativeTab);
        TiMGenericRegistry.RegisterItem(new ItemCraftGuide(),TrainsInMotion.MODID, "craftbook", TrainsInMotion.creativeTab);

        TiMGenericRegistry.RegisterItem(new ItemPaintBucket(),TrainsInMotion.MODID, "paintbucket", TrainsInMotion.creativeTab);
        TiMGenericRegistry.RegisterItem(new ItemKey(),TrainsInMotion.MODID,  "transportkey", TrainsInMotion.creativeTab);
        TiMGenericRegistry.RegisterItem(new ItemTicket(),TrainsInMotion.MODID,  "transportticket", TrainsInMotion.creativeTab);


        //overides the server registration of the rail item, so the client can have a complex model.
        //   server can't load the CustomItemModel class due to it's reliance on GL imports.
        if(!TrainsInMotion.proxy.isClient()) {
            railItem = TiMGenericRegistry.RegisterItem( new ItemRail(), TrainsInMotion.MODID, "timrail", TrainsInMotion.creativeTab);
        } else {
            railItem = TiMGenericRegistry.RegisterItem(new ItemRail(),TrainsInMotion.MODID,  "timrail", null, TrainsInMotion.creativeTab, null, ebf.tim.items.CustomItemModel.instance);
        }


        //Cabins
        craftItmShorthand(cabinWood,"cabin.wood");
        craftItmShorthand(cabinAluminium,"cabin.aluminium");
        craftItmShorthand(cabinCopper, "cabin.copper");
        craftItmShorthand(cabinIron, "cabin.iron");
        craftItmShorthand(cabinSteel, "cabin.steel");
        craftItmShorthand(cabinFiberglass, "cabin.fiberglass");
        craftItmShorthand(cabinPlastic, "cabin.plastic");

        //Frames
        craftItmShorthand(frameWood, "frame.wood");
        craftItmShorthand(frameAluminium, "frame.aluminium");
        craftItmShorthand(frameCopper, "frame.copper");
        craftItmShorthand(frameIron, "frame.iron");
        craftItmShorthand(frameSteel, "frame.steel");
        craftItmShorthand(frameFiberglass, "frame.fiberglass");
        craftItmShorthand(framePlastic, "frame.plastic");

        //Wheels
        craftItmShorthand(wheelWood, "wheel.wood");
        craftItmShorthand(wheelAluminium, "wheel.aluminium");
        craftItmShorthand(wheelIron, "wheel.iron");
        craftItmShorthand(wheelSteel,"wheel.steel");
        craftItmShorthand(wheelPlastic, "wheel.plastic");

        //Chimneys
        craftItmShorthand(chimneyWood, "chimney.wood");
        craftItmShorthand(chimneyAluminium, "chimney.aluminium");
        craftItmShorthand(chimneyCopper, "chimney.Copper");
        craftItmShorthand(chimneyIron, "chimney.iron");
        craftItmShorthand(chimneySteel, "chimney.steel");
        craftItmShorthand(chimneyFiberglass, "chimney.fiberglass");
        craftItmShorthand(chimneyPlastic, "chimney.plastic");

        //Boilers
        craftItmShorthand(boilerWood, "boiler.wood");
        craftItmShorthand(boilerIron, "boiler.iron");
        craftItmShorthand(boilerSteel, "boiler.steel");
        craftItmShorthand(boilerCopper, "boiler.copper");

        //Engines
        craftItmShorthand(petrolEngine, "petrol.engine");
        craftItmShorthand(smallDieselEngine, "small.diesel.engine");
        craftItmShorthand(mediumDieselEngine, "medium.diesel.engine");
        craftItmShorthand(largeDieselEngine, "large.diesel.engine");
        craftItmShorthand(smallElectricEngine, "small.electric.engine");
        craftItmShorthand(mediumElectricEngine, "medium.electric.engine");
        craftItmShorthand(largeElectricEngine, "large.electric.engine");

        //Seats
        craftItmShorthand(seatsWooden, "seats.wooden");
        craftItmShorthand(seatsIron, "seats.Iron");
        craftItmShorthand(seatsPadded, "seats.padded");
        craftItmShorthand(seatsLuxury, "seats.luxury");

        //Transmissions + Other
        craftItmShorthand(hydraulicTransmission, "hydraulic.transmission");
        craftItmShorthand(pnumaticTransmission, "pneumatic.transmission");
        craftItmShorthand(transformer, "transformer");
        craftItmShorthand(transformerHV, "transformer.hv");
        craftItmShorthand(electricControls, "electric.controls");

        //Other
        craftItmShorthand(enginePiston, "engine.piston");
        craftItmShorthand(cylinder, "cylinder");
        craftItmShorthand(camshaft, "camshaft");
        craftItmShorthand(graphite, "graphite");
        craftItmShorthand(electronicCircuit, "electronic.circuit");
        craftItmShorthand(copperWire, "copper.wire");
        craftItmShorthand(goldWire, "gold.wire");

    }

    private static void craftItmShorthand(Item i, String unlocalizedName){
        TiMGenericRegistry.RegisterItem(i =new Item(),TrainsInMotion.MODID,unlocalizedName,TrainsInMotion.creativeTabCrafting);
    }

}
