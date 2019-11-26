package ebf.tim.utility;

import ebf.XmlBuilder;
import net.minecraft.item.ItemStack;

import java.util.*;

public class RecipeManager {

    private static Map<ItemStack[][], List<ItemStack>> recipes = new HashMap<>();


    public static void registerRecipe(Recipe recipe){
        if(recipes.containsKey(recipe.getItems())){
            recipes.get(recipe.getItems()).addAll(Arrays.asList(recipe.getresult()));
        } else {
            recipes.put(recipe.getItems(),Arrays.asList(recipe.getresult()));
        }
        //todo: in later MC versions add function for IDE to write the recipe to a json in editor, and load it from json normally
    }

    public static List<ItemStack> getResult(ItemStack[] recipe){
        if(recipe == new ItemStack[]{null,null,null,null,null,null,null,null}){
            return null;//if all inputs were null, then just return null. this is a common scenario, should save speed overall.
        }


        //iterate all recipes
        boolean[] slot;
        for(ItemStack[][] key : recipes.keySet()){
            slot=new boolean[]{false,false,false,false,false,false,false,false};
            //iterate all items in the input
            for(int i=0;i<8;i++){
                //iterate all item lists in the current recipe
                for(ItemStack s : key[i]){
                    if (s.equals(recipe[i])){
                        slot[i] = true;
                        break;
                    }
                }
            }
            if(slot[0] && slot[1] && slot[2] && slot[3] && slot[4] && slot[5] && slot[6] && slot[7]) {
                return recipes.get(key);
            }
        }
        //if all the recipes were iterated and none were a complete match, then there is no result
        return null;//in later MC versions this will need to return a null item
    }
}
