package me.stutiguias.yaps.handlers;

import me.stutiguias.yaps.init.Util;
import me.stutiguias.yaps.init.Yaps;
import me.stutiguias.yaps.model.Area;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Set;

public class YAPSItemHandler extends Util {

    public String type;
    public final String MsgHr = "&e-----------------------------------------------------";

    public YAPSItemHandler(Yaps plugin) {
        super(plugin);
    }

    public boolean isValidYAPSItemEvent(PlayerInteractEvent event) {
        if (!event.hasItem()) return false;
        if (event.getItem() == null) return false;
        if (!event.getItem().hasItemMeta()) return false;
        if (event.getItem().getItemMeta() == null) return false;
        if (!event.getItem().getItemMeta().hasDisplayName()) return false;
        if (!event.getItem().getItemMeta().getDisplayName().startsWith("YAPS")) return false;
        type = event.getItem().getItemMeta().getDisplayName();
        return true;
    }

    public void Process(PlayerInteractEvent event) {
        if (type.equals("YAPS Wand")) YAPSWand(event, event.getPlayer());
        YAPSInfoWand(event, event.getPlayer());
    }

    public void YAPSWand(PlayerInteractEvent event, Player player){
        if (!event.hasBlock()) return;
        if (!player.hasPermission("yaps.info")) return;
        if (!Yaps.AreaCreating.containsKey(player)) Yaps.AreaCreating.put(player, new Area());

        Location location = event.getClickedBlock().getLocation();
        String setOn = String.format("( %s,%s )", new Object[] { location.getX(), location.getZ() });

        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            Yaps.AreaCreating.get(player).setFirstSpot(location);
            SendMessage(player, "&6First Spot Set on %s", new Object[] { setOn });
            event.setCancelled(true);
        }

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Yaps.AreaCreating.get(player).setSecondSpot(location);
            SendMessage(player, "&6Second Spot Set on %s", new Object[] { setOn });
            event.setCancelled(true);
        }
    }

    public void YAPSInfoWand(PlayerInteractEvent event,Player player) {
        if(!player.hasPermission("yaps.infowand")) return;
        Block b = player.getTargetBlock((Set<Material>) null, 100);
        Location location = b.getLocation();
        int index = plugin.getAreaIndex(location);

        if(index == -1) {
            SendMessage(player, "&4 Not inside any area");
            event.setCancelled(true);
            return;
        }
        Area area = Yaps.Areas.get(index);

        SendMessage(player, MsgHr);
        SendMessage(player, "&3Name: &6%s", new Object[]{ area.getName() });
        SendMessage(player, "&3Owner: &6%s", new Object[]{ area.getOwner() });
        SendMessage(player, "&3Flags: &6%s", new Object[]{ area.getFlags() });
        SendMessage(player, MsgHr);

        event.setCancelled(true);

    }
}
