package me.clonalejandro.combatlogNB.task;

import me.clonalejandro.combatlogNB.utils.CombatLog;
import me.clonalejandro.combatlogNB.Main;

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
    private Player player;

    public DamageTask(Main instance, Player player){
        this.player = player;
        timeCombat = instance.getCManager().getCombatTime();
    }


    /** REST **/

    @Override
    public void run(){
        if (timeCombat != 0)
            timeCombat--;
        else {
            final int id = CombatLog.ID.get(player);
            CombatLog.ID.remove(player);
            CombatLog.INVENTORY.remove(id);
            cancel();
        }
    }


}
