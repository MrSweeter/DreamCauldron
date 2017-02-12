package com.MrSweeter.DreamCauldron;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import CommandsDC.Commands;
import Events.Drop;

public class DreamCauldron extends JavaPlugin {

	Logger log = Logger.getLogger("Minecraft");
	public static FileConfiguration config;
	public static PluginManager pm = Bukkit.getPluginManager();

	public void onEnable() {

		saveDefaultConfig();

		pm.registerEvents(new Drop(this), this);
		getCommand("dcreload").setExecutor(new Commands(this));

		log.info(Color.GREEN + "=============== " + Color.YELLOW + "DreamCauldron enable" + Color.GREEN + " ===============" + Color.RESET);

	}

	public void onDisable() {

		log.info(Color.GREEN + "=============== " + Color.YELLOW + "DreamCauldron disable" + Color.GREEN + " ===============" + Color.RESET);

	}
}
