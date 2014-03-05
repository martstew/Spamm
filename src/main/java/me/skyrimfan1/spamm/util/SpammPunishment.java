package me.skyrimfan1.spamm.util;

import java.util.ArrayList;
import java.util.List;

import me.skyrimfan1.spamm.Spamm;

public enum SpammPunishment {

	KICK("kick"),
	BAN("ban");
	
	private String config;
	
	private SpammPunishment(String config) {
		this.config = config;
	}
	
	public static List<SpammPunishment> loadActive(){
		List<SpammPunishment> active = new ArrayList<SpammPunishment>();
		for (SpammPunishment punish : values()) {
			if (Spamm.getInstance().getConfig().getBoolean("punishments."+punish.config)) {
				active.add(punish);
			}
		}
		return active;
	}
}
