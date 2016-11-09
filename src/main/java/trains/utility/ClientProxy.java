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
import trains.entities.render.RenderObj;
import trains.gui.train.GUISteam;
import trains.registry.TrainRegistry;
import trains.registry.URIRegistry;

import java.util.ArrayList;
import java.util.List;

public class ClientProxy extends CommonProxy {
    private static WorldClient clientWorld= null; //define this ahead of time so we dont have to instance the variable every client tick.
    public static List<EntityTrainCore> carts = new ArrayList<EntityTrainCore>();

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
        //Trains
        if (player != null && player.ridingEntity instanceof EntityTrainCore) {
            switch (ID) {
                case TrainsInMotion.STEAM_GUI_ID: {
                    return new GUISteam(player.inventory, (EntityTrainCore) player.ridingEntity);
                }

                default: {return null;}
            }
        } else {
            //Rollingstock
            return null;
        }
    }

    /**
     * <h2>Model Render Redirect</h2>
     * A redirect loop for registering he items in the train registry with their own textures and models.
     * TODO need java models and need to move away from the RenderObj class
     */
    @Override
    public void registerRenderers() {
        for(TrainRegistry reg : TrainRegistry.listTrains()){
            RenderingRegistry.registerEntityRenderingHandler(reg.trainClass, new RenderObj(
                URIRegistry.MODEL_TRAIN.getResource(reg.model),
                URIRegistry.TEXTURE_GENERIC.getResource(reg.texture)
        ));
        }


    }

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
    @Override
    public void onTick(TickEvent.ClientTickEvent tick) {
        if (TrainsInMotion.EnableLights && tick.phase == TickEvent.Phase.END && carts.size() > 0) {
            clientWorld = Minecraft.getMinecraft().theWorld;
            if (clientWorld != null) {
                for (EntityTrainCore cart : carts) {
                    if (clientWorld.getBlock(cart.lamp.X, cart.lamp.Y, cart.lamp.Z) instanceof LampBlock) {
                        clientWorld.updateLightByType(EnumSkyBlock.Block, cart.lamp.X, cart.lamp.Y, cart.lamp.Z);
                    }
                }
            }
        }
    }
}
