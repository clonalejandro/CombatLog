package me.clonalejandro.combatlogNB.data;

import me.clonalejandro.combatlogNB.Main;

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
        instance.getDataManager().getData().set("data.players." + player.getName(), player.getName());
        instance.getDataManager().saveData();
    }


    /** REST **/

    /**
     * @param data
     * @return
     */
    public static boolean haveData(String data){
        final String ldata = (String) Main.instance.getDataManager().getData().get("data.players." + data);
        return ldata != null && ldata.equalsIgnoreCase(data);
    }


    /**
     * @param data
     */
    public static void removeData(Object data){
        try {
            Main.instance.getDataManager().getData().set("data.players." + data, null);
            Main.instance.getDataManager().saveData();
        }
        catch (Exception ex) { ex.printStackTrace(); }
    }


}
