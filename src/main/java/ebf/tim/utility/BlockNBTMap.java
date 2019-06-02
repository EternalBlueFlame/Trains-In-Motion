package ebf.tim.utility;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

import java.io.Serializable;
import java.util.*;


public class BlockNBTMap extends WorldSavedData {
	public HashMap<Vec2i,HashMap<Vec3i, NBTTagCompound>> data = new HashMap<>();
	private HashMap<Vec3i,NBTTagCompound> shorthand = new HashMap<Vec3i,NBTTagCompound>();

	public BlockNBTMap(String id){
		super(id);
	}

	public void add(int x, int y, int z, NBTTagCompound tag){
		HashMap<Vec3i, NBTTagCompound> oldTag = data.get(new Vec2i(x>>4,z>>4));
		if(oldTag==null) {
			oldTag = new HashMap<Vec3i,NBTTagCompound>();
		}
		oldTag.put(vector(x,y,z), tag);
		data.put(new Vec2i(x>>4,z>>4), oldTag);
		markDirty();
	}


	public NBTTagCompound get(int x, int y, int z){
		if(shorthand.containsKey(vector(x,y,z))) {
			return shorthand.get(vector(x, y, z));
		} else {
			return updateTree(vector(x, y, z));
		}
	}

	public NBTTagCompound updateTree(Vec3i pos){
		if(data.get(new Vec2i(pos.x>>4,pos.z>>4)) ==null){
			return null;
		}
		if(shorthand.size()>=100){
			shorthand.remove(shorthand.keySet().iterator().next());
		}
		return shorthand.put(pos,
				data.get(new Vec2i(pos.x>>4,pos.z>>4)).get(pos));
	}

	private static Vec3i vector(int x, int y, int z){
		return new Vec3i(x,y,z);
	}

	private int coordX,coordZ;
	//distance should be half the client's view distance, rounded up.
	public BlockNBTMap clientChunks(int distance, int x, int z){
		CommonProxy.clientList= new BlockNBTMap(this.mapName);
		for (coordX=-distance; coordX<distance;coordX++){
			for(coordZ=-distance; coordZ<distance;coordZ++){
				if(data.containsKey(new Vec2i((x>>4)+coordX,(z>>4)+coordZ))) {
					CommonProxy.clientList.data.put(new Vec2i((x >> 4) + coordX, (z >> 4) + coordZ),
							data.get(new Vec2i((x >> 4) + coordX, (z >> 4) + coordZ)));
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
			Vec2i chunk = new Vec2i(c.getInteger("chunkX"),c.getInteger("chunkZ"));
			HashMap<Vec3i, NBTTagCompound> chunkData = new HashMap<>();
			while(c.hasKey("data"+ii)){
				NBTTagCompound d = p_76184_1_.getCompoundTag("data"+ii);

				Vec3i v = new Vec3i(d.getInteger("posX"), d.getInteger("posY") ,d.getInteger("posZ"));

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
		for(Vec2i chunk : data.keySet()){
			ii=0;
			chunkTag=new NBTTagCompound();
			chunkTag.setInteger("chunkX", chunk.x);
			chunkTag.setInteger("chunkZ", chunk.z);

			for(Vec3i pos : data.get(chunk).keySet()){
				innerTag=new NBTTagCompound();
				innerTag.setInteger("posX", pos.x);
				innerTag.setInteger("posY", pos.y);
				innerTag.setInteger("posZ", pos.z);
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
			Vec2i chunkPos= new Vec2i(Integer.parseInt(current[0]), Integer.parseInt(current[1]));
			Map<Vec3i, NBTTagCompound> dat =
		}

	}


	public String writeToFile(){
		StringBuilder sb = new StringBuilder();
		for(Vec2i key : this.keySet()){
			sb.append(key.x);
			sb.append(":::");
			sb.append(key.z);
			sb.append(":::");
			for(Vec3i pos : this.get(key).keySet()){
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
