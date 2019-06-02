package ebf.tim.utility;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import ebf.tim.TrainsInMotion;
import ebf.tim.entities.EntitySeat;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.networking.PacketBlockNBTMap;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

public class EventManagerServer {

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void playerQuitEvent(PlayerEvent.PlayerLoggedOutEvent event){
        if (event.player.ridingEntity instanceof GenericRailTransport || event.player.ridingEntity instanceof EntitySeat){
            event.player.dismountEntity(event.player.ridingEntity);
        }
    }


    @SubscribeEvent
    @SuppressWarnings("unused")
    public void entityStruckByLightningEvent(EntityStruckByLightningEvent event) {
        if (event.entity instanceof GenericRailTransport){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void playerEnterchunkEvent(EntityEvent.EnteringChunk event){
        if(event.entity instanceof EntityPlayerMP && event.entity.worldObj!=null && event.entity.worldObj.isRemote) {
            if(CommonProxy.getRailMap(event.entity.worldObj).clientChunks(8,event.newChunkX, event.newChunkZ)!=null) {
                TrainsInMotion.trackChannel.sendTo(new PacketBlockNBTMap(CommonProxy.getRailMap(event.entity.worldObj).clientChunks(8,event.newChunkX, event.newChunkZ)), (EntityPlayerMP) event.entity);
            }
        }
    }
}
