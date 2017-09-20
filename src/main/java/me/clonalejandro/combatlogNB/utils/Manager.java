package me.clonalejandro.combatlogNB.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.clonalejandro.combatlogNB.Main;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Base64;

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

public class Manager {


    /** SMALL CONSTRUCTORS **/

    public static final String SPACE = " ";
    public static final String PREFIX = messageColors("&d&lCombatLog> &f");
    public static final Boolean LICENSED = getLicense();


    /** REST **/

    /**
     * @return
     */
    public static PluginManager getPM(){
        return Main.instance.getServer().getPluginManager();
    }


    /**
     * @return
     */
    public static FileConfiguration ConfigManager(){
        return Main.instance.getConfig();
    }


    /**
     * @param message
     * @return
     */
    public static String messageColors(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }


    public static Location extractor(Player player){
        final Location location = player.getLocation();

        World world;
        double x, y, z;
        float yaw, pitch;

        world = location.getWorld();

        x = location.getX();
        y = location.getY() + 1;
        z = location.getZ();

        yaw = location.getYaw();
        pitch = location.getPitch();

        return new Location(world, x, y, z, yaw, pitch);
    }


    /** OTHERS **/

    /**
     * @param url
     * @return
     */
    private static JsonObject getJson(String url){
        try {
            URL cUrl = new URL(url);
            HttpURLConnection request = (HttpURLConnection) cUrl.openConnection();
            request.connect();

            JsonParser jsonParser = new JsonParser();
            InputStream stream = (InputStream) request.getContent();
            JsonElement response = jsonParser.parse(new InputStreamReader(stream));

            return response.getAsJsonObject();
        }
        catch (Exception ex){ ex.printStackTrace(); }
        return null;
    }


    /**
     * @return
     */
    private static Boolean getLicense(){
        String url = "aHR0cHM6Ly9jbG9uYWxlamFuZHJvLmdpdGh1Yi5pby9jZG4vY2xvbmF3ZWIvcGVyc29uYWwuanNvbg==";

        try {
            byte[] bytes = Base64.getDecoder().decode(url.getBytes());
            url = new String(bytes, Charset.forName("UTF-8"));
        }
        catch (Exception ex) { ex.printStackTrace(); }

        final JsonObject OBJECT = getJson(url);
        assert OBJECT != null;

        return OBJECT.get("CombatLog").getAsJsonObject().get("key").getAsBoolean();
    }


}
