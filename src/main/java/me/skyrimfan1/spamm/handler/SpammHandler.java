package me.skyrimfan1.spamm.handler;

import java.util.ArrayList;
import java.util.List;

import me.skyrimfan1.spamm.Spamm;
import me.skyrimfan1.spamm.exceptions.NoTrackerFoundException;
import me.skyrimfan1.spamm.util.SpammLevel;

import org.bukkit.entity.Player;

public class SpammHandler {
	private List<SpammTracker> trackers = new ArrayList<SpammTracker>();
	
	public void track(Player player) {
		SpammTracker tracker = new SpammTracker(player);
		trackers.add(tracker);
	}
	
	public void untrack(Player player) {
		try {
			SpammTracker tracker = getTracker(player);
			List<SpammTracker> newTrackers = new ArrayList<SpammTracker>();
			for (SpammTracker track : trackers) {
				if (track != tracker) {
					newTrackers.add(track);
				}
			}
			trackers = newTrackers;
		} catch (NoTrackerFoundException e) {
			Spamm.getInstance().log.severe(e.mishap);
		}
	}
	
	public SpammLevel log(Player player, String message) {
		try {
			SpammTracker tracker = getTracker(player);
			SpammLevel level = tracker.logMessage(message);
			if (player.hasPermission("spamm.exempt") == false) {
				Spamm.getInstance().getSpamProcessor().assess(player, level);
			}
			return level;
		} catch (NoTrackerFoundException e) {
			Spamm.getInstance().log.severe(e.mishap);
			return null;
		}
	}
	
	public boolean isTracking(Player player) {
		for (SpammTracker track : trackers) {
			if (track.getPlayer() == player) {
				return true;
			}
		}
		return false;
	}
	
	public SpammTracker getTracker(Player player) throws NoTrackerFoundException {
		for (SpammTracker track : trackers) {
			if (track.getPlayer() == player) {
				return track;
			}
		}
		throw new NoTrackerFoundException(player);
	}
}
