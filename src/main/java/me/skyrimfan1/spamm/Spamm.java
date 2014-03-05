package me.skyrimfan1.spamm;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import me.skyrimfan1.spamm.command.SpammCommand;
import me.skyrimfan1.spamm.files.SpammLogFile;
import me.skyrimfan1.spamm.handler.SpammHandler;
import me.skyrimfan1.spamm.handler.SpammProcessor;
import me.skyrimfan1.spamm.listener.SpammUniversalListener;
import me.skyrimfan1.spamm.listener.exceptions.SpamCountRegistrationException;
import me.skyrimfan1.spamm.util.Metrics;
import me.skyrimfan1.spamm.util.Updater;
import me.skyrimfan1.spamm.util.Updater.UpdateResult;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Spamm extends JavaPlugin{
	public Logger log = Logger.getLogger("Minecraft");
	private SpammLogFile logFile;
	private SpammHandler handler;
	private SpammProcessor processor;
	private SpammUniversalListener listener;
	private Metrics metrics;
	
	@Override
	public void onEnable(){
		update();
		metrics();
		handler = new SpammHandler();
		processor = new SpammProcessor();
		logFile = new SpammLogFile(new File(getDataFolder(), "spamlog.txt"));
		checkCount();
		doConfig();
		doListener();
		doCommands();
		this.log.info("[Spamm] So, I hear you don't like to ... spammmmmm?");
	}
	
	@Override
	public void onDisable(){
		this.log.info("[Spamm] Laters!");
		this.log.info("[Spamm] Laters!");
		this.log.info("[Spamm] Laters!");
		this.log.info("[Spamm] Teehee ... got a bit carried away!");
	}
	
	public static Spamm getInstance(){
		return (Spamm) Bukkit.getPluginManager().getPlugin("Spamm");
	}
	
	public SpammHandler getSpamHandler(){
		return handler;
	}
	
	public SpammProcessor getSpamProcessor(){
		return processor;
	}
	
	
	public SpammLogFile getSpamLog() {
		return logFile;
	}
	
	private void update(){
		Updater updater = new Updater(this, 75425, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, true);
		if (updater.getResult() == UpdateResult.SUCCESS) {
			if (shouldUpdate()) {
				new Updater(this, 75425, this.getFile(), Updater.UpdateType.NO_VERSION_CHECK, true);
				this.log.info("[Spamm] Automatically downloaded latest version: "+updater.getLatestName());
			}
			else {
				this.log.warning("[Spamm] An updated version was found: "+updater.getLatestName());
				this.log.warning("[Spamm] To download it, use the command: /spamm update");
			}
		}
		else {
			this.log.severe("[Spamm] Ignored updater due to complications.");
		}
	}
	
	private void metrics(){
		if (shouldMetricize()) {
			try {
				this.metrics = new Metrics(this);
				this.metrics.start();
				this.log.info("[Spamm] Initialized metrics.");
			} catch (IOException e) {
				log.severe("[Spamm] Ignored metrics due to complications.");
			}
		}
		else {
			this.log.info("[Spamm] Will not use metrics.");
		}
	}
	
	private void checkCount() {
		if (getWarningCount() >= getPunishingCount()) {
			throw new SpamCountRegistrationException(getWarningCount(), getPunishingCount());
		}
		else {
			this.log.info("[Spamm] WARNING Value: "+getWarningCount());
			this.log.info("[Spamm] PUNISHMENT Value: "+getPunishingCount());
		}
	}
	
	private void doConfig(){
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	private void doListener(){
		listener = new SpammUniversalListener();
		this.getServer().getPluginManager().registerEvents(listener, this);
	}
	
	private void doCommands(){
		getCommand("spamm").setExecutor(new SpammCommand());
	}
	
	public int getDelay(){
		return getConfig().getInt("delay");
	}
	
	public int getCooldown(){
		return getConfig().getInt("cooldown");
	}
	
	public int getWarningCount(){
		return getConfig().getInt("count.warning");
	}
	
	public int getPunishingCount(){
		return getConfig().getInt("count.punishment");
	}
	
	public boolean shouldLog(){
		return getConfig().getBoolean("log");
	}
	
	public boolean shouldUpdate(){
		return getConfig().getBoolean("update");
	}
	
	private boolean shouldMetricize(){
		return getConfig().getBoolean("metrics");
	}
}
