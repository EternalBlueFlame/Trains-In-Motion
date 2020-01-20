package ebf.tim.utility;

import ebf.XmlBuilder;
import ebf.tim.blocks.TileEntityStorage;
import ebf.tim.items.ItemRail;
import ebf.tim.registry.TiMItems;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.*;

public class RecipeManager {

    private static Map<String, List<ItemStack>> recipes = new HashMap<>();
    //private static List<Item> ingotDirectory = new ArrayList<>();


    public static String stackArrayToString(ItemStack[][] s){
        XmlBuilder builder = new XmlBuilder();
        int ii;
        for(int i=0;i<9;i++){
            XmlBuilder subBuilder = new XmlBuilder();
            ii=0;
            for(ItemStack stack: s[i]){
                subBuilder.putItemStack("itm"+ii,stack);
                ii++;
            }
            builder.putXml("x"+i,subBuilder);
        }
        return builder.toXMLString();
    }

    public static void registerRecipe(Recipe recipe){
        DebugUtil.println("REGISTERING RECIPE"
        , (recipe.topLeft()==null || recipe.topLeft().length==0 || recipe.topLeft()[0]==null?"null": recipe.topLeft()[0].getDisplayName()),
                recipe.getresult()[0].getDisplayName());
        String input = stackArrayToString(recipe.getRecipeItems());
        for(String key: recipes.keySet()){
            if(input.equals(key)){
                recipes.get(key).addAll(Arrays.asList(recipe.getresult()));
                return;
            }
        }

        List<ItemStack> result = new ArrayList<>();
        result.addAll(Arrays.asList(recipe.getresult()));
        recipes.put(input,result);
        //todo: in later MC versions add function for IDE to write the recipe to a json in editor, and load it from json normally
    }

    public static List<ItemStack> getResult(ItemStack[] recipe){
        if(Arrays.equals(recipe, new ItemStack[]{null, null, null, null, null, null, null, null})){
            return null;//if all inputs were null, then just return null. this is a common scenario, should save speed overall.
        }


        //iterate all recipes
        boolean[] slot;
        XmlBuilder builder, stackSet;
        List<ItemStack> retStacks = new ArrayList<>();
        for(String key : recipes.keySet()){
            builder= new XmlBuilder(key);
            slot=new boolean[]{false,false,false,false,false,false,false,false};
            for(int i=0;i<8;i++){
                stackSet=builder.getXml("x"+i);
                for(String stackKey : stackSet.itemMap.keySet()){
                    if(stackSet.getItemStack(stackKey)==null || recipe[i]==null){
                        if(stackSet.getItemStack(stackKey)==null && recipe[i]==null){
                            slot[i]=true;
                            break;
                        }
                    } else if(stackSet.getItemStack(stackKey).getItem()== recipe[i].getItem() &&
                            recipe[i].stackSize>=stackSet.getItemStack(stackKey).stackSize){
                        slot[i]=true;
                        break;
                    }
                }
                if(!slot[i]){
                    break;
                }
            }

            if(slot[0] && slot[1] && slot[2] && slot[3] && slot[4] && slot[5] && slot[6] && slot[7]) {
                retStacks.addAll(recipes.get(key));
            }
        }
        //if all the recipes were iterated and none were a complete match, then there is no result
        return retStacks;
    }

    public static List<ItemStack> getResult(ItemStack[] recipe, ItemStack match){
        if(Arrays.equals(recipe, new ItemStack[]{null, null, null, null, null, null, null, null}) || match==null){
            return null;//if all inputs were null, then just return null. this is a common scenario, should save speed overall.
        }

        //iterate all recipes
        boolean[] slot;
        XmlBuilder builder, stackSet;
        List<ItemStack> retStacks = new ArrayList<>();
        for(String key : recipes.keySet()){
            slot=new boolean[]{false,false,false,false,false,false,false,false};
            for (ItemStack s : recipes.get(key)) {
                slot[0]=match.getItem() == s.getItem();
                if(slot[0]){break;}
            }
            if(slot[0]){
                slot[0]=false;
            } else {
                continue;
            }

            builder= new XmlBuilder(key);
            for(int i=0;i<8;i++){
                stackSet=builder.getXml("x"+i);
                for(String stackKey : stackSet.itemMap.keySet()){
                    if(stackSet.getItemStack(stackKey)==null || recipe[i]==null){
                        if(stackSet.getItemStack(stackKey)==null && recipe[i]==null){
                            slot[i]=true;
                            retStacks.add(null);
                            break;
                        }
                    } else if(stackSet.getItemStack(stackKey).getItem()== recipe[i].getItem() &&
                            recipe[i].stackSize>=stackSet.getItemStack(stackKey).stackSize){
                        slot[i]=true;
                        retStacks.add(stackSet.getItemStack(stackKey));
                        break;
                    }
                }
                if(!slot[i]){
                    break;
                }
            }

            if(slot[0] && slot[1] && slot[2] && slot[3] && slot[4] && slot[5] && slot[6] && slot[7]) {
                retStacks.addAll(recipes.get(key));
            }
        }
        //if all the recipes were iterated and none were a complete match, then there is no result
        return retStacks;
    }





