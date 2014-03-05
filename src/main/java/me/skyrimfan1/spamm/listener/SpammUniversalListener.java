package me.skyrimfan1.spamm.listener;

import me.skyrimfan1.spamm.Spamm;
import me.skyrimfan1.spamm.util.SpammLevel;
import me.skyrimfan1.spamm.util.SpammMessaging;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SpammUniversalListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent event) {
		SpammLevel level = Spamm.getInstance().getSpamHandler().log(event.getPlayer(), event.getMessage());
		if (event.getPlayer().hasPermission("spamm.exempt")) {
			return;
		}
		if (level == SpammLevel.WARNING) {
			event.getPlayer().sendMessage(SpammMessaging.getPrefix()+ChatColor.AQUA+"Stop spamming! You will be punished.");
		}
		else if (level == SpammLevel.PUNISHING) {
			event.getPlayer().sendMessage(SpammMessaging.getPrefix()+ChatColor.DARK_RED+"Muted temporarily. No one can hear you!");
			event.setCancelled(true);
		}
		/* Debug stuffs
		try {
			SpammTracker track = Spamm.getInstance().getSpamHandler().getTracker(event.getPlayer());
			event.getPlayer().sendMessage(""+track.getCount());
		} catch (NoTrackerFoundException e) {
			e.printStackTrace();
		}
		*/
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
