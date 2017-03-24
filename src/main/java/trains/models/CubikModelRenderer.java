package trains.models;


import net.minecraft.client.model.ModelBase;
import trains.models.tmt.ModelRendererTurbo;


/**
 * <h2>Cubik pro model render</h2>
 * A middleman class to convert Cubik pro models to something tmt can actually render properly.
 * Theoretically this should work with any models that use the setRotationPoint instead of defining position with addBox.
 * @author Eternal Blue Flame
 */
public class CubikModelRenderer extends ModelRendererTurbo {

    private float width=0;
    private float height=0;
    private float depth=0;
    public CubikModelRenderer(ModelBase p_i1172_1_, String p_i1172_2_) {
        super(p_i1172_1_, p_i1172_2_);
    }

    public CubikModelRenderer(ModelBase p_i1174_1_, int p_i1174_2_, int p_i1174_3_) {
        this(p_i1174_1_, "void");
        this.setTextureOffset(p_i1174_2_,p_i1174_3_);
    }

    public CubikModelRenderer(ModelBase p_i1174_1_, int p_i1174_2_, int p_i1174_3_, String name) {
        this(p_i1174_1_, name);
        this.setTextureOffset(p_i1174_2_,p_i1174_3_);
    }

    /**
     * <h2>add Box to the render</h2>
     * adds a box to the render with the defined values, we use the width, height, and depth to help define the center point used for rotation.
     */
    @Override
    public ModelRendererTurbo addBox(float unused1, float unused2, float unused3, float boxWidth, float boxHeight, float boxDepth) {
        width = (-(boxWidth)*0.5f);
        height = (-(boxHeight )*0.5f);
        depth = (-(boxDepth)*0.5f);
        super.addBox(width,height,depth,boxWidth,boxHeight,boxDepth);
        return this;
    }

    /**
     * <h2>add box integer</h2>
     * a simple redirect for integer calls to
     * @see #addBox(float, float, float, float, float, float)
     */
    @Override
    public ModelRendererTurbo addBox(float unused1, float unused2, float unused3, int boxWidth, int boxHeight, int boxDepth) {
        addBox(width,height,depth,(float) boxWidth,(float) boxHeight,(float) boxDepth);
        return this;
    }

    /**
     * <h2>set Rotation point</h2>
     * this set's the rotation point with the offsets defined in
     * @see #addBox(float, float, float, float, float, float)
     */
    @Override
    public void setRotationPoint(float pointX, float pointY, float pointZ) {
            this.rotationPointX = pointX+ Math.copySign(width,1);
            this.rotationPointY = pointY+Math.copySign(height,1);
            this.rotationPointZ = pointZ+Math.copySign(depth,1);
    }

}
