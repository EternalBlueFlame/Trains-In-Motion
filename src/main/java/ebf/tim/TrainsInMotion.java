package ebf.tim;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import ebf.tim.blocks.OreGen;
import ebf.tim.entities.EntityBogie;
import ebf.tim.entities.EntitySeat;
import ebf.tim.items.TiMTab;
import ebf.tim.networking.PacketKeyPress;
import ebf.tim.networking.PacketMount;
import ebf.tim.networking.PacketRemove;
import ebf.tim.networking.PacketRespawn;
import ebf.tim.registry.GenericRegistry;
import ebf.tim.registry.TransportRegistry;
import ebf.tim.utility.ChunkHandler;
import ebf.tim.utility.ClientProxy;
import ebf.tim.utility.CommonProxy;
import ebf.tim.utility.EventManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;


/**
 * <h1>Main class</h1>
 * this class, which manages the mod as a whole.
 * Build number is defined via:
 *      First number denotes entire rebuild of feature set, for instance in the case of when we move to pure 1.9 development or finish the core stuff for 1.7/1.9.
 *      Second is minor, for rebuilds of individual feature sets not intended to effect the whole, like if we were to rebuild the entire GUI system.
 *      Third is for bugfix and/or minor optimization releases, where as we didn't change any features, but it works better.
 *      fourth is new minor content, like new trains or rollingstock that use existing features.
 *
 * @author Eternal Blue Flame
 */
@Mod(modid = TrainsInMotion.MODID, version = TrainsInMotion.MOD_VERSION, name = "Trains in Motion")
public class TrainsInMotion {

    /**the ID of the mod and the version displayed in game, as well as used for version check in the version.txt file*/
    public static final String MODID = "tim";
    /**the version identifier of the mod*/
    public static final String MOD_VERSION="0.2.5.0 alpha";
    /**an instance of the mod*/
    @Mod.Instance(MODID)
    public static TrainsInMotion instance;
    /**the creative tab for the mod*/
    public static CreativeTabs creativeTab = new TiMTab(CreativeTabs.getNextID(), "Trains in Motion");
    /**
     *Setup the proxy, this is used for managing some of the client and server specific features.
     *@see CommonProxy
     *@see ClientProxy
     */
    @SidedProxy(clientSide = "ebf.tim.utility.ClientProxy", serverSide = "ebf.tim.utility.CommonProxy")
    private static CommonProxy proxy;

    /**instance the network wrapper for the channels.
     * Every wrapper runs on it's own thread, so heavy traffic should go on it's own wrapper, using channels to separate packet types.*/
    public static SimpleNetworkWrapper keyChannel;


    /**Instance the event handler, This is used for event based functionality, things like when you right-click an entity.*/
    private static EventManager eventManager = new EventManager();

    /**Instance a new chunk handler, this class manages chunk loading events and functionality.*/
    private static ChunkHandler chunkHandler = new ChunkHandler();

    /**
     * <h3>enums</h3>
     *
     */

     /**define the transport types*/
    public enum transportTypes {
        STEAM,DIESEL,HYDROGEN_DIESEL,ELECTRIC,NUCLEAR_STEAM,NUCLEAR_ELECTRIC,MAGLEV, //trains
        PASSENGER, FREIGHT, HOPPER, TANKER, WORKCAR, SLUG, B_UNIT, //generic rollingstock
        LOGCAR, RAILCAR, FREEZERCAR, LAVATANKER, GRAINHOPPER, COALHOPPER, OILCAR, //specific cargo rollingstock
        FUELTANKER, TENDER, ELECTRIC_TENDER, JUKEBOX, TRACKBUILDER; //specialized Rollingstock

