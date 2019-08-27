package ebf;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import ebf.tim.utility.DebugUtil;
import ebf.tim.utility.RailUtility;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

//Cant cover Object. it's raw data isn't safe over networking due to runtime compiling differences on client and server.
//todo: rename to NBTEnhanced
public class XmlBuilder {
    public HashMap<String, XmlBuilder> xmlMap = new HashMap<>();
    public HashMap<String, Integer> intMap = new HashMap<>();
    public HashMap<String, Float> floatMap = new HashMap<>();
    public HashMap<String, String> stringMap = new HashMap<>();
    public HashMap<String, String[]> itemMap = new HashMap<>();

    /*
     *----------Constructor Section----------
     */
    public XmlBuilder(String str){
        super();
        parseXMLString(str);
    }
    public XmlBuilder(){}
    /*
     *----------put Section----------
     */
    public XmlBuilder putString(String id, String value){
        stringMap.put(id,value);
        return this;
    }
    public XmlBuilder putItemStack(String id, ItemStack stack){
        itemMap.put(id,
                stack==null?new String[]{"null"}:
                        new String[]{
                                stack.getItem().delegate.name(),
                        stack.stackSize+"",stack.getItemDamage()+""});
        return this;
    }
    public XmlBuilder putInt(String id, int value){
        intMap.put(id,value);
        return this;
    }
    public XmlBuilder putFloat(String id, float value){
        floatMap.put(id,value);
        return this;
    }
    public XmlBuilder putXml(String id, XmlBuilder value){
        xmlMap.put(id,value);
        return this;
    }

    public XmlBuilder removeString(String s){
        stringMap.remove(s);
        return this;
    }

    public XmlBuilder removeInt(String s){
        intMap.remove(s);
        return this;
    }

    public XmlBuilder removeFloat(String s){
        floatMap.remove(s);
        return this;
    }

    public XmlBuilder removeXml(String s){
        xmlMap.remove(s);
        return this;
    }

    public XmlBuilder removeItemStack(String s){
        itemMap.remove(s);
        return this;
    }

    /*
     *----------get Section----------
     */

    public String getString(String id){
        return stringMap.get(id);
    }
    public int getInt(String id){
        return intMap.get(id);
    }
    public float getfloat(String id){
        return floatMap.get(id);
    }
    public XmlBuilder getXml(String id){
        return xmlMap.get(id);
    }

    public ItemStack getItemStack(String id){
        if(itemMap.get(id) == null || itemMap.get(id)[0].equals("null")){return null;}
        Item i = GameData.getItemRegistry().get(itemMap.get(id)[0]);
        ItemStack s;
        if (i==null){
            Block b = GameData.getBlockRegistry().get(itemMap.get(id)[0]);
            if(b!=null) {
                s = new ItemStack(b, Integer.parseInt(itemMap.get(id)[1]));
            } else{
                return null;
            }
        } else {
            s = new ItemStack(i,Integer.parseInt(itemMap.get(id)[1]));
        }
        s.setItemDamage(Integer.parseInt(itemMap.get(id)[2]));
        return s;
    }

    public boolean containsString(String id){return stringMap.containsKey(id);}
    public boolean containsInt(String id){return intMap.containsKey(id);}
    public boolean containsFloat(String id){return floatMap.containsKey(id);}
    public boolean containsXml(String id){return xmlMap.containsKey(id);}

    /*
     *----------Internal Method Section----------
     */


    //todo
	/*
	NBTTagCompound convertToNBT(){
	tag =new NBTTagCompound
	for (String s: stringMap.keys){tag.putString(s, stringMap.get(s));}
	for (String s: intMap.keys){tag.putInteger(s, stringMap.get(s));}
	for xml : tag.putNBT(s, xml.convertToNbt);
	...
	return tag;
	}
	 */

	/*
	@Depreciated //it is advised to do this manually, the guesswork required for this method is unreasonable overhead.
	xmlBuilder convertFromNBT(NBT tag){
	for(String key : tag.getKeys()){
	try{String s= tag.getString(key); if (s!null){StringMap.put(tag,s);}
	try{Integer s= tag.getInteger(key); if (s!null){intMap.put(tag,s);}
	}
	}

	 */

