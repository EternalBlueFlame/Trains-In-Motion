package com.example.examplemod;

import com.example.examplemod.items.ItemCore;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.Item;

@Mod(modid = ExampleMod.MODID, version = ExampleMod.VERSION)
public class ExampleMod
{
    public static final String MODID = "examplemod";
    public static final String VERSION = "1.0";


    public static CreativeTabs creativeTab;

    public static Item itemSets;

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

        itemSets = new ItemCore().setUnlocalizedName("itemTest").setTextureName(ExampleMod.MODID+ ":" + "itemTests");

        GameRegistry.registerItem(itemSets, itemSets.getUnlocalizedName().substring(5));
    }
}
