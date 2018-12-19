package fexcraft.fcl.common.math;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class RGB {
	
	public int packed = 0x00000;
	public float alpha = 1f;
	
	public static final RGB RED   = new RGB(255,   0,   0);
	public static final RGB GREEN = new RGB(  0, 255,   0);
	public static final RGB BLUE  = new RGB(  0,   0, 255);
	public static final RGB BLACK = new RGB(  0,   0,   0);
	public static final RGB WHITE = new RGB(255, 255, 255);
	
	public RGB(){
		packed = WHITE.packed;
		alpha = WHITE.alpha;
	}
	
	public RGB(RGB rgb){
		this.packed = rgb.packed;
		this.alpha = rgb.alpha;
	}
	
	public RGB(int color){
		packed = color;
	}
	
	public RGB(byte r, byte g, byte b){
		packed = (65536 * (r + 128)) + (256 * (g + 128)) + (b + 128);
	}
	
	public RGB(byte i, byte j, byte k, float f){
		this(i, j, k);
		alpha = f;
	}
	
	public RGB(int r, int g, int b){
		r = r > 255 ? 255 : r < 0 ? 0 : r;
		g = g > 255 ? 255 : g < 0 ? 0 : g;
		b = b > 255 ? 255 : b < 0 ? 0 : b;
		packed = (65536 * r) + (256 * g) + b;
	}
	
	public RGB(int r, int g, int b, float a){
		this(r, g, b);
		alpha = a;
	}
	
	public RGB(byte[] i){
		this(i.length >= 1 ? i[0] : 0, i.length >= 2 ? i[1] : 0, i.length >= 3 ? i[2] : 0);
		if(i.length >= 4){ alpha = i[3] / 255; }
	}
	
	public RGB(int[] i){
		this(i.length >= 1 ? i[0] : 0, i.length >= 2 ? i[1] : 0, i.length >= 3 ? i[2] : 0);
		if(i.length >= 4){ alpha = i[3] / 255; }
	}
	
	public static RGB fromStrings(String x, String y, String z){
		try{
			int r = Integer.parseInt(x);
			int g = Integer.parseInt(y);
			int b = Integer.parseInt(z);
			return new RGB(r, g, b);
		}
		catch(Exception e){
			e.printStackTrace();
			return new RGB(WHITE);
		}
	}
	
	public static RGB fromStrings(String[] s){
		switch(s.length){
			case 0: return new RGB(WHITE);
			case 1: return fromStrings(s[0], "255", "255");
			case 2: return fromStrings(s[0], s[1], "255");
			case 3:
			default:{
				return fromStrings(s[0], s[1], s[2]);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void glColorApply(){
		org.lwjgl.opengl.GL11.glColor4f((packed >> 16 & 255) / 255.0F, (packed >> 8 & 255) / 255.0F, (packed & 255) / 255.0F, alpha);
	}
	
	@SideOnly(Side.CLIENT)
	public final static void glColorReset(){
		org.lwjgl.opengl.GL11.glColor4b(Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE);
	}
	
	public static final RGB fromDyeColor(int e){
		return new RGB(get(e));
	}
	
	private static final int get(int e){
		switch(e){
			case 15: return 16383998;
			case 14: return 16351261;
			case 13: return 13061821;
			case 12: return 3847130;
			case 11: return 16701501;
			case 10: return 8439583;
			case 9: return 15961002;
			case 8: return 4673362;
			case 7: return 10329495;
			case 6: return 1481884;
			case 5: return 8991416;
			case 4: return 3949738;
			case 3: return 8606770;
			case 2: return 6192150;
			case 1: return 11546150;
			case 0: default: return 1908001;
		}
	}
	
	@Override
	public final String toString(){
		return Integer.toHexString(packed);
	}
	
	public final int getColorInt(){
		return packed;
	}

	public float[] toFloatArray(){
		return new float[]{(packed >> 16 & 255) / 255.0F, (packed >> 8 & 255) / 255.0F, (packed & 255) / 255.0F, alpha};
	}

	public byte[] toByteArray(){
		return new byte[]{(byte)(((packed >> 16) & 255) - 128), (byte)(((packed >> 8) & 255) - 128), (byte)((packed & 255) - 128)};
	}
	
	/// JSON ///
	
	public RGB(JsonElement object){
		this(object, false);
	}

	public RGB(JsonElement elm, boolean write){
		if((elm.isJsonPrimitive() || !elm.isJsonObject()) && !elm.isJsonArray()){
			try{
				packed = Integer.parseInt(elm.getAsString(), 16);
			}
			catch(Exception e){
				e.printStackTrace();
				packed = Integer.parseInt(elm.getAsString(), 10);
			}
			return;
		}
		JsonObject object = elm.getAsJsonObject();
		String[] red = {"Red", "red", "r", "R"};
		String[] blue = {"Blue", "blue", "b", "B"};
		String[] green = {"Green", "green", "g", "G"};
		byte r = getFJO(red, object, write, packed);
		byte b = getFJO(blue, object, write, packed);
		byte g = getFJO(green, object, write, packed);
		packed = new RGB(r, g, b).packed;
	}

	private static final byte getFJO(String[] strings, JsonObject obj, boolean write, int packed){
		for(String s : strings){
			if(obj.has(s)){
				int j = obj.get(s).getAsInt() - 128;
				return (byte)(j > 127 ? 127 : j < -128 ? -128 : j);
			}
		}
		if(write){
			obj.addProperty("RGB", packed);
		}
		return 0;
	}
	
	/// OTHER ///
	
	private static final DecimalFormat df = new DecimalFormat("##.#####");
	static { df.setRoundingMode(RoundingMode.DOWN); }
	
	public static String format(double d){
		return df.format(d);
	}
	
}