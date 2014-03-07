package me.skyrimfan1.spamm.punishments;

import me.skyrimfan1.spamm.util.SpammMessaging;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class KickPunishment implements SpammPunishment {

	@Override
	public void execute(Player player) {
		player.kickPlayer(SpammMessaging.getPrefix()+ChatColor.RED+"KICKED: I warned you!");
	}
}
