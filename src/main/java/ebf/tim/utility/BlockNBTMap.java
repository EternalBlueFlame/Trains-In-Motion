package ebf.tim.utility;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

import java.io.Serializable;
import java.util.*;


public class BlockNBTMap extends WorldSavedData {
	//HashMap is retarded, strings and arrays dont hash right but lists are reliable for some reason.
	public HashMap<List<Integer>,HashMap<List<Integer>, NBTTagCompound>> data = new HashMap<>();
	private HashMap<List<Integer>,NBTTagCompound> shorthand = new HashMap<List<Integer>,NBTTagCompound>();

	public BlockNBTMap(String id){
		super(id);
	}

	public void add(int x, int y, int z, NBTTagCompound tag){
		//DebugUtil.printStackTrace();
		HashMap<List<Integer>, NBTTagCompound> oldTag = data.get(chunkCoord(x,z));
		if(oldTag==null) {
			oldTag = new HashMap<List<Integer>,NBTTagCompound>();
		}
		oldTag.put(vector(x,y,z), tag);
		data.put(chunkCoord(x,z), oldTag);
		markDirty();
	}

	public void remove(int x, int y, int z){
		if(data.containsKey(chunkCoord(x,z))) {
			data.get(chunkCoord(x,z)).remove(vector(x, y, z));
		}
		if(data.containsKey(chunkCoord(x,z)) && data.get(chunkCoord(x,z)).size()==0){
			data.remove(chunkCoord(x,z));
		}
		markDirty();
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
		CommonProxy.clientList= new BlockNBTMap(this.mapName);
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

				chunkData.put(v, d.getCompoundTag("blockData"));

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
		NBTTagCompound chunkTag, innerTag;
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
				innerTag.setTag("blockData", data.get(chunk).get(pos));
				chunkTag.setTag("data"+ii, innerTag);
				ii++;
			}

			p_76187_1_.setTag("chunk"+i, chunkTag);
			i++;
		}


	}


	/*
	public void readFromFile(String data){

		String[] values = data.split(";;;"), current;

		for(String v : values){
			current=v.split(":::");
			List<Integer> chunkPos= new List<Integer>(Integer.parseInt(current[0]), Integer.parseInt(current[1]));
			Map<List<Integer>, NBTTagCompound> dat =
		}

	}


	public String writeToFile(){
		StringBuilder sb = new StringBuilder();
		for(List<Integer> key : this.keySet()){
			sb.append(key.x);
			sb.append(":::");
			sb.append(key.z);
			sb.append(":::");
			for(List<Integer> pos : this.get(key).keySet()){
				sb.append(pos.x);
				sb.append(",");
				sb.append(pos.y);
				sb.append(",");
				sb.append(pos.z);
				sb.append(",");
				this.get(key).get(pos).toString();
			}
			sb.append(";;;");
		}
		return sb.toString();
	}*/

}
