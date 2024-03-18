/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.stutiguias.yaps.listener;

import me.stutiguias.yaps.handlers.YAPSItemHandler;
import me.stutiguias.yaps.init.Yaps;
import me.stutiguias.yaps.init.Util;
import me.stutiguias.yaps.model.Area;
import me.stutiguias.yaps.model.BlockProtected;
import me.stutiguias.yaps.model.Save;

import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 *
 * @author Daniel
 */
public class PlayerListener extends Util implements Listener {

	private final YAPSItemHandler yapsItemHandler;

	public PlayerListener(Yaps plugin) {
		super(plugin);
		yapsItemHandler = new YAPSItemHandler(plugin);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) {}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlace(BlockPlaceEvent event) {

		String block = event.getBlock().getType().name();
		Player player = (Player) event.getPlayer();
		Location location = event.getBlock().getLocation();

		Area area = isInsideArea(location);

		onBlockProtect(area, location, block, player);

		if (area == null) return;

		if (!isValidEvent(area, player, location, "place")) {
			if (plugin.hasPermission(player, "yaps.bypass"))	return;
			event.setCancelled(true);
			SendMessage(player, "&4Sorry! &7You are not allowed to place blocks there!");
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent event) {

		Location location = event.getBlock().getLocation();
		Player player = (Player) event.getPlayer();
		BlockProtected blockProtected = null;
		boolean isBlockProtect;

		if (Yaps.config.SearchAgainstMemory) {
			isBlockProtect = Yaps.Protected.containsKey(location);
		} else {
			blockProtected = Yaps.db.GetProtect(location);
			isBlockProtect = blockProtected != null;
		}

		if (Yaps.config.SaveQueue && !Yaps.SaveList.isEmpty()) {
			for (Save save : Yaps.SaveList) {
				boolean equalsLocation = save.getBlockProtected().getLocation().equals(location);
				boolean equalsPlayer = save.getBlockProtected().getOwner().equals(player.getName());
				if (equalsLocation && equalsPlayer) {
					blockProtected = save.getBlockProtected();
					isBlockProtect = true;
					break;
				}
			}
		}

		if (isBlockProtect) {

			if (Yaps.config.SearchAgainstMemory) {
				blockProtected = Yaps.Protected.get(location);
			}

			if (!blockProtected.getOwner().equals(event.getPlayer().getName())) {
				SendMessage(player, "Can't remove block protected");
				event.setCancelled(true);
				return;
			}

			if (Yaps.config.SearchAgainstMemory)
				Yaps.Protected.remove(location);

			if (Yaps.config.SaveQueue) {
				Yaps.SaveList.add(new Save("remove", new BlockProtected(location, "", "")));
			} else {
				Yaps.db.RemoveProtect(location);
			}

			SendMessage(player, "Block protected break");
		}

		Area area = isInsideArea(location);

		if (area == null)
			return;

		if (!isValidEvent(area, player, location, "break")) {
			if (plugin.hasPermission(player, "yaps.bypass"))
				return;
			event.setCancelled(true);
			SendMessage(player, "&4Sorry! &7You are not allowed to break blocks there!");
		}

	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onMoveInside(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Location location = event.getTo();

		Area area = isInsideArea(location);

		if (area == null) return;

		if (!isValidEvent(area, player, location, "move")) {
			if (plugin.hasPermission(player, "yaps.bypass"))
				return;
			if (area.getExit() == null) {
				Location tpTo = event.getFrom();
//				tpTo.setZ(event.getFrom().getZ() - 1);
			    // tpTo;
				player.teleport(tpTo);
			} else {
				player.teleport(area.getExit());
			}
			SendMessage(player, "&4Sorry! &7You are not allowed to go in there!");
		}

	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void PlayerInteract(PlayerInteractEvent event) {
		if(yapsItemHandler.isValidYAPSItemEvent(event)) yapsItemHandler.Process(event);
	}

	public boolean isValidEvent(Area area, Player player, Location location, String event) {
		switch (event) {
		case "place":
			return isValidPlace(area, player);
		case "break":
			return isValidBreak(area, player);
		case "move":
			return isValidMove(area, player);
		default:
			return true;
		}
	}

	public boolean isValidPlace(Area area, Player player) {
		return isAllowed(area, player);
	}

	public boolean isValidBreak(Area area, Player player) {
		return isAllowed(area, player);
	}

	public boolean isValidMove(Area area, Player player) {

		if (area.getFlags().contains("AllowMoveInside"))
			return true;

		return isAllowed(area, player);
	}

	public boolean isAllowed(Area area, Player player) {
		return area.getOwner().equals(player.getName());
	}

	private Area isInsideArea(Location location) {
		if (Yaps.Areas.isEmpty())
			return null;
		return plugin.getArea(location);
	}

	private void Protect(Location location, Player player, String block) {
		BlockProtected blockProtected = new BlockProtected(location, player.getName(), block);

		if (Yaps.config.SearchAgainstMemory)
			Yaps.Protected.put(location, blockProtected);

		if (Yaps.config.SaveQueue) {
			Yaps.SaveList.add(new Save("add", blockProtected));
		} else {
			Yaps.db.InsertProtect(blockProtected);
		}

		SendMessage(player, "You place protected block");
	}

	private void onBlockProtect(Area area, Location location, String block, Player player) {
		if (Yaps.notProtectPlace.contains(player.getName())) return;
		if (!Yaps.config.Protected.contains(block))	return;
		if (area != null && !Yaps.config.AllowProtectedBlockInsideArea)	return;
		Protect(location, player, block);
	}
}
