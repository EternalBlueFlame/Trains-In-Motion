package ebf.tim.utility;


import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import ebf.tim.blocks.BlockRailOverride;
import ebf.tim.blocks.LampBlock;
import ebf.tim.blocks.TileEntityStorage;
import ebf.tim.entities.EntityBogie;
import ebf.tim.entities.EntitySeat;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.gui.GUITrainTable;
import ebf.tim.gui.GUITransport;
import ebf.tim.gui.HUDTrain;
import ebf.tim.models.RenderEntity;
import ebf.tim.models.RenderScaledPlayer;
import ebf.tim.registry.TransportRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>client proxy</h1>
 * defines some of the more important client-only functionality that runs on the main thread of the mod.
 * @author Eternal Blue Flame
 */
public class ClientProxy extends CommonProxy {
    public static List<GenericRailTransport> carts = new ArrayList<GenericRailTransport>();

    /*
     * <h3>keybinds</h3>
     * Initialize the default values for keybinds.
     * Default values courtesy of Ferdinand
     */
    /**whether or not lights should be enabled*/
    public static boolean EnableLights = true;
    /**whether or not smoke and steam should be enabled*/
    public static boolean EnableSmokeAndSteam = true;
    /**whether or not animations should be enabled*/
    public static boolean EnableAnimations = true;
    /**whether or not to use the 3D rails*/
    public static boolean Enable3DRails = true;
    /**whether or not to use HD skins*/
    public static boolean useHDSkins = false;
    /**whether or not to force texture binding*/
    public static boolean ForceTextureBinding = false;
    /**defines if the inventory graphics should be loaded from a TiM URI or if vanilla graphics should be used*/
    public static boolean useVanillaInventoryTextures = true;
    /**the keybind for the lamp toggle*/
    public static KeyBinding KeyLamp = new KeyBinding("Lamp Toggle", Keyboard.KEY_L, "Trains in Motion");
    /**the keybind for the horn/whistle*/
    public static KeyBinding KeyHorn = new KeyBinding("Use Horn/Whistle", Keyboard.KEY_H, "Trains in Motion");
    /**the keybind for opening the inventory*/
    public static KeyBinding KeyInventory = new KeyBinding("Open Train/rollingstock GUI",  Keyboard.KEY_I, "Trains in Motion");

