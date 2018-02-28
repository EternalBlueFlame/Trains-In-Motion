package ebf.tim.utility;

import cpw.mods.fml.common.registry.GameData;
import ebf.tim.entities.EntityTrainCore;
import ebf.tim.entities.GenericRailTransport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author EternalBlueFlame
 */
public class ServerLogger {
    /*
    --------------------------------------------------
    Writing
    --------------------------------------------------
     */

    //run this on server side when inventory opens or player mounts, maybe other common but not constant events.
    public static void writeWagonToFolder(GenericRailTransport wagon){
        try {
            //make a stringbuilder to build the filename, faster than string+string+string+string etc. MUCH faster.
            StringBuilder sb = new StringBuilder();
            sb.append(DimensionManager.getCurrentSaveRootDirectory().getAbsolutePath());
            sb.append("/traincraft/");
            if (!new File(sb.toString()).exists()){
                new File(sb.toString()).mkdir();
            } else {
                System.out.println(sb.toString());
            }
            sb.append(wagon.getOwnerName().equals("")?"Unknown_Player":wagon.getOwnerName());
            sb.append("/");
            if (!new File(sb.toString()).exists()){
                new File(sb.toString()).mkdir();
            } else {
                System.out.println(sb.toString());
            }
            sb.append(wagon.getInventoryName());
            sb.append("_");
            sb.append(wagon.getUniqueID());
            sb.append(".txt");
            //gen the file with the path
            FileOutputStream fileoutputstream = new FileOutputStream(new File(sb.toString()));
            //reset the string builder then add all the data in an XML seeming format.
            //you don't HAVE to do XML, you can use other formats and use libraries, i just like this way because its easy to read.
            sb = new StringBuilder();
            sb.append("<");
            sb.append(wagon.getUniqueID());
            sb.append(">\n  <delegate>");
            sb.append(wagon.getItem().delegate.name());
            sb.append("</delegate>\n    <pos_x>");
            sb.append(wagon.posX);
            sb.append("</pos_x>\n   <pos_y>");
            sb.append(wagon.posY);
            sb.append("</pos_y>\n   <pos_z>");
            sb.append(wagon.posZ);
            sb.append("</pos_z>\n    <inventory>");
            //cover inventory from a loop of an external function to simplify code
            if(wagon.inventory!=null) {
                for (ItemStackSlot stack : wagon.inventory) {
                    addItemXML(sb, stack.getStack());
                }
            }
            sb.append("    </inventory>\n    <fuel>");
            //if it's a train we can get the fuel from the fuel handler
            if(wagon instanceof EntityTrainCore){
                sb.append((((EntityTrainCore) wagon).fuelHandler.burnHeat!=0?
                        (int)(((EntityTrainCore) wagon).fuelHandler.burnHeat/((EntityTrainCore) wagon).getEfficiency())*0.000625//casting as an int rounds down
                        :0));
                sb.append("</fuel>\n    <fluids>");
            } else {
                sb.append("N/A</fuel>\n    <fluids>");
                for(FluidTankInfo tank : wagon.getTankInfo(ForgeDirection.UNKNOWN)) {
                    addFluidXML(sb, tank.fluid);
                }
                sb.append("</fluids>\n");
            }


            sb.append("</");
            sb.append(wagon.getUniqueID());
            sb.append(">\n");//seemingly unnecessary new line added to the end, linux needs this sometimes.
            fileoutputstream.write(sb.toString().getBytes());
        } catch (Exception e){
            //apparently we don't have permission, so, nevermind.
            e.printStackTrace();
        }
    }

    //run this on attack entity event if the entity dies
    public static void deleteWagon(GenericRailTransport wagon){
        StringBuilder sb = new StringBuilder();
        sb.append(DimensionManager.getCurrentSaveRootDirectory().getAbsolutePath());
        sb.append("/traincraft/");
        sb.append(wagon.getOwner().getName()==null || wagon.getOwner().getName().equals("")?"Unknown_Player": wagon.getOwner().getName());
        sb.append("/");
        sb.append(wagon.getInventoryName());
        sb.append("_");
        sb.append(wagon.getUniqueID());
        sb.append(".txt");
        try {
            File f = new File(sb.toString());
            if (f.exists() && !f.isDirectory()) {
                f.delete();
            }
        } catch (Exception e){e.printStackTrace();}//if it fails there was nothing to delete, so same result
    }



