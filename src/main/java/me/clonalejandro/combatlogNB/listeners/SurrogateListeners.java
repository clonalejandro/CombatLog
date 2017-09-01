package me.clonalejandro.combatlogNB.listeners;

import me.clonalejandro.combatlogNB.Main;
import me.clonalejandro.combatlogNB.utils.Manager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

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
        handlers = new Handlers(instance);
    }


    /** REST **/

    @EventHandler
    public void onDamageSurrogate(EntityDamageByEntityEvent e){
        Entity damager = e.getDamager();
        Entity main = e.getEntity();

        final String prefix = Manager.messageColors(plugin.getCManager().getMobName());

        if ((main.getCustomName() == null ? main.getName() : main.getCustomName()).contains(prefix)){
            if (damager != null && damager.getType() == EntityType.PLAYER)
                handlers.surrogateDamage((Player) damager);
            else e.setCancelled(true);
        }
    }


    @EventHandler
    public void onSurrogateDeath(EntityDeathEvent e){
        Entity entity = e.getEntity();

        final String prefix = Manager.messageColors(plugin.getCManager().getMobName());

        if ((entity.getCustomName() == null ? entity.getName() : entity.getCustomName()).contains(prefix))
            handlers.surrogateDeath(e);
    }


}
