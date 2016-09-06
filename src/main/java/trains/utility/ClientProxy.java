package trains.utility;


import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import trains.TrainsInMotion;
import trains.blocks.LampBlock;
import trains.entities.EntityTrainCore;
import trains.entities.render.RenderCore;
import trains.entities.render.RenderObj;
import trains.entities.trains.FirstTrain;
import trains.gui.train.GUISteam;
import trains.registry.URIRegistry;

import java.util.ArrayList;
import java.util.List;

public class ClientProxy extends CommonProxy {

    WorldClient clientWorld = Minecraft.getMinecraft().theWorld;
    public static List<EntityTrainCore> carts = new ArrayList<EntityTrainCore>();

    /**
     *
     *
     * <h1> Client GUI </h1>
     *
     *
     * defines the GUI element to display based on the ID
     * returns null if the player, cart or ID is invalid
     *
     * the inventory manager that server uses for this menu is defined in
     * @see CommonProxy#getServerGuiElement(int, EntityPlayer, World, int, int, int)
     */
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        //handle GUI direction for trains
        if (player != null && player.ridingEntity instanceof EntityTrainCore) {
			//TODO this should probably be handled by some sort of array based loader in the main class to make it more API friendly
            switch (ID) {
                case GUISteam.GUI_ID: {
                    return new GUISteam(player.inventory, (EntityTrainCore) player.ridingEntity);
                }

                default: {
                    return null;
                }
            }
        } else{
            //handle GUI direction for rollingstock
            return null;
        }
    }

    /**
     *
     *
     * <h1>Model Render Registry</h1>
     *
     *
     * simple registry of .java model render, followed by the same for .obj.
     * @see RenderCore for .java
     * RenderCore will be for .json in 1.9
     * @see RenderObj for .obj
     * Note, the texture can not be null or lead to an invalid file.
     */
    @Override
    public void registerRenderers() {
         //TODO need java model
		 //TODO this should probably be handled by some sort of array based loader in the main class to make it more API friendly
        //RenderingRegistry.registerEntityRenderingHandler(FirstTrain.class, new RenderCore(new MODEL.JAVA(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(FirstTrain.class, new RenderObj(
                URIRegistry.MODEL_TRAIN.getResource("060e2.obj"),
                URIRegistry.TEXTURE_GENERIC.getResource("null.png")
                ));

    }

    /**
     *
     *
     * <h1> OnTick </h1>
     *
     *
     * this is used to force events from the main thread of the mod, it can create a lot of lag sometimes.
     *
     * Currently it's used to force lighting updates (if enabled in config).
     * It also only updates if it's actually needed, to help preserve what performance we can.
     *
     * @param tick the client tick event from the main thread
     */
    @Override
    public void onTick(TickEvent.ClientTickEvent tick) {
        clientWorld = Minecraft.getMinecraft().theWorld;
        if (TrainsInMotion.EnableLights && tick.phase == TickEvent.Phase.END && clientWorld != null && carts.size() > 0){
            //instance the cart here because it's more efficient than instancing it every loop.
            for (EntityTrainCore cart : carts) {
                if (cart == null){
                    carts.remove(cart);
                } else if (clientWorld.getBlock(cart.lamp.X, cart.lamp.Y, cart.lamp.Z) instanceof LampBlock) {
                    clientWorld.updateLightByType(EnumSkyBlock.Block, cart.lamp.X, cart.lamp.Y, cart.lamp.Z);
                }
            }
        }
    }
}
