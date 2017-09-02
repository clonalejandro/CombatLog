package me.clonalejandro.combatlogNB.task;

import me.clonalejandro.combatlogNB.Main;
import me.clonalejandro.combatlogNB.listeners.Handlers;
import me.clonalejandro.combatlogNB.utils.CombatLog;
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

public class SurrogateTask extends BukkitRunnable {


    /** SMALL CONSTRUCTORS **/

    private int timeDespawn;
    private final Player player;
    private final Handlers handlers;

    public SurrogateTask(Main instance, Player player){
        timeDespawn = instance.getCManager().getMobTime();
        this.player = player;
        handlers = instance.getHandlers();
    }


    /** REST **/

    @Override
    public void run(){
        if (timeDespawn != 0)
            timeDespawn--;
        else {
            int id = CombatLog.ID.get(player);
            CombatLog.ID.remove(player);
            CombatLog.INVENTORY.remove(id);
            handlers.getSurround().despawn(id);
            cancel();
        }
    }


}