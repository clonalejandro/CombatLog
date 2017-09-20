package me.clonalejandro.combatlogNB;

import me.clonalejandro.combatlogNB.commands.Setter;
import me.clonalejandro.combatlogNB.data.DataManager;
import me.clonalejandro.combatlogNB.listeners.Handlers;
import me.clonalejandro.combatlogNB.listeners.PlayerListeners;
import me.clonalejandro.combatlogNB.listeners.SurrogateListeners;
import me.clonalejandro.combatlogNB.utils.ConfigManager;
import me.clonalejandro.combatlogNB.utils.Manager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

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
    private Handlers handlers;
    private ConfigManager configManager;


    /** REST **/

    @Override
    public void onLoad(){
        Bukkit.getConsoleSender().sendMessage(Manager.messageColors(Manager.PREFIX + "&bplugin loaded"));
    }


    @Override
    public void onEnable(){
        try {
            instance = this;
            handlers = new Handlers(instance);

            Config();
            Events();
            Commands();

            configManager = new ConfigManager(Manager.ConfigManager());

            Bukkit.getConsoleSender().sendMessage(Manager.messageColors(Manager.PREFIX + "&aplugin enabled"));
        } catch (Exception ex){
            ex.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(Manager.messageColors("&4&lCombatLog> &cseveral error"));
            onDisable();
        }
    }


    @Override
    public void onDisable(){
        Clear();

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
        getCommand("combatlog").setExecutor(new Setter(instance));
    }


    private void Config(){
        saveDefaultConfig();
        dataManager = new DataManager(instance);
        dataManager.getData().set("data.players.Notch", "Notch");
        dataManager.saveData();
    }


    private void Clear(){
        if (getCManager().getDebug())
            Bukkit.getConsoleSender().sendMessage(Manager.messageColors("&bclonalejandro> &cClearing custom entities 🗑"));

        List<LivingEntity> entityList = new ArrayList<>();

        for (World world : Bukkit.getWorlds())
            for (Object str : getCManager().getWorlds())
                if (str.toString().equalsIgnoreCase(world.getName()))
                    entityList.addAll(world.getLivingEntities());

        for (LivingEntity entity : entityList){
            final String name = entity.getCustomName() == null ? entity.getName() : entity.getCustomName();
            final String prefix = Manager.messageColors(getCManager().getMobName());
            if (name.contains(prefix))
                entity.remove();
        }
    }


    /** GETTERS **/

    public ConfigManager getCManager(){
        return configManager;
    }

    public DataManager getDataManager(){
        return dataManager;
    }

    public Handlers getHandlers(){
        return handlers;
    }


}