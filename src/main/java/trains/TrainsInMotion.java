package trains;

import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;
import trains.registry.ItemRegistry;
import trains.blocks.LampBlock;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.MinecraftForge;
import trains.networking.PacketGUI;
import trains.networking.PacketKeyPress;
import trains.registry.TiMEntityRegistry;
import trains.utility.CommonProxy;
import trains.utility.LampHandler;
import trains.utility.TiMEventHandler;
import trains.utility.TiMTab;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

import java.util.List;


@Mod(modid = TrainsInMotion.MODID, version = TrainsInMotion.VERSION)
public class TrainsInMotion
{
    public static final String MODID = "trainsinmotion";
    public static final String VERSION = "1.0pre-alpha";

    public Minecraft ClientInstance;
    public static List<LampHandler> cartLamps;

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
    //define the creative tab
    public static CreativeTabs creativeTab = new TiMTab(CreativeTabs.getNextID(), "Trains in Motion");
    //create the registries
    public  ItemRegistry itemSets = new ItemRegistry();
    public TiMEntityRegistry entities = new TiMEntityRegistry();

    //initialize the lamp block
    public static Block lampBlock = new LampBlock();

    //setup the proxy
    @SidedProxy(clientSide = "trains.utility.ClientProxy", serverSide = "trains.utility.CommonProxy")
    public static CommonProxy proxy;


    //create the networking channel
    public static SimpleNetworkWrapper keyChannel;


    //setup the config values
    private boolean EnableLights = true;
    public char KeyLamp = 'l';
    public char KeyInventory = 'i';


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        //register the entities
        entities.registerEntities();

        //register the lamp block
        GameRegistry.registerBlock(lampBlock, "lampblock");

        //handle the config file
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        EnableLights = config.get(Configuration.CATEGORY_GENERAL, "EnableLamp", true).getBoolean(true);

        config.addCustomCategoryComment("Keybinds", "accepted values are Lowercase a-z, along with the special characters:  ,.;'[]\\`-=");
        KeyInventory = config.getString("InventoryKeybind","Keybinds", "i","").charAt(0);
        KeyLamp = config.getString("LampKeybind","Keybinds", "l","").charAt(0);

        config.save();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        //register the items
        itemSets.RegisterItems();
        //register GUI handler
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);

        //register the networking instances and channels
        TrainsInMotion.keyChannel = NetworkRegistry.INSTANCE.newSimpleChannel("TiM.key");
        TrainsInMotion.keyChannel.registerMessage(PacketKeyPress.Handler.class, PacketKeyPress.class, 1, Side.SERVER);
        TrainsInMotion.keyChannel.registerMessage(PacketGUI.Handler.class, PacketGUI.class, 2, Side.SERVER);

        MinecraftForge.EVENT_BUS.register(new TiMEventHandler());
        FMLCommonHandler.instance().bus().register(new TiMEventHandler());
    }


    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent tick) {
        //handle processing lamps on the mod's main thread to be sure they update, doing this from any other thread is unreliable.
        if (EnableLights && tick.phase == TickEvent.Phase.END && ClientInstance.theWorld != null && cartLamps.size()>0) {
            //instance the lamp here because it's more efficient than instancing it every loop.
            LampHandler tempLamp = new LampHandler();
            for (LampHandler lamp : cartLamps){
                if (lamp != tempLamp && ClientInstance.theWorld.getBlock(lamp.X, lamp.Y, lamp.Z) instanceof LampBlock) {
                    ClientInstance.theWorld.updateLightByType(EnumSkyBlock.Block, lamp.X, lamp.Y, lamp.Z);
                }
            }
        }
    }


    public static int parseKey(char key){
        switch (key){
            case 'a' :{return 30;}
            case 'b' :{return 48;}
            case 'c' :{return 46;}
            case 'd' :{return 32;}
            case 'e' :{return 18;}
            case 'f' :{return 33;}
            case 'g' :{return 34;}
            case 'h' :{return 35;}
            case 'i' :{return 23;}
            case 'j' :{return 36;}
            case 'k' :{return 37;}
            case 'l' :{return 38;}
            case 'm' :{return 50;}
            case 'n' :{return 49;}
            case 'o' :{return 24;}
            case 'p' :{return 25;}
            case 'q' :{return 16;}
            case 'r' :{return 19;}
            case 's' :{return 31;}
            case 't' :{return 20;}
            case 'u' :{return 22;}
            case 'v' :{return 47;}
            case 'w' :{return 17;}
            case 'x' :{return 45;}
            case 'y' :{return 21;}
            case 'z' :{return 44;}
            case ',' :{return 51;}
            case '.' :{return 52;}
            case '/' :{return 53;}
            case ';' :{return 39;}
            case '[' :{return 26;}
            case ']' :{return 27;}
            case '\\':{return 43;}
            case '\'':{return 40;}
            case '-' :{return 12;}
            case '=' :{return 13;}
            case '`' :{return 41;}
            default: {return 0;}
        }
    }

}
