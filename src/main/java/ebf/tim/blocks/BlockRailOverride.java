package ebf.tim.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import ebf.tim.models.rails.*;
import ebf.tim.models.tmt.ModelBase;
import ebf.tim.models.tmt.Tessellator;
import ebf.tim.models.tmt.Vec3d;
import ebf.tim.registry.URIRegistry;
import ebf.tim.utility.DebugUtil;
import ebf.tim.utility.RailUtility;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRail;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
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

    private static final ModelRailStraight railStraightModel = new ModelRailStraight();
    private static final ModelRailCurveVerySmall railCurveModel = new ModelRailCurveVerySmall();
    private static final ModelRailSlope railSlopeModel = new ModelRailSlope();
    private static final ModelRailSlopeDown railSlopeModelDown = new ModelRailSlopeDown();
    private static final ModelRailSlopeUp railSlopeModelUp = new ModelRailSlopeUp();
    private static final ResourceLocation railStraightTexture = URIRegistry.MODEL_RAIL_TEXTURE.getResource("RailStraight.png");
    private static final ResourceLocation railCurveTexture = URIRegistry.MODEL_RAIL_TEXTURE.getResource("RailCurveVerySmall.png");


    private static final ModelRailPixel rail = new ModelRailPixel();

    enum direction{NORTH,SOUTH, EAST, WEST};

    public class renderTileEntity extends TileEntity{

        private int metal =0;
        private int ties =0;
        private int ticksExisted =0;
        private direction rotation = direction.NORTH;
        private ModelBase model=railStraightModel;
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
                GL11.glPushMatrix();
                GL11.glScaled(1,0.5,1);
                Tessellator.bindTexture(texture);
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
                if(!DebugUtil.dev()) {
                    model.render();
                } else {
                    //model = new ModelRailStraight();
                    ///*
                    //GL11.glRotatef(180, 1, 0, -1);


                    rail.render();

                    double[] angle = RailUtility.rotatePoint(new double[]{0.9, 0, 0}, 0, 45, 0);
                    GL11.glRotatef(0.016f, 0, 45, 0);
                    GL11.glTranslated(angle[0], angle[1], angle[2]);

                    rail.render();//*/
                }
                GL11.glPopMatrix();
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
        public void updateEntity(){
            if (boundingBox == null){
                boundingBox = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
            }
            if (ticksExisted %40 ==0){
                ticksExisted =0;
            } else {
                ticksExisted++;
                return;
            }

            int modelID = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
            setRenderType(modelID);
            //choose what to render based on own path
            switch (modelID) {
                //straight rails
                case 1:case 0: {
                    Block b1 = worldObj.getBlock(xCoord+1,yCoord-1,zCoord);
                    Block b2 = worldObj.getBlock(xCoord-1,yCoord-1,zCoord);
                    Block b3 = worldObj.getBlock(xCoord+1,yCoord,zCoord);
                    Block b4 = worldObj.getBlock(xCoord-1,yCoord,zCoord);
                    int b3m = worldObj.getBlockMetadata(xCoord+1,yCoord,zCoord);
                    int b4m = worldObj.getBlockMetadata(xCoord-1,yCoord,zCoord);
                    Block b1z = worldObj.getBlock(xCoord,yCoord-1,zCoord+1);
                    Block b2z = worldObj.getBlock(xCoord,yCoord-1,zCoord-1);
                    Block b3z = worldObj.getBlock(xCoord,yCoord,zCoord+1);
                    Block b4z = worldObj.getBlock(xCoord,yCoord,zCoord-1);
                    int b3mz = worldObj.getBlockMetadata(xCoord,yCoord,zCoord+1);
                    int b4mz = worldObj.getBlockMetadata(xCoord,yCoord,zCoord-1);


                    if(b1 instanceof BlockRailOverride && b2 instanceof BlockRailOverride){
                        //slope down to both sides
                    } else if (b3 instanceof BlockRailOverride && b4 instanceof BlockRailOverride && b3m ==2 && b4m == 3){
                        // slope up to both sides
                    } else if (b1 instanceof BlockRailOverride) {
                        rotation = modelID==1?direction.WEST:direction.NORTH;
                        model = railSlopeModelDown;
                        // slope down to x+1
                    } else if (b2 instanceof BlockRailOverride) {
                        rotation = modelID==1?direction.EAST:direction.SOUTH;
                        model = railSlopeModelDown;
                        //slope down to x-1
                    } else if (b3 instanceof BlockRailOverride && b3m ==2){
                        rotation = modelID==1?direction.WEST:direction.NORTH;
                        model = railSlopeModelUp;
                        //slope up to x-1
                    } else if (b4 instanceof BlockRailOverride && b4m ==3){
                        rotation = modelID==1?direction.EAST:direction.SOUTH;
                        model = railSlopeModelUp;
                        //slope up to x+1
                    } else if (b1z instanceof BlockRailOverride) {
                        rotation = modelID==1?direction.EAST:direction.SOUTH;
                        model = railSlopeModelDown;
                        // slope down to x+1
                    } else if (b2z instanceof BlockRailOverride) {
                        rotation = modelID==1?direction.WEST:direction.NORTH;
                        model = railSlopeModelDown;
                        //slope down to x-1
                    } else if (b3z instanceof BlockRailOverride && b3mz ==5){
                        rotation = modelID==1?direction.EAST:direction.SOUTH;
                        model = railSlopeModelUp;
                        //slope up to x-1
                    } else if (b4z instanceof BlockRailOverride && b4mz ==4){
                        rotation = modelID==1?direction.WEST:direction.NORTH;
                        model = railSlopeModelUp;
                        //slope up to x+1
                    } else {
                        rotation = modelID==1?direction.WEST:direction.NORTH;
                        model = railStraightModel;
                        // normal flat rail
                    }
                    texture = railStraightTexture;
                break;
                }
                //slopes
                case 2: case 3:case 4:case 5:{
                    switch (modelID){
                        case 2:{rotation=direction.EAST; break;}
                        case 3:{rotation=direction.WEST; break;}
                        case 4:{rotation=direction.SOUTH; break;}
                        case 5:{rotation=direction.NORTH; break;}
                    }
                    model = railSlopeModel;
                    texture = railStraightTexture;
                    break;
                }

                case 9:case 8:case 7:case 6:{
                    switch (modelID){
                        case 9:{rotation=direction.EAST; break;}
                        case 8:{rotation=direction.SOUTH; break;}
                        case 7:{rotation=direction.WEST; break;}
                    }
                    model = railCurveModel;
                    texture = railCurveTexture;
                    break;
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

    private class railPoint{
        public Vec3d position;

        private static final int textureX = 64;
        private static final int textureY = 32;


    }
}
