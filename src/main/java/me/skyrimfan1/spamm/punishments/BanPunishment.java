package me.skyrimfan1.spamm.punishments;

import me.skyrimfan1.spamm.util.SpammMessaging;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BanPunishment implements SpammPunishment {

	@Override
	public void execute(Player player) {
		player.kickPlayer(SpammMessaging.getPrefix()+ChatColor.RED+"BANNED: Now, get lost and scram!");
		player.setBanned(true);
	}


}
