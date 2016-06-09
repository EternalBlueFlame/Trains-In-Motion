package com.example.examplemod.utility;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TiMTab extends CreativeTabs {
    public TiMTab(int modID, String name) {
        super(modID, name);
    }

    @Override
    public String getTranslatedTabLabel() {
        return super.getTabLabel();
    }
    //get the item that defines the tab icon
    @Override
    public Item getTabIconItem(){return new Item();}

}
