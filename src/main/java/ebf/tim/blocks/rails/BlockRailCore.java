package ebf.tim.blocks.rails;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.TrainsInMotion;
import ebf.tim.blocks.RailTileEntity;
import ebf.tim.items.ItemRail;
import ebf.tim.utility.CommonProxy;
import ebf.tim.utility.RailUtility;
import fexcraft.tmt.slim.Vec3f;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLogic;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Eternal Blue Flame
 */
public class BlockRailCore extends BlockRail {

    //RailTileEntity tile = null;
    private static final int[] updateMatrix = {-1,0,1};

    /*
    public ItemStack rail;
    private int[] railColor=null;
    public ItemStack ballast;
    public ItemStack ties;
    public ItemStack wires;
    public int snow=0;
    public int timer=0;
    public int overgrowth=0;
    Integer railGLID=null;


    //used for the actual path, and rendered
    public List<float[]> points = new ArrayList<>();
    public float segmentLength=0;
    //used to define the number of rails and their offset from the center.
    public float[] railGauges;
    //TODO: only rendered, to show other paths, maybe rework so they are all in the same list and just have a bool for which is active?
    //public List<RailPointData> cosmeticPoints = new ArrayList<>();*/


    public BlockRailCore(){
        setCreativeTab(null);
    }


    public boolean hasTileEntity(int metadata) {
        return true;
    }
    @Override
    public int tickRate(World world){return 10;}


    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean getTickRandomly(){return true;}

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isFlexibleRail(IBlockAccess world, int y, int x, int z){return true;}

    @Override
    public float getRailMaxSpeed(World world, EntityMinecart cart, int y, int x, int z){
        return 0.4f;//getTile(world, x, y, z)!=null?getTile(world, x, y, z).getRailSpeed():0.4f;
    }

