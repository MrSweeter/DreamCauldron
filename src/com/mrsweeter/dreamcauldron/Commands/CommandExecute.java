package com.mrsweeter.dreamcauldron.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.mrsweeter.dreamcauldron.DreamCauldron;

public class CommandExecute {

	public static void createCauldron(Player sender, DreamCauldron pl, String[] args) {
		
		int x = sender.getLocation().getBlockX();
		int y = sender.getLocation().getBlockY();
		int z = sender.getLocation().getBlockZ();
		World w = sender.getWorld();
		String name = args[0].trim();
		String type = "";
		if (args.length > 1)	{
			type = args[1].trim();
		}
		FileConfiguration config = pl.getConfig();
		
		if (!config.contains(name))	{
		
			Location loc = new Location(w, x+0.5, y+1, z+0.5);
			
			switch (type)	{
			case "ores":
				editConfigCauldron(name, w, x, y, z, Particle.EXPLOSION_NORMAL, Sound.BLOCK_END_GATEWAY_SPAWN, config);
				// drops
				config.set(name + ".drops.DIAMOND_ORE", 5);
				config.set(name + ".drops.EMERALD_ORE", 5);
				config.set(name + ".drops.GOLD_ORE", 15);
				config.set(name + ".drops.IRON_ORE", 15);
				config.set(name + ".drops.REDSTONE_ORE", 20);
				config.set(name + ".drops.COAL_ORE", 25);
				config.set(name + ".drops.LAPIS_ORE", 15);
				// drops_allow
				editDropAllowCauldron(name, config, "COAL_ORE", "REDSTONE_ORE", "OBSIDIAN");
				break;
			case "nether":
				editConfigCauldron(name, w, x, y, z, Particle.DRAGON_BREATH, Sound.ENTITY_WITCH_DRINK, config);
				// drops
				config.set(name + ".drops.QUARTZ_ORE", 10);
				config.set(name + ".drops.GLOWSTONE", 20);
				config.set(name + ".drops.NETHER_STAR", 1);
				config.set(name + ".drops.NETHER_WART", 20);
				config.set(name + ".drops.RED_NETHER_BRICK", 15);
				config.set(name + ".drops.NETHER_WART_BLOCK", 9);
				config.set(name + ".drops.NETHER_BRICK", 25);
				// drops_allow
				editDropAllowCauldron(name, config, "NETHERRACK", "SOUL_SAND", "GLOWSTONE");
				break;
			case "decoration":
				editConfigCauldron(name, w, x, y, z, Particle.TOTEM, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, config);
				// drops
				config.set(name + ".drops.ENCHANTING_TABLE", 10);
				config.set(name + ".drops.BOOKSHELF", 13);
				config.set(name + ".drops.BRICK_BLOCK", 14);
				config.set(name + ".drops.NOTEBLOCK", 13);
				config.set(name + ".drops.JUKEBOX", 13);
				config.set(name + ".drops.ANVIL", 12);
				config.set(name + ".drops.SEA_LANTERN", 12);
				config.set(name + ".drops.HAY_BLOCK", 12);
				config.set(name + ".drops.BEACON", 1);
				// drops_allow
				editDropAllowCauldron(name, config, "CRAFTING_TABLE", "FURNACE", "BOOKSHELF", "NOTEBLOCK", "JUKEBOX");
				break;
			case "redstone":
				editConfigCauldron(name, w, x, y, z, Particle.REDSTONE, Sound.BLOCK_CHEST_LOCKED, config);
				// drops
				config.set(name + ".drops.REPEATER", 12);
				config.set(name + ".drops.COMPARATOR", 15);
				config.set(name + ".drops.OBSERVER", 15);
				config.set(name + ".drops.STICKY_PISTON", 15);
				config.set(name + ".drops.PISTON", 13);
				config.set(name + ".drops.HOPPER", 14);
				config.set(name + ".drops.TRIPWIRE_HOOK", 13);
				// drops_allow
				editDropAllowCauldron(name, config, "REDSTONE", "PISTON", "HOPPER", "REPEATER");
				break;
			case "disc":
				editConfigCauldron(name, w, x, y, z, Particle.NOTE, Sound.BLOCK_NOTE_BASEDRUM, config);
				// drops
				config.set(name + ".drops.RECORD_11", 9);
				config.set(name + ".drops.RECORD_13", 9);
				config.set(name + ".drops.RECORD_BLOCKS", 9);
				config.set(name + ".drops.RECORD_CAT", 9);
				config.set(name + ".drops.RECORD_CHIRP", 8);
				config.set(name + ".drops.RECORD_FAR", 8);
				config.set(name + ".drops.RECORD_MALL", 8);
				config.set(name + ".drops.RECORD_MELLOHI", 8);
				config.set(name + ".drops.RECORD_STAL", 8);
				config.set(name + ".drops.RECORD_STRAD", 8);
				config.set(name + ".drops.RECORD_WAIT", 8);
				config.set(name + ".drops.RECORD_WARD", 8);
				// drops_allow
				editDropAllowCauldron(name, config, "RECORD_11", "RECORD_13", "RECORD_BLOCKS",
						"RECORD_CAT", "RECORD_CHIRP", "RECORD_FAR",
						"RECORD_MALL","RECORD_MELLOHI","RECORD_STAL",
						"RECORD_STRAD", "RECORD_WAIT", "RECORD_WARD");
				break;
			default:
				editConfigCauldron(name, w, x, y, z, Particle.END_ROD, Sound.BLOCK_BREWING_STAND_BREW, config);
				// drops
				config.set(name + ".drops.BEDROCK", 100);
				// drops_allow
				editDropAllowCauldron(name, config, "BEDROCK");
				sender.sendMessage("§c[§aDreamCauldron§c] §7No type define, loading default config");
				break;
			}
			pl.saveConfig();
			sender.getLocation().getBlock().setType(Material.CAULDRON);
			sender.sendMessage("§c[§aDreamCauldron§c] §7Cauldron created");
			sender.teleport(loc);
		} else {
			sender.sendMessage("§c[§aDreamCauldron§c] §7The name receive is already use for an other cauldron, please check config.yml");
		}
	}

	public static void removeCauldron(Player sender, DreamCauldron pl, String name) {
		
		Set<String> cauldrons = pl.getConfig().getKeys(false);
		FileConfiguration config = pl.getConfig();
		if (cauldrons.contains(name))	{
			Location loc = new Location(Bukkit.getWorld(config.getString(name + ".world")),
					config.getInt(name + ".x"),
					config.getInt(name + ".y"),
					config.getInt(name + ".z"));
			
			if (loc.getBlock().getType() == Material.CAULDRON)	{
				loc.getBlock().setType(Material.AIR);
			}
			config.set(name, null);
			pl.saveConfig();
		} else {
			sender.sendMessage("§c[§aDreamCauldron§c] §7The name receive is not used for a cauldron in config.yml");
		}
		
	}
	
	private static void editConfigCauldron(String path, World w, int x, int y, int z, Particle p, Sound s, FileConfiguration config)	{
		config.set(path + ".world", w.getName());
		config.set(path + ".x", x);
		config.set(path + ".y", y);
		config.set(path + ".z", z);
		config.set(path + ".particle", p.toString());
		config.set(path + ".sound", s.toString());
	}
	private static void editDropAllowCauldron(String path, FileConfiguration config, String...items)	{	
		List<String> drops_allow = new ArrayList<String>();
		drops_allow.clear();
		for (String str : items)	{
			drops_allow.add(str);
		}
		config.set(path + ".drops_allow", drops_allow);
	}
}
