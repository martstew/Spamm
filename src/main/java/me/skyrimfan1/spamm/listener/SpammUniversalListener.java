package me.skyrimfan1.spamm.listener;

import java.util.concurrent.Future;

import me.skyrimfan1.spamm.Spamm;
import me.skyrimfan1.spamm.api.events.PlayerSpamEvent;
import me.skyrimfan1.spamm.callable.SpammQueriedCallable;
import me.skyrimfan1.spamm.exceptions.AsyncCallableException;
import me.skyrimfan1.spamm.exceptions.NoTrackerFoundException;
import me.skyrimfan1.spamm.util.SpammLevel;
import me.skyrimfan1.spamm.util.SpammMessaging;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SpammUniversalListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(final AsyncPlayerChatEvent event) {
		if (event.getPlayer().hasPermission("spamm.exempt")) {
			return;
		}
		if (event.isAsynchronous()) {
			SpammQueriedCallable callable = new SpammQueriedCallable(event.getPlayer(), event.getMessage());
			Future<Object> future = Spamm.getInstance().getServer().getScheduler().callSyncMethod(Spamm.getInstance(), callable);
			try {
				SpammLevel level = (SpammLevel) future.get();
				if (level == SpammLevel.WARNING) {
					event.getPlayer().sendMessage(SpammMessaging.getPrefix()+ChatColor.RED+"Message ("+Spamm.getInstance().getSpamHandler().getTracker(event.getPlayer()).getLastMessage()+ChatColor.RED+") spammed: "+ChatColor.DARK_GREEN+Spamm.getInstance().getSpamHandler().getTracker(event.getPlayer()).getCount()+" times");
					event.setMessage(ChatColor.RESET+""+ChatColor.STRIKETHROUGH+ChatColor.stripColor(event.getMessage()));
					PlayerSpamEvent spamEvent = new PlayerSpamEvent(event.getPlayer(), Spamm.getInstance().getSpamHandler().getTracker(event.getPlayer()).getCount(), level, event.getMessage());
					Bukkit.getPluginManager().callEvent(spamEvent);
				}
				else if (level == SpammLevel.PUNISHING) {
					event.getPlayer().sendMessage(SpammMessaging.getPrefix()+ChatColor.DARK_RED+"Muted temporarily for persistent spamming.");
					event.setCancelled(true);
					PlayerSpamEvent spamEvent = new PlayerSpamEvent(event.getPlayer(), Spamm.getInstance().getSpamHandler().getTracker(event.getPlayer()).getCount(), level, event.getMessage());
					Bukkit.getPluginManager().callEvent(spamEvent);
				}
			} catch (NoTrackerFoundException ex) {
				if (event.getPlayer().isOnline()) {
					Spamm.getInstance().log.severe("Unable to locate "+event.getPlayer().getName()+"'s tracker.");
				}
			} catch (Exception e) {
				throw new AsyncCallableException(e);
			}

		}
		else {
			SpammLevel level = Spamm.getInstance().getSpamHandler().log(event.getPlayer(), event.getMessage());
			if (level == SpammLevel.WARNING) {
				try {
					event.getPlayer().sendMessage(SpammMessaging.getPrefix()+ChatColor.RED+"Message ("+Spamm.getInstance().getSpamHandler().getTracker(event.getPlayer()).getLastMessage()+ChatColor.RED+") spammed: "+ChatColor.DARK_GREEN+Spamm.getInstance().getSpamHandler().getTracker(event.getPlayer()).getCount()+" times");
				} catch (NoTrackerFoundException e) {
					if (event.getPlayer().isOnline()) {
						Spamm.getInstance().log.severe("Unable to locate "+event.getPlayer().getName()+"'s tracker.");
					}
				}
				event.setMessage(ChatColor.RESET+""+ChatColor.STRIKETHROUGH+ChatColor.stripColor(event.getMessage()));
			}
			else if (level == SpammLevel.PUNISHING) {
				event.getPlayer().sendMessage(SpammMessaging.getPrefix()+ChatColor.DARK_RED+"Muted temporarily for persistent spamming.");
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Spamm.getInstance().getSpamHandler().track(event.getPlayer());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Spamm.getInstance().getSpamHandler().untrack(event.getPlayer());
	}
}
