package fexcraft.fvtm.model;

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

}
