package ebf.tim.api;

import ebf.tim.entities.EntityRollingStockCore;
import ebf.tim.models.Bogie;
import ebf.tim.models.tmt.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;
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

    /*if this needs to be changed, then after you change it, have the renderdata.modelList variable set to null so it has to regen the data with the new models*/
    public abstract List<? extends ModelBase> getModel();

    public abstract Bogie[] getBogieModels();

}
