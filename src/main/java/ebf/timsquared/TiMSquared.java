package ebf.timsquared;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import ebf.tim.TrainsInMotion;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.items.ItemTransport;
import ebf.tim.items.TiMTab;
import ebf.tim.registry.TiMGenericRegistry;
import ebf.timsquared.entities.rollingstock.EntityGTAX13000GallonTanker;
import ebf.timsquared.entities.rollingstock.EntityPullmansPalace;
import ebf.timsquared.entities.rollingstock.EntityUP3Bay100TonHopper;
import ebf.timsquared.entities.rollingstock.EntityVATLogCar;
import ebf.timsquared.entities.trains.EntityBrigadelok080;
import ebf.timsquared.entities.trains.EntityBrigadelok080Electric;
import net.minecraft.item.Item;

@Mod(modid = TiMSquared.MODID, version = TiMSquared.MOD_VERSION, name = "TiM^2")
public class TiMSquared {
    public static final String MODID = "timsquared";
    public static final String MOD_VERSION = "0.01_pre-alpha";

    private static ItemTransport tabItem;

    public static TiMTab creativeTab;


    @Mod.Instance(MODID)
    public static TiMSquared instance;


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        creativeTab = new TiMTab("timsquared", tabItem);

        TiMGenericRegistry.registerTransports(MODID, listSteamTrains(), null);
        TiMGenericRegistry.registerTransports(MODID, listFreight(), null);
        TiMGenericRegistry.registerTransports(MODID, listPassenger(), null);
        TiMGenericRegistry.registerTransports(MODID, listTanker(), null);
        creativeTab.tabItem = tabItem= (ItemTransport) TiMGenericRegistry.RegisterItem(
                new ItemTransport(new EntityBrigadelok080(null),MODID,null)
                ,MODID, "tab.timsquared.name",null,null,null,null);

    }



    public static GenericRailTransport[] listSteamTrains() {
        return new GenericRailTransport[]{new EntityBrigadelok080(null), new EntityBrigadelok080Electric(null)};
    }

    public static GenericRailTransport[] listPassenger() {
        return new GenericRailTransport[]{new EntityPullmansPalace(null)};
    }

    public static GenericRailTransport[] listFreight() {
        return new GenericRailTransport[]{new EntityVATLogCar(null), new EntityUP3Bay100TonHopper(null)};
    }

    public static GenericRailTransport[] listTanker() {
        return new GenericRailTransport[]{new EntityGTAX13000GallonTanker(null)};
    }



}
