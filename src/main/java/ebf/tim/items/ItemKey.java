package ebf.tim.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

import java.util.List;
import java.util.UUID;

/**
 * <h1>Key Item</h1>
 * the key used to allow people other than the owner to interact with a locked train or rollingstock.
 * @author Eternal Blue Flame
 */
public class ItemKey extends Item{

    public ItemKey(UUID host, String entityName){
        data.host = host;
        data.hostname = entityName;
    }

    private KeySaveData data = new KeySaveData("ItemKey");

    /**
     * <h2>Description text</h2>
     * Allows items to add custom lines of information to the mouseover description, by adding new lines to stringList.
     * Each string added defines a new line.
     * We can cover the key and ticket description here, to simplify other classes.
     */
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack thisStack, EntityPlayer player, List stringList, boolean p_77624_4_) {
        if (this instanceof ItemTicket){
            stringList.add("This ticket is for: ");
        } else {
            stringList.add("This key belongs to a: ");
        }
        stringList.add(data.hostname);
    }


    public UUID getTransport(){
        return data.host;
    }

    private class KeySaveData extends WorldSavedData{
        /**the UUID from server for the entity this belongs to, this will be compared to a key item in the entity, rather than the entity itself due to different UUID's on client and server.*/
        private UUID host;
        /**the name of the host that's displayed, This isn't used for anything more than saving us from having to search the world for an entity, that probably isn't loaded, just to get it's name.*/
        private String hostname;

        public KeySaveData(String name){
            super(name);
        }

        /**
         * <h2> Data Syncing and Saving </h2>
         * NBT is save data, which only happens on server.
         */
        /**loads the entity's save file*/
        @Override
        public void readFromNBT(NBTTagCompound tag) {
            host = new UUID(tag.getLong("key.most"), tag.getLong("key.least"));
            hostname = tag.getString("key.parent");

        }

        /**saves the entity to server world*/
        @Override
        public void writeToNBT(NBTTagCompound tag) {
            tag.setLong("key.most", host.getMostSignificantBits());
            tag.setLong("key.least", host.getLeastSignificantBits());
            tag.setString("key.parent", hostname);


        }
    }
}