    /**
     * <h2> Client GUI Redirect </h2>
     *
     * Mostly a redirect between the event handler and the actual GUI
     *
     * defines the GUI element to display based on the ID
     * returns null if the player, cart or ID is invalid
     *
     * the inventory manager that server uses for this menu is defined in
     * @see CommonProxy#getServerGuiElement(int, EntityPlayer, World, int, int, int)
     */
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (player != null) {
            //Trains
            if (player.worldObj.getEntityByID(ID) instanceof GenericRailTransport) {
                return new GUITransport(player.inventory, (GenericRailTransport) player.worldObj.getEntityByID(ID));
                //tile entities
            } else if (player.worldObj.getTileEntity(x,y,z) instanceof TileEntityStorage) {
                return new GUITrainTable(player.inventory, player.worldObj, x, y, z);
            }
        }
        return null;
    }

    /**
     * <h2>Load config</h2>
     * this loads the config values that will only effect client.
     */
    @Override
    public void loadConfig(Configuration config){
        super.loadConfig(config);
        config.addCustomCategoryComment("Quality (Client only)", "Lamps take up a lot of extra processing on client side due to forced chunk reloading");
        EnableLights = config.get("Quality (Client only)", "EnableLamp", true).getBoolean(true);
        config.addCustomCategoryComment("Quality (Client only)", "Smoke and steam effects are more lightweight than those of normal minecraft. These shouldn't cause much lag if any, but its client only so if you wanna disable it you can.");
        EnableSmokeAndSteam = config.get("Quality (Client only)", "EnableSmokeAndSteam", true).getBoolean(true);
        config.addCustomCategoryComment("Quality (Client only)", "Animations are calculated by vector positioning and rotation every frame. These shouldn't cause much lag if any, but its client only so if you wanna disable it you can.");
        EnableAnimations = config.get("Quality (Client only)", "EnableAnimations", true).getBoolean(true);
        config.addCustomCategoryComment("Quality (Client only)", "Overrides the render of vanilla rails to make them use a more detailed 3D render which supports more detailed switches and diagonals.");
        Enable3DRails = config.get("Quality (Client only)", "Enable3DRails", false).getBoolean(false);
        config.addCustomCategoryComment("Quality (Client only)", "Overrides the render of train and rollingstock inventories to use textures from vanilla (including resourcepacks), so you can use textures in a texturepack specifically for this mod");
        useVanillaInventoryTextures = config.get("Quality (Client only)", "UseVanillaInventoryTextures", true).getBoolean(true);
        config.addCustomCategoryComment("Quality (Client only)", "Overrides the render of train and rollingstock inventories to use textures from vanilla (including resourcepacks), so you can use textures in a texturepack specifically for this mod");
        useVanillaInventoryTextures = config.get("Quality (Client only)", "UseVanillaInventoryTextures", true).getBoolean(true);
        config.addCustomCategoryComment("Quality (Client only)", "Forces textures to be bound, slows performance on some machines, speeds it up on others, and fixes a rare bug where the the texture does not get bound. So... This REALLY depends on your machine, see what works best for you.");
        ForceTextureBinding = config.get("Quality (Client only)", "ForceTextureBinding", false).getBoolean(false);

        config.addCustomCategoryComment("Keybinds (Client only)", "accepted values can be set from in-game, or defined using the key code values from: http://minecraft.gamepedia.com/Key_codes");

        KeyLamp.setKeyCode(config.getInt("LampKeybind", "Keybinds (Client only)", Keyboard.KEY_L, 0, 0, ""));
        KeyLamp.setKeyCode(config.getInt("HornKeybind", "Keybinds (Client only)", Keyboard.KEY_H, 0, 0, ""));
        KeyInventory.setKeyCode(config.getInt("InventoryKeybind", "Keybinds (Client only)", Keyboard.KEY_I, 0, 0, ""));
    }

    /**the client only lamp block*/
    public static Block lampBlock= new LampBlock();

    /**
     * <h2>Client Register</h2>
     * Used for registering client only functions and redirecting registering the items in the train registry with their own textures and models.
     */
    @Override
    public void register() {
        super.register();
        GameRegistry.registerBlock(lampBlock, "lampblock");
        lampBlock.setLightLevel(1f);

        //register the fluid icons
        fluidOil.setIcons(BlockLiquid.getLiquidIcon("water_still"), BlockLiquid.getLiquidIcon("water_flow"));

        //trains and rollingstock
        int index=0;
        GenericRailTransport transport = TransportRegistry.listTrains(0);
        while (transport!=null) {
            RenderingRegistry.registerEntityRenderingHandler(transport.getClass(), transportRenderer);
            index++;
            transport = TransportRegistry.listTrains(index);
        }
        //hitboxes
        RenderingRegistry.registerEntityRenderingHandler(HitboxHandler.MultipartHitbox.class, nullRender);
        //bogies
        RenderingRegistry.registerEntityRenderingHandler(EntityBogie.class, nullRender);
        //seats
        RenderingRegistry.registerEntityRenderingHandler(EntitySeat.class, nullRender);
        //player scaler
        RenderingRegistry.registerEntityRenderingHandler(EntityPlayer.class, new RenderScaledPlayer());



        //GameRegistry.registerBlock(new BlockRailOverride(), Item);
        ClientRegistry.bindTileEntitySpecialRenderer(BlockRailOverride.renderTileEntity.class, specialRenderer);




        //keybinds
        ClientRegistry.registerKeyBinding(KeyLamp);
        ClientRegistry.registerKeyBinding(KeyInventory);

        //register the transport HUD.
        HUDTrain hud = new HUDTrain();
        FMLCommonHandler.instance().bus().register(hud);
        MinecraftForge.EVENT_BUS.register(hud);

    }

    public static final TileEntitySpecialRenderer specialRenderer = new TileEntitySpecialRenderer() {
        @Override
        public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float p_147500_8_) {
            GL11.glPushMatrix();
            GL11.glTranslated(x,y, z);
            tileEntity.func_145828_a(null);
            GL11.glPopMatrix();
        }

        @Override
        protected void bindTexture(ResourceLocation p_147499_1_){}
    };

    private static final RenderEntity transportRenderer = new RenderEntity();

    /**
     * <h3>null render</h3>
     * this is just a simple render that never draws anything, since its static it only ever needs to exist once, which makes it lighter on the render.
     */
    private static final Render nullRender = new Render() {
        @Override
        public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {}

        @Override
        protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
            return null;
        }
    };

    /**
     * <h2> Forced Dynamic Lighting </h2>
     *
     * this is used to force events from the main thread of the mod, it can create a lot of lag sometimes.
     *
     * Used to force lighting updates (if enabled in config).
     * It also only updates if it's actually needed, to help preserve what performance we can because of how much lag this can create.
     * Because this is a client only method, it creates no overhead on the server.
     *
     * @param tick the client tick event from the main thread
     */
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent tick) {
        if (EnableLights && tick.phase == TickEvent.Phase.END && carts.size() > 0) {
            if (Minecraft.getMinecraft().theWorld != null) {
                for (GenericRailTransport cart : carts) {
                    if (cart != null) {
                        Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, cart.lamp.X, cart.lamp.Y, cart.lamp.Z);
                    }
                }
            }
        }
    }
}
