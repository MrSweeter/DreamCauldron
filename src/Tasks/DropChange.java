package Tasks;

import java.util.List;

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

import com.MrSweeter.DreamCauldron.DreamCauldron;

public class DropChange extends BukkitRunnable	{
	
	private FileConfiguration config;
	private PlayerDropItemEvent event;
	private List<Material> items = Events.Items.getItemsConfig();
	private List<ItemStack> drop = Events.Items.getDropConfig();
	
	public DropChange(DreamCauldron main, PlayerDropItemEvent e) {
		this.config = main.getConfig();
		this.event = e;
	}

	@Override
	public void run() {
		
		Item itemToChange = event.getItemDrop();
		ItemStack itemDrop = event.getItemDrop().getItemStack();
		
		if (items.size() == 100)	{
			
			for (String cauldrons : config.getKeys(false))	{
				
				ConfigurationSection section = config.getConfigurationSection(cauldrons);
				
				if (section.getString("world") != null)	{
					
					World world = event.getItemDrop().getWorld();
					Location locCauldron = new Location(world, section.getDouble("x"), section.getDouble("y"), section.getDouble("z"));
					Location locItem = event.getItemDrop().getLocation();
					//Block size
					double x = section.getDouble("x"), dx = section.getDouble("x") + 1;
					double y = section.getDouble("y"), dy = section.getDouble("y") + 1;
					double z = section.getDouble("z"), dz = section.getDouble("z") + 1;
					//Item location
					double xItem = locItem.getX();
					double yItem = locItem.getY();
					double zItem = locItem.getZ();
					//Particle and Sound
					Location locParticle = locItem;
					locParticle.setY(locParticle.getY()+1);
					Particle particle = Particle.TOTEM;
					Sound sound = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
					
					if (locCauldron.getBlock().getType() == Material.CAULDRON)	{
						
						if (xItem >= x && xItem <= dx && yItem >= y && yItem <= dy && zItem >= z && zItem <= dz)	{
							
							if (drop.contains(itemDrop))	{
								
								int random = (int) (Math.random() * 100);
								Material lootToSet = items.get(random);
								ItemStack loot = new ItemStack(lootToSet);
								world.spawnParticle(particle, locParticle, 20, 0.5, 0.5, 0.5, 0.1);
								event.getPlayer().playSound(locParticle, sound, 1, 1);
								itemToChange.setItemStack(loot);
							}
						}
					}
				}
			}
		} else if (items.size() == 1) {
			ItemStack loot = new ItemStack(items.get(0), 1, (byte) 1);
			itemToChange.setItemStack(loot);
		}
	}
}
