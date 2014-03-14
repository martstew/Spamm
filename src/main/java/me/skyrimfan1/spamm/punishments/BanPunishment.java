package me.skyrimfan1.spamm.punishments;

import me.skyrimfan1.spamm.Spamm;

import org.bukkit.BanList.Type;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BanPunishment implements SpammPunishment {

	@Override
	public void execute(Player player) {
		player.kickPlayer(ChatColor.DARK_RED+"BANNED:"+ChatColor.RED+" Scram, spammer!");
		Spamm.getInstance().getServer().getBanList(Type.NAME).addBan(player.getName(), "Spamming", null, null);
	}


}
