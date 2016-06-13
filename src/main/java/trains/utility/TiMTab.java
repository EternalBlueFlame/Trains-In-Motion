package trains.utility;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TiMTab extends CreativeTabs {
    //init
    public TiMTab(int modID, String name) {
        super(modID, name);
    }
    //set tab name
    @Override
    public String getTranslatedTabLabel() {
        return getTabLabel();
    }
    //get the item that defines the tab icon
    @Override
    public Item getTabIconItem(){return new Item();}

}
