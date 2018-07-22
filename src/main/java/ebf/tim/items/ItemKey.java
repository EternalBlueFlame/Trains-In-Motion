package ebf.tim.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <h1>Key Item</h1>
 * the key used to allow people other than the owner to interact with a locked train or rollingstock.
 * @author Eternal Blue Flame
 */
public class ItemKey extends Item{

    public ItemKey(UUID host, String entityName){
        data.host.add(host);
        data.hostname.add(entityName);
    }

    public void addHost(UUID host, String entityName){
        data.host.add(host);
        data.hostname.add(entityName);
    }

    private KeySaveData data = new KeySaveData("ItemKey");

    public List<UUID> getHostList(){
        return data.host;
    }

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
            stringList.add("This key unlocks: ");
        }
        for (int i=0; i<data.hostname.size(); i++) {
            stringList.add(data.hostname.get(i));
            stringList.add(data.host.get(i));
        }
    }


    public List<UUID> getTransport(){
        return data.host;
    }

    private class KeySaveData extends WorldSavedData{
        /**the UUID from server for the entity this belongs to, this will be compared to a key item in the entity, rather than the entity itself due to different UUID's on client and server.*/
        private List<UUID> host = new ArrayList<>();
        /**the name of the host that's displayed, This isn't used for anything more than saving us from having to search the world for an entity, that probably isn't loaded, just to get it's name.*/
        private List<String> hostname = new ArrayList<>();

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
            int arrayLength = tag.getInteger("locks");
            for (int i=0; i<arrayLength; i++) {
                host.add(new UUID(tag.getLong("key.most."+i), tag.getLong("key.least."+i)));
                hostname.add(tag.getString("key.parent."+i));
            }

        }

        /**saves the entity to server world*/
        @Override
        public void writeToNBT(NBTTagCompound tag) {
            tag.setInteger("locks", host.size());
            for (int i=0; i<host.size(); i++) {
                tag.setLong("key.most."+i, host.get(i).getMostSignificantBits());
                tag.setLong("key.least."+i, host.get(i).getLeastSignificantBits());
                tag.setString("key.parent."+i, hostname.get(i));
            }


        }
    }
}
