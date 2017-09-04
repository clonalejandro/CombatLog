package me.clonalejandro.combatlogNB.listeners;

import me.clonalejandro.combatlogNB.Main;
import me.clonalejandro.combatlogNB.utils.CombatLog;
import me.clonalejandro.combatlogNB.utils.Manager;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
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

    @EventHandler
    public void onDamageSurrogate(EntityDamageByEntityEvent e){
        Entity damager = e.getDamager();
        Entity main = e.getEntity();

        final String prefix = Manager.messageColors(plugin.getCManager().getMobName());

        if ((getECName(main) == null ? getEName(main) : getECName(main)).contains(prefix)){
            final Player player = CombatLog.INVERSE.get(main);
            if (damager.getType() == EntityType.PLAYER)
                handlers.surrogateDamage(main, player);
            else e.setCancelled(true);
        }
    }


    @EventHandler
    public void onSurrogateTarget(EntityTargetEvent e){
        final String prefix = Manager.messageColors(plugin.getCManager().getMobName());
        final Entity entity = e.getEntity();

        if ((getECName(entity) == null ? getEName(entity) : getECName(entity)).contains(prefix))
            e.setCancelled(true);
    }


    @EventHandler
    public void onSurrogateSpawn(EntitySpawnEvent e){
        final Entity main = e.getEntity();
        final String prefix = Manager.messageColors(plugin.getCManager().getMobName());

        if ((getECName(main) == null ? getEName(main) : getECName(main)).contains(prefix)){
            Skeleton skeleton = (Skeleton) e.getEntity();
            skeleton.setTarget(null);
            skeleton.getEquipment().setItemInHand(new ItemStack(Material.AIR));
        }
    }


    @EventHandler
    public void onSurrogateDeath(EntityDeathEvent e){
        Entity entity = e.getEntity();

        final String prefix = Manager.messageColors(plugin.getCManager().getMobName());

        if (e.getEntity().getKiller() != null &&
                (getECName(entity) == null ? getEName(entity) : getECName(entity)).contains(prefix))
            handlers.surrogateDeath(e);
    }


    /** OTHERS **/

    private String getECName(Entity entity){
        return entity.getCustomName();
    }

    private String getEName(Entity entity){
        return entity.getName();
    }


}
