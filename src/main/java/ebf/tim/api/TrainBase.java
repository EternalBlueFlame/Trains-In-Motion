package ebf.tim.api;

import ebf.tim.entities.EntityTrainCore;
import ebf.tim.models.Bogie;
import ebf.tim.models.tmt.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.UUID;

/**
 * Created by Eternal Blue Flame
 */
public abstract class TrainBase extends EntityTrainCore{

    //Constructors, no need to override these unless you need to.
    public TrainBase(UUID owner, World world, double xPos, double yPos, double zPos){
        super(owner, world, xPos, yPos, zPos);
    }
    public TrainBase(World worldObj){
        super(worldObj);
    }

    public abstract ModelBase getModel();

    public abstract Bogie[] getBogieModels();

}
