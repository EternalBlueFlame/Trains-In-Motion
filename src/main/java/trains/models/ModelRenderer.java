package trains.models;


import net.minecraft.client.model.ModelBase;
import trains.models.tmt.ModelRendererTurbo;


/**
 * <h2>Cubik pro model render</h2>
 * A middleman class to convert Cubik pro models to something tmt can actually render properly.
 * Currently doesn't work.
 */
public class ModelRenderer extends ModelRendererTurbo {

    private float width=0;
    private float height=0;
    private float depth=0;
    public ModelRenderer(ModelBase p_i1172_1_, String p_i1172_2_) {
        super(p_i1172_1_, p_i1172_2_);
    }

    public ModelRenderer(ModelBase p_i1174_1_, int p_i1174_2_, int p_i1174_3_) {
        this(p_i1174_1_, "void");
        this.setTextureOffset(p_i1174_2_,p_i1174_3_);
    }

    public ModelRenderer(ModelBase p_i1174_1_, int p_i1174_2_, int p_i1174_3_, String name) {
        this(p_i1174_1_, name);
        this.setTextureOffset(p_i1174_2_,p_i1174_3_);
    }

    @Override
    public ModelRendererTurbo addBox(float p_78789_1_, float p_78789_2_, float p_78789_3_, float p_78789_4_, float p_78789_5_, float p_78789_6_) {
        width = (-(p_78789_4_)*0.5f);
        height = (-(p_78789_5_ )*0.5f);
        depth = (-(p_78789_6_)*0.5f);
        super.addBox(width,height,depth,p_78789_4_,p_78789_5_,p_78789_6_);
        return this;
    }

    @Override
    public ModelRendererTurbo addBox(float p_78789_1_, float p_78789_2_, float p_78789_3_, int p_78789_4_, int p_78789_5_, int p_78789_6_) {
        addBox(width,height,depth,(float) p_78789_4_,(float) p_78789_5_,(float) p_78789_6_);
        return this;
    }

    @Override
    public void setRotationPoint(float p_78793_1_, float p_78793_2_, float p_78793_3_) {
            this.rotationPointX = p_78793_1_+ Math.copySign(width,1);
            this.rotationPointY = p_78793_2_+Math.copySign(height,1);
            this.rotationPointZ = p_78793_3_+Math.copySign(depth,1);
    }

}
