package com.example.examplemod.entities;

import net.minecraft.block.Block;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class EntityMovingLight extends TileEntity implements IUpdatePlayerListBox {

    public MinecartExtended theCart;
    public double[] oldpos = new double[]{0d,0d,0d};
    public int[] offset = new int[]{0,0,0};
    public EntityMovingLight() {}

    public TileEntity EntityMovingLight(MinecartExtended cart, int offsetSide, int offsetFront, int offsetHeight) {
        theCart = cart;
        this.offset = new int[]{offsetSide, offsetFront, offsetHeight};
        this.oldpos = new double[]{cart.xPosition,cart.yPosition, cart.zPosition};
        this.xCoord = (int)Math.round(cart.xPosition) + offset[0];
        this.yCoord = (int)Math.round(cart.yPosition) + offset[1];
        this.zCoord = (int)Math.round(cart.zPosition) + offset[2];
        return this;
    }

    /**
     * This controls whether the tile entity gets replaced whenever the block state

     * is changed. Normally only want this when block actually is replaced.
     */
    @Override
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int posX, int posY, int posZ) {
        return (blockMetadata != newMeta);
    }

    @Override
    public void update() {
        // check if cart has moved away from the tile entity, or has disappeared
        if (theCart == null || !theCart.lamp) {
            this.invalidate();
        } else if (Math.round(theCart.zPosition) != this.oldpos[0] ^ Math.round(theCart.yPosition) != this.oldpos[1] ^ Math.round(theCart.zPosition) != this.oldpos[2]) {
            this.oldpos[0] =  theCart.xPosition + offset[0];
            this.oldpos[1] =  theCart.yPosition + offset[1];
            this.oldpos[2] =  theCart.zPosition + offset[2];
        }
    }
}
