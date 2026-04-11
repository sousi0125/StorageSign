package wacky.storagesign.Integrations;

import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.object.Translation;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class TownyIntegration {


    public static boolean canInteract(Player player, Block block) {
        boolean canInteract = PlayerCacheUtil.getCachePermission(
                player,
                block.getLocation(),
                block.getType(),
                TownyPermission.ActionType.SWITCH
        );

        if (!canInteract) {
            TownyMessaging.sendErrorMsg(player, Translation.of("msg_cache_block_error_town_outsider",player.locale() , Translation.of("switch", player.locale())));
        }

        return canInteract;
    }
}