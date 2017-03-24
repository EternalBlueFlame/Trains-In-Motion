package trains.models;


import net.minecraft.client.model.ModelBase;
import trains.models.tmt.ModelRendererTurbo;


/**
 * <h2>Techne model render</h2>
 * A middleman class to convert Techns models to something tmt can actually render properly.
 * Theoretically this should work with any models that use the setRotationPoint instead of defining position with addBox.
 */
public class TechneModelRenderer extends ModelRendererTurbo {

    public TechneModelRenderer(ModelBase p_i1172_1_, String p_i1172_2_) {
        super(p_i1172_1_, p_i1172_2_);
        this.setTextureSize(512,512);
    }

    public TechneModelRenderer(ModelBase p_i1174_1_, int p_i1174_2_, int p_i1174_3_) {
        this(p_i1174_1_, "void");
        this.setTextureOffset(p_i1174_2_,p_i1174_3_);
    }

    public TechneModelRenderer(ModelBase p_i1174_1_, int p_i1174_2_, int p_i1174_3_, String name) {
        this(p_i1174_1_, name);
        this.setTextureOffset(p_i1174_2_,p_i1174_3_);
    }

    /**
     * <h2>set Rotation point</h2>
     * this set's the rotation point with the offsets defined in
     * @see #addBox(float, float, float, float, float, float)
     */
    @Override
    public void setRotationPoint(float pointX, float pointY, float pointZ) {
            this.rotationPointX = pointX;
            this.rotationPointY = pointY-15;
            this.rotationPointZ = pointZ;
    }

}