         public boolean isTrain(){
             return this == STEAM || this == DIESEL || this == HYDROGEN_DIESEL || this == ELECTRIC ||
                     this == NUCLEAR_STEAM || this == NUCLEAR_ELECTRIC || this == MAGLEV || this == B_UNIT;
         }
         public boolean isHopper(){
             return this == HOPPER || this == GRAINHOPPER || this == COALHOPPER;
         }
         public boolean isTanker(){
             return this == TANKER || this == LAVATANKER || this == OILCAR || this == FUELTANKER;
         }
    }
    /**define the inventory size values, this lets us get values to define rows and columns rather than just overall size.*/
    public enum inventorySizes{NULL(0), CRAFTING(1),
        FREIGHT_ONE(1), FREIGHT_TWO(2), FREIGHT_THREE(3), FREIGHT_FOUR(4), FREIGHT_FIVE(5), FREIGHT_SIX(6),
        FREIGHT_SEVEN(7), FREIGHT_EIGHT(8), FREIGHT_NINE(9), FREIGHT_TEN(10), FREIGHT_ELEVEN(11);
        private int row;
        inventorySizes(int row){
            this.row = row;
        }
        public int getRow() {
            return row;
        }
    }
    /**defines the type of block, so that way our generic block classes can change the functionality without needing a bunch of different classes.*/
    public enum blockTypes {
        CRAFTING, CONTAINER, COSMETIC, SWITCH
    }

    /**
     * <h2>load config</h2>
     * we use the pre-init to load the config file.
     * Most of the configs are decided by the proxy, no need to setup controls on the server.
     */
    @SuppressWarnings("unused")
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();
        proxy.loadConfig(config);
        //settings that effect client and server here.
        config.save();
        ForgeChunkManager.setForcedChunkLoadingCallback(TrainsInMotion.instance, chunkHandler);
        MinecraftForge.EVENT_BUS.register(chunkHandler);
    }

    /**
     * <h2>Registries</h2>
     * register everything here.
     * blocks and items handle themselves in their own class, but entities we have to handle a bit different by doing it here.
     *
     * networking we have packets for each major channel type, its more overhead overall but it will help significantly to prevent delays.
     *
     * This could be done in pre-init but that would brake compatibility with Dragon API and a number of 3rd party mods.
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        GenericRegistry.RegisterStuff();

        //loop for registering the entities. the values needed are the class, entity name, entity ID, mod instance, update range, update rate, and if it does velocity things,
        cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(EntityBogie.class, "Bogie", 15, TrainsInMotion.instance, 60, 1, true);
        cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(EntitySeat.class, "Seat", 16, TrainsInMotion.instance, 60, 2, true);
        int index =0;
        ///now we loop for every value in the train registry and registry it, when the index reaches a null value, then it will stop.
        while (TransportRegistry.listTrains(index)!=null) {
            TransportRegistry registry = TransportRegistry.listTrains(index);
            cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(registry.trainClass, registry.item.getUnlocalizedName().replace("item","entity"), index+17, TrainsInMotion.instance, 60, 1, true);
            GameRegistry.registerItem(registry.item, registry.item.getUnlocalizedName().substring(5));
            index++;
        }


        //register the networking instances and channels
        TrainsInMotion.keyChannel = NetworkRegistry.INSTANCE.newSimpleChannel("TiM.key");
        TrainsInMotion.keyChannel.registerMessage(PacketKeyPress.Handler.class, PacketKeyPress.class, 1, Side.SERVER);
        TrainsInMotion.keyChannel.registerMessage(PacketMount.Handler.class, PacketMount.class, 2, Side.SERVER);
        TrainsInMotion.keyChannel.registerMessage(PacketRemove.Handler.class, PacketRemove.class, 3, Side.SERVER);
        TrainsInMotion.keyChannel.registerMessage(PacketRespawn.Handler.class, PacketRespawn.class, 4, Side.SERVER);

        //register the worldgen
        GameRegistry.registerWorldGenerator(new OreGen(), 0);
        //register the event handler
        MinecraftForge.EVENT_BUS.register(eventManager);
        FMLCommonHandler.instance().bus().register(eventManager);

        //register GUI, model renders, Keybinds, client only blocks, and HUD
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
        proxy.register();
    }

}
