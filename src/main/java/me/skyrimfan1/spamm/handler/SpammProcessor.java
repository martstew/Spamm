package me.skyrimfan1.spamm.handler;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.skyrimfan1.spamm.Spamm;
import me.skyrimfan1.spamm.util.SpammLevel;
import me.skyrimfan1.spamm.util.SpammMessaging;
import me.skyrimfan1.spamm.util.SpammPunishment;

public class SpammProcessor {
	private List<SpammPunishment> punishments;
	
	public SpammProcessor(){
		load();
	}
	
	private void load(){
		punishments = SpammPunishment.loadActive();
	}
	
	public void assess(Player player, SpammLevel level) {
		if (player.hasPermission("spamm.exempt")) {
			return;
		}
		write(player, level);
		if (level == SpammLevel.PUNISHING) {
			levyPunishments(player);
		}
	}
	
	private void levyPunishments(Player player){
		for (SpammPunishment punish : punishments) {
			switch(punish) {
			case BAN:
				player.kickPlayer(SpammMessaging.getPrefix()+ChatColor.RED+"BANNED: Now, get lost and scram!");
				player.setBanned(true);
				break;
			case KICK:
				player.kickPlayer(SpammMessaging.getPrefix()+ChatColor.RED+"KICKED: I warned you!");
				break;
			}
		}
	}
	
	
	private void write(Player player, SpammLevel level) {
		if (Spamm.getInstance().shouldLog()) {
			if (level == SpammLevel.PERMITTED) {
				return;
			}
			String log = SpammMessaging.getDate()+player.getName()+" was spamming at level: "+level.name();
			Spamm.getInstance().getSpamLog().addLine(log);
		}
	}
}
