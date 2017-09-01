package me.clonalejandro.combatlogNB.listeners;

import me.clonalejandro.combatlogNB.data.Data;
import me.clonalejandro.combatlogNB.utils.CombatLog;
import me.clonalejandro.combatlogNB.Main;
import me.clonalejandro.combatlogNB.utils.Manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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

public class PlayerListeners implements Listener {


    /** SMALL CONSTRUCTORS **/

    private final Handlers handlers;
    private final Main plugin;

    public PlayerListeners(Main instance){
        handlers = new Handlers(instance);
        plugin = instance;
    }


    /** REST **/

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        final String playerName = e.getPlayer().getName();
        final Player player = e.getPlayer();

        if (Data.haveData(playerName))
            player.setHealth(0.0D);

        if (CombatLog.ID.get(player) != null){
            final int id = CombatLog.ID.get(player);
            handlers.getSurround().despawn(id);
            CombatLog.ID.remove(player);
            CombatLog.INVENTORY.remove(id);
        }
    }


    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();

        if (CombatLog.ID.get(player) != null)
            handlers.onLeave(player);
    }


    @EventHandler
    public void onPlayerKick(PlayerKickEvent e){
        Player player = e.getPlayer();

        if (CombatLog.ID.get(player) != null)
            handlers.onLeave(player);
    }


    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e){
        Entity kicked = e.getEntity(),
                kicker = e.getDamager();

        if (kicker.getType() == EntityType.PLAYER && kicked.getType() == EntityType.PLAYER){
            Player pkicked = (Player) kicked,
                    pkicker = (Player) kicker;
            handlers.onDamage(pkicked);
            handlers.onDamage(pkicker);
        }
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        final String player = e.getEntity().getName();

        if (Data.haveData(player)) {
            e.setDeathMessage(null);
            Data.removeData(player);
            Bukkit.getPlayer(player).sendMessage(Manager.messageColors(plugin.getCManager().getPunishMessage()));
        }
    }


}
