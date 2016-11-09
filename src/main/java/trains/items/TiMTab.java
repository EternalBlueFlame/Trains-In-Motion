package trains.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TiMTab extends CreativeTabs {

    /**
     * <h2>Creative tab</h2>
     * first function instances the tab by handling it through the super.
     * @see CreativeTabs
     *
     * second show's the lable of the tab, this is defined in the language files, I think.
     *
     * Third part sets the icon for the tab.
     * TODO we don't actually have an icon for the mod yet.
     */
    public TiMTab(int modID, String name) {
        super(modID, name);
    }
    @Override
    public String getTranslatedTabLabel() {
        return getTabLabel();
    }
    @Override
    public Item getTabIconItem(){return new Item();}

}
