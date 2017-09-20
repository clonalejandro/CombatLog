package me.clonalejandro.combatlogNB.utils;

import org.bukkit.configuration.file.FileConfiguration;

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

public class ConfigManager {


    /** SMALL CONSTRUCTORS **/

    private final FileConfiguration Configuration;

    public ConfigManager(FileConfiguration configuration){
        this.Configuration = configuration;
    }


    /** REST **/

    /**
     * @return
     */
    public List<?> getWorlds(){
        return (List<?>) Configuration.get("Worlds");
    }


    /**
     * @return
     */
    public String getDeathMessage(){
        return (String) Configuration.get("Death.Message");
    }


    /**
     * @return
     */
    public String getPunishMessage(){
        return (String) Configuration.get("Death.MessageKilled");
    }


    /**
     * @return
     */
    public String getMobName(){
        return (String) Configuration.get("Mob.Name");
    }


    /**
     * @return
     */
    public String getMobColor(){
        return (String) Configuration.get("Mob.Color");
    }


    /**
     * @return
     */
    public Integer getMobTime(){
        return (Integer) Configuration.get("Mob.Time");
    }


    /**
     * @return
     */
    public Integer getCombatTime(){
        return (Integer) Configuration.get("Combat.Time");
    }


    /**
     * @return
     */
    public Boolean getCombatMessager(){
        return (Boolean) Configuration.get("Combat.Messager");
    }


    /**
     * @return
     */
    public String getCombatMessageIn(){
        return (String) Configuration.get("Combat.MessageIn");
    }


    /**
     * @return
     */
    public String getCombatMessageOut(){
        return (String) Configuration.get("Combat.MessageOut");
    }


    /**
     * @return
     */
    public Boolean getDebug(){
        return (Boolean) Configuration.get("Debug");
    }


    /**
     * @return
     */
    public Boolean getDebugDrops(){
        return (Boolean) Configuration.get("DebugDrops");
    }


}