package me.skyrimfan1.spamm.handler;

import me.skyrimfan1.spamm.Spamm;
import me.skyrimfan1.spamm.util.SpammLevel;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class SpammTracker {
	private Player player;
	private String lastMessage;
	private int count;
	private boolean isAccepting;
	private SpammLevel level;
	private BukkitTask task; 
	
	public SpammTracker(Player player) {
		this.player = player;
		this.isAccepting = true;
		this.count = 0;
		this.level = SpammLevel.PERMITTED;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	/**
	 * Logs a message
	 * Usually only Spamm handles this but can be externally used
	 * 
	 * @param last The last message a player said
	 * @return Their current SpammLevel with the latest string inputted
	 */
	public SpammLevel logMessage(String last) {
		if (task != null)
			task.cancel();
		if (isAccepting == false) {
			count += 1;
		}
		if (count < Spamm.getInstance().getWarningCount()) {
			this.level = SpammLevel.PERMITTED;
		}
		if (count >= Spamm.getInstance().getWarningCount() && count < Spamm.getInstance().getPunishingCount()) {
			this.level = SpammLevel.WARNING;
		}
		if (count >= Spamm.getInstance().getPunishingCount()) {
			this.level = SpammLevel.PUNISHING;
		}	
		this.lastMessage = last;
		isAccepting = false;
		task = Spamm.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(Spamm.getInstance(), new Runnable(){

			@Override
			public void run() {
				isAccepting = true;
				if (count > 0) {
					count -= 1;
				}
			}
			
		}, Spamm.getInstance().getDelay(), Spamm.getInstance().getCooldown());
		return level;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getLastMessage() {
		return lastMessage;
	}
	
	public SpammLevel getLevel() {
		return level;
	}
	
	public int getCount(){
		return count;
	}
}
