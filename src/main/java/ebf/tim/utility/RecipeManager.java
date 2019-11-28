package ebf.tim.utility;

import ebf.XmlBuilder;
import net.minecraft.item.ItemStack;

import java.util.*;

public class RecipeManager {

    private static Map<String, List<ItemStack>> recipes = new HashMap<>();


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
                    } else if(stackSet.getItemStack(stackKey).getItem()== recipe[i].getItem()){
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

            /*
            //iterate all items in the input
            for(int i=0;i<8;i++){
                //iterate all item lists in the current recipe
                for(ItemStack s : key[i]){
                    if (s == recipe[i]){
                        slot[i] = true;
                        break;
                    }
                }
            }
            if(slot[0] && slot[1] && slot[2] && slot[3] && slot[4] && slot[5] && slot[6] && slot[7]) {
                DebugUtil.println(recipes.get(key).size());
                return recipes.get(key);
            }*/
        }
        //if all the recipes were iterated and none were a complete match, then there is no result
        return retStacks;
    }
}
