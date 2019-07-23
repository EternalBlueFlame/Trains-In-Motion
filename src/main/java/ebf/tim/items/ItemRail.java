package ebf.tim.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.XmlBuilder;
import ebf.tim.blocks.RailTileEntity;
import ebf.tim.blocks.rails.BlockRailCore;
import ebf.tim.utility.CommonProxy;
import ebf.tim.utility.DebugUtil;
import ebf.tim.utility.RailUtility;
import mods.railcraft.api.core.items.ITrackItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockMushroom;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

/**
 * <h1>Key Item</h1>
 * the key used to allow people other than the owner to interact with a locked train or rollingstock.
 * @author Eternal Blue Flame
 */
public class ItemRail extends Item implements ITrackItem {

    public ItemRail(){}

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    @Deprecated
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        if(world.isRemote){return true;}
        net.minecraft.block.Block block = world.getBlock(x, y, z);

        if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush) {
            switch (meta) {
                case 0:{--y;break;}
                case 1:{++y;break;}
                case 2:{--z;break;}
                case 3:{++z;break;}
                case 4:{--x;break;}
                case 5:{++x;break;}
            }
        }

        if (!player.canPlayerEdit(x,y,z, meta, stack) || stack.stackSize==0 ||
        !world.getChunkProvider().chunkExists(
                x>>4, z>>4)) {
            return false;
        } else {
            if (world.canPlaceEntityOnSide(getPlacedBlock(),x,y,z, false, meta, null, stack))
            {
                int i1 = getPlacedBlock().onBlockPlaced(world, x,y,z, meta, p_77648_8_, p_77648_9_, p_77648_10_, 0);

                if (world.setBlock(x,y,z, getPlacedBlock(), 0, 3)) {
                    if (world.getBlock(x,y,z) == getPlacedBlock()) {
                        getPlacedBlock().onBlockPlacedBy(world, x,y,z, player, stack);
                        getPlacedBlock().onPostBlockPlaced(world, x,y,z, i1);

                        XmlBuilder c = new XmlBuilder();
                        if(stack.getTagCompound().getTag("rail")!=null) {
                            c.putItemStack("rail",ItemStack.loadItemStackFromNBT(stack.getTagCompound().getCompoundTag("rail")));
                        } else {
                            c.putItemStack("rail", new ItemStack(Items.iron_ingot));
                        }

                        if(stack.getTagCompound().getTag("ties")!=null) {
                            c.putItemStack("ties",ItemStack.loadItemStackFromNBT(stack.getTagCompound().getCompoundTag("ties")));
                        }
                        if(stack.getTagCompound().getTag("ballast")!=null) {
                            c.putItemStack("ballast",ItemStack.loadItemStackFromNBT(stack.getTagCompound().getCompoundTag("ballast")));
                        }
                        if(stack.getTagCompound().getTag("wires")!=null) {
                            c.putItemStack("wires",ItemStack.loadItemStackFromNBT(stack.getTagCompound().getCompoundTag("wires")));
                        }
                        BlockRailCore.updateShape(x,y,z,world,c);
                    }

                    world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, getPlacedBlock().stepSound.func_150496_b(), (getPlacedBlock().stepSound.getVolume() + 1.0F) / 2.0F, getPlacedBlock().stepSound.getPitch() * 0.8F);
                    --stack.stackSize;
                }
            }


            /*
            {//DEPRECIATED
            if(world.getTileEntity(x,y,z) instanceof RailTileEntity && stack.hasTagCompound()){
                if(stack.getTagCompound().getTag("rail")!=null) {
                    ((RailTileEntity) world.getTileEntity(x,y,z)).rail = ItemStack.loadItemStackFromNBT(stack.getTagCompound().getCompoundTag("rail"));
                } else {
                    ((RailTileEntity) world.getTileEntity(x,y,z)).rail = new ItemStack(Items.iron_ingot);
                }
                if(stack.getTagCompound().getTag("ties")!=null) {
                    ((RailTileEntity) world.getTileEntity(x,y,z)).ties = ItemStack.loadItemStackFromNBT(stack.getTagCompound().getCompoundTag("ties"));
                }
                if(stack.getTagCompound().getTag("ballast")!=null) {
                    ((RailTileEntity) world.getTileEntity(x,y,z)).ballast = ItemStack.loadItemStackFromNBT(stack.getTagCompound().getCompoundTag("ballast"));
                }
                if(stack.getTagCompound().getTag("wires")!=null) {
                    ((RailTileEntity) world.getTileEntity(x,y,z)).wires = ItemStack.loadItemStackFromNBT(stack.getTagCompound().getCompoundTag("wires"));
                }
                world.getTileEntity(x,y,z).markDirty();
            } else if(stack.hasTagCompound()) {
                System.out.println("Trains In Motion ERROR, TILE ENTITY NOT SPAWNED FAST ENOUGH, that can happen?");
            }
            }*/
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
    public void addInformation(ItemStack stack, EntityPlayer player, List stringList, boolean p_77624_4_) {


        if( stack.hasTagCompound()){
            if(stack.getTagCompound().getTag("count")!=null) {
                stringList.add(RailUtility.translate("menu.items") + stack.getTagCompound().getInteger("count"));
            } else {
                stringList.add("1 "+RailUtility.translate("menu.item"));
            }

            if(stack.getTagCompound().hasKey("rail")) {
                stringList.add(RailUtility.translate("menu.rails") + " " + ItemStack.loadItemStackFromNBT(stack.getTagCompound().getCompoundTag("rail")).getDisplayName());
            } else {
                stringList.add("default rails");
            }

            //todo: for some reason i ill never understand, the lang file returns ties and ballast backwards.
            if(stack.getTagCompound().hasKey("ballast")) {
                stringList.add(RailUtility.translate("menu.ties")+ " " + ItemStack.loadItemStackFromNBT(stack.getTagCompound().getCompoundTag("ballast")).getDisplayName());
            } else {
                stringList.add(RailUtility.translate("menu.noties"));
            }
            if(stack.getTagCompound().hasKey("ties")) {
                stringList.add(RailUtility.translate("menu.ballast")+ " " + ItemStack.loadItemStackFromNBT(stack.getTagCompound().getCompoundTag("ties")).getDisplayName());
            } else {
                stringList.add(RailUtility.translate("menu.noballast"));
            }

            if(stack.getTagCompound().hasKey("wires")) {
                stringList.add(RailUtility.translate("menu.wires") + " " +ItemStack.loadItemStackFromNBT(stack.getTagCompound().getCompoundTag("wires")).getDisplayName());
            } else {
                stringList.add(RailUtility.translate("menu.nowires"));
            }
        }
    }

    public static ItemStack setStackData(ItemStack stack, ItemStack ingot, ItemStack ballast, ItemStack ties, ItemStack wires){
        //init stack NBT
        stack.setTagCompound(new NBTTagCompound());
        //add a tag for the stack then put the stack in it.
        if(ingot!=null && ingot.getItem()!=null) {
            stack.getTagCompound().setTag("rail", new NBTTagCompound());
            ingot.writeToNBT(stack.getTagCompound().getCompoundTag("rail"));
        }

        //rinse and repeat
        if(ties!=null && ties.getItem()!=null && !isItemBanned(ties)) {
            stack.getTagCompound().setTag("ties",new NBTTagCompound());
            ties.writeToNBT(stack.getTagCompound().getCompoundTag("ties"));
        } else if(ties!=null && ties.getItem()!=null){
            return null;
        }
        if(ballast!=null && ballast.getItem()!=null && !isItemBanned(ballast)) {
            stack.getTagCompound().setTag("ballast",new NBTTagCompound());
            ballast.writeToNBT(stack.getTagCompound().getCompoundTag("ballast"));
        } else if(ballast!=null && ballast.getItem()!=null){
            return null;
        }
        if(wires!=null && wires.getItem()!=null && !isItemWires(wires)) {
            stack.getTagCompound().setTag("wires",new NBTTagCompound());
            wires.writeToNBT(stack.getTagCompound().getCompoundTag("wires"));
        } else if(wires!=null && wires.getItem()!=null){
            return null;
        }
        return stack;
    }

    public static boolean isItemWires(ItemStack s){
        return true;
    }

    public static boolean isItemBanned(ItemStack s){
        return s.getItem().delegate.name().contains("chisel") || Block.getBlockFromItem(s.getItem()).hasTileEntity(s.getItemDamage());
    }

    //adds custom versions of this to the creative menu, with the necessary NBT and metadata
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List tabItems) {
        if(p_150895_1_ instanceof ItemRail) {
            tabItems.add(setStackData(new ItemStack(p_150895_1_), new ItemStack(Items.iron_ingot), new ItemStack(Blocks.log), new ItemStack(Blocks.gravel),null));
            tabItems.add(setStackData(new ItemStack(p_150895_1_), new ItemStack(Items.iron_ingot), new ItemStack(Blocks.planks), new ItemStack(Blocks.gravel), null));
            tabItems.add(setStackData(new ItemStack(p_150895_1_), new ItemStack(Items.iron_ingot), null, new ItemStack(Blocks.gravel),null));
            tabItems.add(setStackData(new ItemStack(p_150895_1_), new ItemStack(Items.iron_ingot), null, new ItemStack(Blocks.stone), null));
            tabItems.add(setStackData(new ItemStack(p_150895_1_), new ItemStack(Items.iron_ingot), null, null, null));
        } else {
            tabItems.add(new ItemStack(p_150895_1_));
        }
    }
}