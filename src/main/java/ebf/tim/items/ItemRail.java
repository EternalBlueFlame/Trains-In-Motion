package ebf.tim.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.blocks.RailTileEntity;
import ebf.tim.blocks.rails.BlockRailCore;
import ebf.tim.utility.CommonProxy;
import jdk.nashorn.internal.ir.Block;
import mods.railcraft.api.core.items.ITrackItem;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockMushroom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import java.util.List;
import java.util.UUID;

/**
 * <h1>Key Item</h1>
 * the key used to allow people other than the owner to interact with a locked train or rollingstock.
 * @author Eternal Blue Flame
 */
public class ItemRail extends Item implements ITrackItem {

    public ItemRail(){}

    public ItemRail setIngot(Item railIngot){
        data.railIngot = railIngot.delegate.name();
        return this;
    }
    public ItemRail setBallast(Item railIngot){
        data.ballast = railIngot.delegate.name();
        return this;
    }
    public ItemRail setTies(Item railIngot){
        data.ties = railIngot.delegate.name();
        return this;
    }
    public ItemRail setWire(Item railIngot){
        data.wires = railIngot.delegate.name();
        return this;
    }

    private railSaveData data = new railSaveData("ItemRail");

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        net.minecraft.block.Block block = world.getBlock(x, y, z);

        if (block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1) {
            meta = 1;
        } else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush) {
            switch (meta) {
                case 0:{--y;break;}
                case 1:{++y;break;}
                case 2:{--z;break;}
                case 3:{++z;break;}
                case 4:{--x;break;}
                case 5:{++x;break;}
            }
        }

        if (!player.canPlayerEdit(x, y, z, meta, stack) || stack.stackSize==0) {
            return false;
        } else {
            if (world.canPlaceEntityOnSide(getPlacedBlock(), x, y, z, false, meta, null, stack))
            {
                int i1 = getPlacedBlock().onBlockPlaced(world, x, y, z, meta, p_77648_8_, p_77648_9_, p_77648_10_, 0);

                if (world.setBlock(x, y, z, getPlacedBlock(), 0, 3)) {
                    if (world.getBlock(x, y, z) == getPlacedBlock()) {
                        getPlacedBlock().onBlockPlacedBy(world, x, y, z, player, stack);
                        getPlacedBlock().onPostBlockPlaced(world, x, y, z, i1);
                    }

                    world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, getPlacedBlock().stepSound.func_150496_b(), (getPlacedBlock().stepSound.getVolume() + 1.0F) / 2.0F, getPlacedBlock().stepSound.getPitch() * 0.8F);
                    --stack.stackSize;
                }
            }

            return true;
        }
    }

    public boolean isPlacedTileEntity(ItemStack stack, TileEntity tile){
        return tile.getClass() == RailTileEntity.class;
    }

    public net.minecraft.block.Block getPlacedBlock(){
        return CommonProxy.railBlock;
    }

    public boolean placeTrack(ItemStack stack, World world, int x, int y, int z){
        net.minecraft.block.Block block = world.getBlock(x, y, z);

        if (!(World.doesBlockHaveSolidTopSurface(world ,x, y, z))){
            return false;
        }

        if(block.isReplaceable(world, x, y+1, z) || block instanceof BlockFlower || block == Blocks.double_plant || block instanceof BlockMushroom){
            block.dropBlockAsItem(world, x, y+1, z, world.getBlockMetadata(x, y+1, z), 0);
        }

        world.setBlock(x, y+1, z, getPlacedBlock(), 0/*no meta, let the block figure that out*/, 3 /*force update and re-render*/);
        return true;
    }

    /**
     * <h2>Description text</h2>
     * Allows items to add custom lines of information to the mouseover description, by adding new lines to stringList.
     * Each string added defines a new line.
     * We can cover the key and ticket description here, to simplify other classes.
     */
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack thisStack, EntityPlayer player, List stringList, boolean p_77624_4_) {
        stringList.add(data.railIngot + " rails.");
        if (!data.ballast.equals("")){
            stringList.add(data.ballast + " ballast.");
        }
        if (!data.ties.equals("")){
            stringList.add(data.ties + " ties.");
        }
        if (!data.wires.equals("")){
            stringList.add(data.wires + " wire.");
        }
    }


    private class railSaveData extends WorldSavedData{
        public String railIngot ="";
        public String ballast ="";
        public String ties ="";
        public String wires ="";

        public railSaveData(String name){
            super(name);
        }

        /**
         * <h2> Data Syncing and Saving </h2>
         * NBT is save data, which only happens on server.
         */
        /**loads the entity's save file*/
        @Override
        public void readFromNBT(NBTTagCompound tag) {
            railIngot=tag.getString("rail");
            ballast=tag.getString("ballast");
            ties=tag.getString("ties");
            wires=tag.getString("wires");
        }

        /**saves the entity to server world*/
        @Override
        public void writeToNBT(NBTTagCompound tag) {
            tag.setString("rail", railIngot);
            tag.setString("ballast", ballast);
            tag.setString("ties", ties);
            tag.setString("wires", wires);
        }
    }
}