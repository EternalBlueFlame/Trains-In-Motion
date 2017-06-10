package ebf.tim.models.tmt;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class TextureGroup {
	public TextureGroup() {
		poly = new ArrayList<TexturedPolygon>();
		texture = "";
	}
	
	public void addPoly(TexturedPolygon quad)
	{
		poly.add(quad);
	}

	public void loadTexture() {
		if(!texture.equals("")) {
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("", texture));
		}
	}
	
	public ArrayList<TexturedPolygon> poly;
	public String texture;
}
