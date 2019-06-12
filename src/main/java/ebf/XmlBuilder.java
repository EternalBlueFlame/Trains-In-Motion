package ebf;

//TODO: could be converted to Json quite easily, additionally Json may be more efficient,
// however there will be no reliable ability to store tags in tags with the json format.

//TODO: cover support for having more than one tag with the same name by requesting an index then doing,
// for(i< length){substring=data.substring(tag)}; getCLASS(tag,substring)

//TODO: cover more data formats. Cant cover Object.
// it's raw data isn't safe over networking due to runtime compiling differences on client and server.
public class XmlBuilder {
    private StringBuilder data = new StringBuilder();

    /*
     *----------Constructor Section----------
     */
    public XmlBuilder(String str){
        data.append(str);
    }
    public XmlBuilder(){}


    /*
     *----------Put Section----------
     */
    public XmlBuilder putString(String id, String str){
        tag(id, data, true);
        data.append(str);
        tag(id, data, false);
        return this;
    }

    public XmlBuilder putInt(String id, int num){
        tag(id, data, true);
        data.append(num);
        tag(id, data, false);
        return this;
    }

    /*
    *----------Get Section----------
     */

    private String parse, t;
    private int substringIndex;
    public String getString(String id){
        parse=data.toString();
        t=tag(id,true);
        if(parse.contains(t)) {
            substringIndex= parse.indexOf(t);
            return parse.substring(
                    substringIndex + t.length(),
                    parse.substring(substringIndex).indexOf(tag(id,false))
            );
        } else {
            return "";
        }
    }

    public int getInteger(String id){
        parse=data.toString();
        t=tag(id,true);
        if(parse.contains(t)) {
            substringIndex= parse.indexOf(t);
            return Integer.parseInt(
                    parse.substring(substringIndex + t.length(),
                    parse.substring(substringIndex).indexOf(tag(id,false))
                    )
            );
        } else {
            return 0;
        }
    }


    /*
     *----------Internal Method Section----------
     */


    @Override
    public String toString(){
        return data.toString();
    }

    private static void tag(String id, StringBuilder builder, boolean isStart){
        builder.append(isStart?"<":"</");
        builder.append(id);
        builder.append(isStart?">":">\n");
    }

    private static String tag(String id, boolean isStart){
        return (isStart?"<":"</") + id +">";
    }

}
