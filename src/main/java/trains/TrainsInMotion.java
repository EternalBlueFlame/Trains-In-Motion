package trains;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import trains.entities.MinecartExtended;
import trains.registry.ItemRegistry;
import trains.blocks.LampBlock;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
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

import WorldGen.GenerateOil;


@Mod(modid = TrainsInMotion.MODID, version = TrainsInMotion.VERSION, name = "Trains in Motion")
public class TrainsInMotion
{
    public static final String MODID = "tim";
    public static final String VERSION = "1.0pre-alpha";
    public static List<MinecartExtended> carts;


    //resource directories
    public enum Resources{GUI_PREFIX("textures/gui/"),
        MODEL_TRAIN("models/train/"), MODEL_TRAIN_TEXTURE("models/train/textures/"),
        MODEL_ROLLINGSTOCK("models/rollingstock/"), MODEL_ROLLINGSTOCK_TEXTURE("models/rollingstock/textures/"),
        MODEL_RAIL("models/rail/"), MODEL_RAIL_TEXTURES("models/rail/textures/"),
        TEXTURE("textures/");
        private String value;
        Resources(String value) {this.value = value;}
        public String getValue(){return value;}

        public ResourceLocation getResourceLocation(String fileName){return new ResourceLocation(MODID, value + fileName);}
    };

    /**
     * initialize all the instances, creative tabs, the lamp handler, and the block/item/entity registries.
     * most of these we will have to actually setup later, in other parts of the mod.
     *
     * setup the proxy
     * @see CommonProxy
     * @see trains.utility.ClientProxy
     *
     * setup the networking channels'
     *
     * lastly setup the config values.
     */
    @Mod.Instance(MODID)
    public static TrainsInMotion instance;
    public Minecraft ClientInstance;
    public static CreativeTabs creativeTab = new TiMTab(CreativeTabs.getNextID(), "Trains in Motion");
    public  ItemRegistry itemSets = new ItemRegistry();
    public TiMEntityRegistry entities = new TiMEntityRegistry();
    public static Block lampBlock = new LampBlock();

    @SidedProxy(clientSide = "trains.utility.ClientProxy", serverSide = "trains.utility.CommonProxy")
    public static CommonProxy proxy;

    //create the networking channel
    public static SimpleNetworkWrapper keyChannel;

    //setup the config values
    private boolean EnableLights = true;
    public char KeyLamp = 'l';
    public char KeyInventory = 'i';
    public char KeyAccelerate = 'w';
    public char KeyReverse = 's';

    /**
     * register the entities and load/create the config file.
     * @param event pre-load event.
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        //register the entities
        entities.registerEntities();

        //handle the config file
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
     * register the items and blocks here, do not register them in pre-int, it creates incompatibilities with other mods.
     *
     * register the GUI handler
     *
     * register the channels for networking
     *
     * lastly register the event handler.
     *
     * @param event actual load event
     */
    @EventHandler
    public void init(FMLInitializationEvent event) {
        //item/block registry
        GameRegistry.registerBlock(lampBlock, "lampblock");
        itemSets.RegisterItems();
        GameRegistry.registerWorldGenerator(new GenerateOil(), 0);
        //register GUI handler
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);

        //register the networking instances and channels
        TrainsInMotion.keyChannel = NetworkRegistry.INSTANCE.newSimpleChannel("TiM.key");
        TrainsInMotion.keyChannel.registerMessage(PacketKeyPress.Handler.class, PacketKeyPress.class, 1, Side.SERVER);
        TrainsInMotion.keyChannel.registerMessage(PacketGUI.Handler.class, PacketGUI.class, 2, Side.SERVER);

        MinecraftForge.EVENT_BUS.register(new TiMEventHandler());
        FMLCommonHandler.instance().bus().register(new TiMEventHandler());

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
        if (EnableLights && tick.phase == TickEvent.Phase.END && ClientInstance.theWorld != null && carts.size()>0) {
            //instance the lamp here because it's more efficient than instancing it every loop.
            for (MinecartExtended cart : carts){
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
