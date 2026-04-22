package wacky.storagesign.Integrations;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import wacky.storagesign.StorageSignCore;

public class IntegrationsManager {

    private static boolean isIntegrationsEnabled =false;
    private static boolean isWorldGuardEnabled = false;
    private static boolean isTownyEnabled = false;

    public static void init(StorageSignCore plugin) {
        PluginManager pm = Bukkit.getPluginManager();
        isWorldGuardEnabled = pm.isPluginEnabled("WorldGuard");
        isTownyEnabled = pm.isPluginEnabled("Towny");

        isIntegrationsEnabled = isWorldGuardEnabled || isTownyEnabled;
        if (isIntegrationsEnabled) {
            plugin.getLogger().info("Integrations Detected");
            plugin.getLogger().info("WorldGuard : " + isWorldGuardEnabled);
            plugin.getLogger().info("Towny : " + isTownyEnabled);
        }
    }

    public static boolean isWorldGuardEnabled() {
        return isWorldGuardEnabled;
    }

    public static boolean isTownyEnabled() {
        return isTownyEnabled;
    }

    public static boolean isIntegrationsEnabled() {
        return isIntegrationsEnabled;
    }
}
