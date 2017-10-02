package ebf.tim.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import ebf.tim.models.rails.*;
import ebf.tim.models.tmt.ModelBase;
import ebf.tim.models.tmt.Tessellator;
import ebf.tim.models.tmt.Vec3d;
import ebf.tim.registry.URIRegistry;
import ebf.tim.utility.DebugUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRail;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Eternal Blue Flame
 */
public class BlockRailOverride extends BlockRail implements ITileEntityProvider {

    @Override
    public int tickRate(World world){return 20;}

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return RenderingRegistry.getNextAvailableRenderId();
    }

    @Override
    public boolean isFlexibleRail(IBlockAccess world, int y, int x, int z){return true;}

    @Override
    public float getRailMaxSpeed(World world, EntityMinecart cart, int y, int x, int z){
        if (world.getTileEntity(x,y,z) instanceof renderTileEntity){
            return ((renderTileEntity) world.getTileEntity(x,y,z)).getRailSpeed();
        }
        return 0.4f;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta){
        return new renderTileEntity();
    }

    /**
     * generated a 3 point bezier curve using De Casteljau’s algorithm on each axis.
     * @param t number of blocks in length
     * @param P1 first point
     * @param P2 second point (mostly modifies first)
     * @param P3 third point (mostly modifies last)
     * @param P4 end point
     * @return the list of rail points to define positional data.
     */
    public static List<RailPointRenderData> quadCurveAtPoint(float t, Vec3d P1, Vec3d P2, Vec3d P3, Vec3d P4){
        if (t ==0){
            throw new ReportedException(CrashReport.makeCrashReport(new Throwable(), "Why did you make a rail with a length of 0 blocks???"));
        } else {
            t *=(1F/16F)/t;
        }
        final float originalT =t;
        t=0;
        List<RailPointRenderData> points = new ArrayList<RailPointRenderData>();
        while(t<=1.0f) {
            RailPointRenderData tempPoint = new RailPointRenderData();
            //define position
            tempPoint.position = new double[]{
                    (Math.pow(1 - t, 3) * P1.xCoord) + (3*Math.pow(1-t,2)*t*P2.xCoord) + (3*(1-t)*Math.pow(t,2)*P3.xCoord) + (Math.pow(t,3)*P4.xCoord),//X
                    (Math.pow(1 - t, 3) * P1.yCoord) + (3*Math.pow(1-t,2)*t*P2.yCoord) + (3*(1-t)*Math.pow(t,2)*P3.yCoord) + (Math.pow(t,3)*P4.yCoord),//Y
                    (Math.pow(1 - t, 3) * P1.zCoord) + (3*Math.pow(1-t,2)*t*P2.zCoord) + (3*(1-t)*Math.pow(t,2)*P3.zCoord) + (Math.pow(t,3)*P4.zCoord),//X
                    0,0,0
            };
            //define yaw rotation
            if(t==originalT){
                points.get(0).position[4] = 180-Math.toDegrees(Math.atan2(tempPoint.position[2] - points.get(0).position[2], tempPoint.position[0] - points.get(0).position[0]));
            }
            if (t !=0){
                tempPoint.position[4] = 180-Math.toDegrees(Math.atan2(tempPoint.position[2] - points.get(points.size()-1).position[2], tempPoint.position[0] - points.get(points.size()-1).position[0]));
            }
            //TODO: define pitch and roll
            points.add(tempPoint);
            t += originalT;
        }
        return points;
    }



    private static final ModelRailStraight railStraightModel = new ModelRailStraight();
    private static final ModelRailCurveVerySmall railCurveModel = new ModelRailCurveVerySmall();
    private static final ModelRailSlope railSlopeModel = new ModelRailSlope();
    private static final ModelRailSlopeDown railSlopeModelDown = new ModelRailSlopeDown();
    private static final ModelRailSlopeUp railSlopeModelUp = new ModelRailSlopeUp();
    private static final ResourceLocation railStraightTexture = URIRegistry.MODEL_RAIL_TEXTURE.getResource("RailStraight.png");
    private static final ResourceLocation railCurveTexture = URIRegistry.MODEL_RAIL_TEXTURE.getResource("RailCurveVerySmall.png");

    //private static final List<RailPointRenderData> RailStraight = null;//curveAtPoint(1, new Vec3d(-0.5,0,0),new Vec3d(-0.5,0,-0.5),new Vec3d(-0.5,0,-1));
    //the following letters refer to the from and to directions, N/S/E/W are kinda obvious, so NW would be from north to west (or the other way around).
    private static final List<RailPointRenderData> RailCurveVanillaNW = quadCurveAtPoint(1, new Vec3d(0,0,0.5),new Vec3d(0.3,0,0.5),new Vec3d(0.5,0,0.3), new Vec3d(0.5,0,0));
    private static final List<RailPointRenderData> RailCurveVanillaNE = quadCurveAtPoint(1, new Vec3d(1,0,0.5),new Vec3d(0.7,0,0.5),new Vec3d(0.5,0,0.3), new Vec3d(0.5,0,0));
    private static final List<RailPointRenderData> RailCurveVanillaSW = quadCurveAtPoint(1, new Vec3d(0,0,0.5),new Vec3d(0.3,0,0.5),new Vec3d(0.5,0,0.3), new Vec3d(0.5,0,0));
    //straights
    private static final List<RailPointRenderData> RailStraightVanillaNS = quadCurveAtPoint(1, new Vec3d(1,0,0.5),new Vec3d(0.75,0,0.5),new Vec3d(0.25,0,0.5), new Vec3d(0,0,0.5));
    private static final List<RailPointRenderData> RailStraightVanillaEW = quadCurveAtPoint(1, new Vec3d(0.5,0,1),new Vec3d(0.5,0,0.75),new Vec3d(0.5,0,0.25), new Vec3d(0.5,0,0));

    private static final ModelRailPixel rail = new ModelRailPixel();

    enum direction{NORTH,SOUTH, EAST, WEST};

    public class renderTileEntity extends TileEntity{

        private int metal =0;
        private int ties =0;
        private int ticksExisted =0;
        private direction rotation = direction.NORTH;
        private ModelBase model=railStraightModel;
        private List<RailPointRenderData> railPoints = RailStraightVanillaNS;
        private ResourceLocation texture = railStraightTexture;
        private AxisAlignedBB boundingBox = null;

        public float getRailSpeed(){
            float speed =0.4f;
            if (metal == 0){speed+=0.2f;}
            if (ties == 1){speed+=0.2f;}
            return speed;
        }

        public String getRailTexture(){
            String name;
            switch (metal){
                case 1:{name = "iron"; break;}
                default:{name = "steel"; break;}//0 and fallback
            }
            switch (ties){
                case 1:{name += "concrete"; break;}
                default:{name += "wood"; break;}//0 and fallback
            }
            return name;
        }

        public void setType(int metalType, int tiesType){
            this.metal = metalType;
            this.ties = tiesType;
        }

        @Override
        public void func_145828_a(@Nullable CrashReportCategory p_145828_1_)  {
            if (p_145828_1_ == null && worldObj.isRemote) {
                //GL11.glPushMatrix();
                //GL11.glScaled(1,0.5,1);
                Tessellator.bindTexture(texture,railCurveModel);
                if(!DebugUtil.dev()) {
                    switch (rotation) {
                        case NORTH: {
                            GL11.glRotatef(180, 1, 0, -1);
                            break;
                        }
                        case SOUTH: {
                            GL11.glRotatef(180, 1, 0, 1);
                            break;
                        }
                        case EAST: {
                            GL11.glRotatef(180, 0, 0, 1);
                            break;
                        }
                        case WEST: {
                            GL11.glRotatef(180, 1, 0, 0);
                            break;
                        }
                    }
                    model.render();
                } else {

                    //System.out.println("rendering rail ");
                    for (RailPointRenderData point : railPoints) {
                        GL11.glPushMatrix();
                        //System.out.println(point.position[0] + ":" + point.position[1] + ":" + point.position[2]);
                        GL11.glTranslated(point.position[0], point.position[1]-0.15, point.position[2]);
                        GL11.glScaled(1,0.5,1);
                        GL11.glRotated(point.position[4], 0, 1, 0);

                        rail.render();
                        GL11.glPopMatrix();
                    }

                }
                //GL11.glPopMatrix();
            } else if (p_145828_1_ != null){
                super.func_145828_a(p_145828_1_);
            }
        }

        @Override
        public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
            return oldBlock != newBlock;
        }

        @Override
        public boolean canUpdate(){return true;}

        @Override
        public void updateEntity() {
            if (boundingBox == null) {
                boundingBox = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
            }
            if (ticksExisted % 80 == 0) {
                ticksExisted = 0;
            } else {
                ticksExisted++;
                return;
            }

            if (DebugUtil.dev()) {
                switch (worldObj.getBlockMetadata(xCoord, yCoord, zCoord)) {
                    case 0: {
                        railPoints = RailStraightVanillaEW;
                        break;
                    }
                    case 1: {
                        railPoints = RailStraightVanillaNS;
                        break;
                    }
                    //case 9:case 8:case 7:case 6:
                    case 9: {
                        railPoints = RailCurveVanillaNE;
                        break;
                    }
                    case 8: {
                        railPoints = RailCurveVanillaNW;
                        break;
                    }
                    case 7: {
                        railPoints = quadCurveAtPoint(1, new Vec3d(0,0,0.5),new Vec3d(0.3,0,0.5),new Vec3d(0.5,0,0.7), new Vec3d(0.5,0,1));
                        break;
                    }
                    case 6: {
                        railPoints = quadCurveAtPoint(1, new Vec3d(1,0,0.5),new Vec3d(0.7,0,0.5),new Vec3d(0.5,0,0.7), new Vec3d(0.5,0,1));
                        break;
                    }
                }


            } else {
                int modelID = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
                //choose what to render based on own path
                switch (worldObj.getBlockMetadata(xCoord, yCoord, zCoord)) {
                    case 0: {
                        if (!(worldObj.getBlock(xCoord, yCoord, zCoord) instanceof BlockRailOverride)) {
                            worldObj.removeTileEntity(xCoord, yCoord, zCoord);
                            break;
                        }
                    }
                    //straight rails
                    case 1: {
                        Block b1 = worldObj.getBlock(xCoord + 1, yCoord - 1, zCoord);
                        Block b2 = worldObj.getBlock(xCoord - 1, yCoord - 1, zCoord);
                        Block b3 = worldObj.getBlock(xCoord + 1, yCoord, zCoord);
                        Block b4 = worldObj.getBlock(xCoord - 1, yCoord, zCoord);
                        int b3m = worldObj.getBlockMetadata(xCoord + 1, yCoord, zCoord);
                        int b4m = worldObj.getBlockMetadata(xCoord - 1, yCoord, zCoord);
                        Block b1z = worldObj.getBlock(xCoord, yCoord - 1, zCoord + 1);
                        Block b2z = worldObj.getBlock(xCoord, yCoord - 1, zCoord - 1);
                        Block b3z = worldObj.getBlock(xCoord, yCoord, zCoord + 1);
                        Block b4z = worldObj.getBlock(xCoord, yCoord, zCoord - 1);
                        int b3mz = worldObj.getBlockMetadata(xCoord, yCoord, zCoord + 1);
                        int b4mz = worldObj.getBlockMetadata(xCoord, yCoord, zCoord - 1);


                        if (b1 instanceof BlockRailOverride && b2 instanceof BlockRailOverride) {
                            //slope down to both sides
                        } else if (b3 instanceof BlockRailOverride && b4 instanceof BlockRailOverride && b3m == 2 && b4m == 3) {
                            // slope up to both sides
                        } else if (b1 instanceof BlockRailOverride) {
                            rotation = modelID == 1 ? direction.WEST : direction.NORTH;
                            model = railSlopeModelDown;
                            // slope down to x+1
                        } else if (b2 instanceof BlockRailOverride) {
                            rotation = modelID == 1 ? direction.EAST : direction.SOUTH;
                            model = railSlopeModelDown;
                            //slope down to x-1
                        } else if (b3 instanceof BlockRailOverride && b3m == 2) {
                            rotation = modelID == 1 ? direction.WEST : direction.NORTH;
                            model = railSlopeModelUp;
                            //slope up to x-1
                        } else if (b4 instanceof BlockRailOverride && b4m == 3) {
                            rotation = modelID == 1 ? direction.EAST : direction.SOUTH;
                            model = railSlopeModelUp;
                            //slope up to x+1
                        } else if (b1z instanceof BlockRailOverride) {
                            rotation = modelID == 1 ? direction.EAST : direction.SOUTH;
                            model = railSlopeModelDown;
                            // slope down to x+1
                        } else if (b2z instanceof BlockRailOverride) {
                            rotation = modelID == 1 ? direction.WEST : direction.NORTH;
                            model = railSlopeModelDown;
                            //slope down to x-1
                        } else if (b3z instanceof BlockRailOverride && b3mz == 5) {
                            rotation = modelID == 1 ? direction.EAST : direction.SOUTH;
                            model = railSlopeModelUp;
                            //slope up to x-1
                        } else if (b4z instanceof BlockRailOverride && b4mz == 4) {
                            rotation = modelID == 1 ? direction.WEST : direction.NORTH;
                            model = railSlopeModelUp;
                            //slope up to x+1
                        } else {
                            rotation = modelID == 1 ? direction.WEST : direction.NORTH;
                            model = railStraightModel;
                            // normal flat rail
                        }
                        texture = railStraightTexture;
                        break;
                    }
                    //slopes
                    case 2:
                    case 3:
                    case 4:
                    case 5: {
                        switch (modelID) {
                            case 2: {
                                rotation = direction.EAST;
                                break;
                            }
                            case 3: {
                                rotation = direction.WEST;
                                break;
                            }
                            case 4: {
                                rotation = direction.SOUTH;
                                break;
                            }
                            case 5: {
                                rotation = direction.NORTH;
                                break;
                            }
                        }
                        model = railSlopeModel;
                        texture = railStraightTexture;
                        break;
                    }

                    case 9:
                    case 8:
                    case 7:
                    case 6: {
                        switch (modelID) {
                            case 9: {
                                rotation = direction.EAST;
                                break;
                            }
                            case 8: {
                                rotation = direction.SOUTH;
                                break;
                            }
                            case 7: {
                                rotation = direction.WEST;
                                break;
                            }
                        }
                        model = railCurveModel;
                        texture = railCurveTexture;
                        break;
                    }
                }
            }
        }



        @Override
        public AxisAlignedBB getRenderBoundingBox(){return boundingBox!= null?boundingBox:
                AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);}


        @Override
        public void writeToNBT(NBTTagCompound tag){
            super.writeToNBT(tag);
            tag.setInteger("metal", metal);
            tag.setInteger("ties", ties);
        }

        @Override
        public void readFromNBT(NBTTagCompound tag){
            super.readFromNBT(tag);
            metal = tag.getInteger("metal");
            ties = tag.getInteger("ties");
        }

    }
    private static final List<Vec3d> railStraightPoints = Arrays.asList(new Vec3d(0,0,0), new Vec3d(1,0,0), new Vec3d(2,0,0), new Vec3d(3,0,0),
            new Vec3d(4,0,0), new Vec3d(5,0,0), new Vec3d(6,0,0), new Vec3d(7,0,0), new Vec3d(8,0,0), new Vec3d(9,0,0),
            new Vec3d(10,0,0), new Vec3d(11,0,0),new Vec3d(12,0,0), new Vec3d(13,0,0), new Vec3d(14,0,0),
            new Vec3d(15,0,0), new Vec3d(16,0,0)
    );

}
