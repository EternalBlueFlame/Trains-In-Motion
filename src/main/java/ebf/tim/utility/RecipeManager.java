package ebf.tim.utility;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Map;

public class RecipeManager {

    private static Map<ItemStack[], ItemStack> recipes = new HashMap<>();

    public static void registerRecipe(ItemStack result, ItemStack[] recipe){
        recipes.put(new ItemStack[]{
                ODC(recipe[0]),ODC(recipe[1]),ODC(recipe[2]),
                ODC(recipe[3]),ODC(recipe[4]),ODC(recipe[5]),
                ODC(recipe[6]),ODC(recipe[7]),ODC(recipe[8])},
                result);
        // in later MC versions add function for IDE to write the recipe to a json in editor, and load it from json normally
    }

    public static ItemStack getResult(ItemStack[] recipe){
        ItemStack[] newRecipe = new ItemStack[]{
                ODC(recipe[0]),ODC(recipe[1]),ODC(recipe[2]),
                ODC(recipe[3]),ODC(recipe[4]),ODC(recipe[5]),
                ODC(recipe[6]),ODC(recipe[7]),ODC(recipe[8])};
        if(recipes.containsKey(newRecipe)) {
            return recipes.get(newRecipe);
        } else {
            return null;//in later MC versions this will need to return a null item
        }
    }

    /**Ore Directory Converter
     * converts any input to the ore directory version so recipes will have automatic ore directory support*/
    public static ItemStack ODC(ItemStack s){
        if(s==null){
            return s;
        }
        int oldSize = s.stackSize;
        s.stackSize=1;
        if(checkStack("ingotIron", s)){return new ItemStack(Items.iron_ingot, oldSize);}
        else if(checkStack("ingotGold", s)){return new ItemStack(Items.gold_ingot, oldSize);}
        else if(checkStack("flint", s)){return new ItemStack(Items.flint, oldSize);}
        else if(checkStack("ingotBrick", s)){return new ItemStack(Items.brick, oldSize);}
        else if(checkStack("ingotBrickNether", s)){return new ItemStack(Items.netherbrick, oldSize);}
        else if(checkStack("stone", s)){return new ItemStack(Blocks.stone, oldSize);}
        else if(checkStack("sand", s)){return new ItemStack(Blocks.sand, oldSize);}
        else if(checkStack("gemQuartz", s)){return new ItemStack(Items.quartz, oldSize);}
        else if(checkStack("dustRedstone", s)){return new ItemStack(Items.redstone, oldSize);}
        else if(checkStack("dustGlowstone", s)){return new ItemStack(Items.glowstone_dust);}
        //dyes
        else if(checkStack("dyeBlack", s)){return new ItemStack(Items.dye, oldSize,0);}
        else if(checkStack("dyeRed", s)){return new ItemStack(Items.dye, oldSize,1);}
        else if(checkStack("dyeGreen", s)){return new ItemStack(Items.dye, oldSize,2);}
        else if(checkStack("dyeBrown", s)){return new ItemStack(Items.dye, oldSize,3);}
        else if(checkStack("dyeBlue", s)){return new ItemStack(Items.dye, oldSize,4);}
        else if(checkStack("dyePurple", s)){return new ItemStack(Items.dye, oldSize,5);}
        else if(checkStack("dyeCyan", s)){return new ItemStack(Items.dye, oldSize,6);}
        else if(checkStack("dyeLightGrey", s)){return new ItemStack(Items.dye, oldSize,7);}
        else if(checkStack("dyeGrey", s)){return new ItemStack(Items.dye, oldSize,8);}
        else if(checkStack("dyePink", s)){return new ItemStack(Items.dye, oldSize,9);}
        else if(checkStack("dyeLime", s)){return new ItemStack(Items.dye, oldSize,10);}
        else if(checkStack("dyeYellow", s)){return new ItemStack(Items.dye, oldSize,11);}
        else if(checkStack("dyeLightBlue", s)){return new ItemStack(Items.dye, oldSize,12);}
        else if(checkStack("dyeMagenta", s)){return new ItemStack(Items.dye, oldSize,13);}
        else if(checkStack("dyeOrange", s)){return new ItemStack(Items.dye, oldSize,14);}
        else if(checkStack("dyeWhite", s)){return new ItemStack(Items.dye, oldSize,15);}


        return s;
    }

    private static boolean checkStack(String s, ItemStack stack){
        return OreDictionary.getOres(s).contains(stack);
    }
}
