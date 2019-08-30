package ebf.tim.utility;

import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class RecipeManager {

    private static Map<ItemStack[][], ItemStack> recipes = new HashMap<>();

    public static void registerRecipe(Recipe recipe){
        recipes.put(new ItemStack[][]{
                recipe.topLeft(),recipe.topCenter(),recipe.topRight(),
                recipe.middleLeft(),recipe.middleCenter(),recipe.middleRight(),
                recipe.bottomLeft(),recipe.bottomCenter(),recipe.bottomRight()},
                recipe.getresult()[0]);
        //todo: in later MC versions add function for IDE to write the recipe to a json in editor, and load it from json normally
    }

    public static ItemStack getResult(ItemStack[] recipe){
        if(recipe == new ItemStack[]{null,null,null,null,null,null,null,null}){
            return null;//if all inputs were null, then just return null. this is a common scenario, should save speed overall.
        }

        //iterate all recipes
        boolean slot;
        for(ItemStack[][] key : recipes.keySet()){
            //iterate all items in the input
            for(int i=0;i<8;i++){
                //iterate all item lists in the current recipe
                slot=false;
                for(ItemStack s : key[i]){
                    if (s.equals(recipe[i])){
                        slot=true; break;
                    }
                }
                if(!slot){
                    return null;
                }
            }
        }
        //if all the recipes were iterated and none were a complete match, then there is no result
        return null;//in later MC versions this will need to return a null item
    }
}
