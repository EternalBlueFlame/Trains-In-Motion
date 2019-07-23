package fexcraft.fvtm;

import ebf.tim.utility.DebugUtil;
import fexcraft.fvtm.model.TurboList;
import fexcraft.tmt.slim.ModelBase;
import fexcraft.tmt.slim.ModelRendererTurbo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

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
    private Integer GLDisplaylistID=null;

    public void addToCreators(String s){
        creators.add(s);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5){
        if(GLDisplaylistID==null) {
            GLDisplaylistID = GLAllocation.generateDisplayLists(1);
            GL11.glNewList(GLDisplaylistID, GL11.GL_COMPILE);
            for (TurboList list : groups) {
                for (ModelRendererTurbo turbo : list) {
                    if(turbo!=null) {
                        turbo.render();
                    }
                }
            }
            GL11.glEndList();
        } else {
            org.lwjgl.opengl.GL11.glCallList(GLDisplaylistID);
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
