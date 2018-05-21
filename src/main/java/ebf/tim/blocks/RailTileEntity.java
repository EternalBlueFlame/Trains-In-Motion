package ebf.tim.blocks;

import ebf.tim.blocks.rails.RailVanillaShapes;
import ebf.tim.models.rails.ModelRailSegment;
import tmt.Tessellator;
import net.minecraft.block.Block;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.List;


public class RailTileEntity extends TileEntity {

    private AxisAlignedBB boundingBox = null;
    private List<?extends ModelRailSegment> path[] = null;

    public float getRailSpeed(){
        float speed =0.4f;
        //if (metal == 0){speed+=0.2f;}
        //if (ties == 1){speed+=0.2f;}
        return speed;
    }

    //todo rework this list so its not an array of lists, we only ever get a single list anyway!
    @SafeVarargs
    public final void setRenderShape(List<?extends ModelRailSegment> ... newShape){

            path = newShape;

    }

    private static final double[] color = {
            (Blocks.iron_block.getMapColor(0).colorValue >> 16 & 0xFF)* 0.00392156863,
            (Blocks.iron_block.getMapColor(0).colorValue >> 8 & 0xFF)* 0.00392156863,
            (Blocks.iron_block.getMapColor(0).colorValue & 0xFF)* 0.00392156863
    };

    public void func_145828_a(@Nullable CrashReportCategory report)  {
        if (report == null) {
            if (!worldObj.isRemote || path == null) {
                return;
            }
                //Tessellator.bindTexture(texture);
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            //GL11.glDisable(GL11.GL_LIGHTING);

            //RenderHelper.enableGUIStandardItemLighting();
            //int color = Minecraft.getMinecraft().renderEngine.getTexture().getGlTextureId();
            Tessellator tessellator = Tessellator.getInstance();
            //GL11.glColor3d(color[0],color[1],color[2]);
            int i=0;
            int last=0;
            for(List<? extends ModelRailSegment> railSegments : path) {
                i=0;
                last= railSegments.size()-2;
                for (ModelRailSegment point : railSegments) {

                    for(ModelRailSegment.subModel model : point.models) {
                        model.render(tessellator, 0, i == 0, i == last);
                    }
                    i++;
                }
            }
            //GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glPopMatrix();

        } else {super.func_145828_a(report);}
    }

    @Override
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
        return oldBlock != newBlock;
    }

    @Override
    public boolean canUpdate(){return true;}

    //todo have block set the nearbymeta when a neighboring block changes
    public int[] nearbymeta;
    public boolean needsUpdate = true;

    private static final float[] gauge750mm={0f, 0.3125f, -0.3125f};

    @Override
    public void updateEntity() {

        if (path == null || needsUpdate){
            //needsUpdate = false;

            switch (worldObj.getBlockMetadata(xCoord, yCoord, zCoord)){
                //Z straight
                case 0: {
                    setRenderShape(
                            RailVanillaShapes.vanillaZStraight(worldObj, xCoord, yCoord, zCoord, gauge750mm));
                    return;
                }
                //X straight
                case 1: {
                    setRenderShape(
                            RailVanillaShapes.vanillaXStraight(worldObj, xCoord, yCoord, zCoord, gauge750mm));
                    return;
                }

                //curves
                case 9: {
                    setRenderShape(
                            RailVanillaShapes.vanillaCurve9(worldObj, xCoord, yCoord, zCoord, gauge750mm));
                    return;
                }
                case 8: {
                    setRenderShape(
                            RailVanillaShapes.vanillaCurve8(worldObj, xCoord, yCoord, zCoord, gauge750mm));
                    return;
                }
                case 7: {
                    setRenderShape(
                            RailVanillaShapes.vanillaCurve7(worldObj, xCoord, yCoord, zCoord, gauge750mm));
                    return;
                }
                case 6: {
                    setRenderShape(RailVanillaShapes.vanillaCurve6(worldObj, xCoord, yCoord, zCoord, gauge750mm));
                    return;
                }
                //Z slopes
                case 5 :{
                    setRenderShape(
                            RailVanillaShapes.vanillaSlopeZ5(worldObj, xCoord, yCoord, zCoord, gauge750mm));
                    return;
                }
                case 4 :{
                    setRenderShape(
                            RailVanillaShapes.vanillaSlopeZ4(worldObj, xCoord, yCoord, zCoord, gauge750mm));
                    return;
                }
                //X slopes
                case 2 :{
                    setRenderShape(
                            RailVanillaShapes.vanillaSlopeX2(worldObj, xCoord, yCoord, zCoord, gauge750mm));
                    return;
                }
                case 3 :{
                    setRenderShape(
                            RailVanillaShapes.vanillaSlopeX3(worldObj, xCoord, yCoord, zCoord, gauge750mm));
                    return;
                }
            }

        }



    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        if (boundingBox == null) {
            boundingBox = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
        }
        return boundingBox;
    }


    @Override
    public void writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
    }

}
