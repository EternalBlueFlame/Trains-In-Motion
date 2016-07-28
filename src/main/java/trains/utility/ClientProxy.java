package trains.utility;


import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import trains.entities.MinecartExtended;
import trains.entities.render.RenderCore;
import trains.entities.render.RenderObj;
import trains.entities.trains.FirstTrain;
import trains.gui.GUITrain;
import trains.registry.URIRegistry;

public class ClientProxy extends CommonProxy {

    /**
     * defines the GUI element to display based on the ID
     * returns null if the player, cart or ID is invalid
     *
     * the inventory manager that server uses for this menu is defined in
     * @see CommonProxy#getServerGuiElement(int, EntityPlayer, World, int, int, int)
     */
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (player != null && player.ridingEntity instanceof MinecartExtended) {
            switch (ID) {
                case GUITrain.GUI_ID: {
                    return new GUITrain(player.inventory, (MinecartExtended) player.ridingEntity);
                }

                default: {
                    return null;
                }
            }
        } else{

            return null;
        }
    }

    /**
     * registers the model for the trains/rolling stock
     * @see RenderCore
     */
    @Override
    public void registerRenderers() {
        /**
         * simple registry of .java model render, followed by the same for .obj.
         * @see RenderCore for .java
         * RenderCore will be for .json in 1.9
         * @see RenderObj for .obj
         *
         * Note, the texture can not be null or lead to an invalid file.
         * TODO need java model
         */
        //RenderingRegistry.registerEntityRenderingHandler(FirstTrain.class, new RenderCore(new MODEL.JAVA(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(FirstTrain.class, new RenderObj(
                URIRegistry.MODEL_TRAIN.getResource("060e2.obj"),
                URIRegistry.TEXTURE_GENERIC.getResource("null.png")
                ));

    }
}