    /**
     * Crafting table related stuff
     */

    public static  @Nullable ItemStack getStackBallast(IInventory hostInventory){
        if(hostInventory.getStackInSlot(4)==null || hostInventory.getStackInSlot(4).getTagCompound()==null){
            return null;
        } else {
            return !hostInventory.getStackInSlot(4).getTagCompound().hasKey("ballast") ? null :
                    ItemStack.loadItemStackFromNBT(hostInventory.getStackInSlot(4).getTagCompound().getCompoundTag("ballast"));
        }
    }
    public static @Nullable ItemStack getStackTies(IInventory hostInventory){
        if(hostInventory.getStackInSlot(4)==null || hostInventory.getStackInSlot(4).getTagCompound()==null){
            return null;
        } else {
            return !hostInventory.getStackInSlot(4).getTagCompound().hasKey("ties") ? null :
                    ItemStack.loadItemStackFromNBT(hostInventory.getStackInSlot(4).getTagCompound().getCompoundTag("ties"));
        }
    }
    public static @Nullable ItemStack getStackIngot(IInventory hostInventory){
        if(hostInventory.getStackInSlot(4)==null || hostInventory.getStackInSlot(4).getTagCompound()==null){
            return null;
        } else {
            return !hostInventory.getStackInSlot(4).getTagCompound().hasKey("ingot") ? null :
                    ItemStack.loadItemStackFromNBT(hostInventory.getStackInSlot(4).getTagCompound().getCompoundTag("ingot"));
        }
    }


    public static List<ItemStack> getAcceptedRailItems(){
        List<ItemStack> Ores=new ArrayList<>();

        Ores.add(new ItemStack(Items.diamond));
        Ores.add(new ItemStack(Items.blaze_rod));

        for(String o: OreDictionary.getOreNames()) {
            if (o.contains("ingot") || o.contains("plank")) {
                Ores.addAll(OreDictionary.getOres(o));
            }
        }
        return Ores;
    }

    public static ItemStack[] getTransportRecipe(IInventory hostInventory){
        return new ItemStack[]{
                hostInventory.getStackInSlot(0),hostInventory.getStackInSlot(1),hostInventory.getStackInSlot(2),
                hostInventory.getStackInSlot(3),hostInventory.getStackInSlot(4),hostInventory.getStackInSlot(5),
                hostInventory.getStackInSlot(6),hostInventory.getStackInSlot(7),hostInventory.getStackInSlot(8),
        };
    }

    public static ItemStack railRecipe(IInventory hostInventory){
        //handle adding to an existing stack
        if(hostInventory.getStackInSlot(5)!=null && hostInventory.getStackInSlot(5).getItem() instanceof ItemRail &&
                hostInventory.getStackInSlot(0)==getStackIngot(hostInventory) &&
                hostInventory.getStackInSlot(1)==getStackTies(hostInventory) &&
                hostInventory.getStackInSlot(2)==getStackBallast(hostInventory)){

            ItemStack rail = ItemRail.setStackData(new ItemStack(TiMItems.railItem),
                    hostInventory.getStackInSlot(0),hostInventory.getStackInSlot(1),hostInventory.getStackInSlot(2),
                    null);

            rail.getTagCompound().setInteger("count",
                    hostInventory.getStackInSlot(5).getTagCompound().getInteger("count")+1);
            return rail;
        }
        //handle making a new stack
        if(hostInventory.getStackInSlot(0)!=null && ingotInDirectory(hostInventory.getStackInSlot(0).getItem())) {
            return ItemRail.setStackData(new ItemStack(TiMItems.railItem),
                    hostInventory.getStackInSlot(0),hostInventory.getStackInSlot(2),hostInventory.getStackInSlot(1),
                    null);
        }
        //todo: add support for augument slot
        return null;

    }



    public static boolean ingotInDirectory(Item i){
        for(ItemStack stack : getAcceptedRailItems()){
            if (stack !=null && stack.getItem()==i){
                return true;
            }
        }
        return false;
    }

}
