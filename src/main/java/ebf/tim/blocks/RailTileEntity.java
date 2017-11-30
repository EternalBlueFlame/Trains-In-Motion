package ebf.tim.blocks;

import ebf.tim.models.rails.ModelRailSegment;
import ebf.tim.models.rails.RailPointRenderData;
import tmt.Tessellator;
import ebf.tim.registry.URIRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RailTileEntity extends TileEntity {

    private int metal =0;
    private int ties =0;
    private List<RailPointRenderData> path = null;
    private ResourceLocation texture = URIRegistry.MODEL_RAIL_TEXTURE.getResource("RailNew.png");
    private AxisAlignedBB boundingBox = null;
    private List<RailPointRenderData> renderShape[] = null;

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

    public void setPath(List<RailPointRenderData> newShape){
        path = newShape;
    }


    @SafeVarargs
    public final void setRenderShape(List<RailPointRenderData> ... newShape){
        renderShape = newShape;
    }

    private static ItemStack grass = new ItemStack(Blocks.stone, 1);

    protected RenderBlocks blocks = new RenderBlocks();

    private double[] lastPoints = null;

    private List<ModelRailSegment> segments = new ArrayList<>();

    public void func_145828_a(@Nullable CrashReportCategory report)  {
        if (report == null) {
            if (!worldObj.isRemote || renderShape == null) {
                return;
            }
                Tessellator.bindTexture(texture);
            for(List<RailPointRenderData> renderData : renderShape) {
                for (RailPointRenderData point : renderData) {
                    GL11.glPushMatrix();
                    GL11.glTranslated(point.position[0], point.position[1] - 0.05, point.position[2]);
                    GL11.glRotated(point.position[4], 0, 1, 0);
                    GL11.glRotated(180, 0, 0, 1);
                    GL11.glScaled(1, 0.5, 0.5);

                    point.segment.render(null, 0, 0, 0, 0, 0, 0.0625f);

                    //GL11.glScaled(1.25, 2.15, 1.25);
                    //blocks.renderBlockAsItem(Block.getBlockFromItem(grass.getItem()), grass.getItemDamage(), 1.0f);
                    GL11.glPopMatrix();
                }
            }

        } else {super.func_145828_a(report);}
    }

    @Override
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
        return oldBlock != newBlock;
    }

    @Override
    public boolean canUpdate(){return true;}

    @Override
    public void updateEntity() {
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
