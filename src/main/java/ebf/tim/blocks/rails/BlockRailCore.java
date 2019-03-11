package ebf.tim.blocks.rails;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.blocks.RailTileEntity;
import ebf.tim.items.ItemRail;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Eternal Blue Flame
 */
public class BlockRailCore extends BlockRail implements ITileEntityProvider {

    RailTileEntity tile = null;
    private static final int[] updateMatrix = {-1,0,1};


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
        return getTile(world, x, y, z)!=null?getTile(world, x, y, z).getRailSpeed():0.4f;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta){
        return new RailTileEntity();
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata){
        return new RailTileEntity();
    }

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
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        //todo: return the generated item you'd get from middle click
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return null;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        int count = quantityDropped(metadata, fortune, world.rand);
        for(int i = 0; i < count; i++) {
            ret.add(ItemRail.setStackData(
                    new ItemStack(new ItemRail(), 1),tile.rail, tile.ballast, tile.ties, tile.wires));
        }
        return ret;
    }

    @Override
    public void randomDisplayTick(World worldObj, int xCoord, int yCoord, int zCoord, Random p_149674_5_) {
        //super.randomDisplayTick(worldObj, xCoord, yCoord, zCoord, p_149674_5_);
    }


    @Override
    public void updateTick(World worldObj, int xCoord, int yCoord, int zCoord, Random p_149674_5_) {
        //super.updateTick(worldObj, xCoord,yCoord,zCoord,p_149674_5_);
        if(!worldObj.isRemote && tile ==null && worldObj.getTileEntity(xCoord,yCoord,zCoord) instanceof RailTileEntity){
            tile = (RailTileEntity) worldObj.getTileEntity(xCoord,yCoord,zCoord);
        }
    }




    @Override
    public void onNeighborBlockChange(World worldObj, int x, int y, int z, Block b) {
        super.onNeighborBlockChange(worldObj, x, y, z, b);
        if(this.tile!=null){
            this.tile.updateShape();
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
                    }
                }
            }
        }
        return p_149660_9_;
    }

    @Override
    public boolean onBlockEventReceived(World p_149696_1_, int p_149696_2_, int p_149696_3_, int p_149696_4_, int p_149696_5_, int p_149696_6_) {
        super.onBlockEventReceived(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_5_, p_149696_6_);
        return getTile(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_) != null && getTile(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_).receiveClientEvent(p_149696_5_, p_149696_6_);
    }

    public RailTileEntity getTile(World worldObj, int x, int y, int z){
        if(tile !=null){
            return tile;
        } else if (worldObj.getTileEntity(x,y,z) instanceof RailTileEntity) {
            return tile = (RailTileEntity) worldObj.getTileEntity(x,y,z);
        } else {
            return null;
        }
    }


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

    public static void multiTriGenModel(Vec3f[] shape, float[] railOffsets, float segmentation, RailTileEntity tile){
        for(int v=0;v<shape.length-2;v+=3) {
            segmentation = Math.max(segmentation, 1);

            float originalT = Math.abs(shape[v].xCoord) + Math.abs(shape[v].zCoord);
            originalT += Math.abs(shape[v+1].xCoord) + Math.abs(shape[v+1].zCoord);
            originalT += Math.abs(shape[v+2].xCoord) + Math.abs(shape[v+2].zCoord);
            originalT = originalT / (originalT * segmentation);

            float t = -originalT;
            int i;
            //calculate the bezier curve, this initial janky version is used to get an accurate gauge of the distance between points.
            List<float[]> points = new ArrayList<>();
            for (i = 0; i < segmentation + 3; i++) {
                //define position
                points.add(new float[]{
                        (((1 - t) * (1 - t)) * shape[v].xCoord) + (2 * (1 - t) * t * shape[v+1].xCoord) + ((t * t) * shape[v+2].xCoord),//X
                        (((1 - t) * (1 - t)) * shape[v].yCoord) + (2 * (1 - t) * t * shape[v+1].yCoord) + ((t * t) * shape[v+2].yCoord),//Y
                        (((1 - t) * (1 - t)) * shape[v].zCoord) + (2 * (1 - t) * t * shape[v+1].zCoord) + ((t * t) * shape[v+2].zCoord),//X
                });
                t += originalT;
            }

            for (i=1; i < points.size() - 1; i++) {
                tile.points.add(
                        new float[]{points.get(i)[0],points.get(i)[1],points.get(i)[2],0, RailUtility.atan2degreesf(
                                points.get(i-1)[2] - (points.get(i+1)[2]),
                                points.get(i-1)[0] - (points.get(i+1)[0])),0}
                );
            }
        }
        tile.railGauges =railOffsets;
        tile.segmentLength=segmentation;

    }

    @Deprecated
    protected static void triGenModel(Vec3f P1, Vec3f P2, Vec3f P3, float[] railOffsets, float segmentation, RailTileEntity tile){
        segmentation=Math.max(segmentation, 1);

        float originalT =Math.abs(P1.xCoord)+Math.abs(P1.zCoord);
        originalT+=Math.abs(P2.xCoord)+Math.abs(P2.zCoord);
        originalT+=Math.abs(P3.xCoord)+Math.abs(P3.zCoord);
        originalT= originalT/(originalT*segmentation);

        float t=-originalT;
        int i;
        //calculate the bezier curve, this initial janky version is used to get an accurate gauge of the distance between points.
        List<float[]> points = new ArrayList<>();
        for (i=0; i<segmentation+3;i++){
            //define position
            points.add(new float[]{
                    (((1-t)*(1-t)) * P1.xCoord) + (2 * (1-t) * t * P2.xCoord) + ((t*t) * P3.xCoord),//X
                    (((1-t)*(1-t)) * P1.yCoord) + (2 * (1-t) * t * P2.yCoord) + ((t*t) * P3.yCoord),//Y
                    (((1-t)*(1-t)) * P1.zCoord) + (2 * (1-t) * t * P2.zCoord) + ((t*t) * P3.zCoord),//X
            });
            t += originalT;
        }

        for (i=1; i < points.size() - 1; i++) {
            tile.points.add(
                    new float[]{points.get(i)[0],points.get(i)[1],points.get(i)[2],0, RailUtility.atan2degreesf(
                            points.get(i-1)[2] - (points.get(i+1)[2]),
                            points.get(i-1)[0] - (points.get(i+1)[0])),0}
            );
        }
        tile.railGauges =railOffsets;
        tile.segmentLength=segmentation;
    }
}
