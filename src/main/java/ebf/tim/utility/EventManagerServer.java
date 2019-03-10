package ebf.tim.utility;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import ebf.tim.entities.EntitySeat;
import ebf.tim.entities.GenericRailTransport;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
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
    public void EntityStruckByLightningEvent(EntityStruckByLightningEvent event) {
        if (event.entity instanceof GenericRailTransport){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void EntityInteractEvent(EntityInteractEvent e){
        if(e.target instanceof GenericRailTransport){
            ((GenericRailTransport)e.target).interact(Minecraft.getMinecraft().thePlayer, false, false, -1);
            e.setCanceled(true);
        }
    }

}
