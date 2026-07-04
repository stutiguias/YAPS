package me.stutiguias.yaps.commands;

import me.stutiguias.yaps.handlers.ProtectedBlockInfoHandler;
import me.stutiguias.yaps.init.Yaps;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockInfo extends CommandHandler {

    private final ProtectedBlockInfoHandler protectedBlockInfoHandler;

    public BlockInfo(Yaps plugin) {
        super(plugin);
        protectedBlockInfoHandler = new ProtectedBlockInfoHandler(plugin);
    }

    @Override
    protected Boolean OnCommand(CommandSender sender, String[] args) {
        this.sender = sender;

        if (isInvalid(sender, args)) return true;

        protectedBlockInfoHandler.ShowTargetBlockInfo((Player) sender);
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
