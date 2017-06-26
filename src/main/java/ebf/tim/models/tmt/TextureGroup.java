package ebf.tim.models.tmt;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class TextureGroup {
	public TextureGroup() {}
	
	public void addPoly(TexturedPolygon quad)
	{
		poly.add(quad);
	}
	public ArrayList<TexturedPolygon> poly = new ArrayList<TexturedPolygon>();
}
