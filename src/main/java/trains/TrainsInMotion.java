package trains;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import trains.entities.EntityBogie;
import trains.gui.HUDTrain;
import trains.items.TiMTab;
import trains.networking.PacketKeyPress;
import trains.networking.PacketMount;
import trains.networking.PacketRemove;
import trains.registry.GenericRegistry;
import trains.registry.TrainRegistry;
import trains.utility.CommonProxy;
import trains.utility.EventHandler;
import trains.worldgen.OreGen;

/**
 * <h1>Main class</h1>
 * all other classes eventually lead back to this class, which manages the mod as a whole.
 * Build number is defined via:
 *      First number denotes entire rebuild of feature set, for instance in the case of when we move to pure 1.9 development or finish the core stuff for 1.7/1.9.
 *      Second is minor, for rebuilds of individual feature sets not intended to effect the whole, like if we were to rebuild the entire GUI system.
 *      Third is for bugfix and/or minor optimization releases, where as we didn't change any features, but it works better.
 *      fourth is new minor content, like new trains or rollingstock that use existing features.
 *
 */
@Mod(modid = TrainsInMotion.MODID, version = "0.6.0.0pre-alpha", name = "Trains in Motion")
public class TrainsInMotion {

    public static final String MODID = "tim";
    @Mod.Instance(MODID)
    public static TrainsInMotion instance;
    public static CreativeTabs creativeTab = new TiMTab(CreativeTabs.getNextID(), "Trains in Motion");
    //Setup the proxy, this is used for managing some of the client and server specific features.
    @SidedProxy(clientSide = "trains.utility.ClientProxy", serverSide = "trains.utility.CommonProxy")
    public static CommonProxy proxy;


    //instance the network wrapper for each channel.
    public static SimpleNetworkWrapper keyChannel;

    //GUI ID's
    public static final int STEAM_GUI_ID = 200;


    //Instance the event handler, This is used for event based functionality, things like when you right-click an entity.
    public static EventHandler eventHandler = new EventHandler();


    //enums
    public enum transportTypes {
        STEAM,DIESEL,HYDROGEN_DIESEL,ELECTRIC,NUCLEAR_STEAM,NUCLEAR_ELECTRIC,MAGLEV
    }
    public enum inventorySizes{
        TWOxTWO, THREExTWO, THREExTHREE, FOURxTHREE, FOURxFOUR, FIVExFOUR, FIVExFIVE, SIXxFIVE, NINExTHREE, NINExSIX
    }

    /**
     * <h2>load config</h2>
     * we use the pre-init to load the config file.
     * Most of the configs are decided by the proxy, no need to setup controls on the server.
     * any generic settings that effect both come after proxy.loadConfig.
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();
        proxy.loadConfig(config);
        config.save();
    }

    /**
     * <h2>Registries</h2>
     * register everything here.
     * blocks and items handle themselves in their own class, but entities we have to handle a bit different by doing it here.
     *
     * networking we have packets for each major channel type, its more overhead overall but it will help significantly to prevent delays.
     *
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        GenericRegistry.RegisterStuff();

        //loop for registering the entities.
        int index =3;
        cpw.mods.fml.common.registry.EntityRegistry.registerGlobalEntityID(EntityBogie.class, "Bogie", index);
        cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(EntityBogie.class, "Bogie", index, TrainsInMotion.instance, 64, 1, true);
        index++;
        for (TrainRegistry train : TrainRegistry.listTrains()) {
            cpw.mods.fml.common.registry.EntityRegistry.registerGlobalEntityID(train.trainClass, train.entityWorldName, index);
            cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(train.trainClass, train.entityWorldName, index, TrainsInMotion.instance, 64, 1, true);
            GameRegistry.registerItem(train.item, train.item.getUnlocalizedName().substring(5));
            index++;
        }


        //register the networking instances and channels
        TrainsInMotion.keyChannel = NetworkRegistry.INSTANCE.newSimpleChannel("TiM.key");
        TrainsInMotion.keyChannel.registerMessage(PacketKeyPress.Handler.class, PacketKeyPress.class, 1, Side.SERVER);
        TrainsInMotion.keyChannel.registerMessage(PacketMount.Handler.class, PacketMount.class, 2, Side.SERVER);
        TrainsInMotion.keyChannel.registerMessage(PacketRemove.Handler.class, PacketRemove.class, 3, Side.SERVER);

        //register the worldgen
        GameRegistry.registerWorldGenerator(new OreGen(), 0);
        //register the event handler
        MinecraftForge.EVENT_BUS.register(eventHandler);
        FMLCommonHandler.instance().bus().register(eventHandler);

        //register GUI, model renders, Keybinds, and HUD
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
        proxy.register();
        if (event.getSide().isClient()) {
            GenericRegistry.RegisterClientStuff();
            HUDTrain hud = new HUDTrain();
            FMLCommonHandler.instance().bus().register(hud);
            MinecraftForge.EVENT_BUS.register(hud);
        }
    }

}
