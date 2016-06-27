package trains;

import net.minecraft.block.Block;
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
import trains.entities.MinecartExtended;
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

import java.util.List;


@Mod(modid = TrainsInMotion.MODID, version = TrainsInMotion.VERSION)
public class TrainsInMotion
{
    public static final String MODID = "trainsinmotion";
    public static final String VERSION = "1.0pre-alpha";

    private Minecraft ClientInstance;
    public static List<MinecartExtended> carts;

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
    //create the item registry
    public  ItemRegistry itemSets = new ItemRegistry();
    //initialize the lamp block
    public static Block lampBlock = new LampBlock();

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

        //register the lamp block
        GameRegistry.registerBlock(lampBlock, "lampblock");

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
        if (tick.phase == TickEvent.Phase.END && ClientInstance.theWorld != null && carts.size()>0) {
            for (MinecartExtended cart : carts){
                if (cart != null && cart.lamp.X !=0 && cart.lamp.Y != 0 && cart.lamp.Z !=0 && ClientInstance.theWorld.getBlock(cart.lamp.X, cart.lamp.Y, cart.lamp.Z) instanceof LampBlock) {
                    ClientInstance.theWorld.updateLightByType(EnumSkyBlock.Block, cart.lamp.X, cart.lamp.Y, cart.lamp.Z);
                }
            }
        }
    }

}
