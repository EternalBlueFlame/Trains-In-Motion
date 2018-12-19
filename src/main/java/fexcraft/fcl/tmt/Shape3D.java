package fexcraft.fcl.tmt;


import fexcraft.fcl.common.math.TexturedPolygon;
import fexcraft.fcl.common.math.TexturedVertex;

public class Shape3D {
	
	public TexturedVertex[] vertices;
	public TexturedPolygon[] faces;
	
	public Shape3D(TexturedVertex[] verts, TexturedPolygon[] poly){
		vertices = verts; faces = poly;
	}
	
}
