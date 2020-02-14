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

    public static Item enginePiston,cylinder,camshaft,graphite,electronicCircuit,copperWire,goldWire;

    public static Item railItem;


    public static void registerItems(){

        RegisterItem(new ItemAdminBook(),TrainsInMotion.MODID, "adminbook", TrainsInMotion.creativeTab);
        RegisterItem(new ItemCraftGuide(),TrainsInMotion.MODID, "craftbook", TrainsInMotion.creativeTab);

        RegisterItem(new ItemPaintBucket(),TrainsInMotion.MODID, "paintbucket", TrainsInMotion.creativeTab);
        RegisterItem(new ItemKey(),TrainsInMotion.MODID,  "transportkey", TrainsInMotion.creativeTab);
        RegisterItem(new ItemTicket(),TrainsInMotion.MODID,  "transportticket", TrainsInMotion.creativeTab);

        //All crafting items here
        //Cabins
        RegisterItem(cabinWood, TrainsInMotion.MODID, "cabinwood", TrainsInMotion.creativeTabCrafting);
        RegisterItem(cabinAluminium , TrainsInMotion.MODID, "cabinaluminium", TrainsInMotion.creativeTabCrafting);
        RegisterItem(cabinCopper, TrainsInMotion.MODID, "cabincopper", TrainsInMotion.creativeTabCrafting);
        RegisterItem(cabinIron, TrainsInMotion.MODID, "cabiniron", TrainsInMotion.creativeTabCrafting);
        RegisterItem(cabinSteel, TrainsInMotion.MODID, "cabinsteel", TrainsInMotion.creativeTabCrafting);
        RegisterItem(cabinFiberglass, TrainsInMotion.MODID, "cabinfiberglass", TrainsInMotion.creativeTabCrafting);
        RegisterItem(cabinPlastic, TrainsInMotion.MODID, "cabinplastic", TrainsInMotion.creativeTabCrafting);

        //Frames
        RegisterItem(frameWood, TrainsInMotion.MODID, "framewood", TrainsInMotion.creativeTabCrafting);
        RegisterItem(frameAluminium, TrainsInMotion.MODID, "framealuminium", TrainsInMotion.creativeTabCrafting);
        RegisterItem(frameCopper, TrainsInMotion.MODID, "framecopper", TrainsInMotion.creativeTabCrafting);
        RegisterItem(frameIron, TrainsInMotion.MODID, "frameiron", TrainsInMotion.creativeTabCrafting);
        RegisterItem(frameSteel, TrainsInMotion.MODID, "framesteel", TrainsInMotion.creativeTabCrafting);
        RegisterItem(frameFiberglass, TrainsInMotion.MODID, "framefiberglass", TrainsInMotion.creativeTabCrafting);
        RegisterItem(framePlastic, TrainsInMotion.MODID, "frameplastic", TrainsInMotion.creativeTabCrafting);

        //Wheels
        RegisterItem(wheelWood, TrainsInMotion.MODID, "wheelwood", TrainsInMotion.creativeTabCrafting);
        RegisterItem(wheelAluminium, TrainsInMotion.MODID, "wheelaluminium", TrainsInMotion.creativeTabCrafting);
        RegisterItem(wheelIron, TrainsInMotion.MODID, "wheeliron", TrainsInMotion.creativeTabCrafting);
        RegisterItem(wheelSteel, TrainsInMotion.MODID, "wheelsteel", TrainsInMotion.creativeTabCrafting);
        RegisterItem(wheelPlastic, TrainsInMotion.MODID, "wheelplastic", TrainsInMotion.creativeTabCrafting);

        //Chimneys
        RegisterItem(chimneyWood, TrainsInMotion.MODID, "chimneywood", TrainsInMotion.creativeTabCrafting);
        RegisterItem(chimneyAluminium, TrainsInMotion.MODID, "chimneyaluminium", TrainsInMotion.creativeTabCrafting);
        RegisterItem(chimneyCopper, TrainsInMotion.MODID, "chimneycopper", TrainsInMotion.creativeTabCrafting);
        RegisterItem(chimneyIron, TrainsInMotion.MODID, "chimneyiron", TrainsInMotion.creativeTabCrafting);
        RegisterItem(chimneySteel, TrainsInMotion.MODID, "chimneySteel", TrainsInMotion.creativeTabCrafting);
        RegisterItem(chimneyFiberglass, TrainsInMotion.MODID, "chimneyfiberglass", TrainsInMotion.creativeTabCrafting);
        RegisterItem(chimneyPlastic, TrainsInMotion.MODID, "chimneyplastic", TrainsInMotion.creativeTabCrafting);

        //Boilers
        RegisterItem(boilerWood, TrainsInMotion.MODID, "boilerwood", TrainsInMotion.creativeTabCrafting);
        RegisterItem(boilerIron, TrainsInMotion.MODID,"boileriron", TrainsInMotion.creativeTabCrafting);
        RegisterItem(boilerSteel, TrainsInMotion.MODID, "boilersteel", TrainsInMotion.creativeTabCrafting);
        RegisterItem(boilerCopper, TrainsInMotion.MODID, "boilercopper", TrainsInMotion.creativeTabCrafting);

        //Engines
        RegisterItem(petrolEngine, TrainsInMotion.MODID, "petrolengine", TrainsInMotion.creativeTabCrafting);
        RegisterItem(smallDieselEngine, TrainsInMotion.MODID, "smalldieselengine", TrainsInMotion.creativeTabCrafting);
        RegisterItem(mediumDieselEngine, TrainsInMotion.MODID, "mediumdieselengine", TrainsInMotion.creativeTabCrafting);
        RegisterItem(largeDieselEngine, TrainsInMotion.MODID, "largedieselengine", TrainsInMotion.creativeTabCrafting);
        RegisterItem(smallElectricEngine, TrainsInMotion.MODID, "smallelectricengine", TrainsInMotion.creativeTabCrafting);
        RegisterItem(mediumElectricEngine, TrainsInMotion.MODID, "mediumelectricengine", TrainsInMotion.creativeTabCrafting);
        RegisterItem(largeElectricEngine, TrainsInMotion.MODID, "largeelectricengine", TrainsInMotion.creativeTabCrafting);

        //Seats
        RegisterItem(seatsWooden, TrainsInMotion.MODID, "seatswooden", TrainsInMotion.creativeTabCrafting);
        RegisterItem(seatsIron, TrainsInMotion.MODID, "seatsiron", TrainsInMotion.creativeTabCrafting);
        RegisterItem(seatsPadded, TrainsInMotion.MODID, "seatspadded", TrainsInMotion.creativeTabCrafting);
        RegisterItem(seatsLuxury, TrainsInMotion.MODID, "seatsluxury", TrainsInMotion.creativeTabCrafting);

        //Transmissions + Other
        RegisterItem(hydraulicTransmission, TrainsInMotion.MODID, "hydraulictransmission", TrainsInMotion.creativeTabCrafting);
        RegisterItem(pneumaticTransmission, TrainsInMotion.MODID, "pneumatictransmission", TrainsInMotion.creativeTabCrafting);
        RegisterItem(transformer, TrainsInMotion.MODID, "transformer", TrainsInMotion.creativeTabCrafting);
        RegisterItem(transformerHV, TrainsInMotion.MODID, "transformerhv", TrainsInMotion.creativeTabCrafting);
        RegisterItem(electricControls, TrainsInMotion.MODID, "electriccontrols", TrainsInMotion.creativeTabCrafting);

        //Other
        RegisterItem(enginePiston, TrainsInMotion.MODID, "enginepiston", TrainsInMotion.creativeTabCrafting);
        RegisterItem(cylinder, TrainsInMotion.MODID, "cylinder", TrainsInMotion.creativeTabCrafting);
        RegisterItem(camshaft, TrainsInMotion.MODID, "camshaft", TrainsInMotion.creativeTabCrafting);
        RegisterItem(graphite, TrainsInMotion.MODID, "graphite", TrainsInMotion.creativeTabCrafting);
        RegisterItem(electronicCircuit, TrainsInMotion.MODID, "electroniccircuit", TrainsInMotion.creativeTabCrafting);
        RegisterItem(copperWire, TrainsInMotion.MODID, "copperwire", TrainsInMotion.creativeTabCrafting);
        RegisterItem(goldWire, TrainsInMotion.MODID, "goldwire", TrainsInMotion.creativeTabCrafting);


        //overides the server registration of the rail item, so the client can have a complex model.
        //   server can't load the CustomItemModel class due to it's reliance on GL imports.
        if(!TrainsInMotion.proxy.isClient()) {
            railItem = RegisterItem( new ItemRail(), TrainsInMotion.MODID, "timrail", TrainsInMotion.creativeTab);
        } else {
            railItem = RegisterItem(new ItemRail(),TrainsInMotion.MODID,  "timrail", null, TrainsInMotion.creativeTab, null, ebf.tim.items.CustomItemModel.instance);
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
        craftItmShorthand(pneumaticTransmission, "pneumatic.transmission");
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
        TiMGenericRegistry.RegisterItem(i=new Item(),TrainsInMotion.MODID,unlocalizedName,TrainsInMotion.creativeTabCrafting);
    }

}
