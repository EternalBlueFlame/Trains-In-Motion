package ebf.tim.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * <h1>Creative tab</h1>
 * defines the creative tab's information.
 * @author Eternal Blue Flame
 */
public class TiMTab extends CreativeTabs {

    /**instances the tab by handling it through the super.*/
    public TiMTab(int modID, String name) {
        super(modID, name);
    }
    /**returns the label of the tab, this is defined in the language files,*/
    @Override
    public String getTranslatedTabLabel() {
        return getTabLabel();
    }
    /**the icon for the tab.
     * TODO we don't actually have an icon for the mod yet.*/
    @Override
    public Item getTabIconItem(){return new Item();}

}
