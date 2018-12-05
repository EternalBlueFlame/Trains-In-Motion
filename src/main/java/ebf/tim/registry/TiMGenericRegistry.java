package ebf.tim.registry;


import cpw.mods.fml.common.registry.GameRegistry;
import ebf.tim.TrainsInMotion;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.entities.rollingstock.EntityGTAX13000GallonTanker;
import ebf.tim.entities.rollingstock.EntityPullmansPalace;
import ebf.tim.entities.rollingstock.EntityUP3Bay100TonHopper;
import ebf.tim.entities.rollingstock.EntityVATLogCar;
import ebf.tim.entities.trains.EntityBrigadelok080;
import ebf.tim.utility.DebugUtil;
import ebf.tim.utility.RecipeManager;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Train registry</h1>
 * this class lists the data necessary to register trains and rollingstock.
 * this is not intended to be a way to get specific trains from the mod. (use the unlocalized entity name, or a class instanceof check)
 * @author Eternal Blue Flame
 */
public class TiMGenericRegistry {

    public GenericRailTransport transport;
    public Item[] recipe;

    public TiMGenericRegistry(GenericRailTransport transport, Item[] recipe){
        this.transport = transport;
        this.recipe = recipe;
    }

    public static void registerBlock(boolean isClient, Block block, CreativeTabs tab, String name, @Nullable Object TESR){
        if (tab!=null){
            block.setCreativeTab(tab);
        }
        if (!name.equals("")){
            block.setBlockName(name);
            GameRegistry.registerBlock(block, name);
            usedNames.add(name);
        }
        if (TrainsInMotion.proxy.isClient() && block.getUnlocalizedName().equals(StatCollector.translateToLocal(block.getUnlocalizedName()))){
            DebugUtil.println("Block missing lang entry: " + block.getUnlocalizedName());
        }
        if(block instanceof ITileEntityProvider){
            GameRegistry.registerTileEntity(
                    ((ITileEntityProvider)block).createNewTileEntity(null,0).getClass(),
                    name+"tile");
            usedNames.add(name+"tile");

            if(isClient){
                if(TESR!=null) {
                    cpw.mods.fml.client.registry.ClientRegistry.bindTileEntitySpecialRenderer(((ITileEntityProvider) block).createNewTileEntity(null, 0).getClass(), (TileEntitySpecialRenderer) TESR);
                } else {
                    cpw.mods.fml.client.registry.ClientRegistry.bindTileEntitySpecialRenderer(((ITileEntityProvider) block).createNewTileEntity(null, 0).getClass(), (TileEntitySpecialRenderer)TrainsInMotion.proxy.getTESR());
                }
            }
        }
    }

    public static Item RegisterItem(Item itm, String name, String unlocalizedName, @Nullable CreativeTabs tab, @Nullable Item container){
        if (tab!=null) {
            itm.setCreativeTab(tab);
        }
        if (container!=null){
            itm.setContainerItem(container);
        }
        if (!unlocalizedName.equals("")){
            itm.setUnlocalizedName(unlocalizedName);
        }
        GameRegistry.registerItem(itm, name);
        if (TrainsInMotion.proxy.isClient() && itm.getUnlocalizedName().equals(StatCollector.translateToLocal(itm.getUnlocalizedName()))){
            DebugUtil.println("Item missing lang entry: " + itm.getUnlocalizedName());
        }
        return itm;
    }


    private static List<String>usedNames = new ArrayList<>();
    private static int registryPosition =17;

    public static void registerTransports(boolean isClient, GenericRailTransport[] entities, Object entityRender){
        if(registryPosition==-1){
            DebugUtil.println("ERROR", "ADDING TRANSPORT REGISTRY ITEMS OUTSIDE MOD INIT", "PLEASE REGISTER YOUR ENTITIES IN THE FOLLOWING EVENT:",
                    "@Mod.EventHandler public void init(FMLInitializationEvent event)");
        }
        for (GenericRailTransport registry : entities) {
            if(usedNames.contains(registry.transportName())){
                DebugUtil.println(registry.getClass().getName(),"is trying to register under the name", usedNames.contains(registry.transportName()), "which is already used");
            }
            cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(
                    registry.getClass(),
                    registry.transportName().replace(" ","") + ".entity",
                    registryPosition, TrainsInMotion.instance, 60, 1, true);
            GameRegistry.registerItem(registry.getCartItem().getItem(), registry.transportName());
            registry.registerSkins();
            if(registry.getRecipie()!=null){
                RecipeManager.registerRecipe(registry.getCartItem(), registry.getRecipie());
            }
            if(isClient){
                if(entityRender==null){
                    cpw.mods.fml.client.registry.RenderingRegistry.registerEntityRenderingHandler(registry.getClass(), (net.minecraft.client.renderer.entity.Render)TrainsInMotion.proxy.getEntityRender());
                } else {
                    cpw.mods.fml.client.registry.RenderingRegistry.registerEntityRenderingHandler(registry.getClass(), (net.minecraft.client.renderer.entity.Render)entityRender);
                }
            }
            usedNames.add(registry.transportName());
            registryPosition++;
        }
    }
    public static void endRegistration(){
        usedNames =null; registryPosition=-1;
    }


    public static GenericRailTransport[] listSteamTrains() {
        return new GenericRailTransport[]{new EntityBrigadelok080(null)};
    }

    public static GenericRailTransport[] listPassenger() {
        return new GenericRailTransport[]{new EntityPullmansPalace(null)};
    }

    public static GenericRailTransport[] listFreight() {
        return new GenericRailTransport[]{new EntityVATLogCar(null), new EntityUP3Bay100TonHopper(null)};
    }

    public static GenericRailTransport[] listTanker() {
        return new GenericRailTransport[]{new EntityGTAX13000GallonTanker(null)};
    }


}
