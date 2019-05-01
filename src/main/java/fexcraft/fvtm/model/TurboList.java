package fexcraft.fvtm.model;

import ebf.tim.utility.RailUtility;
import fexcraft.fcl.common.lang.ArrayList;
import fexcraft.tmt.slim.ModelRendererTurbo;
/**
 * A compatibility class for FMT models
 * @Author Eternal BlueFlame
 *
 * @OriginalAuthor Ferdinand Calo' (FEX___96)
 */
public class TurboList extends ArrayList<ModelRendererTurbo> {

    String boxname;
    public TurboList(String name){
        boxname=name;
    }

    @Override
    public boolean add(ModelRendererTurbo t){

        t.rotateAngleY *= RailUtility.radianF;
        t.rotateAngleZ *= RailUtility.radianF;
        t.rotateAngleX *= RailUtility.radianF;
        return super.add(t);
    }

}
