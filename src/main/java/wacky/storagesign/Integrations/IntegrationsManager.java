package wacky.storagesign.Integrations;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class IntegrationsManager {

    private static boolean isIntegrationsEnabled =false;
    private static boolean isWorldGuardEnabled = false;
    private static boolean isTownyEnabled = false;

    public static void init() {
        PluginManager pm = Bukkit.getPluginManager();
        isWorldGuardEnabled = pm.isPluginEnabled("WorldGuard");
        isTownyEnabled = pm.isPluginEnabled("Towny");

        isIntegrationsEnabled = isWorldGuardEnabled || isTownyEnabled;
        if (isIntegrationsEnabled) {
            System.out.println("Integrations Detected");
            System.out.println("WorldGuard : " + isWorldGuardEnabled);
            System.out.println("Towny : " + isTownyEnabled);
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
