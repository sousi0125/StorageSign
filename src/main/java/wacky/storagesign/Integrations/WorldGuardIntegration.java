package wacky.storagesign.Integrations;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldGuardIntegration {


    public static boolean canInteract(Player player, Location loc) {
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        com.sk89q.worldedit.util.Location weLoc = BukkitAdapter.adapt(loc);
        com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(loc.getWorld());
        RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
        if (WorldGuard.getInstance().getPlatform().getSessionManager().hasBypass(localPlayer, weWorld)) {
            return true;
        }

        boolean canInteract = query.testBuild(weLoc, localPlayer, Flags.CHEST_ACCESS);

        if (!canInteract) {
            Location visualLoc = loc.add(0.5, 1.5, 0.5);
            visualLoc.getWorld().playEffect(visualLoc, org.bukkit.Effect.SMOKE, 1);
            player.sendMessage("§c§lHey! §r§7Sorry, but you can't interact that storage sign here.");
        }

        return canInteract;
    }
}