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

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5){
        for(TurboList list : groups){
            list.render(entity, f, f1, f2, f3, f4, f5);
        }
    }

    @Override
    public List<ModelRendererTurbo> getParts(){
        List<ModelRendererTurbo> turboList = new ArrayList<ModelRendererTurbo>();
        for(TurboList g: groups){
            turboList.addAll(g);
        }
        return turboList;
    }
}
