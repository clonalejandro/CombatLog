package me.clonalejandro.combatlogNB.task;

import me.clonalejandro.combatlogNB.utils.CombatLog;
import me.clonalejandro.combatlogNB.Main;
import me.clonalejandro.combatlogNB.utils.Manager;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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

public class DamageTask extends BukkitRunnable {


    /** SMALL CONSTRUCTORS **/

    private int timeCombat;
    private final Player player;
    private static String MSGOUT;
    public static String MSGIN;
    public static boolean MESSAGER;

    public DamageTask(Main instance, Player player){
        this.player = player;

        timeCombat = instance.getCManager().getCombatTime();

        MSGIN = Manager.messageColors(instance.getCManager().getCombatMessageIn());
        MSGOUT = Manager.messageColors(instance.getCManager().getCombatMessageOut());

        MESSAGER = instance.getCManager().getCombatMessager();
    }


    /** REST **/

    @Override
    public void run(){
        if (timeCombat != 0)
            --timeCombat;
        else {
            final int id = CombatLog.ID.get(player);
            CombatLog.ID.remove(player);
            CombatLog.INVENTORY.remove(id);
            CombatLog.GETTER.remove(id);
            CombatLog.TASKID.remove(player);

            senderCombat(player);
            cancel();
        }
    }


    /** OTHERS **/

    /**
     * @param p
     */
    private static void senderCombat(Player p){
        if (MESSAGER)
            p.sendMessage(MSGOUT);
    }


}
