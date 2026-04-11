package wacky.storagesign;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.StringUtil;

import java.util.Objects;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StoragesignCommands implements CommandExecutor {

    private final StorageSignCore plugin;


    public StoragesignCommands(StorageSignCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cThis command is players only");
            return true;
        }

        if (!player.hasPermission("storagesign.command.storagesign")) {
            player.sendMessage("§cNo Permission!");
            return true;
        }

        if (args.length > 0) {
            String sub = args[0];
            if (
                    !sub.equalsIgnoreCase("help")
                    && !sub.equalsIgnoreCase("setsignitem")
                    && !sub.equalsIgnoreCase("setsignamount")
                ) {
                player.sendMessage("§cUnknown subcommand. Use /ss help for more info.");
                return true;
            }
        }

        if (args.length == 0 || Objects.equals(args[0], "help")) {
            if (args.length > 1) {
                player.sendMessage("§cUsage: /ss help");
                return true;
            }
            sender.sendMessage("§6[StorageSign Command Help]");
            sender.sendMessage("§6/ss or /storagesign");
            sender.sendMessage("§c/ss help §7: View Help");
            sender.sendMessage("§c/ss setsignitem §7: Change the StorageSign item you are watching to the one you are holding");
            sender.sendMessage("§c/ss setsignamount [<count>] §7: Change the amount of StorageSign items displayed");
            return true;
        }

        String subCommand1 = "";
        subCommand1 = args[0];


        if (Objects.equals(subCommand1, "setsignamount") || Objects.equals(subCommand1, "setsignitem")) {
            Block targetBlock = player.getTargetBlockExact(3);
            if (targetBlock == null || !(plugin.isStorageSign(targetBlock))) {
                player.sendMessage("§cThis block is not Storage Sign");
                return true;
            }
            else {
                Sign sign = (Sign) targetBlock.getState();
                StorageSign storageSign = new StorageSign(sign,targetBlock.getType());
                if (subCommand1.equals("setsignamount")) {
                    if (!player.hasPermission("storagesign.command.storagesign.setsignamount")) {
                        player.sendMessage("§cNo Permission!");
                        return true;
                    }
                    if (args.length != 2) {
                        player.sendMessage("§cUsage: /ss setsignamount [Amount]");
                        return true;
                    }

                    try {
                        int amount = Integer.parseInt(args[1]);
                        storageSign.setAmount(amount);
                        for (int i=0; i<4; i++) sign.setLine(i, storageSign.getSigntext(i));
                        sign.update();
                        player.sendMessage("§aSet StorageSign item amount to " + amount);
                    } catch (NumberFormatException e) {
                        player.sendMessage("§cInvalid amount");
                    }
                    return true;
                }

                else {//setsignitemの場合
                    if (!player.hasPermission("storagesign.command.storagesign.setsignitem")) {
                        player.sendMessage("§cNo Permission!");
                        return true;
                    }
                    if (args.length != 1) {
                        player.sendMessage("§cUsage: /ss setsignitem");
                        return true;
                    }
                    ItemStack itemMainHand = player.getInventory().getItemInMainHand();
                    if (itemMainHand.getType() == org.bukkit.Material.AIR) {
                        player.sendMessage("§cYou must have an item in your main hand");
                        return true;
                    }

                    Material mat;
                    mat = itemMainHand.getType();
                    ItemMeta meta = itemMainHand.getItemMeta();
                    if (meta.hasDisplayName()) {
                        if (plugin.isStorageSign(itemMainHand) && meta.getDisplayName().equals("StorageSign")) {
                            storageSign.setMaterial(mat);
                            storageSign.setDamage((short) 1);
                        }
                        else {
                            storageSign.setMaterial(mat);
                            storageSign.setItemName(meta.getDisplayName());
                        }
                    }
                    else if (meta.hasItemName()) {
                        storageSign.setMaterial(mat);
                        storageSign.setItemName(meta.getItemName());
                    }
                    else if (plugin.isStorageSign(itemMainHand)) {
                        storageSign.setMaterial(mat);
                        storageSign.setDamage((short) 1);
                    }
                    else if (mat == Material.POTION || mat == Material.SPLASH_POTION || mat == Material.LINGERING_POTION)
                    {
                        storageSign.setMaterial(mat);
                        PotionMeta potionMeta = (PotionMeta)itemMainHand.getItemMeta();

                        storageSign.setPotion(
                                potionMeta.getBasePotionType()
                        );
                    }
                    else if (mat == Material.ENCHANTED_BOOK)
                    {
                        EnchantmentStorageMeta enchantMeta = (EnchantmentStorageMeta)itemMainHand.getItemMeta();
                        if(!enchantMeta.getStoredEnchants().isEmpty()) {
                            Enchantment ench = enchantMeta.getStoredEnchants().keySet().toArray(new Enchantment[0])[0];
                            storageSign.setMaterial(mat);
                            storageSign.setDamage((short) enchantMeta.getStoredEnchantLevel(ench));
                            storageSign.setEnchant(ench);
                        }
                    }
                    else
                    {
                        storageSign.setMaterial(mat);
                        storageSign.setDamage(itemMainHand.getDurability());
                    }

                    storageSign.setStoredItem(itemMainHand.clone());
                    sign.getPersistentDataContainer().set(StorageSign.getIsStorageSign(), PersistentDataType.BYTE, (byte) 1);
                    storageSign.storeItemStack(sign);
                    for (int i=0; i<4; i++) sign.setLine(i, storageSign.getSigntext(i));
                    sign.update();
                    player.sendMessage("§aSet StorageSign item");
                    return true;
                }
            }
        }
        return false;
    }
}

