<b>
Modified TMT from https://github.com/Fexcraft/FCL
Original branch version tbis was copied from:
</b><hr/>
<h3>Primary license:</h3>
This version of TMT was updated, maintened, as well as extended for 1.8 and newer Minecraft versions by FEX___96

 @author GaryCXJk, Ferdinand (FEX___96)
 @license http://fexcraft.net/license?id=tmt

<hr/>
 <h3>Eternal's notes and license:</h3>

 This version of TMT is <b>heavily</b> modified to fix some bugs in my own way, and attempt to improve performance overall.
 - for performance reasons, the legacy render mode is used by default since we don't expect multiple textures to be used across the same model.
 - The bones system has been fully removed and replaced with some minor support functionality for my static model animator.
 - For performance and typesafe reasons PositionTransformVertex has been fully reworked to no longer extend or require the variables from PositionTextureVertex.
 - For performance reasons TexturedPolygon has been fully reworked to no longer require TexturedQuad.
 - Because of the reworked classes mentioned above, Tessellator has been reworked so it uses floats rather than doubles for performance reasons.
 - public void copyTo(PositionTextureVertex[] verts, TexturedQuad[] quad) was removed since the new reworks make it fully obsolete.

 You are free to use any of my changes to this system in any way you want with no restrictions.