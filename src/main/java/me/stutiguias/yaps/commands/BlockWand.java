package me.stutiguias.yaps.commands;

import me.stutiguias.yaps.init.Yaps;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BlockWand extends CommandHandler {

    public BlockWand(Yaps plugin) {
        super(plugin);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Boolean OnCommand(CommandSender sender, String[] args) {
        this.sender = sender;

        if (isInvalid(sender, args)) return true;

        Player player = (Player) sender;
        ItemStack itemStack = new ItemStack(Material.getMaterial(plugin.getConfig().getString("InfoWandID")), 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("YAPS Block Info Wand");
        itemStack.setItemMeta(itemMeta);

        player.setItemInHand(itemStack);
        SendMessage("&6Use right or left click to get information of a protected block.");
        return true;
    }

    @Override
    protected Boolean isInvalid(CommandSender sender, String[] args) {
        if (!plugin.hasPermission((Player) sender, "yaps.blockinfo")) {
            SendMessage("&4Not have permission");
            return true;
        }
        return false;
    }
}
