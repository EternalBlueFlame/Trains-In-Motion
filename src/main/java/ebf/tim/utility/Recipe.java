package ebf.tim.utility;

import ebf.XmlBuilder;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;


public class Recipe {

    private ItemStack[][] items = new ItemStack[10][];
    private int[] displayItem=new int[]{0,0,0,0,0,0,0,0,0,0};

    public Recipe(ItemStack[] result,
            ItemStack[] topLeft, ItemStack[] topCenter, ItemStack[] topRight,
                  ItemStack[] middleLeft, ItemStack[] middleCenter, ItemStack[] middleRight,
                  ItemStack[] bottomLeft, ItemStack[] bottomCenter, ItemStack[] bottomRight){

        items[0]=topLeft; items[1]=topCenter; items[2]=topRight;
        items[3]=middleLeft; items[4]=middleCenter; items[5]=middleRight;
        items[6]=bottomLeft; items[7]=bottomCenter; items[8]=bottomRight;
        items[9]=result;
    }

    public Recipe(ItemStack result,
                  ItemStack topLeft, ItemStack topCenter, ItemStack topRight,
                  ItemStack middleLeft, ItemStack middleCenter, ItemStack middleRight,
                  ItemStack bottomLeft, ItemStack bottomCenter, ItemStack bottomRight){

        items[0]=new ItemStack[]{topLeft}; items[1]=new ItemStack[]{topCenter}; items[2]=new ItemStack[]{topRight};
        items[3]=new ItemStack[]{middleLeft}; items[4]=new ItemStack[]{middleCenter}; items[5]=new ItemStack[]{middleRight};
        items[6]=new ItemStack[]{bottomLeft}; items[7]=new ItemStack[]{bottomCenter}; items[8]=new ItemStack[]{bottomRight};
        items[9]=new ItemStack[]{result};
    }

    //gets the individual stacks to check for crafting matches
    public ItemStack[] topLeft(){return items[0];}
    public ItemStack[] topCenter(){return items[1];}
    public ItemStack[] topRight(){return items[2];}

    public ItemStack[] middleLeft(){return items[3];}
    public ItemStack[] middleCenter(){return items[4];}
    public ItemStack[] middleRight(){return items[5];}

    public ItemStack[] bottomLeft(){return items[6];}
    public ItemStack[] bottomCenter(){return items[7];}
    public ItemStack[] bottomRight(){return items[8];}

    public ItemStack[] getresult(){return items[9];}

    public ItemStack[][] getItems() {
        return items;
    }

    public ItemStack[][] getRecipeItems() {
        return new ItemStack[][]{items[0],items[1],items[2],items[3],items[4],items[5],items[6],items[7],items[8]};
    }

    public void nextDisplayItem(){
        for(int i=0;i<10;i++) {
            displayItem[i]++;
            if (displayItem[i] >= items[i].length) {
                displayItem[i] = 0;
            }
        }
    }

    //gets the list of itemstacks that should display on the crafting guide
    public ItemStack[] getDisplayArray(){
        return new ItemStack[]{
                items[0][displayItem[0]],items[1][displayItem[1]],items[2][displayItem[2]],
                items[3][displayItem[3]],items[4][displayItem[4]],items[5][displayItem[5]],
                items[6][displayItem[6]],items[7][displayItem[7]],items[8][displayItem[8]],
                items[9][displayItem[9]]
        };
    }

    public String saveRecipe(){
        XmlBuilder xml = new XmlBuilder();
        int ii;
        for (int i=0;i<10;i++){
            ii=0;
            XmlBuilder slot = new XmlBuilder();
            for(ItemStack s : items[i]){
                slot.putItemStack("variant "+ii,s);
                ii++;
            }
            xml.putXml("slot " +i, slot);
        }
        return xml.toXMLString();
    }

    public Recipe loadRecipe(String s){
        XmlBuilder xml = new XmlBuilder(s);
        for (int i=0;i<10;i++){
            List<ItemStack> list=new ArrayList<>();

            XmlBuilder builder = xml.getXml("slot "+i);
            for(String stack : builder.itemMap.keySet()) {
                list.add(builder.getItemStack(stack));
            }
            items[i]=list.toArray(new ItemStack[]{});

        }

        return this;
    }

}
