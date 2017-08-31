package me.clonalejandro.combatlogNB.data;

import me.clonalejandro.combatlogNB.Main;

import me.clonalejandro.combatlogNB.utils.CombatLog;
import org.bukkit.entity.Player;

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

public class Data {


    /** SMALL CONSTRUCTORS **/
    public Data(Main instance, Player player){
        instance.getDataManager().getData().addDefault("data.players." + player.getName(), player.getName());
        instance.getDataManager().saveData();
    }


    /** REST **/

    /**
     * @param data
     * @return
     */
    public static boolean haveData(String data){
        if (Main.instance.getDataManager().getData().get("data.players." + data) == data)
            return true;
        else return false;
    }


    /**
     * @param data
     */
    public static void removeData(Object data){
        try { Main.instance.getConfig().set("data.players." + data, null); }
        catch (Exception ex) { ex.printStackTrace(); }
    }


}
