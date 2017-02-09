package com.MrSweeter.DreamCauldron;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import Events.Drop;

public class DreamCauldron extends JavaPlugin	{
	
	Logger log = Logger.getLogger("Minecraft");
	public static FileConfiguration config;
	public static YamlConfiguration items;
	public File itemsFile;
	public static PluginManager pm = Bukkit.getPluginManager();

	public void onEnable() {

		// Generate/repair config
		saveDefaultConfig();
		Config.onLoad(this, "items.yml");

		// Listen event on server
		// pm = getServer().getPluginManager();

		// EventListener
		pm.registerEvents(new Drop(this), this);

		log.info("=============== DreamCauldron enable ===============");

	}

	public void onDisable() {
		
		log.info("=============== DreamCauldron disable ===============");

	}	
}
