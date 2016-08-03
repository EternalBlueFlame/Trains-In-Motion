package trains;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Configuration;
import trains.entities.MinecartExtended;
import trains.gui.HUDTrain;
import trains.registry.BlockRegistry;
import trains.registry.ItemRegistry;
import trains.blocks.LampBlock;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.MinecraftForge;
import trains.networking.PacketGUI;
import trains.networking.PacketKeyPress;
import trains.registry.EntityRegistry;
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

import trains.utility.GenerateOil;

import java.util.List;


//build number is according to feature set.
// First number denotes entire rebuild of feature set, for instance in the case of when we move to pure 1.9 development or finish the core stuff for 1.7/1.9.
// Second is minor, for rebuilds of individual feature sets not intended to effect the whole, like if we were to rebuild the entire GUI system.
// Third is for bugfix and/or minor optimization releases, where as we didn't change any features, but it works better.
// fourth is new minor content, like new trains or rollingstock that use existing features.
@Mod(modid = TrainsInMotion.MODID, version = "0.4.0.0pre-alpha", name = "Trains in Motion")
public class TrainsInMotion {

    /**
     * Setup the variables.
     * We want these to be public, and static if possible, because many features of the mod will need to reference this information later.
     */
    //Create the MODID and the instances of the mod itself along with the client running it.
    public static final String MODID = "tim";
    @Mod.Instance(MODID)
    public static TrainsInMotion instance;
    public Minecraft ClientInstance;
    //Instance the registries that maintain the blocks, items, entities, etc.
    public BlockRegistry blockRegistry = new BlockRegistry();
    public ItemRegistry itemRegistry = new ItemRegistry();
    public EntityRegistry entityRegistry = new EntityRegistry();
    //Instance the creative tab
    public static CreativeTabs creativeTab = new TiMTab(CreativeTabs.getNextID(), "Trains in Motion");
    //Setup the proxy, this is used for managing how each part of the mod acts on it's respective side.
    @SidedProxy(clientSide = "trains.utility.ClientProxy", serverSide = "trains.utility.CommonProxy")
    public static CommonProxy proxy;
    //instance the network wrapper for each channel.
    public static SimpleNetworkWrapper keyChannel;

    //Instance the list of trains/rollingstock that are in the game, this is later used to manage the dynamic lighting.
    //This is ONLY used on client side.
    public static List<MinecartExtended> carts;

    //Instance the event handler, This is used for event based functionality, things like when you right-click an entity.
    public static TiMEventHandler eventHandler = new TiMEventHandler();

    /**
     * Initialize the values for the config file, then load or create the config file.
     */
    private static boolean EnableLights = true;
    public static char KeyLamp = 'l';
    public static char KeyInventory = 'i';
    public static char KeyAccelerate = 'w';
    public static char KeyReverse = 's';
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        EnableLights = config.get(Configuration.CATEGORY_GENERAL, "EnableLamp", true).getBoolean(true);

        config.addCustomCategoryComment("Keybinds", "accepted values are Lowercase a-z, along with the special characters:  ,.;'[]\\`-=");

        KeyInventory = config.getString("InventoryKeybind","Keybinds", "i","").charAt(0);
        KeyLamp = config.getString("LampKeybind","Keybinds", "l","").charAt(0);
        KeyAccelerate = config.getString("AccelerateKeybind","Keybinds", "w","").charAt(0);
        KeyReverse = config.getString("ReverseKeybind","Keybinds", "s","").charAt(0);

        config.save();
    }

    /**
     * register everything here.
     * @param event actual load event
     */
    @EventHandler
    public void init(FMLInitializationEvent event) {
        //item/block/entity registry
        entityRegistry.registerEntities();
        blockRegistry.RegisterBlocks();
        itemRegistry.RegisterItems();

        //register GUI handler
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);

        //register the networking instances and channels
        TrainsInMotion.keyChannel = NetworkRegistry.INSTANCE.newSimpleChannel("TiM.key");
        TrainsInMotion.keyChannel.registerMessage(PacketKeyPress.Handler.class, PacketKeyPress.class, 1, Side.SERVER);
        TrainsInMotion.keyChannel.registerMessage(PacketGUI.Handler.class, PacketGUI.class, 2, Side.SERVER);

        //register the worldgen
        GameRegistry.registerWorldGenerator(new GenerateOil(), 0);

        //register the event handler and the HUD.
        MinecraftForge.EVENT_BUS.register(eventHandler);
        FMLCommonHandler.instance().bus().register(eventHandler);

        //Initialize the HUD
        HUDTrain hud = new HUDTrain();
        FMLCommonHandler.instance().bus().register(hud);
        MinecraftForge.EVENT_BUS.register(hud);

        //register the renders
        proxy.registerRenderers();
    }

    /**
     * this is used to force events from the main thread of the mod, it can create a lot of lag sometimes.
     *
     * Currently it's used to force lighting updates (if enabled in config).
     * This is the best way I have found to do it other than making a fully new lighting system.
     * It also only updates if it's actually needed, to help preserve performance.
     *
     * @param tick the client tick event from the main thread
     */
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent tick) {
        if (EnableLights && tick.phase == TickEvent.Phase.END && ClientInstance.theWorld != null && carts.size() > 0){
            //instance the lamp here because it's more efficient than instancing it every loop.
            for (MinecartExtended cart : carts) {
                if (cart != null && ClientInstance.theWorld.getBlock(cart.lamp.X, cart.lamp.Y, cart.lamp.Z) instanceof LampBlock) {
                    ClientInstance.theWorld.updateLightByType(EnumSkyBlock.Block, cart.lamp.X, cart.lamp.Y, cart.lamp.Z);
                }
            }
        }
    }


    /**
     *
     * @param key the key trying to be parsed
     * @return the key's value to return, wether or not it was valid.
     */
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
            default: {
                System.out.println("Invalid key: " + key);
                return 0;
            }
        }
    }

}
