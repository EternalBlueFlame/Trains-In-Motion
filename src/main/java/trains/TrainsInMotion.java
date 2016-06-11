package trains;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;
import trains.items.ItemCore;
import trains.utility.CommonProxy;
import trains.utility.TiMTab;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.Item;

import static trains.utility.CommonProxy.keyBindings;

@Mod(modid = TrainsInMotion.MODID, version = TrainsInMotion.VERSION)
public class TrainsInMotion
{
    public static final String MODID = "TrainsinMotion";
    public static final String VERSION = "1.0";

    @Mod.Instance(MODID)
    public static TrainsInMotion instance;
    //define the creative tab, then the items
    public static CreativeTabs creativeTab = new TiMTab(CreativeTabs.getNextID(), "Trains in Motion");;
    //items should be promoted to their own class that contains all the individual items, so we don't clutter the main class.
    public static Item itemSets = new ItemCore().setUnlocalizedName("itemTest").setTextureName(TrainsInMotion.MODID+ ":" + "itemTests");

    //setup the proxy
    @SidedProxy(clientSide = "trains.utility.CommonProxy", serverSide = "trains.utility.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        //this should be a loop for all the items in the itemSet class
        GameRegistry.registerItem(itemSets, itemSets.getUnlocalizedName().substring(5));
        //setup the keybinds for the proxy
        proxy.setKeyBinding("Lamp", Keyboard.KEY_L);;
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);

    }
}
