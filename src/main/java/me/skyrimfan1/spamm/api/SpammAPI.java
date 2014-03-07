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
	
	private static SpammTracker getTracker(Player player) {
		try {
			return Spamm.getInstance().getSpamHandler().getTracker(player);
		} catch (NoTrackerFoundException e) {
			return null;
		}
	}
	
	/**
	 * Returns a player's spam count
	 * Spam counts show how many times a player has said consecutive messages within a config-dependent delay time
	 * 
	 * @param player The player to get the count for
	 * @return The player's current spam count
	 */
	public static Integer getSpamCount(Player player) {
		return getTracker(player).getCount();
	}
	
	/**
	 * Returns the last message a player said
	 * 
	 * @param player The player to get it for
	 * @return What they last said
	 */
	public static String getLastMessage(Player player) {
		return getTracker(player).getLastMessage();
	}
}
