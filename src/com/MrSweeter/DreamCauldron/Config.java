package com.MrSweeter.DreamCauldron;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	public static void onLoad(DreamCauldron main, String name) {

		main.itemsFile = Config.create(main, name);
		main.items = Config.load(main.itemsFile);

		if (main.itemsFile.length() == 0) {
			Config.edit(main.items);
		}

		if (main.items.getBoolean("reset")) {
			main.log.info("========== Configuration: reset has occured ==========");
			Config.edit(main.items);
		}

		if (!main.items.contains("reset")) {

			Date date = new Date();
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd'-'HH'.'mm'.'ss");
			String str = f.format(date);

			File configFileBroken = Config.copy(main, main.itemsFile,
					"MessageEdit-" + str + ".yml.broken");
			YamlConfiguration configBroken = Config.load(configFileBroken);

			main.log.warning("========== Configuration error : reset has occured, broken file generated ==========");
			Config.edit(main.items);
		}

		try {
			main.items.save(main.itemsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static File create(DreamCauldron main, String name) {

		if (!main.getDataFolder().exists()) {
			main.getDataFolder().mkdir();
		}

		File configFile = new File(main.getDataFolder() + File.separator + name);
		File readMeFile = new File(main.getDataFolder() + File.separator + "READ ME.txt");
		if (!configFile.exists()) {

			try {
				configFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!readMeFile.exists()) {
			FileWriter readMe = Config.edit(readMeFile);
		}
		return configFile;
	}

	public static YamlConfiguration load(File configFile) {

		return YamlConfiguration.loadConfiguration(configFile);

	}

	public static void edit(YamlConfiguration config) {
		
		config.set("drops.diamond", 10);
		config.set("drops.emerald", 10);
		config.set("drops.gold_ingot", 30);
		config.set("drops.iron_ingot", 50);
		
		List<String> to_drop = new ArrayList<>();
		to_drop.add("redstone");
		to_drop.add("coal");
		to_drop.add("nether_star");
		
		config.set("drops_allow", to_drop);
		config.set("reset", Boolean.FALSE);

	}

	public static FileWriter edit(File file) {

		FileWriter fileW = null;

		try {
			file.createNewFile();
			fileW = new FileWriter(file);
			fileW.write("########## CONFIGURATION ##########");
			fileW.write("\r\n\r\nreset: false #Do not change the value except for reset");
			fileW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileW;
	}
	
	// Code: https://www.mkyong.com/java/how-to-copy-file-in-java/
	public static File copy(DreamCauldron main, File a, String name) {

		InputStream inStream = null;
		OutputStream outStream = null;

		File b = new File(main.getDataFolder() + File.separator + name);

		try {

			inStream = new FileInputStream(a);
			outStream = new FileOutputStream(b);

			byte[] buffer = new byte[1024];

			int length;

			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}

			inStream.close();
			outStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return b;

	}
}
