package com.example.examplemod;

import com.example.examplemod.items.ItemCore;
import com.example.examplemod.utility.TiMTab;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.Item;

@Mod(modid = TrainsInMotion.MODID, version = TrainsInMotion.VERSION)
public class TrainsInMotion
{
    public static final String MODID = "TrainsinMotion";
    public static final String VERSION = "1.0";
    //define the creative tab, then the items
    public static CreativeTabs creativeTab;
    //items should be promoted to their own class that contains all the individual items, so we don't clutter the main class.
    public static Item itemSets;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        //register tab before items
        creativeTab = new TiMTab(CreativeTabs.getNextID(), "Trains in Motion");

        //this should be defined in the create method of a new itemSet class.
        itemSets = new ItemCore().setUnlocalizedName("itemTest").setTextureName(TrainsInMotion.MODID+ ":" + "itemTests");
        //this should be a loop for all the items in the itemSet class
        GameRegistry.registerItem(itemSets, itemSets.getUnlocalizedName().substring(5));

    }
}
