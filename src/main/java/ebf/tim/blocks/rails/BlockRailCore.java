package ebf.tim.blocks.rails;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.XmlBuilder;
import ebf.tim.blocks.RailTileEntity;
import ebf.tim.items.ItemRail;
import ebf.tim.utility.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLogic;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Eternal Blue Flame
 */
public class BlockRailCore extends BlockRail implements ITileEntityProvider {

    //RailTileEntity tile = null;
    private static final int[] updateMatrix = {-2,-1,0,1,2};

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
    public float tieCount=0;
    //used to define the number of rails and their offset from the center.
    public float[] railGauges;
    //TODO: only rendered, to show other paths, maybe rework so they are all in the same list and just have a bool for which is active?
    //public List<RailPointData> cosmeticPoints = new ArrayList<>();*/


    public BlockRailCore(){
        setCreativeTab(null);
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public int tickRate(World world){return 40;}


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


    @Override
    public TileEntity createNewTileEntity(World world, int meta){
        return new RailTileEntity();
    }

    //@Override
    //public TileEntity createTileEntity(World world, int metadata){
    //    return new RailTileEntity();
    //}

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
                }
                changed = true;
                break;
            }
            case 1: {
                if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 0) {
                    meta = 0;
                }
                changed = true;
                break;
            }
            case 6:{
                if(world.getBlockMetadata(x+1,y,z)==9 && cart.motionZ>0 && cart.getEntityData().getInteger("tim.lastusedrail.meta")==0){
                    return 0;//this already worked fine, but make it smoother
                } else if(world.getBlockMetadata(x+1,y,z)==7){
                    if(cart.getEntityData().getInteger("tim.lastusedrail.meta")==0) {
                        return 0;//cover parallel entering from wrong end on straight
                    } else if(cart.getEntityData().getInteger("tim.lastusedrail.meta")==7){
                        return 9;//cover parallel off shape
                    }
                } else if(world.getBlockMetadata(x,y,z+1)==8 && cart.motionX>0 && cart.getEntityData().getInteger("tim.lastusedrail.meta")==1){
                    return 1;//this already worked fine, but make it smoother
                } else if (world.getBlockMetadata(x,y,z+1)==9) {
                    if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 1) {
                        return 1;//cover parallel entering from wrong end on straight
                    } else if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 7) {
                        return 9;//cover parallel off shape
                    }
                }
                changed = true;
                break;
            }
            case 7:{
                if(world.getBlockMetadata(x-1,y,z)==9 && cart.motionX>0 && cart.getEntityData().getInteger("tim.lastusedrail.meta")==0){
                    return 0;//this already worked fine, but make it smoother
                } else if(world.getBlockMetadata(x-1,y,z)==8){
                    if(cart.getEntityData().getInteger("tim.lastusedrail.meta")==0) {
                        return 0;//cover parallel entering from wrong end on straight
                    } else if(cart.getEntityData().getInteger("tim.lastusedrail.meta")==9){
                        return 6;//cover parallel off shape
                    }
                } else if(world.getBlockMetadata(x,y,z+1)==9 && cart.motionX>0 && cart.getEntityData().getInteger("tim.lastusedrail.meta")==1){
                    return 1;//this already worked fine, but make it smoother
                } else if (world.getBlockMetadata(x,y,z+1)==8) {
                    if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 1) {
                        return 1;//cover parallel entering from wrong end on straight
                    } else if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 7) {
                        return 6;//cover parallel off shape
                    }
                }
                changed = true;
                break;
            }
            case 8:{
                if(world.getBlockMetadata(x-1,y,z)==6 && cart.motionZ<0 && cart.getEntityData().getInteger("tim.lastusedrail.meta")==0){
                    return 0;//this already worked fine, but make it smoother
                } else if(world.getBlockMetadata(x-1,y,z)==9){
                    if(cart.getEntityData().getInteger("tim.lastusedrail.meta")==0) {
                        return 0;//cover parallel entering from wrong end on straight
                    } else if(cart.getEntityData().getInteger("tim.lastusedrail.meta")==9){
                        return 7;//cover parallel off shape
                    }
                } else if(world.getBlockMetadata(x,y,z-1)==6 && cart.motionX<0 && cart.getEntityData().getInteger("tim.lastusedrail.meta")==1){
                    return 1;//this already worked fine, but make it smoother
                } else if (world.getBlockMetadata(x,y,z-1)==7) {
                    if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 1) {
                        return 1;//cover parallel entering from wrong end on straight
                    } else if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 7) {
                        return 9;//cover parallel off shape
                    }
                }
                changed = true;
                break;
            }
            case 9:{
                if(world.getBlockMetadata(x+1,y,z)==6 && cart.motionZ>0 && cart.getEntityData().getInteger("tim.lastusedrail.meta")==0){
                    return 0;//this already worked fine, but make it smoother
                } else if(world.getBlockMetadata(x+1,y,z)==7){
                    if(cart.getEntityData().getInteger("tim.lastusedrail.meta")==0) {
                        return 0;//cover parallel entering from wrong end on straight
                    } else if(cart.getEntityData().getInteger("tim.lastusedrail.meta")==8){
                        return 6;//cover parallel off shape
                    }
                } else if(world.getBlockMetadata(x,y,z-1)==7 && cart.motionX>0 && cart.getEntityData().getInteger("tim.lastusedrail.meta")==1){
                    return 1;//this already worked fine, but make it smoother
                } else if (world.getBlockMetadata(x,y,z-1)==6) {
                    if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 1) {
                        return 1;//cover parallel entering from wrong end on straight
                    } else if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 6) {
                        return 8;//cover parallel off shape
                    }
                }
                changed = true;
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
        if(world.getTileEntity(x,y,z) instanceof RailTileEntity) {
            XmlBuilder xml =new XmlBuilder(((RailTileEntity) world.getTileEntity(x,y,z)).data.toXMLString());
            return ItemRail.setStackData(
                    new ItemStack(CommonProxy.railItem, 1), xml.getItemStack("rail"),
                    xml.getItemStack("ballast"), xml.getItemStack("ties"), xml.getItemStack("wires"));
        } else {
            return null;
        }
    }
    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        if(world.getTileEntity(x,y,z) instanceof RailTileEntity) {
            XmlBuilder xml =new XmlBuilder(((RailTileEntity) world.getTileEntity(x,y,z)).data.toXMLString());

            ArrayList<ItemStack> out = new ArrayList<>();
            out.add(ItemRail.setStackData(
                    new ItemStack(CommonProxy.railItem, 1), xml.getItemStack("rail"),
                    xml.getItemStack("ballast"), xml.getItemStack("ties"), xml.getItemStack("wires")));
            return out;
        } else {
            return null;
        }
    }

    @Override
    public void randomDisplayTick(World worldObj, int xCoord, int yCoord, int zCoord, Random p_149674_5_) {
        //super.randomDisplayTick(worldObj, xCoord, yCoord, zCoord, p_149674_5_);
    }




    @Override
    public void onNeighborBlockChange(World worldObj, int x, int y, int z, Block b) {
        if(!(b instanceof BlockRailCore)) {
            super.onNeighborBlockChange(worldObj, x, y, z, b);
        }
        updateShape(x,y,z,worldObj, null);
        if(worldObj.getTileEntity(x,y,z) instanceof RailTileEntity){
            worldObj.getTileEntity(x,y,z).markDirty();
        }
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
    }

    public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_) {
        for(int x : updateMatrix){
            for(int z : updateMatrix){
                for(int y : updateMatrix){
                    if(p_149660_1_.getBlock(x+p_149660_2_,y+p_149660_3_,z+p_149660_4_) instanceof  BlockRailCore){
                        p_149660_1_.getBlock(x+p_149660_2_,y+p_149660_3_,z+p_149660_4_).onNeighborBlockChange(p_149660_1_,p_149660_2_,p_149660_3_,p_149660_4_, this);
                        updateShape(x,y,z,p_149660_1_, null);
                    }
                }
            }
        }
        return p_149660_9_;
    }


    @Override
    public boolean onBlockEventReceived(World p_149696_1_, int p_149696_2_, int p_149696_3_, int p_149696_4_, int p_149696_5_, int p_149696_6_) {
        return super.onBlockEventReceived(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_5_, p_149696_6_);
        //return getTile(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_) != null && getTile(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_).receiveClientEvent(p_149696_5_, p_149696_6_);
    }

    public static final int[] gauge750mm={750};

    public static void updateShape(int xPos, int yPos, int zPos, World worldObj, @Nullable XmlBuilder data){
        RailShapeCore.processPoints(xPos, yPos, zPos, getShape(worldObj, xPos, yPos, zPos),gauge750mm,worldObj, data);
    }


    public static RailSimpleShape getShape(World worldObj, int xPos, int yPos, int zPos){
        switch (worldObj.getBlockMetadata(xPos, yPos, zPos)){
            //Z straight
            case 0: {
                return RailVanillaShapes.vanillaZStraight(worldObj, xPos, yPos, zPos);
            }
            //X straight
            case 1: {
                return RailVanillaShapes.vanillaXStraight(worldObj, xPos, yPos, zPos);
            }

            //curves
            case 9: {
                return RailVanillaShapes.vanillaCurve9(worldObj, xPos, yPos, zPos);
            }
            case 8: {
                return RailVanillaShapes.vanillaCurve8(worldObj, xPos, yPos, zPos);
            }
            case 7: {
                return RailVanillaShapes.vanillaCurve7(worldObj, xPos, yPos, zPos);
            }
            case 6: {
                return RailVanillaShapes.vanillaCurve6(worldObj, xPos, yPos, zPos);
            }
            //Z slopes
            case 5 :{
                return RailVanillaShapes.vanillaSlopeZ5(worldObj, xPos, yPos, zPos);
            }
            case 4 :{
                return RailVanillaShapes.vanillaSlopeZ4(worldObj, xPos, yPos, zPos);
            }
            //X slopes
            case 2 :{
                return RailVanillaShapes.vanillaSlopeX2(worldObj, xPos, yPos, zPos);
            }
            case 3 :{
                return RailVanillaShapes.vanillaSlopeX3(worldObj, xPos, yPos, zPos);
            }
        }
        return null;
    }


    /**
     * checks an array of blocks for a specific class and meta to check if the shape should be made.
     * @param worldobj
     * @param positionsWithMeta array of vector 4's with the 4th value being the desired meta
     * @return boolean for if the shape can be made
     */
    public static boolean checkBlockMeta(World worldobj, int x, int y, int z, int ... meta){
        if (!worldobj.getChunkProvider().chunkExists(x/16, z/16) ||
                !(worldobj.getBlock(x,y,z) instanceof BlockRailBase)){
            return false;
        }
        for(int i : meta){
            if(worldobj.getBlockMetadata(x, y, z) ==i){
                return true;
            }
        }
        return  false;
    }


    public static int[] getNearbyMeta(World world, int xCoord, int yCoord, int zCoord){
        int[] meta = new int[9];
        int i=0;
        for(int z=-1;z<2;z++){
            for(int x=-1;x<2;x++){
                if(world.getBlock(xCoord+x,yCoord,zCoord+z) instanceof BlockRailBase){
                    meta[i]= ((BlockRailBase) world.getBlock(xCoord+x,yCoord,zCoord+z)).
                            getBasicRailMetadata(world,null,xCoord+x,yCoord,zCoord+z);
                } else {
                    meta[i]=-1;
                }
                i++;
            }
        }
        return meta;
    }


    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return Blocks.rail.getIcon(p_149691_1_, p_149691_2_);
    }
}
