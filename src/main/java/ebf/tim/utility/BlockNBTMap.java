package ebf.tim.utility;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import java.io.Serializable;
import java.util.*;


public class BlockNBTMap extends WorldSavedData {
	//HashMap is retarded, strings and arrays dont hash right but lists are reliable for some reason.
	public HashMap<List<Integer>,HashMap<List<Integer>, NBTTagCompound>> data = new HashMap<>();
	private HashMap<List<Integer>,NBTTagCompound> shorthand = new HashMap<List<Integer>,NBTTagCompound>();

	public BlockNBTMap(){
		super("railMap");
	}

	public void add(int x, int y, int z, NBTTagCompound tag, World worldObj){
		//DebugUtil.printStackTrace();
		HashMap<List<Integer>, NBTTagCompound> oldTag = data.get(chunkCoord(x,z));
		if(oldTag==null) {
			oldTag = new HashMap<List<Integer>,NBTTagCompound>();
		}
		oldTag.put(vector(x,y,z), tag);
		data.put(chunkCoord(x,z), oldTag);
		markDirty();
		worldObj.perWorldStorage.setData("railMap", this);
	}

	public void remove(int x, int y, int z, World worldObj){
		if(data.containsKey(chunkCoord(x,z))) {
			data.get(chunkCoord(x,z)).remove(vector(x, y, z));
		}
		if(data.containsKey(chunkCoord(x,z)) && data.get(chunkCoord(x,z)).size()==0){
			data.remove(chunkCoord(x,z));
		}
		markDirty();
		worldObj.perWorldStorage.setData("railMap", this);
	}


	public NBTTagCompound get(int x, int y, int z){
		if(shorthand.containsKey(vector(x,y,z))) {
			return shorthand.get(vector(x, y, z));
		} else {
			return updateTree(vector(x, y, z));
		}
	}

	public NBTTagCompound updateTree(List<Integer> pos){
		if(data.get(chunkCoord(pos.get(0),pos.get(2))) ==null){
			return null;
		}
		if(shorthand.size()>=100){
			shorthand.remove(shorthand.keySet().iterator().next());
		}
		return shorthand.put(pos,
				data.get(chunkCoord(pos.get(0),pos.get(2))).get(pos));
	}

	private static List<Integer> vector(int x, int y, int z){
		return Arrays.asList(x,y,z);
	}
	private static List<Integer> chunkCoord(int x, int z){return Arrays.asList(x>>4,z>>4);}

	private int coordX,coordZ;
	//distance should be half the client's view distance, rounded up.
	public BlockNBTMap clientChunks(int distance, int x, int z){
		CommonProxy.clientList= new BlockNBTMap();
		for (coordX=-distance; coordX<distance;coordX++){
			for(coordZ=-distance; coordZ<distance;coordZ++){
				if(data.containsKey(Arrays.asList((x>>4)+coordX,(z>>4)+coordZ))) {
					CommonProxy.clientList.data.put(Arrays.asList((x >> 4) + coordX, (z >> 4) + coordZ),
							data.get(Arrays.asList((x >> 4) + coordX, (z >> 4) + coordZ)));
				}
			}
		}
	return CommonProxy.clientList;
	}

	/**
	 * reads in data from the NBTTagCompound into this MapDataBase
	 *
	 * @param p_76184_1_
	 */
	@Override
	public void readFromNBT(NBTTagCompound p_76184_1_) {
		if(p_76184_1_==null){return;}
		data= new HashMap<>();
		int i=0, ii;
		while(p_76184_1_.hasKey("chunk"+i)){
			ii=0;
			NBTTagCompound c = p_76184_1_.getCompoundTag("chunk"+i);
			List<Integer> chunk = Arrays.asList(c.getInteger("chunkX"),c.getInteger("chunkZ"));
			HashMap<List<Integer>, NBTTagCompound> chunkData = new HashMap<>();
			while(c.hasKey("data"+ii)){
				NBTTagCompound d = p_76184_1_.getCompoundTag("data"+ii);

				List<Integer> v = Arrays.asList(d.getInteger("posX"), d.getInteger("posY") ,d.getInteger("posZ"));
				chunkData.put(v, d.getCompoundTag("data"));

				ii++;
			}

			data.put(chunk, chunkData);
			i++;
		}
	}

	/**
	 * write data to NBTTagCompound from this MapDataBase, similar to Entities and TileEntities
	 *
	 * @param p_76187_1_
	 */
	@Override
	public void writeToNBT(NBTTagCompound p_76187_1_) {
		int i=0,ii;
		NBTTagCompound chunkTag, innerTag, tempTag;
		for(List<Integer> chunk : data.keySet()){
			ii=0;
			chunkTag=new NBTTagCompound();
			chunkTag.setInteger("chunkX", chunk.get(0));
			chunkTag.setInteger("chunkZ", chunk.get(1));

			for(List<Integer> pos : data.get(chunk).keySet()){
				innerTag=new NBTTagCompound();
				innerTag.setInteger("posX", pos.get(0));
				innerTag.setInteger("posY", pos.get(1));
				innerTag.setInteger("posZ", pos.get(2));
				innerTag.setTag("data", data.get(chunk).get(pos));
				chunkTag.setTag("data"+ii, innerTag);
				ii++;
			}

			p_76187_1_.setTag("chunk"+i, chunkTag);
			i++;
		}


	}



	public void readFromString(String data){
		String[] values = RailUtility.decompressString(data).split(";;;"), current,blocks, blockTag;

		DebugUtil.println(data);
		for(String v : values){
			if(v.equals("")){continue;}
			current=v.split(":::");
			List<Integer> chunkPos= Arrays.asList(Integer.parseInt(current[0]), Integer.parseInt(current[1]));

			HashMap<List<Integer>, NBTTagCompound> map = new HashMap<>();
			NBTTagCompound chunkData;
			blocks=current[2].split("---");
			for(String b : blocks) {
				blockTag=b.split(",");
				if(blockTag.length<4){continue;}
				chunkData = new NBTTagCompound();
				chunkData.setString("route",blockTag[3]);

				map.put(Arrays.asList(Integer.parseInt(blockTag[0]),Integer.parseInt(blockTag[1]),Integer.parseInt(blockTag[2])), chunkData);
			}
			this.data.put(chunkPos, map);
		}

	}


	public String writeToString(){
		StringBuilder sb = new StringBuilder();
		for(List<Integer> key : this.data.keySet()){
			sb.append(key.get(0));
			sb.append(":::");
			sb.append(key.get(1));
			sb.append(":::");
			for(List<Integer> pos : data.get(key).keySet()){
				sb.append(pos.get(0));
				sb.append(",");
				sb.append(pos.get(1));
				sb.append(",");
				sb.append(pos.get(2));
				sb.append(",");
				sb.append(data.get(key).get(pos).getString("route"));
				sb.append(",");
				sb.append("---");
			}
			sb.append(";;;");
		}
		return RailUtility.compressString(sb.toString());
	}

}
