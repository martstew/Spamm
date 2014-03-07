package me.skyrimfan1.spamm.handler;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.skyrimfan1.spamm.Spamm;
import me.skyrimfan1.spamm.punishments.BanPunishment;
import me.skyrimfan1.spamm.punishments.KickPunishment;
import me.skyrimfan1.spamm.punishments.SpammPunishment;
import me.skyrimfan1.spamm.util.SpammLevel;
import me.skyrimfan1.spamm.util.SpammMessaging;

public class SpammProcessor {
	private List<SpammPunishment> punishments;
	
	public SpammProcessor(){
		punishments = new ArrayList<SpammPunishment>();
		loadInternally();
	}
	
	private void loadInternally(){
		if (Spamm.getInstance().getConfig().getBoolean("punishments.kick")) {
			load(new KickPunishment());
		}
		if (Spamm.getInstance().getConfig().getBoolean("punishments.ban")) {
			load(new BanPunishment());
		}
	}
	
	private void load(SpammPunishment punishment) {
		punishments.add(punishment);
	}
	
	public void loadExternally(SpammPunishment punishment){
		load(punishment);
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
			punish.execute(player);
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
