package ebf.tim.utility;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.launchwrapper.Launch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Ferdinand
 * Fex's debugging util;
 */
public class DebugUtil {
	
	private static final Logger logger = LogManager.getLogger("TiM-Debug");
	
	private static boolean dev, ck;
	
	public static boolean dev(){
		if(!ck){
			ck = true;
			dev = (Boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment");
		}
		return dev;
	}
	
	/**
	 * Generic logging method;
	 * @param obj the object to be logged or string
	 */
	public static void log(Object obj){
		if(dev()){
			logger.info(String.valueOf(obj));
		}
	}
	
	//CODE BELLOW COPY/EDITED FROM FCL
	
	/**
	 * Alternative to {@link #halt()} or {@link #stop()} which will only work in a developement workspace, nice for debugging.
	 * <br>
	 * Which also prints the caller classes into console.
	 * <br>
	 * See also {@link #dev()}
	 */
	public static void exception(int i, String string, boolean halt){
		Exception ex = new Exception();
		for(int j = i; j > 0; j--){
			StackTraceElement elm = ex.getStackTrace()[j];
			log("{" + elm.getClassName() + "#"  + elm.getMethodName() + " [LINE: " + elm.getLineNumber() + "]}");
		}
		if(string != null){
			log(string);
		}
		if(dev() && halt){
			halt();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static Side side(){
		return FMLCommonHandler.instance().getSide();
	}
	
	/**
	 * Simple method to halt the current Minecraft Instance, "force close".
	 */
	public static void halt(){
		halt(1);
	}
	
	public static void halt(int errc){
		FMLCommonHandler.instance().exitJava(errc, true);
	}
	
}