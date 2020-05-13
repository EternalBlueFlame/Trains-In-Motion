package ebf.tim.blocks.rails;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.XmlBuilder;
import ebf.tim.blocks.RailTileEntity;
import ebf.tim.items.ItemRail;
import ebf.tim.registry.TiMItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eternal Blue Flame
 */
public class BlockRailCore extends BlockRail implements ITileEntityProvider {

    private static final int[] updateMatrix = {-2,-1,0,1,2};






    //750mm single gauge track, each entry in the array is another gauge the rails would support
    public final int[] gaugemm;
    //scales the model parts
    public final float renderScale;


    public BlockRailCore(){
        setCreativeTab(null);
        gaugemm=new int[]{750};
        renderScale=1;
    }

    public BlockRailCore(int gauge, float renderScale){
        setCreativeTab(null);
        gaugemm=new int[]{gauge};
        this.renderScale=renderScale;
    }

    public BlockRailCore(int[] gauge, float renderScale){
        setCreativeTab(null);
        gaugemm=gauge;
        this.renderScale=renderScale;
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
    public boolean getTickRandomly(){return false;}

    @Override
    public int getRenderType() {
        return Blocks.rail.getRenderType();
    }

    @Override
    public boolean isFlexibleRail(IBlockAccess world, int y, int x, int z){return true;}

    @Override
    public float getRailMaxSpeed(World world, EntityMinecart cart, int y, int x, int z){
        return 0.4f;//getTile(world, x, y, z)!=null?getTile(world, x, y, z).getRailSpeed():0.4f;
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_) {
        return super.colorMultiplier(p_149720_1_, p_149720_2_, p_149720_3_, p_149720_4_);
    }

    @Override
    public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_){
        return true;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        if (getBasicRailMetadata(world, null, x,y,z) >6) {
            return AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5f, 1.0F);
        } else {
            return AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        }
    }


    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
        if (getBasicRailMetadata(p_149719_1_, null, p_149719_2_, p_149719_3_, p_149719_4_) >6) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5f, 1.0F);
        } else {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        }
    }


    public boolean isBlockSolid(IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_) {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta){
        return new RailTileEntity();
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata){
        return createNewTileEntity(world,metadata);
    }



    @Override
    public int getBasicRailMetadata(IBlockAccess world, EntityMinecart cart, int x, int y, int z) {
        if(!(world.getTileEntity(x,y,z) instanceof RailTileEntity)){
            return 0;
        }
        int meta = ((RailTileEntity) world.getTileEntity(x,y,z)).getMeta();
        if(isPowered()) {
            meta = meta & 7;
        }

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
                if(getTileEntityMeta(world,x+1,y,z)==9 && cart.motionZ>0 && cart.getEntityData().getInteger("tim.lastusedrail.meta")==0){
                    return 0;//this already worked fine, but make it smoother
                } else if(getTileEntityMeta(world,x+1,y,z)==7){
                    if(cart.getEntityData().getInteger("tim.lastusedrail.meta")==0) {
                        return 0;//cover parallel entering from wrong end on straight
                    } else if(cart.getEntityData().getInteger("tim.lastusedrail.meta")==7){
                        return 9;//cover parallel off shape
                    }
                } else if(getTileEntityMeta(world,x,y,z+1)==8 && cart.motionX>0 && cart.getEntityData().getInteger("tim.lastusedrail.meta")==1){
                    return 1;//this already worked fine, but make it smoother
                } else if (getTileEntityMeta(world,x,y,z+1)==9) {
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
                if(getTileEntityMeta(world,x-1,y,z)==9 && cart.motionX>0 && cart.getEntityData().getInteger("tim.lastusedrail.meta")==0){
                    return 0;//this already worked fine, but make it smoother
                } else if(getTileEntityMeta(world,x-1,y,z)==8){
                    if(cart.getEntityData().getInteger("tim.lastusedrail.meta")==0) {
                        return 0;//cover parallel entering from wrong end on straight
                    } else if(cart.getEntityData().getInteger("tim.lastusedrail.meta")==9){
                        return 6;//cover parallel off shape
                    }
                } else if(getTileEntityMeta(world,x,y,z+1)==9 && cart.motionX>0 && cart.getEntityData().getInteger("tim.lastusedrail.meta")==1){
                    return 1;//this already worked fine, but make it smoother
                } else if (getTileEntityMeta(world,x,y,z+1)==8) {
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
                if(getTileEntityMeta(world,x-1,y,z)==6 && cart.motionZ<0 && cart.getEntityData().getInteger("tim.lastusedrail.meta")==0){
                    return 0;//this already worked fine, but make it smoother
                } else if(getTileEntityMeta(world,x-1,y,z)==9){
                    if(cart.getEntityData().getInteger("tim.lastusedrail.meta")==0) {
                        return 0;//cover parallel entering from wrong end on straight
                    } else if(cart.getEntityData().getInteger("tim.lastusedrail.meta")==9){
                        return 7;//cover parallel off shape
                    }
                } else if(getTileEntityMeta(world,x,y,z-1)==6 && cart.motionX<0 && cart.getEntityData().getInteger("tim.lastusedrail.meta")==1){
                    return 1;//this already worked fine, but make it smoother
                } else if (getTileEntityMeta(world,x,y,z-1)==7) {
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
                if(getTileEntityMeta(world,x+1,y,z)==6 && cart.motionZ>0 && cart.getEntityData().getInteger("tim.lastusedrail.meta")==0){
                    return 0;//this already worked fine, but make it smoother
                } else if(getTileEntityMeta(world,x+1,y,z)==7){
                    if(cart.getEntityData().getInteger("tim.lastusedrail.meta")==0) {
                        return 0;//cover parallel entering from wrong end on straight
                    } else if(cart.getEntityData().getInteger("tim.lastusedrail.meta")==8){
                        return 6;//cover parallel off shape
                    }
                } else if(getTileEntityMeta(world,x,y,z-1)==7 && cart.motionX>0 && cart.getEntityData().getInteger("tim.lastusedrail.meta")==1){
                    return 1;//this already worked fine, but make it smoother
                } else if (getTileEntityMeta(world,x,y,z-1)==6) {
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
                changed = false;
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

    public int getTileEntityMeta(IBlockAccess w, int x, int y, int z){
        if(w.getTileEntity(x,y,z) instanceof RailTileEntity){
            return ((RailTileEntity) w.getTileEntity(x,y,z)).getMeta();
        }
        return -1;
    }

    @Override
    public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_) {
        if (!p_149726_1_.isRemote) {
            (new BlockRailCore.RailData(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_))
                    .func_150655_a(p_149726_1_.isBlockIndirectlyGettingPowered(p_149726_4_, p_149726_3_, p_149726_4_), true);

            if (this.field_150053_a) {
                this.onNeighborBlockChange(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_, this);
            }
        }
    }


    @Override
    public Material getMaterial(){
        return Blocks.iron_block.getMaterial();
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
                    new ItemStack(TiMItems.railItem, 1), xml.getItemStack("rail"),
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
                    new ItemStack(TiMItems.railItem, 1), xml.getItemStack("rail"),
                    xml.getItemStack("ballast"), xml.getItemStack("ties"), xml.getItemStack("wires")));
            return out;
        } else {
            return null;
        }
    }



    @Override
    public void onNeighborBlockChange(World worldObj, int x, int y, int z, Block b) {
        if(b instanceof BlockRailBase) {
            updateShape(x, y, z, worldObj, null);
            if (worldObj.getTileEntity(x, y, z) instanceof RailTileEntity) {
                worldObj.getTileEntity(x, y, z).markDirty();
            }
        }
    }


    @Override
    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
        p_149749_1_.removeTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
    }

    public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_) {
        updateNearbyShapes(p_149660_1_, p_149660_2_, p_149660_3_, p_149660_4_);
        return p_149660_9_;
    }

    public void updateNearbyShapes(World world, int xCoord, int yCoord, int zCoord){
        for(int x : updateMatrix){
            for(int z : updateMatrix){
                for(int y : updateMatrix){
                    if(world.getBlock(x+xCoord,y+yCoord,z+zCoord) instanceof  BlockRailCore){
                        world.getBlock(x+xCoord,y+yCoord,z+zCoord).onNeighborBlockChange(world,xCoord,yCoord,zCoord, this);
                        updateShape(x,y,z,world, null);
                    }
                }
            }
        }
    }

    //override this to provide a different gauge
    public void updateShape(int xPos, int yPos, int zPos, World worldObj, @Nullable XmlBuilder data){
        RailShapeCore.processPoints(xPos, yPos, zPos, getShape(worldObj, xPos, yPos, zPos),gaugemm, renderScale, worldObj, data);
    }



    public static RailSimpleShape getShape(World worldObj, int xPos, int yPos, int zPos){
        TileEntity te= worldObj.getTileEntity(xPos, yPos, zPos);
        if(!(te instanceof RailTileEntity)){
            te = new RailTileEntity();
            te.xCoord=xPos;
            te.yCoord=yPos;
            te.zCoord=zPos;
        }
        switch (((RailTileEntity)te).getMeta()){
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
     * @return boolean for if the shape can be made
     */
    public static boolean checkBlockMeta(World worldobj, int x, int y, int z, int ... meta){
        if (!worldobj.getChunkProvider().chunkExists(x/16, z/16) ||
                !(worldobj.getTileEntity(x,y,z) instanceof RailTileEntity)){
            return false;
        }
        for(int i : meta){
            if(((RailTileEntity)worldobj.getTileEntity(x,y,z)).getMeta() ==i){
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



    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return Blocks.rail.getIcon(0, 0);
    }


    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int p_149646_5_) {
        return false;
    }

    @Override
    public int getLightOpacity(IBlockAccess world, int x, int y, int z) {
        return 0;
    }


    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 1;
    }

    /*@Override
    @SideOnly(Side.CLIENT)
    protected String getTextureName() {
        Blocks.flowing_water.getTextureName();
    }*/






    public class RailData {
        private World field_150660_b;
        private int field_150661_c;
        private int field_150658_d;
        private int field_150659_e;
        private final boolean field_150656_f;
        private List<ChunkPosition> field_150657_g = new ArrayList<>();
        private final boolean canMakeSlopes;

        public RailData(World p_i45388_2_, int p_i45388_3_, int p_i45388_4_, int p_i45388_5_) {
            this.field_150660_b = p_i45388_2_;
            this.field_150661_c = p_i45388_3_;
            this.field_150658_d = p_i45388_4_;
            this.field_150659_e = p_i45388_5_;
            BlockRailBase block = (BlockRailBase)p_i45388_2_.getBlock(p_i45388_3_, p_i45388_4_, p_i45388_5_);
            int l = block.getBasicRailMetadata(p_i45388_2_, null, p_i45388_3_, p_i45388_4_, p_i45388_5_);
            this.field_150656_f = !block.isFlexibleRail(p_i45388_2_, p_i45388_3_, p_i45388_4_, p_i45388_5_);
            canMakeSlopes = block.canMakeSlopes(p_i45388_2_, p_i45388_3_, p_i45388_4_, p_i45388_5_);
            this.func_150648_a(l);
        }

        private void func_150648_a(int p_150648_1_) {
            this.field_150657_g.clear();

            if (p_150648_1_ == 0) {
                this.field_150657_g.add(new ChunkPosition(this.field_150661_c, this.field_150658_d, this.field_150659_e - 1));
                this.field_150657_g.add(new ChunkPosition(this.field_150661_c, this.field_150658_d, this.field_150659_e + 1));
            }
            else if (p_150648_1_ == 1) {
                this.field_150657_g.add(new ChunkPosition(this.field_150661_c - 1, this.field_150658_d, this.field_150659_e));
                this.field_150657_g.add(new ChunkPosition(this.field_150661_c + 1, this.field_150658_d, this.field_150659_e));
            }
            else if (p_150648_1_ == 2) {
                this.field_150657_g.add(new ChunkPosition(this.field_150661_c - 1, this.field_150658_d, this.field_150659_e));
                this.field_150657_g.add(new ChunkPosition(this.field_150661_c + 1, this.field_150658_d + 1, this.field_150659_e));
            }
            else if (p_150648_1_ == 3) {
                this.field_150657_g.add(new ChunkPosition(this.field_150661_c - 1, this.field_150658_d + 1, this.field_150659_e));
                this.field_150657_g.add(new ChunkPosition(this.field_150661_c + 1, this.field_150658_d, this.field_150659_e));
            }
            else if (p_150648_1_ == 4) {
                this.field_150657_g.add(new ChunkPosition(this.field_150661_c, this.field_150658_d + 1, this.field_150659_e - 1));
                this.field_150657_g.add(new ChunkPosition(this.field_150661_c, this.field_150658_d, this.field_150659_e + 1));
            }
            else if (p_150648_1_ == 5) {
                this.field_150657_g.add(new ChunkPosition(this.field_150661_c, this.field_150658_d, this.field_150659_e - 1));
                this.field_150657_g.add(new ChunkPosition(this.field_150661_c, this.field_150658_d + 1, this.field_150659_e + 1));
            }
            else if (p_150648_1_ == 6) {
                this.field_150657_g.add(new ChunkPosition(this.field_150661_c + 1, this.field_150658_d, this.field_150659_e));
                this.field_150657_g.add(new ChunkPosition(this.field_150661_c, this.field_150658_d, this.field_150659_e + 1));
            }
            else if (p_150648_1_ == 7) {
                this.field_150657_g.add(new ChunkPosition(this.field_150661_c - 1, this.field_150658_d, this.field_150659_e));
                this.field_150657_g.add(new ChunkPosition(this.field_150661_c, this.field_150658_d, this.field_150659_e + 1));
            }
            else if (p_150648_1_ == 8) {
                this.field_150657_g.add(new ChunkPosition(this.field_150661_c - 1, this.field_150658_d, this.field_150659_e));
                this.field_150657_g.add(new ChunkPosition(this.field_150661_c, this.field_150658_d, this.field_150659_e - 1));
            }
            else if (p_150648_1_ == 9) {
                this.field_150657_g.add(new ChunkPosition(this.field_150661_c + 1, this.field_150658_d, this.field_150659_e));
                this.field_150657_g.add(new ChunkPosition(this.field_150661_c, this.field_150658_d, this.field_150659_e - 1));
            }
        }

        private void func_150651_b() {
            for (int i = 0; i < this.field_150657_g.size(); ++i) {
                BlockRailCore.RailData rail = this.func_150654_a(this.field_150657_g.get(i));

                if (rail != null && rail.func_150653_a(this)) {
                    this.field_150657_g.set(i, new ChunkPosition(rail.field_150661_c, rail.field_150658_d, rail.field_150659_e));
                }
                else {
                    this.field_150657_g.remove(i--);
                }
            }
        }

        private BlockRailCore.RailData func_150654_a(ChunkPosition p_150654_1_) {
            return BlockRailBase.func_150049_b_(this.field_150660_b, p_150654_1_.chunkPosX, p_150654_1_.chunkPosY, p_150654_1_.chunkPosZ) ? new RailData(this.field_150660_b, p_150654_1_.chunkPosX, p_150654_1_.chunkPosY, p_150654_1_.chunkPosZ) : (BlockRailBase.func_150049_b_(this.field_150660_b, p_150654_1_.chunkPosX, p_150654_1_.chunkPosY + 1, p_150654_1_.chunkPosZ) ? new RailData(this.field_150660_b, p_150654_1_.chunkPosX, p_150654_1_.chunkPosY + 1, p_150654_1_.chunkPosZ) : (BlockRailBase.func_150049_b_(this.field_150660_b, p_150654_1_.chunkPosX, p_150654_1_.chunkPosY - 1, p_150654_1_.chunkPosZ) ? new RailData(this.field_150660_b, p_150654_1_.chunkPosX, p_150654_1_.chunkPosY - 1, p_150654_1_.chunkPosZ) : null));
        }

        private boolean func_150653_a(BlockRailCore.RailData p_150653_1_) {
            for (ChunkPosition chunkposition : this.field_150657_g) {
                if (chunkposition.chunkPosX == p_150653_1_.field_150661_c && chunkposition.chunkPosZ == p_150653_1_.field_150659_e) {
                    return true;
                }
            }

            return false;
        }

        private boolean func_150652_b(int p_150652_1_, int p_150652_3_) {
            for (ChunkPosition chunkposition : this.field_150657_g) {
                if (chunkposition.chunkPosX == p_150652_1_ && chunkposition.chunkPosZ == p_150652_3_) {
                    return true;
                }
            }

            return false;
        }

        private boolean func_150649_b(BlockRailCore.RailData p_150649_1_) {
            return this.func_150653_a(p_150649_1_) || this.field_150657_g.size() != 2;
        }

        private void func_150645_c(BlockRailCore.RailData p_150645_1_) {
            this.field_150657_g.add(new ChunkPosition(p_150645_1_.field_150661_c, p_150645_1_.field_150658_d, p_150645_1_.field_150659_e));
            boolean flag = this.func_150652_b(this.field_150661_c, this.field_150659_e - 1);
            boolean flag1 = this.func_150652_b(this.field_150661_c, this.field_150659_e + 1);
            boolean flag2 = this.func_150652_b(this.field_150661_c - 1, this.field_150659_e);
            boolean flag3 = this.func_150652_b(this.field_150661_c + 1, this.field_150659_e);
            byte b0 = -1;

            if (flag || flag1) {
                b0 = 0;
            }

            if (flag2 || flag3) {
                b0 = 1;
            }

            if (!this.field_150656_f) {
                if (flag1 && flag3 && !flag && !flag2) {
                    b0 = 6;
                }

                if (flag1 && flag2 && !flag && !flag3) {
                    b0 = 7;
                }

                if (flag && flag2 && !flag1 && !flag3) {
                    b0 = 8;
                }

                if (flag && flag3 && !flag1 && !flag2) {
                    b0 = 9;
                }
            }

            if (b0 == 0 && canMakeSlopes) {
                if (BlockRailBase.func_150049_b_(this.field_150660_b, this.field_150661_c, this.field_150658_d + 1, this.field_150659_e - 1)) {
                    b0 = 4;
                }

                if (BlockRailBase.func_150049_b_(this.field_150660_b, this.field_150661_c, this.field_150658_d + 1, this.field_150659_e + 1)) {
                    b0 = 5;
                }
            }

            if (b0 == 1 && canMakeSlopes) {
                if (BlockRailBase.func_150049_b_(this.field_150660_b, this.field_150661_c + 1, this.field_150658_d + 1, this.field_150659_e)) {
                    b0 = 2;
                }

                if (BlockRailBase.func_150049_b_(this.field_150660_b, this.field_150661_c - 1, this.field_150658_d + 1, this.field_150659_e)) {
                    b0 = 3;
                }
            }

            if (b0 < 0) {
                b0 = 0;
            }

            int i = b0;

            if (this.field_150656_f) {
                if(field_150660_b.getTileEntity(this.field_150661_c, this.field_150658_d, this.field_150659_e) instanceof RailTileEntity){
                    i= ((RailTileEntity) field_150660_b.getTileEntity(this.field_150661_c, this.field_150658_d, this.field_150659_e)).getMeta();
                }
                //i = this.field_150660_b.getBlockMetadata(this.field_150661_c, this.field_150658_d, this.field_150659_e) & 8 | b0;
            }

            if(field_150660_b.getTileEntity(this.field_150661_c, this.field_150658_d, this.field_150659_e) instanceof RailTileEntity){
                ((RailTileEntity) field_150660_b.getTileEntity(this.field_150661_c, this.field_150658_d, this.field_150659_e)).setMeta(i);
            }
            //this.field_150660_b.setBlockMetadataWithNotify(this.field_150661_c, this.field_150658_d, this.field_150659_e, i, 3);
        }

        private boolean func_150647_c(int p_150647_1_, int p_150647_2_, int p_150647_3_) {
            BlockRailCore.RailData rail = this.func_150654_a(new ChunkPosition(p_150647_1_, p_150647_2_, p_150647_3_));

            if (rail == null) {
                return false;
            } else {
                rail.func_150651_b();
                return rail.func_150649_b(this);
            }
        }

        public void func_150655_a(boolean p_150655_1_, boolean p_150655_2_) {
            boolean flag2 = this.func_150647_c(this.field_150661_c, this.field_150658_d, this.field_150659_e - 1);
            boolean flag3 = this.func_150647_c(this.field_150661_c, this.field_150658_d, this.field_150659_e + 1);
            boolean flag4 = this.func_150647_c(this.field_150661_c - 1, this.field_150658_d, this.field_150659_e);
            boolean flag5 = this.func_150647_c(this.field_150661_c + 1, this.field_150658_d, this.field_150659_e);
            byte b0 = -1;

            if ((flag2 || flag3) && !flag4 && !flag5) {
                b0 = 0;
            }

            if ((flag4 || flag5) && !flag2 && !flag3) {
                b0 = 1;
            }

            if (!this.field_150656_f) {
                if (flag3 && flag5 && !flag2 && !flag4) {
                    b0 = 6;
                }

                if (flag3 && flag4 && !flag2 && !flag5) {
                    b0 = 7;
                }

                if (flag2 && flag4 && !flag3 && !flag5) {
                    b0 = 8;
                }

                if (flag2 && flag5 && !flag3 && !flag4) {
                    b0 = 9;
                }
            }

            if (b0 == -1) {
                if (flag2 || flag3) {
                    b0 = 0;
                }

                if (flag4 || flag5) {
                    b0 = 1;
                }

                if (!this.field_150656_f) {
                    if (p_150655_1_) {
                        if (flag3 && flag5) {
                            b0 = 6;
                        }

                        if (flag4 && flag3) {
                            b0 = 7;
                        }

                        if (flag5 && flag2) {
                            b0 = 9;
                        }

                        if (flag2 && flag4) {
                            b0 = 8;
                        }
                    }
                    else {
                        if (flag2 && flag4) {
                            b0 = 8;
                        }

                        if (flag5 && flag2) {
                            b0 = 9;
                        }

                        if (flag4 && flag3) {
                            b0 = 7;
                        }

                        if (flag3 && flag5) {
                            b0 = 6;
                        }
                    }
                }
            }

            if (b0 == 0 && canMakeSlopes) {
                if (BlockRailBase.func_150049_b_(this.field_150660_b, this.field_150661_c, this.field_150658_d + 1, this.field_150659_e - 1)) {
                    b0 = 4;
                }

                if (BlockRailBase.func_150049_b_(this.field_150660_b, this.field_150661_c, this.field_150658_d + 1, this.field_150659_e + 1)) {
                    b0 = 5;
                }
            }

            if (b0 == 1 && canMakeSlopes) {
                if (BlockRailBase.func_150049_b_(this.field_150660_b, this.field_150661_c + 1, this.field_150658_d + 1, this.field_150659_e)) {
                    b0 = 2;
                }

                if (BlockRailBase.func_150049_b_(this.field_150660_b, this.field_150661_c - 1, this.field_150658_d + 1, this.field_150659_e)) {
                    b0 = 3;
                }
            }

            if (b0 < 0) {
                b0 = 0;
            }

            this.func_150648_a(b0);
            int i = b0;

            if(field_150660_b.getTileEntity(this.field_150661_c, this.field_150658_d, this.field_150659_e) instanceof RailTileEntity) {
                if (this.field_150656_f) {
                    i = ((RailTileEntity) field_150660_b.getTileEntity(this.field_150661_c, this.field_150658_d, this.field_150659_e)).getMeta() & 8 | b0;
                }

                if (p_150655_2_ || ((RailTileEntity) field_150660_b.getTileEntity(this.field_150661_c, this.field_150658_d, this.field_150659_e)).getMeta() != i) {
                    ((RailTileEntity) field_150660_b.getTileEntity(this.field_150661_c, this.field_150658_d, this.field_150659_e)).setMeta(i);

                    for (ChunkPosition chunkPosition : this.field_150657_g) {
                        RailData rail = this.func_150654_a(chunkPosition);

                        if (rail != null) {
                            rail.func_150651_b();

                            if (rail.func_150649_b(this)) {
                                rail.func_150645_c(this);
                            }
                        }
                    }
                }
            }
        }
    }
}
