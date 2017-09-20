package me.clonalejandro.combatlogNB.listeners;

import me.clonalejandro.combatlogNB.data.Data;
import me.clonalejandro.combatlogNB.mobs.WitherSkeleton;
import me.clonalejandro.combatlogNB.task.SurrogateTask;
import me.clonalejandro.combatlogNB.utils.CombatLog;
import me.clonalejandro.combatlogNB.Main;
import me.clonalejandro.combatlogNB.task.DamageTask;
import me.clonalejandro.combatlogNB.utils.Manager;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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

public class Handlers {


    /** SMALL CONSTRUCTORS **/

    private final Main plugin;

    public Handlers(Main instance){
        plugin = instance;
    }

    private final WitherSkeleton surround = new WitherSkeleton();


    /** REST **/

    /**
     * @param player
     */
    void onDamage(Player player){
        final Inventory inventory = player.getInventory();

        if (CombatLog.ID.containsKey(player)){
            if (DamageTask.MESSAGER)
                player.sendMessage(DamageTask.MSGIN);

            final int id = CombatLog.ID.size() + 1;

            CombatLog.ID.put(player, id);
            CombatLog.INVENTORY.put(id, inventory);
            CombatLog.GETTER.put(id, player);
            DamageTask task = new DamageTask(plugin, player);

            task.runTaskTimer(plugin, 1L, 20L);

            CombatLog.TASKID.put(player, task.getTaskId());
        }
        else {
            final int id = CombatLog.TASKID.get(player);

            Bukkit.getScheduler().cancelTask(id);
            CombatLog.TASKID.remove(player);
            DamageTask task = new DamageTask(plugin, player);

            task.runTaskTimer(plugin, 1L, 20L);

            CombatLog.TASKID.put(player, task.getTaskId());
        }
    }


    /**
     * @param player
     */
    void onDeath(Player player){
        final int id = CombatLog.TASKID.get(player);
        final int ID = CombatLog.ID.get(player);

        Bukkit.getScheduler().cancelTask(id);

        CombatLog.TASKID.remove(player);
        CombatLog.ID.remove(player);
        CombatLog.INVENTORY.remove(ID);
        CombatLog.GETTER.remove(ID);
    }


    /**
     * @param player
     */
    void onLeave(Player player){
        int id = CombatLog.TASKID.get(player);
        Bukkit.getScheduler().cancelTask(id);
        CombatLog.TASKID.remove(player);
        leaveFunctions(player);
    }


    /**
     * @param entity
     * @throws RuntimeException
     */
    void surrogateDamage(Entity entity) throws RuntimeException{
        Player p = null;

        final String prefix = plugin.getCManager().getMobName();
        final String color = plugin.getCManager().getMobColor();
        String name = entity.getCustomName() == null ? entity.getName() : entity.getCustomName();

        name = name.replace(Manager.SPACE, "");//Clear empty spaces
        name = name.replace(Manager.messageColors(prefix), "");//Remove prefix
        name = name.replace(Manager.messageColors(color), "");//Fix without color name

        for (Integer i : CombatLog.ID.values()){
            final Player lp = CombatLog.GETTER.get(i);
            final String str = lp.getName();
            if (name.equalsIgnoreCase(str))
                p = lp;
        }

        if (p == null) throw new RuntimeException();

        final int id = CombatLog.ID.get(p);
        final int taskID = CombatLog.TASKSURROUNDER.get(id);

        Bukkit.getScheduler().cancelTask(taskID);
        CombatLog.TASKSURROUNDER.remove(id);

        SurrogateTask task = new SurrogateTask(plugin, p);
        task.runTaskTimer(plugin, 1L, 20L);

        CombatLog.TASKSURROUNDER.put(id, task.getTaskId());
    }


    /**
     * @param e
     */
    void surrogateDeath(EntityDeathEvent e){
        e.getDrops().clear();

        final Entity entity = e.getEntity();
        final String prefix = plugin.getCManager().getMobName();
        final String color = plugin.getCManager().getMobColor();

        String name = entity.getCustomName() == null ? entity.getName() : entity.getCustomName();

        name = name.replace(Manager.SPACE, "");//Clear empty spaces
        name = name.replace(Manager.messageColors(prefix), "");//Clear prefix
        name = name.replace(Manager.messageColors(color), "");//Clear white name

        Player player = null;

        for (Integer i : CombatLog.ID.values()){
            final Player p = CombatLog.GETTER.get(i);
            final String str = p.getName();
            if (str.equalsIgnoreCase(name))
                player = p;
        }

        if (player == null) throw new RuntimeException();

        final Player killer = e.getEntity().getKiller();
        final int id = CombatLog.ID.get(player);
        final Inventory inventory = CombatLog.INVENTORY.get(id);


        /** REMOVE TASK **/

        int taskID = CombatLog.TASKSURROUNDER.get(id);
        Bukkit.getScheduler().cancelTask(taskID);
        CombatLog.TASKSURROUNDER.remove(id);


        ItemStack[] items = inventory.getContents();

        boolean debug = plugin.getCManager().getDebugDrops();

        if (debug)
            for (ItemStack item : items)
                killer.getInventory().addItem(item);
        else
            for (ItemStack item : items)
                e.getDrops().add(item);

        String message = plugin.getCManager().getDeathMessage();

        message = message.replace("{KILLED}", player.getName());
        message = message.replace("{KILLER}", killer.getName());

        Bukkit.broadcastMessage(Manager.messageColors(message));
        final Object obj = CombatLog.SURROGATE.get(id);

        CombatLog.ID.remove(player);
        CombatLog.INVENTORY.remove(id);
        CombatLog.SURROGATE.remove(id);
        CombatLog.INVERSE.remove(obj);
        CombatLog.GETTER.remove(id);

        new Data(plugin, player);
    }


    /**
     * @return
     */
    public WitherSkeleton getSurround(){
        return surround;
    }


    /** OTHERS **/

    /**
     * @param player
     */
    private void leaveFunctions(Player player){
        final int id = CombatLog.ID.get(player);
        final String color = plugin.getCManager().getMobColor();
        final String name = Manager.messageColors(plugin.getCManager().getMobName() + Manager.SPACE + color + player.getName());

        if (player.getGameMode() == GameMode.SURVIVAL) {
            surround.spawn(id, name, Manager.extractor(player));

            SurrogateTask task = new SurrogateTask(plugin, player);
            task.runTaskTimer(plugin, 1L, 20L);

            CombatLog.TASKSURROUNDER.put(id, task.getTaskId());
        } else {
            CombatLog.ID.remove(player);
            CombatLog.GETTER.remove(id);
            CombatLog.INVENTORY.remove(id);
            if (CombatLog.TASKID.containsKey(player))
                CombatLog.TASKID.remove(player);
        }
    }


}