package me.clonalejandro.combatlogNB.listeners;

import me.clonalejandro.combatlogNB.data.Data;
import me.clonalejandro.combatlogNB.utils.CombatLog;
import me.clonalejandro.combatlogNB.Main;
import me.clonalejandro.combatlogNB.utils.Manager;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

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
        handlers = instance.getHandlers();
        plugin = instance;
    }


    /** REST **/

    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e){
        final String playerName = e.getPlayer().getName();
        final Player player = e.getPlayer();

        if (Data.haveData(playerName)) {
            player.getInventory().clear();
            player.setHealth(0.0D);
        }

        List<LivingEntity> list = player.getLocation().getWorld().getLivingEntities();

        for (LivingEntity entity : list){
            final String prefix = plugin.getCManager().getMobName();
            final String color = plugin.getCManager().getMobColor();
            String name = entity.getCustomName() == null ? entity.getName() : entity.getCustomName();

            name = name.replace(Manager.SPACE, "");
            name = name.replace(Manager.messageColors(prefix), "");
            name = name.replace(Manager.messageColors(color), "");

            if (name.equalsIgnoreCase(playerName)){
                if (CombatLog.ID.containsKey(player)){
                    final int id = CombatLog.ID.get(player) == null ? help(player) : CombatLog.ID.get(player);
                    final int taskId = CombatLog.TASKSURROUNDER.get(id);

                    handlers.getSurround().despawn(id);
                    CombatLog.ID.remove(player);
                    CombatLog.INVENTORY.remove(id);
                    CombatLog.GETTER.remove(id);
                    CombatLog.TASKSURROUNDER.remove(id);
                    try { Bukkit.getScheduler().cancelTask(taskId); }
                    catch (Exception ignored) { return; }
                }
                else entity.remove();
            }
        }
    }


    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();
        if (CombatLog.ID.containsKey(player))
            handlers.onLeave(player);
    }


    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerKick(PlayerKickEvent e){
        Player player = e.getPlayer();
        if (CombatLog.ID.containsKey(player))
            handlers.onLeave(player);
    }


    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerDamage(EntityDamageByEntityEvent e){
        Entity kicked = e.getEntity(),
                kicker = e.getDamager();

        if (kicked.getType() == EntityType.PLAYER) {

            Player pkicked = (Player) kicked, pkicker = null;

            switch (kicker.getType()) {
                case PLAYER:
                    pkicker = (Player) kicker;
                    break;
                case ARROW:
                    Arrow arrow = (Arrow) kicker;
                    pkicker = (Player) arrow.getShooter();
                    break;
                case FISHING_HOOK:
                    FishHook fishHook = (FishHook) kicker;
                    pkicker = (Player) fishHook.getShooter();
                    break;
                case SNOWBALL:
                    Snowball snowball = (Snowball) kicker;
                    pkicker = (Player) snowball.getShooter();
                    break;
                case EGG:
                    Egg egg = (Egg) kicker;
                    pkicker = (Player) egg.getShooter();
                    break;
            }

            if (pkicker == null) throw new RuntimeException();

            boolean isGamemode = pkicker.getGameMode() == GameMode.SURVIVAL;
            boolean isWorldD = false;

            for (Object obj : plugin.getCManager().getWorlds())
                isWorldD = kicked.getLocation().getWorld().getName().equalsIgnoreCase(obj.toString());

            boolean isOn = plugin.getDataManager().getData().get("data.state") != null && (plugin.getDataManager().getData().get("data.state").toString().equalsIgnoreCase("on"));

            if (isOn){
                if (!isWorldD && isGamemode) {
                    handlers.onDamage(pkicked);
                    handlers.onDamage(pkicker);
                }
            }
            else
                Bukkit.getConsoleSender().sendMessage(Manager.messageColors(Manager.PREFIX + "&cPlugin disabled because this is in off mode"));
        }
    }


    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent e){
        final String player = e.getEntity().getName();
        final Player p = e.getEntity();

        if (Data.haveData(player)) {
            e.setDeathMessage(null);
            Data.removeData(player);
            Bukkit.getPlayer(player).sendMessage(Manager.messageColors(plugin.getCManager().getPunishMessage()));
        }

        if (CombatLog.ID.containsKey(p))
            handlers.onDeath(p);
    }


    /** OTHERS **/

    /**
     * @param player
     * @return
     */
    private Integer help(Player player){
        int it = 0;
        for (Integer i : CombatLog.ID.values()){
            final String name = player.getName();
            final Player p = CombatLog.GETTER.get(i);

            assert p != null;
            final String str = p.getName();

            if (name.equalsIgnoreCase(str))
                it = CombatLog.ID.get(p);
        }
        return it;
    }


}
