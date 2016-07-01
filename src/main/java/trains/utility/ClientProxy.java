package trains.utility;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import trains.entities.MinecartExtended;
import trains.gui.GUITrain;

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
     * @see trains.entities.render.Render
     */
    @Override
    public void registerRenderers() {
        //simple render .java model, for entity TODO need model
        //RenderingRegistry.registerEntityRenderingHandler(FirstTrain.class, new Render(new MODEL.JAVA(), 0.5F));

    }
}
