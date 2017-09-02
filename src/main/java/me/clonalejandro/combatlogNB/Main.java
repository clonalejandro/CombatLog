package me.clonalejandro.combatlogNB;

import me.clonalejandro.combatlogNB.data.DataManager;
import me.clonalejandro.combatlogNB.listeners.Handlers;
import me.clonalejandro.combatlogNB.listeners.PlayerListeners;
import me.clonalejandro.combatlogNB.listeners.SurrogateListeners;
import me.clonalejandro.combatlogNB.utils.ConfigManager;
import me.clonalejandro.combatlogNB.utils.Manager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by alejandrorioscalera
 * On 30/8/17
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

public class Main extends JavaPlugin {


    /** SMALL CONSTRUCTORS **/

    public static Main instance;

    private DataManager dataManager;
    private final Handlers handlers = new Handlers(instance);


    /** REST **/

    @Override
    public void onLoad(){
        Bukkit.getConsoleSender().sendMessage(Manager.messageColors(Manager.PREFIX + "&bplugin loaded"));
    }


    @Override
    public void onEnable(){
        try {
            instance = this;

            Config();
            Events();
            Commands();

            //More...

            Bukkit.getConsoleSender().sendMessage(Manager.messageColors(Manager.PREFIX + "&aplugin enabled"));
        } catch (Exception ex){
            ex.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(Manager.messageColors("&4&lCombatLog> &cseveral error"));
            onDisable();
        }
    }


    @Override
    public void onDisable(){
        try {
            Manager.getPM().disablePlugin(instance);
            Bukkit.getConsoleSender().sendMessage(Manager.messageColors(Manager.PREFIX + "&ekilling process plugin"));
        } catch (Exception ex){
            ex.printStackTrace();
            Manager.getPM().disablePlugin(this);
            Bukkit.getConsoleSender().sendMessage(Manager.messageColors(Manager.PREFIX + "&4killing process plugin with errors!"));
        }
        Bukkit.getConsoleSender().sendMessage(Manager.messageColors(Manager.PREFIX + "&cplugin disabled"));
        instance = null;
    }


    /** OTHERS **/

    private void Events(){
        if (Manager.LICENSED){
            Manager.getPM().registerEvents(new PlayerListeners(instance), instance);
            Manager.getPM().registerEvents(new SurrogateListeners(instance), instance);
        }
    }


    private void Commands(){

    }


    private void Config(){
        saveDefaultConfig();
        dataManager = new DataManager(instance);
        dataManager.getData().set("data.players.Notch", "Notch");
        dataManager.saveData();
    }


    /** GETTERS **/

    public ConfigManager getCManager(){
        return new ConfigManager(Manager.ConfigManager());
    }

    public DataManager getDataManager(){
        return dataManager;
    }

    public Handlers getHandlers(){
        return handlers;
    }


}