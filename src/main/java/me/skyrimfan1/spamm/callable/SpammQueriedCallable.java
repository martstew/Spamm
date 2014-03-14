package me.skyrimfan1.spamm.callable;

import java.util.concurrent.Callable;

import me.skyrimfan1.spamm.Spamm;
import me.skyrimfan1.spamm.util.SpammLevel;

import org.bukkit.entity.Player;

public class SpammQueriedCallable implements Callable<Object> {
	private Player player;
	private String message;
	
	public SpammQueriedCallable(Player player, String message) {
		this.player = player;
		this.message = message;
	}
	
	/**
	 * @author David
	 * 
	 * The main method of the callable
	 * DO NOT ACCESS DIRECTLY. Use callSyncMethod() in Bukkit to transfer to main thread
	 * 
	 * @return SpammLevel of player
	 */
	@Override
	public SpammLevel call() {
		SpammLevel level = Spamm.getInstance().getSpamHandler().log(player, message);
		return level;
	}

}
