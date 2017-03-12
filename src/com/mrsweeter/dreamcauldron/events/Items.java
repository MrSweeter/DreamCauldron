package com.mrsweeter.dreamcauldron.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class Items {

	public static List<Material> getItemsConfig(ConfigurationSection section) {

		List<Material> matItem = new ArrayList<>();

		if (section.contains("drops"))
			for (String items : section.getConfigurationSection("drops").getKeys(false)) {

				Material mat = Material.getMaterial(items.toUpperCase());
				int pct = section.getInt("drops." + items);
				for (int i = 0; i < pct; i++) {
					matItem.add(mat);
				}
			}

		// Default item
		if (matItem.size() != 100) {
			Bukkit.broadcastMessage("§c[§aDreamCauldron§c] §7The rate of total drops is equal to §a" + matItem.size()
					+ " §7(default_drop:blue orchid), please check §aconfig.yml");
			matItem = new ArrayList<>();
			ItemStack orchid = new ItemStack(Material.RED_ROSE, 1, (byte) 1);
			matItem.add(orchid.getType());
		}

		return matItem;

	}

	public static List<ItemStack> getDropConfig(ConfigurationSection section) {

		List<?> drop;
		List<ItemStack> matDrop = new ArrayList<>();

		if (section.contains("drops_allow")) {

			drop = section.getList("drops_allow");

			for (Object str : drop) {

				if (str instanceof String) {
					ItemStack toAdd = new ItemStack(Material.getMaterial(((String) str).toUpperCase().trim()));
					matDrop.add(toAdd);
				}
			}
		}

		// Default item
		if (matDrop.size() == 0) {
			Bukkit.broadcastMessage("§c[§aDreamCauldron§c] §7The drop list is empty (default_drop:orchid), please check §aconfig.yml");
			matDrop = new ArrayList<>();
			ItemStack orchid = new ItemStack(Material.RED_ROSE, 1, (byte) 1);
			matDrop.add(orchid);
		}

		return matDrop;
	}
}
