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

    private static List<Recipe> recipeList = new ArrayList<>();
    //private static List<Item> ingotDirectory = new ArrayList<>();


    public static void registerRecipe(Recipe recipe){
        DebugUtil.println("REGISTERING RECIPE"
        , (recipe.topLeft()==null || recipe.topLeft().size()==0 || recipe.topLeft().get(0)==null?"null": recipe.topLeft().get(0).getDisplayName()),
                recipe.getresult().get(0).getDisplayName());

        for(Recipe r : recipeList){
            if(r.recipeInputMatches(recipe.input)){
                r.addResults(recipe.result);
                return;
            }
        }

        recipeList.add(recipe);

        //todo: in later MC versions add function for IDE to write the recipe to a json in editor, and load it from json normally
    }


    public static Recipe getRecipe(ItemStack result){
        for(Recipe r : recipeList){
            for(ItemStack stack : r.getresult()){
                if(stack==null || result==null){
                    if(stack==null && result==null){
                        return r;
                    }
                }
                else if(stack.getItem()==result.getItem()){
                    return r;
                }
            }

        }
        return null;
    }

    public static List<ItemStack> getResult(ItemStack[] recipe){
        if(Arrays.equals(recipe, new ItemStack[]{null, null, null, null, null, null, null, null})){
            return null;//if all inputs were null, then just return null. this is a common scenario, should save speed overall.
        }

        for(Recipe r : recipeList){
            if(r.inputMatches(Arrays.asList(recipe))){
                return r.result;
            }
        }
        return null;
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
