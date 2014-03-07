package me.skyrimfan1.spamm.api;

import org.bukkit.entity.Player;

import me.skyrimfan1.spamm.Spamm;
import me.skyrimfan1.spamm.exceptions.NoTrackerFoundException;
import me.skyrimfan1.spamm.handler.SpammTracker;
import me.skyrimfan1.spamm.punishments.SpammPunishment;

public class SpammAPI {

	/**
	 * Loads an externally-created punishment
	 * 
	 * @param punishment The punishment to load
	 */
	public static void loadPunishment(SpammPunishment punishment) {
		Spamm.getInstance().getSpamProcessor().loadExternally(punishment);
	}
	
	/**
	 * Returns a logged-in player's tracker
	 * If the plugin could not find a player's tracker, it will return null
	 * 
	 * SpammTrackers store data about a player's last message as well as their current spam count
	 * Please, use caution when modifying it
	 * 
	 * @param player The player to get the tracker for
	 * @return The player's spam tracker
	 */
	public static SpammTracker getTracker(Player player) {
		try {
			return Spamm.getInstance().getSpamHandler().getTracker(player);
		} catch (NoTrackerFoundException e) {
			return null;
		}
	}
}
