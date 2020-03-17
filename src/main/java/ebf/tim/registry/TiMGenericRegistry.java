package ebf.tim.registry;


import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.TrainsInMotion;
import ebf.tim.blocks.BlockTrainFluid;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.items.ItemCraftGuide;
import ebf.tim.items.ItemTransport;
import ebf.tim.utility.*;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

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

    public static Block registerBlock(Block block, CreativeTabs tab, String unlocalizedName, @Nullable String oreDictionaryName, @Nullable Object TESR){
        if (tab!=null){
            block.setCreativeTab(tab);
        }
        if (!unlocalizedName.equals("")){
            block.setBlockName(unlocalizedName);
            GameRegistry.registerBlock(block, unlocalizedName);
            usedNames.add(unlocalizedName);
        }
        if(oreDictionaryName!=null){
            OreDictionary.registerOre(oreDictionaryName, block);
        }
        if (DebugUtil.dev() && TrainsInMotion.proxy.isClient() && block.getUnlocalizedName().equals(StatCollector.translateToLocal(block.getUnlocalizedName()))){
            DebugUtil.println("Block missing lang entry: " + block.getUnlocalizedName());
        }
        if(block instanceof ITileEntityProvider){
            GameRegistry.registerTileEntity(
                    ((ITileEntityProvider)block).createNewTileEntity(null,0).getClass(),
                    unlocalizedName+"tile");
            usedNames.add(unlocalizedName+"tile");

            if(TrainsInMotion.proxy.isClient() && TESR!=null){
                cpw.mods.fml.client.registry.ClientRegistry.bindTileEntitySpecialRenderer(((ITileEntityProvider) block).createNewTileEntity(null, 0).getClass(), (TileEntitySpecialRenderer) TESR);
            }
        }
        return block;
    }

    public static Item RegisterItem(Item itm, String MODID, String unlocalizedName, CreativeTabs tab){
        return RegisterItem(itm, MODID, unlocalizedName,null,tab, null,null);
    }

    public static Item RegisterItem(Item itm, String MODID, String unlocalizedName, @Nullable String oreDictionaryName, @Nullable CreativeTabs tab, @Nullable Item container, @Nullable Object itemRender){
        if (tab!=null) {
            itm.setCreativeTab(tab);
        }
        if (container!=null){
            itm.setContainerItem(container);
        }
        itm.setUnlocalizedName(unlocalizedName);
        if(TrainsInMotion.proxy.isClient()){
            itm.setTextureName(MODID+":"+unlocalizedName);
        }
        GameRegistry.registerItem(itm,unlocalizedName);
        if(oreDictionaryName!=null){
            OreDictionary.registerOre(oreDictionaryName, itm);
        }
        if (DebugUtil.dev() && TrainsInMotion.proxy!=null && TrainsInMotion.proxy.isClient() && itm.getUnlocalizedName().equals(StatCollector.translateToLocal(itm.getUnlocalizedName()))){
            DebugUtil.println("Item missing lang entry: " + itm.getUnlocalizedName());
        }
        if(TrainsInMotion.proxy.isClient() && itemRender!=null){
            MinecraftForgeClient.registerItemRenderer(itm, (IItemRenderer)itemRender);
        } else if (TrainsInMotion.proxy.isClient() && itm instanceof ItemTransport){
            MinecraftForgeClient.registerItemRenderer(itm, ebf.tim.items.CustomItemModel.instance);
            if(ClientProxy.preRenderModels) {
                ebf.tim.items.CustomItemModel.instance.renderItem(IItemRenderer.ItemRenderType.INVENTORY, new ItemStack(itm));
            }
        }
        return itm;
    }


    public static void RegisterFluid(Fluid fluid, @Nullable Item bucket, String MODID, String unlocalizedName, boolean isGaseous, int density, MapColor color, CreativeTabs tab){
        fluid.setUnlocalizedName(unlocalizedName).setGaseous(isGaseous).setDensity(density);
        FluidRegistry.registerFluid(fluid);

        Block block = new BlockTrainFluid(fluid, new MaterialLiquid(color)).setBlockName("block."+unlocalizedName.replace(".item","")).setBlockTextureName(MODID+":block_"+unlocalizedName);
        GameRegistry.registerBlock(block, "block."+unlocalizedName);
        fluid.setBlock(block);

        bucket = new ItemBucket(block).setCreativeTab(tab).setUnlocalizedName(unlocalizedName + ".bucket").setContainerItem(Items.bucket);
                if(TrainsInMotion.proxy.isClient()){
                    bucket.setTextureName(MODID+":bucket_"+unlocalizedName);
                }
        GameRegistry.registerItem(bucket, "fluid." + unlocalizedName + ".bucket");
        FluidContainerRegistry.registerFluidContainer(fluid, new ItemStack(bucket), new ItemStack(Items.bucket));


        if (DebugUtil.dev() && TrainsInMotion.proxy.isClient()){
            if(fluid.getUnlocalizedName().equals(StatCollector.translateToLocal(fluid.getUnlocalizedName()))) {
                DebugUtil.println("Fluid missing lang entry: " + fluid.getUnlocalizedName());
            }
            if (bucket.getUnlocalizedName().equals(StatCollector.translateToLocal(block.getUnlocalizedName()))) {
                DebugUtil.println("Item missing lang entry: " + bucket.getUnlocalizedName());
            }
            if(block.getUnlocalizedName().equals(StatCollector.translateToLocal(block.getUnlocalizedName()))){
                DebugUtil.println("Block missing lang entry: " + block.getUnlocalizedName());
            }

        }
    }

    private static List<String>usedNames = new ArrayList<>();
    private static int registryPosition =17;

    public static void registerTransports(String MODID, GenericRailTransport[] entities, Object entityRender){
        if(registryPosition==-1){
            DebugUtil.println("ERROR", "ADDING TRANSPORT REGISTRY ITEMS OUTSIDE MOD INIT", "PLEASE REGISTER YOUR ENTITIES IN THE FOLLOWING EVENT:",
                    "@Mod.EventHandler public void init(FMLInitializationEvent event)");
        }
        for (GenericRailTransport registry : entities) {
            if(DebugUtil.dev() && usedNames.contains(registry.transportName())){
                DebugUtil.println(registry.getClass().getName(),"is trying to register under the name", usedNames.contains(registry.transportName()), "which is already used");
            }
            cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(
                    registry.getClass(),
                    registry.transportName().replace(" ","") + ".entity",
                    registryPosition, TrainsInMotion.instance, 1600, 3, true);
            GameRegistry.registerItem(registry.getCartItem().getItem(), registry.getCartItem().getItem().getUnlocalizedName());
            if(CommonProxy.recipesInMods.containsKey(MODID)){
                CommonProxy.recipesInMods.get(MODID).add(getRecipe(registry.getRecipie(), registry.getCartItem()));
            } else {
                CommonProxy.recipesInMods.put(MODID, new ArrayList<Recipe>());
                CommonProxy.recipesInMods.get(MODID).add(getRecipe(registry.getRecipie(), registry.getCartItem()));
            }
            if(TrainsInMotion.proxy.isClient() && ClientProxy.hdTransportItems){
                MinecraftForgeClient.registerItemRenderer(registry.getCartItem().getItem(), ebf.tim.items.CustomItemModel.instance);
            }
            registry.registerSkins();
            if(registry.getRecipie()!=null){
                RecipeManager.registerRecipe(getRecipe(registry.getRecipie(), registry.getCartItem()));
            }
            ItemCraftGuide.itemEntries.add(registry.getClass());
            if(TrainsInMotion.proxy.isClient()) {
                if (entityRender == null) {
                    cpw.mods.fml.client.registry.RenderingRegistry.registerEntityRenderingHandler(registry.getClass(), (net.minecraft.client.renderer.entity.Render)TrainsInMotion.proxy.getEntityRender());
                    if (ClientProxy.preRenderModels) {
                        ((net.minecraft.client.renderer.entity.Render) TrainsInMotion.proxy.getEntityRender()).doRender(registry, 0, 0, 0, 0, 0);
                    }
                } else {
                    cpw.mods.fml.client.registry.RenderingRegistry.registerEntityRenderingHandler(registry.getClass(), (net.minecraft.client.renderer.entity.Render)entityRender);
                    if (ClientProxy.preRenderModels) {
                        ((net.minecraft.client.renderer.entity.Render) entityRender).doRender(registry, 0, 0, 0, 0, 0);
                    }
                }
                if (ClientProxy.preRenderModels && ClientProxy.hdTransportItems) {
                    ebf.tim.items.CustomItemModel.instance.renderItem(IItemRenderer.ItemRenderType.INVENTORY, registry.getCartItem());
                }
            }
            usedNames.add(registry.transportName());
            registryPosition++;
        }
    }


    public static void endRegistration(){
        usedNames =null; registryPosition=-1;
    }

    private static Recipe getRecipe(Object[] obj, ItemStack cartItem){
        return new Recipe(new ItemStack[]{cartItem},
                getItem(obj[0]),
                getItem(obj[1]),
                getItem(obj[2]),
                getItem(obj[3]),
                getItem(obj[4]),
                getItem(obj[5]),
                getItem(obj[6]),
                getItem(obj[7]),
                getItem(obj[8])
        );
    }

    public static ItemStack[] getItem(Object itm){
        ItemStack[] list = new ItemStack[1];
        if(itm==null){
            return list;
        }
        if(itm instanceof ItemStack){
            list=ODC((ItemStack)itm);
        }
        else if (itm instanceof Item){
            list=ODC(new ItemStack((Item)itm));
        }
        else if (itm instanceof Block){
            list=ODC(new ItemStack((Block)itm));
        }
        else if(itm instanceof String){
            String[] data = ((String) itm).split(" ");
            int stacksize = data.length>1?Integer.parseInt(data[1].trim()):1;
            //cover actual items
            if(data[0].contains(":")){
                list=ODC(GameRegistry.findItemStack(data[0].split(":")[0], data[0].split(":")[1], stacksize));
            } else {
                //cover ore directory values
                list=OreDictionary.getOres(data[0]).toArray(new ItemStack[]{});
                for(ItemStack s : list){
                    s.stackSize=stacksize;
                }
            }

        }
        return list;
    }

    /**Ore Directory Converter
     * converts any input to the ore directory version so recipes will have automatic ore directory support*/
    public static ItemStack[] ODC(ItemStack s){
        if(s==null){
            return null;
        }
        //cache the old size and set it to 1, the ore directory only contains entries with a stacksize of 1,
        //   anything else can break the equals check.
        int oldSize = s.stackSize;
        s.stackSize=1;

        List<ItemStack> dir = new ArrayList<>();
        //create a list of ore directory entries
        for(int oreID : OreDictionary.getOreIDs(s)){
            dir.addAll(OreDictionary.getOres(oreID));
        }
        if(dir.size()>0) {
            for (ItemStack stack : dir) {
                stack.stackSize = oldSize;
            }
        } else {
            s.stackSize=oldSize;
            dir.add(s);
        }
        return dir.toArray(new ItemStack[]{});

    }



}
