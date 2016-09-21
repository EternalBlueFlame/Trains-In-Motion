package trains.entities;

import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import trains.utility.ClientProxy;
import trains.utility.Util;
import trains.utility.LampHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityTrainCore extends Entity implements IInventory, IEntityAdditionalSpawnData {


    /**
     *
     *
     * <h1> Variable Assignment </h1>
     *
     *
     */
    public float[] acceleration = new float[]{0.0005F,0.0006F,0.0005F}; //the first 3 values are a point curve, representing <35%, 35-70% and >70% to modify how acceleration is handled at each point. //the 4th value defines how much the weight hauled effects acceleration.
    public int trainType=0;//list of train types 0 is null, 1 is steam, 2 is diesel, 3 is electric, 4 is hydrogen, 5 is nuclear steam, 6 is nuclear electric
    public boolean isRunning = false;// if the train is running/using fuel
    public int furnaceFuel = 0; //the amount of fuel in the furnace, only used for steam and nuclear trains
    public int maxFuel =0; //the max fuel in the train's furnace.
    public float maxSpeed = 0; // the max speed
    public int trainTicks =0; //defines the train's tick count.
    public int accelerator =0; //defines the value of how much to speed up, or brake the train.


    //Main Values
    public int[] colors = new int[]{0,0,0}; //allows certain parts of certain trains to be recolored
    public String name = "";
    public boolean isLocked = false; //mostly used to lock other players from using/accessing parts of the cart/train
    public boolean brake = false; //bool for the train/rollingstock's break.
    public LampHandler lamp = new LampHandler(); //manages the lamp, or lack there of.
    public int GUIID = 0; //id for the GUI
    public UUID owner = null;  //universal, get train owner
    public List<Double> bogieOffsets = new ArrayList<Double>(); //number of bogies the train is supposed to maintain
    private int bogieCount =0;

    //list of boogies
    public List<MinecartExtended> bogie = new ArrayList<MinecartExtended>();

    private boolean isReverse =false;
    public List<double[]> bogieXYZ = new ArrayList<double[]>();

    //inventory
    public List<ItemStack> inventory = new ArrayList<ItemStack>(){};//Inventory, every train will have this to some extent or another, //the first two slots are for crafting
    public FluidTank[] tank = new FluidTank[]{};//depending on the train this is either used for diesel, steam, or redstone flux



    /**
    *
    *
    * <h1> Constructor and Variables</h1>
    *
    * default constructor for setting up variables after this is created
    * @param owner the owner profile, used to define owner of the entity,
    * @param world the world to spawn the entity in, used in super's super.
    * @param xPos the x position to spawn entity at, used in super's super.
    * @param yPos the y position to spawn entity at, used in super's super.
    * @param zPos the z position to spawn entity at, used in super's super.
    * @param maxSpeed the max speed a train can go, 1.0 is equal to 72kmph or 72000 blocks/h.
    * @param type what kind of rolling stock it is.
    *             type list:
    *             1: steam
    *             2: diesel
    *             3: hydrogen diesel
    *             4: electric
    *             5: nuclear steam
    *             6: nuclear electric
    *             7: maglev
    * @param tank used to define the fluid tank(s) if there are any
    *             empty array for no tanks, - steam and nuclear take two tanks. - all other trains take one tank
    *             all tanks besides diesel should use FluidRegistry.WATER
    * @param GUIid the ID used to define what GUI the entity uses (0 for no GUI).
    */

    public EntityTrainCore(UUID owner, World world, double xPos, double yPos, double zPos, double riderOffsetXZ, List<Double> bogiePositions, float maxSpeed, float[] acceleration,
                           int type,FluidTank[] tank,int inventorySize, int GUIid){
        super(world);
        this.posY = yPos;
        this.posX = xPos;
        this.posZ = zPos;

        this.acceleration = acceleration;
        trainType = type;
        this.maxSpeed = maxSpeed;
        this.owner = owner;
        this.tank = tank;
        this.bogieOffsets = bogiePositions;
        this.bogieCount = bogiePositions.size();

        //define the number of inventory slots to add to the train based on type for crafting slots and inventory scale
        int craftingSlots =0;
        switch (type){
            case 1:case 5: {craftingSlots =2;break;}
            case 2:case 3: case 4:case 6:{craftingSlots =1;break;}
        }
        switch (inventorySize){
            case 1:{craftingSlots+=4;break;}//2x2
            case 2:{craftingSlots+=6;break;}//2x3
            case 3:{craftingSlots+=9;break;}//3x3
            case 4:{craftingSlots+=12;break;}//4x3
            case 5:{craftingSlots+=16;break;}//4x4
        }
        for (int i=0; i<=craftingSlots;i++){
            inventory.add(null);
        }

        GUIID = GUIid;

    }
    //this constructor is lower level spawning
    public EntityTrainCore(World world){
        super(world);
        //set the size of the collision box
        this.setSize(1.5F,2.0F);

    }
    /**
     * entity initialization, I think this happens between constructor and the first onUpdate, or before the constructor.
     */
    @Override
    public void entityInit(){}

    //variable assigners
    public void setOwner(UUID player){owner = player;}
    public UUID getOwnerUUID(){return owner;}

    //return if the train can be used by the player, if it's locked, only the owner can use it.
    @Override
    public boolean isUseableByPlayer(EntityPlayer player){
        //if it is locked, return if player is owner, otherwise return false.
        return isLocked || owner.equals(player.getUniqueID());
    }
    //manage the bounding and collision boxes
    @Override
    public AxisAlignedBB getBoundingBox(){
        return this.boundingBox;
    }
    @Override
    public AxisAlignedBB getCollisionBox(Entity collidedWith){
        if (collidedWith != this.riddenByEntity) {
            return this.boundingBox.intersectsWith(collidedWith.boundingBox) ? collidedWith.boundingBox : null;
        } else { return null;}
    }
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }



    /**
    *
    *
    * <h1> Data Syncing and Saving </h1>
    *
    * used for syncing the spawn data with client and server, and saving/loading information from world (NBT)
    * @see IEntityAdditionalSpawnData
    * @see NBTTagCompound
    */

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        maxSpeed = additionalData.readFloat();
        isReverse = additionalData.readBoolean();
        owner = new UUID(additionalData.readLong(), additionalData.readLong());
        bogieCount = additionalData.readInt();
        //we loop using the offset double length because we expect bogieXYZ to be null.
        for (int i=0; i<bogieCount; i++){
            bogieOffsets.add(additionalData.readDouble());

            bogieXYZ.add(new double[]{additionalData.readDouble(), additionalData.readDouble(), additionalData.readDouble()});
        }

    }
    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeFloat(maxSpeed);
        buffer.writeBoolean(isReverse);
        buffer.writeLong(owner.getMostSignificantBits());
        buffer.writeLong(owner.getLeastSignificantBits());
        buffer.writeInt(bogieCount);
        for (int i=0; i<bogieOffsets.size(); i++) {
            buffer.writeDouble(bogieOffsets.get(i));

            buffer.writeDouble(bogieXYZ.get(i)[0]);
            buffer.writeDouble(bogieXYZ.get(i)[1]);
            buffer.writeDouble(bogieXYZ.get(i)[2]);
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        isRunning = tag.getBoolean("train.isrunning");
        isLocked = tag.getBoolean("extended.islocked");
        brake = tag.getBoolean("extended.brake");
        lamp.isOn = tag.getBoolean("extended.lamp");
        isReverse = tag.getBoolean("extended.isreverse");
        owner = new UUID(tag.getLong("extended.ownerm"),tag.getLong("extended.ownerl"));
        bogieCount = tag.getInteger("extended.bogiecount");
        //read through the bogie positions
        NBTTagList bogieTaglList = tag.getTagList("extended.bogies", 10);
        for (int i = 0; i < bogieTaglList.tagCount(); i++) {
            NBTTagCompound nbttagcompound1 = bogieTaglList.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("bogie");

            if (b0 >= 0) {
                bogieOffsets.add(nbttagcompound1.getDouble("bogieindex.offset." + i));
                bogieXYZ.add(new double[]{nbttagcompound1.getDouble("bogieindex.a." + i),nbttagcompound1.getDouble("bogieindex.b." + i),nbttagcompound1.getDouble("bogieindex.c." + i)});
            }
        }
        //read through the itemstacks
        NBTTagList taglist = tag.getTagList("train.items", 10);
        for (int i = 0; i < taglist.tagCount(); i++) {
            NBTTagCompound nbttagcompound1 = taglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("slot");

            if (b0 >= 0 && b0 < inventory.size()) {
                inventory.set(b0, ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }
        }

        for (FluidTank tempTank : tank){
            tempTank.readFromNBT(tag);
        }
    }
    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        tag.setBoolean("train.isrunning",isRunning);
        tag.setBoolean("extended.islocked", isLocked);
        tag.setBoolean("extended.brake", brake);
        tag.setBoolean("extended.lamp", lamp.isOn);
        tag.setBoolean("extended.isreverse", isReverse);
        tag.setLong("extended.ownerm", owner.getMostSignificantBits());
        tag.setLong("extended.ownerl", owner.getLeastSignificantBits());
        tag.setInteger("extended.bogiecount", bogieCount);
        //write the list of bogies
        NBTTagList nbtBogieTaglist = new NBTTagList();
        for (int i = 0; i < bogieXYZ.size(); ++i) {
            if (bogieXYZ.get(i) != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("bogie", (byte)i);
                nbttagcompound1.setDouble("bogieindex.offset." + i,bogieOffsets.get(i));
                nbttagcompound1.setDouble("bogieindex.a." + i,bogieXYZ.get(i)[0]);
                nbttagcompound1.setDouble("bogieindex.b." + i,bogieXYZ.get(i)[1]);
                nbttagcompound1.setDouble("bogieindex.c." + i,bogieXYZ.get(i)[2]);
                nbtBogieTaglist.appendTag(nbttagcompound1);
            }
        }
        tag.setTag("extended.bogies", nbtBogieTaglist);
        //write the itemset to a tag list before adding it
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < inventory.size(); ++i) {
            if (inventory.get(i) != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("slot", (byte)i);
                inventory.get(i).writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        tag.setTag("train.items", nbttaglist);

        for (FluidTank tempTank : tank){
            tempTank.writeToNBT(tag);
        }
    }

    /**
     *
     *
     * <h1> Inventory management </h1>
     *
     * @see IInventory
     */

    @Override
    public String getInventoryName() {
        return name;
    }
    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }
    @Override
    public void openInventory() {}
    @Override
    public void closeInventory() {}
    @Override
    public void markDirty() {}
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        //be sure the slot exists before trying to return anything,
        if (slot>=0 && slot< inventory.size()) {
            return inventory.get(slot);
        }
        return null;
    }
    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        //we return null no matter what, but we want to make sure the slot is properly set as well.
        if (slot>=0 && slot< inventory.size()) {
            inventory.set(slot, null);
        }
        return null;
    }
    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        //be sure the slot exists before trying to return anything,
        if (slot>=0 && slot< inventory.size()) {
            //if subtraction makes slot empty/null then set it to null and return null, otherwise return the stack.
            if (inventory.get(slot).stackSize <= amount ^ inventory.get(slot).stackSize <= 0) {
                inventory.set(slot, null);
            } else {
                return inventory.get(slot).splitStack(amount);
            }
        }
        return null;
    }
    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack) {
        //be sure item stack isn't null, then add the itemstack, and be sure the slot doesn't go over the limit.
        if (itemstack != null && slot >=0 && slot<inventory.size()) {
            if (itemstack.stackSize >= getInventoryStackLimit()) {
                itemstack.stackSize = getInventoryStackLimit();
            }
            inventory.set(slot, itemstack);
        }
    }

    //return if the item can be placed in the slot, for this slot it's just a check if the slot exists, but other things may have slots for specific items, this filters that.
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item){
        return (slot>=0 && slot< inventory.size());
    }

    //return the number of inventory slots
    @Override
    public int getSizeInventory(){
        if(inventory != null){
            return inventory.size();
        } else{
            return 0;
        }
    }

    //simple function for adding an itemstack to the inventory with an offset
    public boolean addItems(int offset, ItemStack itemStack){
        for (int i=offset; i<inventory.size();) {
            if (inventory.get(i).getItem() == null){
                inventory.set(i, itemStack);
                return true;
            } else if (inventory.get(i).getMaxStackSize() <= inventory.get(i).stackSize){
                inventory.get(i).stackSize+=1;
                return true;
            } else {
                i++;
            }
        }
        return false;
    }

    /**
    *
    *
    * <h1> onUpdate </h1>
    *
    * defines what should be done this tick
    * used for:
    * managing the list of bogies which are used for defining position and rotation.
    * being sure the train is listed in the main class (for lighting management).
    * managing rotationYaw and rotationPitch.
    * managing speed, acceleration. and direction.
    * and calling the fuel handler for fuel consumption.
    * @see Util
    */

    @Override
    public void onUpdate() {

        if (trainTicks >0) {
            //cache the sizes ahead of time, and be sure xyz's size is larger than 0 before bothering to continue.
            int xyzSize = bogieXYZ.size();
            if (xyzSize > 0) {
                int bogieSize = bogie.size() - 1;
                //always be sure the bogies exist on client and server.
                if (bogieSize < 1) {
                    for (double[] pos : bogieXYZ) {//it should never be possible for bogieXYZ to be null unless there is severe server data corruption.
                        MinecartExtended spawnBogie = new MinecartExtended(worldObj, pos[0], pos[1], pos[2]);
                        if (!worldObj.isRemote) {
                            worldObj.spawnEntityInWorld(spawnBogie);
                        }
                        bogie.add(spawnBogie);
                    }
                    bogieSize = bogie.size()-1;
                }
                //move the train's position between the bogies, only do this on server, minecraft syncs this for us.
                if (!worldObj.isRemote) {
                    setPositionAndRotation(
                            (bogie.get(bogieSize).posX + bogie.get(0).posX) * 0.5D,
                            (bogie.get(bogieSize).posY + bogie.get(0).posY) * 0.5D,
                            (bogie.get(bogieSize).posZ + bogie.get(0).posZ) * 0.5D,
                            180-MathHelper.floor_double(Math.toDegrees(Math.atan2(bogie.get(0).posZ - bogie.get(bogieSize).posZ, bogie.get(0).posX - bogie.get(bogieSize).posX))),
                            MathHelper.floor_double(Math.acos(bogie.get(0).posY / bogie.get(bogieSize).posY))
                    );
                    motionZ = (bogie.get(0).motionZ + bogie.get(bogieSize).motionZ) * 0.5D;
                    motionX = (bogie.get(0).motionX + bogie.get(bogieSize).motionX) * 0.5D;



                    for (int i = 0; i < xyzSize && i < bogieSize; i++) {

                        //TODO this still needs work, so its disabled (in a very hacky method) currently
                        //be sure the bogies are in relative position to the train.
                        // besides updating positions we don't care about the back bogie, because the positions for other bogies are mostly defined from it, indirectly.
                        if (trainTicks >1 && i>0 && true == false) {
                            double bogieX1 = (posX + (Math.cos(Math.toRadians(rotationYaw)) * Math.abs(bogieOffsets.get(i))));
                            double bogieZ1 = (posZ + (Math.sin(Math.toRadians(rotationYaw)) * Math.abs(bogieOffsets.get(i))));
                            bogie.get(i).setPosition(bogieX1, bogie.get(i).posY, bogieZ1);
                            bogie.get(i).setPosition(
                                    posX + (Math.cos(bogieOffsets.get(i) * Math.toRadians(rotationYaw))),
                                    bogie.get(i).posY,
                                    posZ + (Math.sin(bogieOffsets.get(i) * Math.toRadians(rotationYaw))));

                            bogie.get(i).motionX = (bogieX1 - posX);
                            bogie.get(i).motionZ = (bogieZ1 - posZ);
                        }

                        //be sure the list of bogie positions is updated to the current values.
                        bogieXYZ.set(i, new double[]{bogie.get(i).posX, bogie.get(i).posY, bogie.get(i).posZ});
                    }
                }
            }

            //client
            if (worldObj.isRemote && xyzSize > 0 && bogieXYZ.get(0)[0] + bogieXYZ.get(0)[1] + bogieXYZ.get(0)[2] != 0.0D) {

                if (!ClientProxy.carts.contains(this)) {
                    /**
                     * add lamp to main class train handler when created, so the main thread can deal with updating the lighting, or not.
                     * @see ClientProxy#onTick(TickEvent.ClientTickEvent)
                     */
                    ClientProxy.carts.add(this);
                }
            }
            //server
            if (!worldObj.isRemote && xyzSize > 0 && bogieXYZ.get(0)[0] + bogieXYZ.get(0)[1] + bogieXYZ.get(0)[2] != 0.0D) {

                /**
                 *
                 * based on the motion compared to max speed, figure out which stage of acceleration the train is in.
                 * from there, if the x and/or z motions are not 0, we create a new x and/or z value,
                 * after we handle the acceleration by multiplying the acceleration incex by the current stage of the accelerator (or brake which is an inverse value.)
                 * TODO after we can link carts, drag needs to be changed to 1-(0.04*ConnectedCars) && braking should be added to the drag
                 */
                if (accelerator != 0) {
                    double x = 0;
                    double z = 0;
                    double motion = Math.sqrt(motionX * motionX + motionZ * motionZ);
                    int accelIndex = 0;
                    if (motion > maxSpeed || motion < -maxSpeed) {
                        accelIndex = -1;
                    } else if (motion > maxSpeed * 0.6d || motion < -(maxSpeed * 0.6d)) {
                        accelIndex = 2;
                    } else if (motion > maxSpeed * 0.3d || motion < -(maxSpeed * 0.3d)) {
                        accelIndex = 1;
                    }

                    if (accelerator != 0 && accelIndex != -1) {
                        if (motionX > 0) {
                            x = (acceleration[accelIndex] * accelerator);
                        } else {
                            x = -(acceleration[accelIndex] * accelerator);
                        }
                        if (motionZ > 0) {
                            z = (acceleration[accelIndex] * accelerator);
                        } else {
                            z = -(acceleration[accelIndex] * accelerator);
                        }

                        //compensate for stopping the train to 0, this is needed for brake and switching from forward to reverse.
                    /*if (motionX != 0.0D && ((x < 0.0D && motionX > 0.0D) || (x > 0.0D && motionX < 0.0D))){
                        x=0.0D;
                    }
                    if ( motionZ != 0.0D && ((z < 0.0D && motionZ > 0.0D) || (z > 0.0D && motionZ < 0.0D))){
                        z=0.0D;
                    }*/
                    }
                    //now apply the changes to all the bogies.
                    for (MinecartExtended bogieClone : bogie) {
                        bogieClone.addVelocity(x, bogieClone.motionY, z);
                    }
                }
            }

            //simple tick management so some code does not need to be run every tick.
            switch (trainTicks) {
                case 5: {
                    if (!worldObj.isRemote && isRunning) {
                        Util.ManageFuel(this);
                    }
                    break;
                }
                default: {
                    //if the tick count is higher than the values used, reset it so it can count up again.
                    if (trainTicks > 10) {
                        trainTicks = 1;
                    }
                }
            }
        }
        trainTicks++;
    }

    //modify the player sitting position
    @Override
    public void updateRiderPosition() {
        if (riddenByEntity != null) {
            if (bogie.size()>1) {
                double offset =1;

                Vec3 position = Vec3.createVectorHelper(offset,2D,0);
                position.rotateAroundX(this.rotationYaw);

                riddenByEntity.setPosition(this.posX + position.xCoord, this.posY +position.yCoord, this.posZ + position.zCoord);
            } else {
                riddenByEntity.setPosition(this.posX, this.posY + 2D, this.posZ);
            }
        }
    }

    /**
     *
     *
     * <h1> Networking </h1>
     *
     * simple function for setting the train's speed and whether or not it is reverse.
     */
    public void setAcceleration(boolean increase){
        if (increase && accelerator <6){
            accelerator++;
        } else if (!increase && accelerator >-6){
            accelerator--;
        }
        if (accelerator>0 && !isReverse){
            isReverse = false;
        } else if (accelerator <0 && isReverse){
            isReverse = true;
        }
    }
}