    /*
    @Override
    public TileEntity createNewTileEntity(World world, int meta){
        return new RailTileEntity();
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata){
        return new RailTileEntity();
    }

*/
    @Override
    public int getBasicRailMetadata(IBlockAccess world, EntityMinecart cart, int x, int y, int z) {
        int meta = super.getBasicRailMetadata(world, cart, x, y, z);
        if (cart == null || cart.getEntityData() == null){
                return meta;
            }
        //first be sure the key exists, and create it if it doesn't, that way we be sure we don't crash. Also if it doesn't exist we can just return the base meta unchanged.
                if (!cart.getEntityData().hasKey("tim.lastusedrail.meta")){
                cart.getEntityData().setInteger("tim.lastusedrail.meta",meta);
                return meta;
            }
        boolean changed = false;
        switch (meta) {
            //add support for intersections
            case 0: {
                //only part that theoretically works.
                if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 1) {
                    meta = 1;
                } else {
                    changed = true;
                }
                break;
            }
            case 1: {
                if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 0) {
                    meta = 0;
                } else {
                    changed = true;
                }
                break;
            }
            default: {
                changed = true;
            }
        }
        //note changes in the entity so we can keep track of whether or not this is an intersection
        if (changed){
            cart.getEntityData().setInteger("tim.lastusedrail.meta",meta);
            cart.getEntityData().setInteger("tim.lastusedrail.x",x);
            cart.getEntityData().setInteger("tim.lastusedrail.z",z);
        }
        return meta;
    }

    @Override
    public Material getMaterial(){
        return new MaterialLogic(MapColor.mapColorArray[28]);
    }

    @Override
    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
        return true;
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        return getPickBlock(target, world, x, y, z);
    }
    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        /*if(getTile(world,x,y,z)!=null) {
            return ItemRail.setStackData(
                    new ItemStack(CommonProxy.railItem, 1), tile.rail, tile.ballast, tile.ties, tile.wires);
        } else {*/
            return null;
        //}
    }
    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
       /* if(getTile(world,x,y,z)!=null) {
            ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

            int count = quantityDropped(metadata, fortune, world.rand);
            for (int i = 0; i < count; i++) {
                ret.add(ItemRail.setStackData(
                        new ItemStack(CommonProxy.railItem, 1), tile.rail, tile.ballast, tile.ties, tile.wires));
            }
            return ret;
        } else {*/
            return null;
        //}
    }

    @Override
    public void randomDisplayTick(World worldObj, int xCoord, int yCoord, int zCoord, Random p_149674_5_) {
        //super.randomDisplayTick(worldObj, xCoord, yCoord, zCoord, p_149674_5_);
    }




    @Override
    public void onNeighborBlockChange(World worldObj, int x, int y, int z, Block b) {
        super.onNeighborBlockChange(worldObj, x, y, z, b);
        updateShape(x,y,z,worldObj, null);
        //if(getTile(worldObj,x,y,z)!=null){
        //    getTile(worldObj,x,y,z).markDirty();
        //}
    }

    //stuff from block container to make tile entity more reliable.
    @Override
    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
        p_149749_1_.removeTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
        for(int x : updateMatrix){
            for(int z : updateMatrix){
                for(int y : updateMatrix){
                    if(p_149749_1_.getBlock(x+p_149749_2_,y+p_149749_3_,z+p_149749_4_) instanceof  BlockRailCore){
                        p_149749_1_.getBlock(x+p_149749_2_,y+p_149749_3_,z+p_149749_4_).onNeighborBlockChange(p_149749_1_,p_149749_2_,p_149749_3_,p_149749_4_, this);
                        updateShape(x,y,z,p_149749_1_, null);
                    }
                }
            }
        }
        CommonProxy.getRailMap(p_149749_1_).remove(p_149749_2_,p_149749_3_,p_149749_4_, p_149749_1_);
    }

    public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_) {
        for(int x : updateMatrix){
            for(int z : updateMatrix){
                for(int y : updateMatrix){
                    if(p_149660_1_.getBlock(x+p_149660_2_,y+p_149660_3_,z+p_149660_4_) instanceof  BlockRailCore){
                        p_149660_1_.getBlock(x+p_149660_2_,y+p_149660_3_,z+p_149660_4_).onNeighborBlockChange(p_149660_1_,p_149660_2_,p_149660_3_,p_149660_4_, this);
                        //p_149660_1_.getTileEntity(x+p_149660_2_,y+p_149660_3_,z+p_149660_4_).markDirty();
//                        updateShape(x,y,z,p_149660_1_, null);
                    }
                }
            }
        }
        return p_149660_9_;
    }

    public static void updateBlocks(int[] matrix, int xPos, int yPos, int zPos, World w){
        for(int x : matrix){
            for(int z : matrix){
                for(int y : matrix){
                    if(w.getBlock(x+xPos,y+yPos,z+zPos) instanceof  BlockRailCore){
                        updateShape(x,y,z,w, null);
                    }
                }
            }
        }
    }

    @Override
    public boolean onBlockEventReceived(World p_149696_1_, int p_149696_2_, int p_149696_3_, int p_149696_4_, int p_149696_5_, int p_149696_6_) {
        return super.onBlockEventReceived(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_5_, p_149696_6_);
        //return getTile(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_) != null && getTile(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_).receiveClientEvent(p_149696_5_, p_149696_6_);
    }

    public static final int[] gauge750mm={375, -375};

    public static void updateShape(int xPos, int yPos, int zPos, World worldObj, @Nullable NBTTagCompound data){
        //List<> points= new ArrayList<>();
        //todo: process these directly into quad/processPoints(); on server, then sync the offsets over the NBT network packet.
        switch (worldObj.getBlockMetadata(xPos, yPos, zPos)){
            //Z straight
            case 0: {
                RailShapeCore.processPoints(xPos, yPos, zPos, RailVanillaShapes.vanillaZStraight(worldObj, xPos, yPos, zPos), gauge750mm, 4, worldObj, data);
                break;
            }
            //X straight
            case 1: {
                RailShapeCore.processPoints(xPos, yPos, zPos, RailVanillaShapes.vanillaXStraight(worldObj, xPos, yPos, zPos), gauge750mm, 4, worldObj, data);
                break;
            }

            //curves
            case 9: {
                RailShapeCore.processPoints(xPos, yPos, zPos, RailVanillaShapes.vanillaCurve9(worldObj, xPos, yPos, zPos), gauge750mm, 4, worldObj, data);
                break;
            }
            case 8: {
                RailShapeCore.processPoints(xPos, yPos, zPos, RailVanillaShapes.vanillaCurve8(worldObj, xPos, yPos, zPos), gauge750mm, 4, worldObj, data);
                break;
            }
            case 7: {
                RailShapeCore.processPoints(xPos, yPos, zPos, RailVanillaShapes.vanillaCurve7(worldObj, xPos, yPos, zPos), gauge750mm, 4, worldObj, data);
                break;
            }
            case 6: {
                RailShapeCore.processPoints(xPos, yPos, zPos, RailVanillaShapes.vanillaCurve6(worldObj, xPos, yPos, zPos), gauge750mm, 4, worldObj, data);
                break;
            }
            //Z slopes
            case 5 :{
                RailShapeCore.processPoints(xPos, yPos, zPos, RailVanillaShapes.vanillaSlopeZ5(worldObj, xPos, yPos, zPos), gauge750mm, 4, worldObj, data);
                break;
            }
            case 4 :{
                RailShapeCore.processPoints(xPos, yPos, zPos, RailVanillaShapes.vanillaSlopeZ4(worldObj, xPos, yPos, zPos), gauge750mm, 4, worldObj, data);
                break;
            }
            //X slopes
            case 2 :{
                RailShapeCore.processPoints(xPos, yPos, zPos, RailVanillaShapes.vanillaSlopeX2(worldObj, xPos, yPos, zPos), gauge750mm, 4, worldObj, data);
                break;
            }
            case 3 :{
                RailShapeCore.processPoints(xPos, yPos, zPos, RailVanillaShapes.vanillaSlopeX3(worldObj, xPos, yPos, zPos), gauge750mm, 4, worldObj, data);
                break;
            }
        }
    }



    /*
    @Deprecated
    public RailTileEntity getTile(World worldObj, int x, int y, int z){
        if(tile !=null){
            return tile;
        } else if (worldObj.getTileEntity(x,y,z) instanceof RailTileEntity) {
            return tile = (RailTileEntity) worldObj.getTileEntity(x,y,z);
        } else {
            return null;
        }
    }*/


    /**
     * checks an array of blocks for a specific class and meta to check if the shape should be made.
     * @param worldobj
     * @param positionsWithMeta array of vector 4's with the 4th value being the desired meta
     * @return boolean for if the shape can be made
     */
    public static boolean checkMultiblock(World worldobj, int[][] positionsWithMeta){
        for(int[] point : positionsWithMeta){
            if (!worldobj.getChunkProvider().chunkExists(point[0]/16, point[2]/16)){
                return false;
            } else if (!(worldobj.getBlock(point[0],point[1],point[2]) instanceof BlockRailBase)){
                return false;
            } else if (worldobj.getBlockMetadata(point[0],point[1],point[2]) != point[3]){
                return false;
            }
        }
        return true;
    }
}
