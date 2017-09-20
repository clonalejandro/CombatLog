package me.clonalejandro.combatlogNB.commands;

import me.clonalejandro.combatlogNB.Main;
import me.clonalejandro.combatlogNB.utils.Manager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 * Created by alejandrorioscalera
 * On 2/9/17
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

public class Setter implements CommandExecutor {


    /** SMALL CONSTRUCTORS **/

    private final Main plugin;

    public Setter(Main instance){
        plugin = instance;
    }


    /** REST **/

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args){
        final String permissions = Manager.messageColors("&c&lServer> &fYou don't have permissions for this");

        if (cmd.getName().equalsIgnoreCase("combatlog")){
            if (args.length < 1)
                pinfo(sender);

            else if (args[0].equalsIgnoreCase("on"))
                if (sender.hasPermission("combatlog.perm"))
                    function(sender, PluginState.ON);
                else sender.sendMessage(permissions);

            else if (args[0].equalsIgnoreCase("off"))
                if (sender.hasPermission("combatlog.perm"))
                    function(sender, PluginState.OFF);
                else sender.sendMessage(permissions);

            else pinfo(sender);
        }
        return true;
    }


    /** OTHERS **/

    /**
     * @param sender
     */
    private void pinfo(CommandSender sender){
        final String version = Main.instance.getDescription().getVersion();

        sender.sendMessage(Manager.messageColors("&f** &d&lCombatLog &f**"));
        sender.sendMessage(Manager.messageColors("&aPlugin developed by: &eclonalejandro"));
        sender.sendMessage(Manager.messageColors("&aPlugin version: &e" + version));
        sender.sendMessage(Manager.messageColors("&b/combatlog &e<on/off>&f: Put this plugin enabled/disabled"));
    }


    /**
     * @param sender
     * @param state
     */
    private void function(CommandSender sender, PluginState state){
        switch (state){
            default:
                break;
            case ON:
                plugin.getDataManager().getData().set("data.state", "on");
                plugin.getDataManager().saveData();
                sender.sendMessage(Manager.messageColors("&d&lCTLOG» &fmode setted &aon"));
                break;
            case OFF:
                final PluginManager pm = Manager.getPM();
                final Plugin pl = plugin;
                pm.disablePlugin(pl);
                pm.enablePlugin(pl);
                plugin.getDataManager().getData().set("data.state", "off");
                plugin.getDataManager().saveData();
                sender.sendMessage(Manager.messageColors("&d&lCTLOG» &fmode setted &coff"));
                break;
        }
    }


}