package me.clonalejandro.combatlogNB.mobs;

import me.clonalejandro.ReflectionAPI.ReflectionAPI;
import me.clonalejandro.combatlogNB.utils.CombatLog;
import me.clonalejandro.combatlogNB.utils.Manager;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Method;


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

public class WitherSkeleton extends ReflectionAPI {


    /** SMALL CONSTRUCTORS **/

    //none...


    /** REST **/

    /**
     * @param ID
     * @param name
     * @param location
     */
    public void spawn(int ID, String name, Location location){
        try {
            final Class<?> lastCW = getBukkitNMSClass("CraftWorld");

            Object CraftWorld = getBukkitNMSClass("CraftWorld").cast(location.getWorld());

            Class<?> NmsWorld = getNMSClass("World");
            Class<?> mob = getNMSClass("EntitySkeleton");
            Class<?> NmsEntity = getNMSClass("Entity");

            Object entity = mob.getConstructor(NmsWorld).newInstance(lastCW.getMethod("getHandle").invoke(CraftWorld));

            configure(entity, name, location);

            lastCW.getMethod("addEntity", NmsEntity, CreatureSpawnEvent.SpawnReason.class).
                    invoke(CraftWorld, entity, CreatureSpawnEvent.SpawnReason.CUSTOM);

            CombatLog.SURROGATE.put(ID, entity);
        }
        catch (Exception ex){ ex.printStackTrace(); }
    }


    /**
     * @param ID
     */
    public void despawn(int ID){
        Object obj = CombatLog.SURROGATE.get(ID);

        Entity entity = (Entity) obj;

        CombatLog.SURROGATE.remove(ID);
        Location location = entity.getLocation();

        delete(obj, location);
    }


    /** OTHERS **/

    /**
     * @param entity
     * @param location
     */
    private void delete(Object entity, Location location){
        try {
            Class<?> lastCW = getBukkitNMSClass("CraftWorld");
            Class<?> NmsWorld = getNMSClass("World");
            Class<?> NmsEntity = getNMSClass("Entity");

            Object CraftWorld = lastCW.cast(location.getWorld());
            NmsWorld.getMethod("removeEntity", NmsEntity).invoke(lastCW.getMethod("getHandle").invoke(CraftWorld), entity);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }


    /**
     * @param entity
     * @param name
     * @param location
     * @throws Exception
     */
    private void configure(Object entity, String name, Location location) throws Exception{
        Method setWither = entity.getClass().getMethod("setSkeletonType", int.class);
        Method setCustomName = entity.getClass().getMethod("setCustomName", String.class);
        Method setCustomNameVisible = entity.getClass().getMethod("setCustomNameVisible", boolean.class);
        Method setLocation = entity.getClass().getMethod("setLocation", double.class, double.class, double.class, float.class, float.class);

        setWither.invoke(entity, 1);
        setCustomName.invoke(entity, Manager.messageColors(name));
        setCustomNameVisible.invoke(entity, true);
        setLocation.invoke(entity, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }


}
