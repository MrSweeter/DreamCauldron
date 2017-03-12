package com.mrsweeter.dreamcauldron.tasks;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.mrsweeter.dreamcauldron.DreamCauldron;
import com.mrsweeter.dreamcauldron.events.Items;

public class DropChange extends BukkitRunnable {

	private FileConfiguration config;
	private PlayerDropItemEvent event;
	private List<Material> items;
	private List<ItemStack> drop;
	private ItemStack item;

	public DropChange(DreamCauldron main, PlayerDropItemEvent e) {
		this.config = main.getConfig();
		this.event = e;
		item = e.getItemDrop().getItemStack();
	}

	@Override
	public void run() {
		
		int quantity = item.getAmount();
		Item itemToChange = event.getItemDrop();
		Material itemDropped = event.getItemDrop().getItemStack().getType();
		ItemStack itemDrop = new ItemStack(itemDropped, 1, (byte) 0);

		for (String cauldrons : config.getKeys(false)) {

			ConfigurationSection section = config.getConfigurationSection(cauldrons);

			if (section.getString("world") != null && section.getString("x") != null && section.getString("y") != null
					&& section.getString("z") != null) {

				// Block location
				World worldBlock = Bukkit.getServer().getWorld(section.getString("world").trim());
				double x = section.getDouble("x"), dx = section.getDouble("x") + 1;
				double y = section.getDouble("y"), dy = section.getDouble("y") + 1;
				double z = section.getDouble("z"), dz = section.getDouble("z") + 1;

				// Item location
				World worldItem = event.getItemDrop().getWorld();
				double xItem = event.getItemDrop().getLocation().getX();
				double yItem = event.getItemDrop().getLocation().getY();
				double zItem = event.getItemDrop().getLocation().getZ();

				// Compare Block and Item
				if (xItem >= x && xItem <= dx && yItem >= y && yItem <= dy && zItem >= z && zItem <= dz
						&& worldBlock == worldItem) {

					// World location
					Location locCauldron = new Location(worldBlock, x, y, z);

					// Particle and Sound location
					Location locParticle = new Location(worldItem, xItem, yItem + 1, zItem);

					// Particle and Sound
					Particle particle = Particle.valueOf(section.getString("particle").toUpperCase().trim());
					Sound sound = Sound.valueOf(section.getString("sound").toUpperCase().trim());

					// Cauldron OK ?
					if (locCauldron.getBlock().getType() == Material.CAULDRON) {

						drop = Items.getDropConfig(section);
						
						// Dropped item OK ?
						if (drop.contains(itemDrop)) {
							
							items = Items.getItemsConfig(section);
	
							// DropChance 100 ?
							if (items.size() == 100) {
	
								int random = (int) (Math.random() * 100);
								ItemStack loot = new ItemStack(items.get(random), quantity);

								worldItem.spawnParticle(particle, locParticle, 20, 0.5, 0.5, 0.5, 0.1);
								event.getPlayer().playSound(locParticle, sound, 1, 1);
								itemToChange.setItemStack(loot);
	
							} else if (items.size() == 1) {
								ItemStack loot = new ItemStack(items.get(0), 1, (byte) 1);
								itemToChange.setItemStack(loot);
							}
						}
					}
				}
			}
		}
	}
}
