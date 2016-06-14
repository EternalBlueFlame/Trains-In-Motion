package trains;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;
import trains.entities.MinecartExtended;
import trains.items.ItemCore;
import trains.networking.PacketGUI;
import trains.networking.PacketKeyPress;
import trains.utility.CommonProxy;
import trains.utility.TiMEventHandler;
import trains.utility.TiMTab;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.Item;

@Mod(modid = TrainsInMotion.MODID, version = TrainsInMotion.VERSION)
public class TrainsInMotion
{
    public static final String MODID = "trainsinmotion";
    public static final String VERSION = "1.0pre-alpha";

    //resource directories
    public enum enumResources{RESOURCES("tim"), GUI_PREFIX("textures/gui/"),
        MODEL_TRAIN("models/train/"), MODEL_TRAIN_TEXTURE("models/train/texture/"),
        MODEL_ROLLINGSTOCK("models/rollingstock/"), MODEL_ROLLINGSTOCK_TEXTURE("models/rollingstock/texture/"),
        MODEL_RAIL("models/rail"), MODEL_RAIL_TEXTURES("models/rail/texture");
        private String value;
        enumResources(String value) {this.value = value;}
        public String getValue(){return value;}
    };

    @Mod.Instance(MODID)
    public static TrainsInMotion instance;
    //define the creative tab, then the items
    public static CreativeTabs creativeTab = new TiMTab(CreativeTabs.getNextID(), "Trains in Motion");
    //items should be promoted to their own class that contains all the individual items, so we don't clutter the main class.
    public static Item itemSets = new ItemCore().setUnlocalizedName("itemTest").setTextureName(MODID + ":itemTests");

    //setup the proxy
    @SidedProxy(clientSide = "trains.utility.ClientProxy", serverSide = "trains.utility.CommonProxy")
    public static CommonProxy proxy;


    //create the networking channel
    public static SimpleNetworkWrapper keyChannel;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        //register the entity
        int id = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(MinecartExtended.class, "entitytraincore", id);
        EntityRegistry.registerModEntity(MinecartExtended.class, "entitytraincore", id, instance, 64, 1, true);

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        //this should be a loop for all the items in the itemSet class
        GameRegistry.registerItem(itemSets, itemSets.getUnlocalizedName().substring(5));
        //setup the keybinds for the proxy
        proxy.setKeyBinding("Lamp", Keyboard.KEY_L);
        //register GUI handler
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);

        //register the networking instances and channels
        TrainsInMotion.keyChannel = NetworkRegistry.INSTANCE.newSimpleChannel("TiM.key");
        TrainsInMotion.keyChannel.registerMessage(PacketKeyPress.Handler.class, PacketKeyPress.class, 1, Side.SERVER);
        TrainsInMotion.keyChannel.registerMessage(PacketGUI.Handler.class, PacketGUI.class, 2, Side.SERVER);

        MinecraftForge.EVENT_BUS.register(new TiMEventHandler());
        FMLCommonHandler.instance().bus().register(new TiMEventHandler());
    }
}
