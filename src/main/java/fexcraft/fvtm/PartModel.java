package fexcraft.fvtm;

import fexcraft.fvtm.model.TurboList;
import fexcraft.tmt.slim.ModelBase;
import fexcraft.tmt.slim.ModelRendererTurbo;
import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * A compatibility class for FMT models
 * @Author Eternal BlueFlame
 *
 * @OriginalAuthor Ferdinand Calo' (FEX___96)
 */

public class PartModel extends ModelBase {

    public List<TurboList> groups = new ArrayList<>();
    public PartModel(){}

    public void addToCreators(String s){
        creators.add(s);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5){
        for(TurboList list : groups){
            for (ModelRendererTurbo turbo : list){
                turbo.render();
            }
        }
    }

}
