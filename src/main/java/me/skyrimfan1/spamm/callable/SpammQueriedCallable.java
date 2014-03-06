package me.skyrimfan1.spamm.callable;

import java.util.concurrent.Callable;

import me.skyrimfan1.spamm.Spamm;
import me.skyrimfan1.spamm.util.SpammLevel;

import org.bukkit.entity.Player;

public class SpammQueriedCallable implements Callable<Object> {
	private Player player;
	private String message;
	private boolean called;
	private SpammLevel level;
	
	public SpammQueriedCallable(Player player, String message) {
		this.player = player;
		this.message = message;
		this.called = false;
	}
	
	/**
	 * @author David
	 * 
	 * The main method of the callable
	 * Switches state to called once accessed
	 * DO NOT ACCESS DIRECTLY. Use callSyncMethod() in Bukkit to transfer to main thread
	 * 
	 * @return SpammLevel of player
	 */
	@Override
	public SpammLevel call() {
		if (hasCalled()) {
			return level;
		}
		this.called = true;
		SpammLevel level = Spamm.getInstance().getSpamHandler().log(player, message);
		this.level = level;
		return level;
	}
	
	public boolean hasCalled(){
		return called;
	}

}
