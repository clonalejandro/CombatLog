package me.clonalejandro.combatlogNB.data;

import me.clonalejandro.combatlogNB.Main;
import me.clonalejandro.combatlogNB.utils.Manager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

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

public class DataManager {


    /** SMALL CONSTRUCTORS **/

    private final Main plugin;
    private final FileConfiguration data;
    private final File dataFile;

    public DataManager(Main instance){
        plugin = instance;
        dataFile = new File(plugin.getDataFolder(), "data.yml");

        if (!dataFile.exists()){
            dataFile.getParentFile().mkdirs();
            plugin.saveResource("data.yml", true);
        }

        data = new YamlConfiguration();

        try { data.load(dataFile); }
        catch (Exception ex){ ex.printStackTrace(); }
    }


    /** REST **/

    public void saveData() {
        try { onSave(); }
        catch (Exception ex) { onException(ex); }
    }


    /**
     * @return
     */
    public FileConfiguration getData(){
        return data;
    }


    /** OTHERS **/

    private void onException(Exception ex){
        ex.printStackTrace();
        Bukkit.getConsoleSender().sendMessage(Manager.messageColors("&4&lclonalejandro[Manager]> &fHey! &cdata error!"));
    }


    private void onSave() throws IOException {
        data.save(dataFile);
        Bukkit.getConsoleSender().sendMessage(Manager.messageColors("&b&lclonalejandro[Manager]> &fHey! &adata saved 😍"));
    }


}