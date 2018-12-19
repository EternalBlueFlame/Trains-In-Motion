package fexcraft.fcl.tmt;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ModelPool {
	
    private static Map<String, ModelPoolEntry> modelMap = new HashMap<String, ModelPoolEntry>();
    public static final Class<ModelPoolObjEntry> OBJ = ModelPoolObjEntry.class;
	
	public static ModelPoolEntry addLocation(String string, InputStream stream, Class<?> modelClass){
		ModelPoolEntry entry = null;
		if(modelMap.containsKey(string)){
			return entry = modelMap.get(string);
		}
		try{
			entry = (ModelPoolEntry)modelClass.newInstance();
		}
		catch(Exception e){
			System.out.println("A new " + entry.getClass().getName() + " could not be initialized.");
			System.out.println(e.getMessage());
			return null;
		}
		entry.name = string; entry.getModel(stream); modelMap.put(string, entry);
		return entry;
	}
    
}
