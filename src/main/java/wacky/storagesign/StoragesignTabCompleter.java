package wacky.storagesign;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StoragesignTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) return Collections.emptyList();
        Player player = (Player) sender;

        List<String> commands = new ArrayList<>();

        if (args.length == 1) {
            if (player.hasPermission("storagesign.command.storagesign.setsignamount")) {
                commands.add("setsignamount");
            }
            if (player.hasPermission("storagesign.command.storagesign.setsignitem")) {
                commands.add("setsignitem");
            }
            if (player.hasPermission("storagesign.command.storagesign.help")) {
                commands.add("help");
            }
            return StringUtil.copyPartialMatches(args[0], commands, new ArrayList<>());
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("setsignamount")) {
                List<String> suggestions = new ArrayList<>();
                suggestions.add("[<count>]");
                return suggestions;
            }
            if (args[0].equalsIgnoreCase("setsignitem")) {
                return Collections.emptyList();
            }
            if (args[0].equalsIgnoreCase("help")) {
                return Collections.emptyList();
            }
        }

        return Collections.emptyList();
    }
}
