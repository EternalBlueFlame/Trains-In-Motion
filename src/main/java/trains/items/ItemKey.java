package trains.items;

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


    private KeySaveData data = new KeySaveData("ItemKey");

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_) {
        p_77624_3_.add("This key belongs to:");
        p_77624_3_.add(data.hostname);
    }



    private class KeySaveData extends WorldSavedData{

        public KeySaveData(String name){
            super(name);
        }

        /**
         * <h2> variables</h2>
         * host defines the UUID from server for the entity this belongs to, this will be compared to a key item
         *  in the entity's inventory, rather than the entity itself due to different UUID's on client and server.
         * The hostname defines the name of the host that's displayed, This isn't used for anything more than
         *  saving us from having to search the world for an entity, that probably isn't loaded, just to get it's name.
         */
        private UUID host;
        private String hostname;


        /**
         * Returns the NBTTagCompound of the ItemStack.
         */
        @Override
        public void readFromNBT(NBTTagCompound tag) {
            return;
        }

        /**
         * Assigns a NBTTagCompound to the ItemStack, minecraft validates that only non-stackable items can have it.
         */
        @Override
        public void writeToNBT(NBTTagCompound p_77982_1_) {

        }
    }
}
