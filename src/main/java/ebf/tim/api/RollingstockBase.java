package ebf.tim.api;

import ebf.tim.entities.EntityRollingStockCore;
import ebf.tim.models.Bogie;
import ebf.tim.models.tmt.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.UUID;

/**
 * Created by Eternal Blue Flame
 */
public abstract class RollingstockBase extends EntityRollingStockCore{

    //Constructors, no need to override these unless you need to.
    public RollingstockBase(UUID owner, World world, double xPos, double yPos, double zPos){
        super(owner, world, xPos, yPos, zPos);
    }
    public RollingstockBase(World worldObj){
        super(worldObj);
    }

    public abstract ResourceLocation getTexture();

    public abstract ModelBase getModel();

    public abstract Bogie[] getBogieModels();

}