    private static void addItemXML(StringBuilder string, ItemStack item){
        if (item == null || item.getItem() == null || item.stackSize<=0){
            return;
        }
        string.append("    <ItemStack>\n        <ID>");
        string.append(Item.getIdFromItem(item.getItem()));
        string.append("</ID>\n        <delegate>");
        string.append(item.getItem().delegate.name());
        string.append("</delegate>\n        <UnlocalizedName>");
        string.append(item.getItem().getUnlocalizedName());
        string.append("</UnlocalizedName>\n        <meta>");
        string.append(item.getItemDamage());
        string.append("</meta>\n        <StackSize>");
        string.append(item.stackSize);
        string.append("</StackSize>\n    </ItemStack>\n");
    }

    private static void addFluidXML(StringBuilder string, FluidStack item){
        if (item == null || item.getFluid() == null || item.amount<=0){
            return;
        }

        string.append("    <FluidStack>\n        <ID>");
        string.append(item.getFluidID());
        string.append("</ID>\n        <UnlocalizedName>");
        string.append(item.getUnlocalizedName());
        string.append("</UnlocalizedName>\n        <StackSize>");
        string.append((int)(item.amount*0.001));
        string.append("</StackSize>\n    </FluidStack>\n");
    }




    /*
    --------------------------------------------------
    Reading
    --------------------------------------------------
     */

    //gets a list of the files in that player's folder, use this to show all the trains the user had
    public static File[] getFilesFromPlayerName(String player){
        try {
            return new File(DimensionManager.getCurrentSaveRootDirectory().getAbsolutePath() + "/traincraft/" + player).listFiles();
        } catch (Exception e){
            e.printStackTrace();
            return new File[]{};
        }
    }

    //use this to drop all the items from that train's save
    public static void dropItemsFromUUID(String player, String uuid, EntityPlayer p){
        //yes im using W3 style HTML parsing to read XML because the syntax is exactly the same and i can be a little lazier on reading.
        //plus the performance of this in testing really isn't bad at all with several thousand line documents....
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        Document document;
        for(File f : getFilesFromPlayerName(player)){
            try {
                document = dbFactory.newDocumentBuilder().parse(f);
                if (document.getElementsByTagName(uuid).getLength()>0){
                    List<ItemStack> itemStacks = getItems(document);
                    for (ItemStack i: itemStacks){
                        p.entityDropItem(i,1);
                    }
                }
            } catch (Exception e){e.printStackTrace();}
        }
    }


    //parses the document for itemstacks
    public static List<ItemStack> getItems(Document doc){
        try {
            ArrayList<ItemStack> itemStacks = new ArrayList<>();
            NodeList nodes = doc.getElementsByTagName("ItemStack");


            for (int i=0; i<nodes.getLength();){
                ItemStack stack = parseItemFromXML((Element) nodes.item(i));//for some reason we can cast like this
                if (stack != null){
                    itemStacks.add(stack);
                }
            }
            return itemStacks;

        } catch (Exception e){
            e.printStackTrace();
            return new ArrayList<ItemStack>();
        }
    }

    //parses the individual item
    public static ItemStack parseItemFromXML(Element doc){
        try {
            ItemStack stack = new ItemStack(
                    GameData.getItemRegistry().getObject(doc.getElementsByTagName("delegate").item(0).getTextContent()),//get item by delegate name since it's static
                    Integer.parseInt(doc.getElementsByTagName("StackSize").item(0).getTextContent())//we always get strings so gotta parse.
            );

            stack.setItemDamage(Integer.parseInt(doc.getElementsByTagName("meta").item(0).getTextContent()));

            return stack;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



}
