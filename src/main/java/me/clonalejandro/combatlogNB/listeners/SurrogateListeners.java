package me.clonalejandro.combatlogNB.listeners;

import me.clonalejandro.combatlogNB.Main;
import me.clonalejandro.combatlogNB.utils.Manager;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;

/**
 * Created by alejandrorioscalera
 * On 31/8/17
 *
 * -- SOCIAL NETWORKS --
 *
 * GitHub: https://github.com/clonalejandro or @clonalejandro
 * Website: https://clonalejandro.me/
 * Twitter: https://twitter.com/clonalejandro11/ or @clonalejandro11
 * Keybase: https://keybase.io/clonalejandro/
 *
 * -- LICENSE --
 *
 * All rights reserved for clonalejandro ©combatlogNB 2017 / 2018
 */

public class SurrogateListeners implements Listener {


    /** SMALL CONSTRUCTORS **/

    private final Main plugin;
    private final Handlers handlers;

    public SurrogateListeners(Main instance){
        plugin = instance;
        handlers = instance.getHandlers();
    }


    /** REST **/

    @EventHandler (priority = EventPriority.MONITOR)
    public void onDamageSurrogate(EntityDamageByEntityEvent e){
        Entity damager = e.getDamager();
        Entity main = e.getEntity();

        final String prefix = Manager.messageColors(plugin.getCManager().getMobName());

        if ((getECName(main) == null ? getEName(main) : getECName(main)).contains(prefix)){
            if (damager.getType() == EntityType.PLAYER)
                handlers.surrogateDamage(main);
            else e.setCancelled(true);
        }
    }


    @EventHandler (priority = EventPriority.MONITOR)
    public void onSoffocateSurrogate(EntityDamageEvent e){
        final Entity entity = e.getEntity();
        final String prefix = Manager.messageColors(plugin.getCManager().getMobName());
        final boolean isSurrogate = (getECName(entity) == null ? getEName(entity) : getECName(entity)).contains(prefix);

        if (isSurrogate && e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION)
            e.setCancelled(true);
    }


    @EventHandler (priority = EventPriority.MONITOR)
    public void onSurrogateTarget(EntityTargetEvent e){
        final String prefix = Manager.messageColors(plugin.getCManager().getMobName());
        final Entity entity = e.getEntity();

        if ((getECName(entity) == null ? getEName(entity) : getECName(entity)).contains(prefix))
            e.setCancelled(true);
    }


    @EventHandler (priority = EventPriority.MONITOR)
    public void onSurrogateSpawn(EntitySpawnEvent e){
        final Entity main = e.getEntity();
        final String prefix = Manager.messageColors(plugin.getCManager().getMobName());

        if ((getECName(main) == null ? getEName(main) : getECName(main)).contains(prefix)){
            Skeleton skeleton = (Skeleton) e.getEntity();
            skeleton.setTarget(null);
            skeleton.getEquipment().setItemInHand(new ItemStack(Material.AIR));
        }
    }


    @EventHandler (priority = EventPriority.MONITOR)
    public void onSurrogateDeath(EntityDeathEvent e){
        Entity entity = e.getEntity();

        final String prefix = Manager.messageColors(plugin.getCManager().getMobName());

        if (e.getEntity().getKiller() != null &&
                (getECName(entity) == null ? getEName(entity) : getECName(entity)).contains(prefix))
            handlers.surrogateDeath(e);
    }


    /** OTHERS **/

    /**
     * @param entity
     * @return
     */
    private String getECName(Entity entity){
        return entity.getCustomName();
    }


    /**
     * @param entity
     * @return
     */
    private String getEName(Entity entity){
        return entity.getName();
    }


}