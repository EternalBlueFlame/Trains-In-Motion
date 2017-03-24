package trains.models.tmt;

import cpw.mods.fml.common.Loader;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ModelPool {
	public static ModelPoolEntry addFile(String file, Class modelClass, Map<String, TransformGroup> group, Map<String, TextureGroup> textureGroup) {
		if(modelMap.containsKey(file)) {
			ModelPoolEntry entry = modelMap.get(file);
			entry.applyGroups(group, textureGroup);
			return entry;
		}
		ModelPoolEntry entry = null;
		try {
			entry = (ModelPoolEntry)modelClass.newInstance();
		}
		catch(Exception e) {
			System.out.println("A new model class could not be initialized.");
			System.out.println(e.getMessage());
			return null;
		}
		File modelFile = null;
		for(int i = 0; i < resourceDir.length && (modelFile == null || !modelFile.exists()); i++) {
			String absPath = new File(Loader.instance().getConfigDir().getParent(), resourceDir[i]).getAbsolutePath();
			if(!absPath.endsWith("/") || !absPath.endsWith("\\"))
				absPath+= "/";
			modelFile = entry.checkValidPath(absPath + file);
		}
		if(modelFile == null || !modelFile.exists()) {
			System.out.println("The model with the name " + file + " does not exist.");
			return null;
		}
		entry.groups = new HashMap<String, TransformGroupBone>();
		entry.textures = new HashMap<String, TextureGroup>();
		entry.name = file;
		entry.setGroup("0");
		entry.setTextureGroup("0");
		entry.getModel(modelFile);
		entry.applyGroups(group, textureGroup);
		modelMap.put(file, entry);
		return entry;
	}
	
    public static ModelPoolEntry addFileF(String file, Class modelClass, Map<String, TransformGroup> group, Map<String, TextureGroup> textureGroup) throws IOException{
		if(modelMap.containsKey(file)){
			ModelPoolEntry entry = modelMap.get(file);
			entry.applyGroups(group, textureGroup);
			return entry;
		}
		ModelPoolEntry entry = null;
		try{
			entry = (ModelPoolEntry)modelClass.newInstance();
		}
		catch(Exception e){
			System.out.println("A new model class could not be initialized.");
			System.out.println(e.getMessage());
			return null;
		}
		InputStream in = entry.getClass().getResourceAsStream("/assets/" + file + ".obj");
		File tempfile = File.createTempFile(file, ".obj");
		FileOutputStream out = new FileOutputStream(tempfile);
		tempfile.deleteOnExit();
		IOUtils.copy(in, out);
		System.out.println("RENDER: " + tempfile.getPath());
		if(tempfile == null || !tempfile.exists()){
			System.out.println("The model with the name " + file + " does not exist.");
			return null;
		}
		entry.groups = new HashMap<String, TransformGroupBone>();
		entry.textures = new HashMap<String, TextureGroup>();
		entry.name = file;
		entry.setGroup("0");
		entry.setTextureGroup("0");
		entry.getModel(tempfile);
		entry.applyGroups(group, textureGroup);
		modelMap.put(file, entry);
		return entry;
	}
	
    private static Map<String, ModelPoolEntry> modelMap = new HashMap<String, ModelPoolEntry>();
    private static String[] resourceDir = new String[] {
    					"minecraft/resources/models/",
    					"minecraft/resources/mod/models/"
    	};
    public static final Class OBJ = ModelPoolObjEntry.class;
}
