package me.stutiguias.yaps.handlers;

import me.stutiguias.yaps.init.Util;
import me.stutiguias.yaps.init.Yaps;
import me.stutiguias.yaps.model.BlockProtected;
import me.stutiguias.yaps.model.Save;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Set;

public class ProtectedBlockInfoHandler extends Util {

    public final String MsgHr = "&e-----------------------------------------------------";

    public ProtectedBlockInfoHandler(Yaps plugin) {
        super(plugin);
    }

    public void ShowTargetBlockInfo(Player player) {
        Block block = player.getTargetBlock((Set<Material>) null, 100);
        if (block == null) {
            SendMessage(player, "&4No block targeted");
            return;
        }

        ShowBlockInfo(player, block.getLocation());
    }

    public void ShowBlockInfo(Player player, Location location) {
        BlockProtected blockProtected = GetProtectedBlock(location);

        SendMessage(player, MsgHr);
        if (blockProtected == null) {
            SendMessage(player, "&6This block is &4not &6protected");
            SendMessage(player, "&3Location: &6%s", new Object[] { FormatLocation(location) });
            SendMessage(player, MsgHr);
            return;
        }

        SendMessage(player, "&6This block is protected");
        SendMessage(player, "&3Owner: &6%s", new Object[] { blockProtected.getOwner() });
        SendMessage(player, "&3Block: &6%s", new Object[] { blockProtected.getBlock() });
        SendMessage(player, "&3Location: &6%s", new Object[] { FormatLocation(blockProtected.getLocation()) });
        SendMessage(player, MsgHr);
    }

    public BlockProtected GetProtectedBlock(Location location) {
        BlockProtected blockProtected = null;

        if (Yaps.config.SearchAgainstMemory) {
            blockProtected = Yaps.Protected.get(location);
        } else {
            blockProtected = Yaps.db.GetProtect(location);
        }

        if (Yaps.config.SaveQueue && !Yaps.SaveList.isEmpty()) {
            for (Save save : Yaps.SaveList) {
                if (save.getBlockProtected() == null) continue;
                if (!save.getBlockProtected().getLocation().equals(location)) continue;

                if (save.getFunction().contains("add")) blockProtected = save.getBlockProtected();
                if (save.getFunction().contains("remove")) blockProtected = null;
            }
        }

        return blockProtected;
    }

    private String FormatLocation(Location location) {
        return String.format("%s,%s,%s,%s", new Object[] {
            location.getWorld().getName(),
            location.getBlockX(),
            location.getBlockY(),
            location.getBlockZ()
        });
    }
}
