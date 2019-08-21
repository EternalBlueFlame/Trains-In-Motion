package ebf.tim.items;

import ebf.tim.models.RenderEntity;
import ebf.tim.utility.ClientProxy;
import ebf.tim.utility.DebugUtil;
import fexcraft.tmt.slim.ModelBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import java.util.*;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.INVENTORY;

public class CustomItemModel implements IItemRenderer /*ICustomModelLoader*/ {

    private static HashMap<ResourceLocation, Item> models = new HashMap<>();

    public static void registerModel(Item itm){
        models.put(new ResourceLocation(itm.getUnlocalizedName()), itm);
    }

    //@Override
    public boolean accepts(ResourceLocation modelLocation) {
        return models.containsKey(modelLocation);
    }

    //@Override
    public /*IModel*/ void loadModel(ResourceLocation modelLocation) throws Exception {
        renderItem(null, new ItemStack(models.get(modelLocation)), null);
    }



    @Override// generally useless but needs to be here
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    //@Override // generally useless but needs to be here
    public void onResourceManagerReload(IResourceManager resourceManager) {}



    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;//models.containsKey(new ResourceLocation(item.getUnlocalizedName()));
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        //DebugUtil.println("RENDERING");
        if (item !=null && item.getItem() instanceof ItemTransport){
            GL11.glPushMatrix();
            GL11.glScalef(0.625f,0.625f,0.625f);
            //GL11.glTranslatef(0,0,100);
            switch (type){
                default:{}
                case EQUIPPED_FIRST_PERSON:{
                    GL11.glScalef(0.5f,0.5f,0.5f);
                    GL11.glTranslatef(1,1,0.5f);
                    break;
                }
                case INVENTORY:{
                    GL11.glRotatef(180,0,1,0);
                    GL11.glTranslatef(0,-0.5f,0);
                    GL11.glScalef(0.5f,0.5f,0.5f);
                    break;
                }
                case ENTITY:{
                    GL11.glScalef(0.5f,0.5f,0.5f);
                }
            }
            ClientProxy.transportRenderer.doRender(((ItemTransport)item.getItem()).entity,0,0,0,0,0, true);
            GL11.glPopMatrix();
        }

    }
}