    public String toXMLString(){
        StringBuilder data = new StringBuilder();
        for(String key : intMap.keySet()){
            tag(key, data, "int");
            data.append(intMap.get(key));
            tag(key, data);
        }
        for(String key : floatMap.keySet()){
            tag(key, data, "float");
            data.append(floatMap.get(key));
            tag(key, data);
        }
        for(String key : stringMap.keySet()){
            tag(key, data, "string");
            data.append(stringMap.get(key));
            tag(key, data);
        }
        for(String key : xmlMap.keySet()){
            tag(key, data, "xml");
            data.append(xmlMap.get(key).toXMLString());
            tag(key, data);
        }
        for(String key : itemMap.keySet()){
            tag(key, data, "item");
            data.append(itemMap.get(key)[0]);
            data.append(",");
            data.append(itemMap.get(key)[1]);
            data.append(",");
            data.append(itemMap.get(key)[2]);

            tag(key, data);
        }

        return data.toString();
    }
    private void parseXMLString(String from){
        if(!from.contains("<")){return;}
        String tag= from.substring(from.indexOf("<")+1, from.indexOf("type")-1);
        String parse=from;
        while (tag !=null){
            switch (getType(parse.substring(0,parse.indexOf(">")))) {//parse the beginning tag for the data type
                case 0:{this.xmlMap.put(tag, new XmlBuilder(tagSubstring(parse, tag)));break;}
                case 1:{this.stringMap.put(tag, tagSubstring(parse, tag));break;}
                case 2:{this.intMap.put(tag, Integer.parseInt(tagSubstring(parse, tag)));break;}
                //case 3:{this.setBoolean(tag, Boolean.parseBoolean(tagSubstring(parse, tag)));break;}
                case 4:{this.floatMap.put(tag, Float.parseFloat(tagSubstring(parse, tag)));break;}
                //case 5:{this.setDouble(tag, Double.parseDouble(tagSubstring(parse, tag)));break;}
                //case 6:{this.setLong(tag, Long.parseLong(tagSubstring(parse, tag)));break;}
                //case 7:{this.setShort(tag, Short.parseShort(tagSubstring(parse, tag)));break;}
                //case 8:{this.setByte(tag, Byte.parseByte(tagSubstring(parse, tag)));break;}
                //case 9:{this.setByteArray(tag, parseByteArray(tagSubstring(parse, tag)));break;}
                //case 10:{this.setIntArray(tag, parseIntArray(tagSubstring(parse, tag)));break;}
                case 11:{this.itemMap.put(tag, tagSubstring(parse, tag).split(","));break;}
            }
            parse=parse.substring(parse.indexOf("</"+tag)+tag.length()+3);//skip string buffer to end of tag
            if(parse.contains("<")) {
                tag = parse.substring(parse.indexOf("<") + 1, parse.indexOf("type")-1);
            } else {
                tag=null;
            }
        }
    }
    private byte[] parseByteArray(String s){
        String[] values = s.split(",");
        byte[] array = new byte[s.length()];
        for(int i=0; i<s.length(); i++){
            array[i] = Byte.parseByte(values[i]);
        }
        return array;
    }
    private int[] parseIntArray(String s){
        String[] values = s.split(",");
        int[] array = new int[s.length()];
        for(int i=0; i<s.length(); i++){
            array[i] = Integer.parseInt(values[i]);
        }
        return array;
    }

    //shorthand to simplify the other code
    private static String tagSubstring(String parse, String tag){
        return parse.substring(parse.indexOf(">")+1, parse.indexOf("</"+tag));
    }

    private int getType(String s){
        return RailUtility.stringContains(s, "xml")?0:
                RailUtility.stringContains(s,"string")?1:
                        RailUtility.stringContains(s,"int")?2:
                                RailUtility.stringContains(s,"bool")?3:
                                        RailUtility.stringContains(s,"float")?4:
                                                RailUtility.stringContains(s,"item")?11:
                                                -1;
    }

    private static void tag(String id, StringBuilder builder, String type){
        builder.append("<");
        builder.append(id);
        builder.append(" type=");
        builder.append(type);
        builder.append(">");
    }

    private static void tag(String id, StringBuilder builder){
        builder.append("</");
        builder.append(id);
        builder.append(">");
    }

}
