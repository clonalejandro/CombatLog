package me.clonalejandro.combatlogNB.listeners;

import me.clonalejandro.combatlogNB.data.Data;
import me.clonalejandro.combatlogNB.mobs.WitherSkeleton;
import me.clonalejandro.combatlogNB.task.SurrogateTask;
import me.clonalejandro.combatlogNB.utils.CombatLog;
import me.clonalejandro.combatlogNB.Main;
import me.clonalejandro.combatlogNB.task.DamageTask;

import me.clonalejandro.combatlogNB.utils.Manager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;
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

class Handlers {


    /** SMALL CONSTRUCTORS **/

    private final Main plugin;

    Handlers(Main instance){
        plugin = instance;
    }

    private final WitherSkeleton surround = new WitherSkeleton();

    private int idTask;
    private int idSTask;


    /** REST **/

    /**
     * @param player
     */
    void onDamage(Player player){
        Inventory inventory = player.getInventory();
        double health = player.getHealth();

        if (CombatLog.ID.get(player) == null){
            final int id = CombatLog.ID.size() + 1;

            CombatLog.ID.put(player, id);
            CombatLog.INVENTORY.put(id, inventory);

            DamageTask task = getTask(player);

            plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, task, 0L, 20L);
            idTask = task.getTaskId();
        }
    }


    /**
     * @param player
     */
    void onLeave(Player player){
        plugin.getServer().getScheduler().cancelTask(idTask);
        leaveFunctions(player);
    }


    void surrogateDamage(){
        plugin.getServer().getScheduler().cancelTask(idSTask);
    }


    /**
     * @param e
     */
    void surrogateDeath(EntityDeathEvent e){
        e.getDrops().clear();

        final Entity entity = e.getEntity();
        String name = entity.getCustomName();
        final String prefix = plugin.getCManager().getMobName();

        name = name.replace(" ", "");//Clear empty spaces
        name = name.replace(prefix, "");//Clear prefix
        name = name.replace(Manager.messageColors("&f"), "");//Clear white name

        final Player player = Bukkit.getPlayer(name);
        final Player killer = (Player) e.getEntity();

        final int id = CombatLog.ID.get(player);
        final Inventory inventory = CombatLog.INVENTORY.get(id);

        ItemStack[] items = inventory.getContents();

        for (ItemStack item : items)
            e.getDrops().add(item);

        String message = plugin.getCManager().getDeathMessage();

        message = message.replace("{KILLED}", player.getName());
        message = message.replace("{KILLER}", killer.getName());

        Bukkit.broadcastMessage(Manager.messageColors(message));

        CombatLog.ID.remove(player);
        CombatLog.INVENTORY.remove(id);
        CombatLog.SURROGATE.remove(id);

        new Data(plugin, player);
    }


    /**
     * @return
     */
    WitherSkeleton getSurround(){
        return surround;
    }


    /** OTHERS **/

    /**
     * @param player
     * @return
     */
    private DamageTask getTask(Player player){
        return new DamageTask(plugin, player);
    }


    /**
     * @param player
     * @return
     */
    private SurrogateTask getSTask(Player player){
        return new SurrogateTask(plugin, player);
    }


    /**
     * @param player
     */
    private void leaveFunctions(Player player){
        final int id = CombatLog.ID.get(player);
        final String name = Manager.messageColors(plugin.getCManager().getMobName() + "&f" + player.getName());

        surround.spawn(id, name, player.getLocation());

        SurrogateTask task = getSTask(player);

        plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, task, 0L, 20L);
        idSTask = task.getTaskId();
    }


}