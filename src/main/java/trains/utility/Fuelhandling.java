package trains.utility;



import java.util.List;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

public class Fuelhandling implements IFuelHandler{
	
	public static int burnTime;
	
	public static Item[] itemArray;
	public static List<Item> items = Arrays.asList(itemArray);
	
	
	public Fuelhandling(ItemStack fuel) {
		// TODO Auto-generated constructor stub
		Fuelhandling.burnTime = getBurnTime(fuel);
		
		
		
		if(burnTime > 0){
			
			items.add(fuel.getItem());
			
		}
		
		
		
	}
	
	
	@Override
	public int getBurnTime(ItemStack fuel) {
		// TODO Auto-generated method stub
		return 0;
	}

} 
