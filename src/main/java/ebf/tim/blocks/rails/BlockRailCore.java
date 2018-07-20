package ebf.tim.blocks.rails;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.TrainsInMotion;
import ebf.tim.blocks.RailTileEntity;
import ebf.tim.items.ItemRail;
import ebf.tim.models.rails.*;
import ebf.tim.utility.ClientProxy;
import ebf.tim.utility.CommonProxy;
import net.minecraft.block.Block;
import ebf.tim.utility.RailUtility;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import fexcraft.tmt.slim.Vec3f;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Eternal Blue Flame
 */
public class BlockRailCore extends BlockRail implements ITileEntityProvider {

    RailTileEntity tile = null;


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
        return Material.circuits;//tells the world not to let entities spawn on it, or things be placed on top of it.
    }

    @Override
    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
        return true;
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return new ItemRail();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return new ItemRail();
    }

    @Override
    public void randomDisplayTick(World worldObj, int xCoord, int yCoord, int zCoord, Random p_149674_5_) {
        //super.randomDisplayTick(worldObj, xCoord, yCoord, zCoord, p_149674_5_);
    }


    @Override
    public void updateTick(World worldObj, int xCoord, int yCoord, int zCoord, Random p_149674_5_) {
        //super.updateTick(worldObj, xCoord,yCoord,zCoord,p_149674_5_);
        if(!worldObj.isRemote && getTile(worldObj,xCoord,yCoord,zCoord) ==null){
            tile = (RailTileEntity) worldObj.getChunkProvider().loadChunk(xCoord >> 4,zCoord >>4).func_150806_e(xCoord,yCoord,zCoord);
        }
    }




    @Override
    public void onNeighborBlockChange(World worldObj, int x, int y, int z, Block b) {
        super.onNeighborBlockChange(worldObj, x, y, z, b);
    }

    //stuff from block container to make tile entity more reliable.
    @Override
    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
        p_149749_1_.removeTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
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


    protected static List<ModelRailSegment> quadGenModel(Vec3f P1, Vec3f P2, Vec3f P3, Vec3f P4, float[] railOffsets, float blockLength, RailTileEntity tile){
        float segment = TrainsInMotion.proxy.isClient()?ClientProxy.railLoD:8;
        segment*=blockLength;
        if (segment<3){
            segment=3;
        }
        double originalT =(Math.abs(P4.xCoord) + Math.abs(P4.zCoord) + Math.abs(P1.xCoord) + Math.abs(P1.zCoord))/ (segment*(Math.abs(P4.xCoord) + Math.abs(P4.zCoord) + Math.abs(P1.xCoord) + Math.abs(P1.zCoord)));
        double t=-originalT;
        int i;
        //calculate the bezier curve
        List<float[]> points = new ArrayList<>();
        for (i=0; i<segment+3;i++){
            //define position
            points.add(new float[]{
                    (float) ((Math.pow(1 - t, 3) * P1.xCoord) + (3*Math.pow(1-t,2)*t*P2.xCoord) + (3*(1-t)*Math.pow(t,2)*P3.xCoord) + (Math.pow(t,3)*P4.xCoord)),//X
                    (float) ((Math.pow(1 - t, 3) * P1.yCoord) + (3*Math.pow(1-t,2)*t*P2.yCoord) + (3*(1-t)*Math.pow(t,2)*P3.yCoord) + (Math.pow(t,3)*P4.yCoord)),//Y
                    (float) ((Math.pow(1 - t, 3) * P1.zCoord) + (3*Math.pow(1-t,2)*t*P2.zCoord) + (3*(1-t)*Math.pow(t,2)*P3.zCoord) + (Math.pow(t,3)*P4.zCoord))//Z
            });
            t += originalT;
        }
        points.set(points.size()-2, new float[]{P4.xCoord,P4.yCoord,P4.zCoord});
        points.set(1, new float[]{P1.xCoord,P1.yCoord,P1.zCoord});

        originalT =(Math.abs(P4.xCoord) + Math.abs(P4.zCoord) + Math.abs(P1.xCoord) + Math.abs(P1.zCoord))/ ((blockLength*4f)*(Math.abs(P4.xCoord) + Math.abs(P4.zCoord) + Math.abs(P1.xCoord) + Math.abs(P1.zCoord)));
        t=-originalT;
        List<float[]> tiePoints = new ArrayList<>();
        for (i=0; i<(blockLength*4f)+3;i++){
            //define position
            tiePoints.add(new float[]{
                    (float) ((Math.pow(1 - t, 3) * P1.xCoord) + (3*Math.pow(1-t,2)*t*P2.xCoord) + (3*(1-t)*Math.pow(t,2)*P3.xCoord) + (Math.pow(t,3)*P4.xCoord)),//X
                    (float) ((Math.pow(1 - t, 3) * P1.yCoord) + (3*Math.pow(1-t,2)*t*P2.yCoord) + (3*(1-t)*Math.pow(t,2)*P3.yCoord) + (Math.pow(t,3)*P4.yCoord)),//Y
                    (float) ((Math.pow(1 - t, 3) * P1.zCoord) + (3*Math.pow(1-t,2)*t*P2.zCoord) + (3*(1-t)*Math.pow(t,2)*P3.zCoord) + (Math.pow(t,3)*P4.zCoord))//Z
            });
            t += originalT;
        }

        return genSegments(points, tiePoints, railOffsets, tile);
    }

    protected static List<ModelRailSegment> triGenModel(Vec3f P1, Vec3f P2, Vec3f P3, float[] railOffsets, float blockLength, RailTileEntity tile){
        float segment = TrainsInMotion.proxy.isClient()?ClientProxy.railLoD:8;
        segment*=blockLength;
        if (segment<3){
            segment=3;
        }
        double originalT =(Math.abs(P3.xCoord) + Math.abs(P3.zCoord) + Math.abs(P1.xCoord) + Math.abs(P1.zCoord))/ (segment*(Math.abs(P3.xCoord) + Math.abs(P3.zCoord) + Math.abs(P1.xCoord) + Math.abs(P1.zCoord)));
        double t=-originalT;
        int i;
        //calculate the bezier curve
        List<float[]> points = new ArrayList<>();
        for (i=0; i<segment+3;i++){
            //define position
            points.add(new float[]{
                    (float) ((Math.pow(1 - t, 2) * P1.xCoord) + (2 * (1 - t) * t * P2.xCoord) + ((Math.pow(t, 2) * P3.xCoord))),//X
                    (float) ((Math.pow(1 - t, 2) * P1.yCoord) + (2 * (1 - t) * t * P2.yCoord) + ((Math.pow(t, 2) * P3.yCoord))),//Y
                    (float) ((Math.pow(1 - t, 2) * P1.zCoord) + (2 * (1 - t) * t * P2.zCoord) + ((Math.pow(t, 2) * P3.zCoord))),//X
            });
            t += originalT;
        }
        points.set(points.size()-2, new float[]{P3.xCoord,P3.yCoord,P3.zCoord});
        points.set(1, new float[]{P1.xCoord,P1.yCoord,P1.zCoord});

        List<float[]> tiePoints = new ArrayList<>();
        originalT =(Math.abs(P3.xCoord) + Math.abs(P3.zCoord) + Math.abs(P1.xCoord) + Math.abs(P1.zCoord))/ ((blockLength*4f)*(Math.abs(P3.xCoord) + Math.abs(P3.zCoord) + Math.abs(P1.xCoord) + Math.abs(P1.zCoord)));
        t=-originalT;
        for (i=0; i<(blockLength*4f)+3;i++){
            //define position
            tiePoints.add(new float[]{
                    (float) ((Math.pow(1 - t, 2) * P1.xCoord) + (2 * (1 - t) * t * P2.xCoord) + ((Math.pow(t, 2) * P3.xCoord))),//X
                    (float) ((Math.pow(1 - t, 2) * P1.yCoord) + (2 * (1 - t) * t * P2.yCoord) + ((Math.pow(t, 2) * P3.yCoord))),//Y
                    (float) ((Math.pow(1 - t, 2) * P1.zCoord) + (2 * (1 - t) * t * P2.zCoord) + ((Math.pow(t, 2) * P3.zCoord))),//X
            });
            t += originalT;
        }

        return genSegments(points, tiePoints, railOffsets, tile);
    }

    public static List<ModelRailSegment> genSegments(List<float[]> points,List<float[]> tiePoints, float[] railOffsets, RailTileEntity tile){

        double[] offsetInner, offsetOuter;
        double dX, dZ;
        int i, length;
        Float inner, left = null, right = null;
        float[] startEndnd=new float[]{0,0};


        //now make the points
        List<ModelRailSegment> segments = new ArrayList<>();
        for (i = 1; i < points.size() - 1; i++) {

            //define the actual path
            ModelRailSegment seg = new ModelRailSegment();
            seg.position = new float[]{
                    points.get(i)[0] * 16, points.get(i)[1] * 16, points.get(i)[2] * 16
            };

            //define the offset for model modifications.
            dX = points.get(i-1)[0] - (points.get(i + 1)[0]);
            dZ = points.get(i-1)[2] - (points.get(i + 1)[2]);
            offsetOuter = RailUtility.rotatePoint(new double[]{0,0,0.0625}, 0,RailUtility.atan2degreesf(dZ,dX),0);
            seg.zOffset = new float[]{(float)offsetOuter[0],(float)offsetOuter[1],(float)offsetOuter[2]};

            left = null;
            right = null;

            //define the front positions for each model
            for (float f : railOffsets){
                if (f==0f || !TrainsInMotion.proxy.isClient()){
                    continue;
                }
                //set the offsets for ballast and ties
                if (left ==null){
                    left = f;
                } else if (f<left){
                    left=f;
                }
                if (right ==null){
                    right = f;
                } else if (f>right){
                    right=f;
                }
                offsetInner = RailUtility.rotatePoint(new double[]{0,0,-0.0625+f}, 0,RailUtility.atan2degreesf(dZ,dX),0);
                offsetOuter = RailUtility.rotatePoint(new double[]{0,0,0.0625+f}, 0,RailUtility.atan2degreesf(dZ,dX),0);

                seg.models.add(seg.genNewSubModel(
                        new float[]{
                                (float)(offsetInner[0]+points.get(i)[0]), (float)(offsetInner[1]+points.get(i)[1]), (float)(offsetInner[2]+points.get(i)[2])
                        },
                        new float[]{
                                (float)(offsetOuter[0]+points.get(i)[0]), (float)(offsetOuter[1]+points.get(i)[1]), (float)(offsetOuter[2]+points.get(i)[2])
                        },tile, (byte)0
                ));
            }


            if (left!=null) {
                //this is used for texture tiling along the path
                if (startEndnd[1] > 0.029f) {
                    startEndnd[1] = 0;
                }
                startEndnd[0] = startEndnd[1];
                startEndnd[1] += (Math.abs(dX) + Math.abs(dZ)) * 0.0155f;
                if (startEndnd[1] > 0.03f) {
                    startEndnd[1] = 0.03f;
                }

                //this segments it horizontally for tiling.
                length = (int) (((Math.abs(left) + Math.abs(right)) * 1.6) + 0.5f);//cast as an int, lazy rounding down to whole number.
                inner = ((Math.abs(left) + Math.abs(right)) * 1.6f);
                if (length == 0) {
                    length = 1;
                    inner = 1F;
                }
            /*
            * -----------------------------------------------------
            * Ballast
            * -----------------------------------------------------
             */
                if (tile.ballast !=null) { //checking if left is null is technically faster than seeing if the array of rails has more than 0 entries


                    //this actually gens the parts
                    for (int ii = 0; ii < length; ii++) {
                        offsetInner = RailUtility.rotatePoint(new double[]{0, 0, (-inner * 0.5f) + ((inner / length) * (ii))}, 0, RailUtility.atan2degreesf(dZ, dX), 0);
                        offsetOuter = RailUtility.rotatePoint(new double[]{0, 0, (-inner * 0.5f) + ((inner / length) * (ii + 1))}, 0, RailUtility.atan2degreesf(dZ, dX), 0);

                        ModelRailSegment.subModel model = seg.genNewSubModel(
                                new float[]{
                                        (float) (offsetInner[0] + points.get(i)[0]), (float) (offsetInner[1] + points.get(i)[1])-0.025f, (float) (offsetInner[2] + points.get(i)[2])
                                },
                                new float[]{
                                        (float) (offsetOuter[0] + points.get(i)[0]), (float) (offsetOuter[1] + points.get(i)[1])-0.025f, (float) (offsetOuter[2] + points.get(i)[2])
                                }, tile, (byte) 1
                        );
                        model.offset[0] = startEndnd[0];
                        model.offset[1] = startEndnd[1];
                        seg.models.add(model);
                    }
                }
            }
            segments.add(seg);
        }

        if (tile.getWorldObj().isRemote && left!=null && segments.size() > 0 && tile.ties != null) {
        /*
        * -----------------------------------------------------
        * Ties
        * -----------------------------------------------------
         */
            for (i = 1; i < tiePoints.size() - 2; i++) {
                dX = tiePoints.get(i - 1)[0] - (tiePoints.get(i + 1)[0]);
                dZ = tiePoints.get(i - 1)[2] - (tiePoints.get(i + 1)[2]);

                //this segments it horizontally for tiling.
                length = (int) (((Math.abs(left) + Math.abs(right)) * 1.6) + 0.5f);//cast as an int, lazy rounding down to whole number.
                inner = ((Math.abs(left) + Math.abs(right)) * 1.6f);
                if (length == 0) {
                    length = 1;
                    inner = 1F;
                }
                float zBufferFix;
                Random r = new Random();
                //this actually gens the parts
                for (int ii = 0; ii < length; ii++) {
                    offsetInner = RailUtility.rotatePoint(new double[]{-0.06, 0, (-inner * 0.49f) + ((inner / length) * (ii))}, 0, RailUtility.atan2degreesf(dZ, dX), 0);
                    offsetOuter = RailUtility.rotatePoint(new double[]{-0.06, 0, (-inner * 0.51f) + ((inner / length) * (ii + 1))}, 0, RailUtility.atan2degreesf(dZ, dX), 0);
                    zBufferFix = (float) r.nextInt(10000) * 0.0000001f;
                    ModelRailSegment.subModel model = segments.get(0).genNewSubModel(
                            new float[]{
                                    (float) (offsetInner[0] + tiePoints.get(i)[0]), (float) (offsetInner[1] + tiePoints.get(i)[1]) + zBufferFix, (float) (offsetInner[2] + tiePoints.get(i)[2])
                            },
                            new float[]{
                                    (float) (offsetOuter[0] + tiePoints.get(i)[0]), (float) (offsetOuter[1] + tiePoints.get(i)[1]) + zBufferFix, (float) (offsetOuter[2] + tiePoints.get(i)[2])
                            }, tile, (byte) 2
                    );

                    offsetInner = RailUtility.rotatePoint(new double[]{-0.18, 0, (-inner * 0.49f) + ((inner / length) * (ii))}, 0, RailUtility.atan2degreesf(dZ, dX), 0);
                    offsetOuter = RailUtility.rotatePoint(new double[]{-0.18, 0, (-inner * 0.51f) + ((inner / length) * (ii + 1))}, 0, RailUtility.atan2degreesf(dZ, dX), 0);

                    model.lastPositionInner = new float[]{
                            (float) (offsetInner[0] + tiePoints.get(i)[0]), (float) (offsetInner[1] + tiePoints.get(i)[1]) + zBufferFix, (float) (offsetInner[2] + tiePoints.get(i)[2])
                    };
                    model.lastPositionOuter = new float[]{
                            (float) (offsetOuter[0] + tiePoints.get(i)[0]), (float) (offsetOuter[1] + tiePoints.get(i)[1]) + zBufferFix, (float) (offsetOuter[2] + tiePoints.get(i)[2])
                    };


                    model.offset[0] = startEndnd[0];
                    model.offset[1] = startEndnd[1];
                    if (ii == 0) {
                        model.offset[2] = ii == length - 1 ? 3 : 1;
                    } else {
                        model.offset[2] = ii == length - 1 ? 2 : 0;
                    }
                    segments.get(0).models.add(model);
                }
            }
        }

        //connect the back positions of each model to the previous model's front positions
        if (segments.size() > 0 && TrainsInMotion.proxy.isClient()) {
            int ii = 0;
            for (i = 0; i < segments.size() - 1; i++) {
                for (ii = 0; ii < segments.get(i).models.size(); ii++) {
                    if (segments.get(i).models.get(ii).type == 2) {
                        continue;//skip ties
                    }
                    segments.get(i).models.get(ii).lastPositionInner = segments.get(i + 1).models.get(ii).positionInner;
                    segments.get(i).models.get(ii).lastPositionOuter = segments.get(i + 1).models.get(ii).positionOuter;
                }
            }

            for (ii = 0; ii < segments.get(i).models.size(); ii++) {
                if (segments.get(i).models.get(ii).type == 2) {
                    continue;//skip ties
                }
                segments.get(segments.size() - 1).models.get(ii).lastPositionInner = segments.get(segments.size() - 1).models.get(ii).positionInner = null;
                segments.get(segments.size() - 1).models.get(ii).lastPositionOuter = segments.get(segments.size() - 1).models.get(ii).lastPositionOuter = null;
            }
        }
        return segments;
    }
}
