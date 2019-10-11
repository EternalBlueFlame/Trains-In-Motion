package fexcraft.fvtm.model;

import ebf.tim.utility.RailUtility;
import fexcraft.fcl.common.lang.ArrayList;
import fexcraft.fvtm.PartModel;
import fexcraft.tmt.slim.ModelBase;
import fexcraft.tmt.slim.ModelRendererTurbo;
/**
 * A compatibility class for FMT models
 * @Author Eternal BlueFlame
 *
 * @OriginalAuthor Ferdinand Calo' (FEX___96)
 */
public class TurboList extends ModelBase {

    public String boxname;
    public TurboList(String name){
        boxname=name;
    }

    public boolean add(ModelRendererTurbo t){

        t.rotateAngleY *= RailUtility.radianF;
        t.rotateAngleZ *= RailUtility.radianF;
        t.rotateAngleX *= RailUtility.radianF;
        super.addPart(t);
        return true;
    }

}
