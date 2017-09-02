package me.clonalejandro.combatlogNB.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

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

public class CombatLog {


    public static HashMap<Player, Integer> ID = new HashMap<>();
    public static HashMap<Integer, Inventory> INVENTORY = new HashMap<>();
    public static HashMap<Integer, Object> SURROGATE = new HashMap<>();
    public static HashMap<Integer, Player> GETTER = new HashMap<>();
    public static HashMap<Object, Player> INVERSE = new HashMap<>();
    public static HashMap<Player, Integer> TASKID = new HashMap<>();
    public static HashMap<Integer, Integer> TASKSURROUNDER = new HashMap<>();


}
