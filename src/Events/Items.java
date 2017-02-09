package Events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.MrSweeter.DreamCauldron.DreamCauldron;

public class Items {
	
	private static YamlConfiguration itemsConfig = DreamCauldron.items; 
	
	public static List<Material> getItemsConfig()	{
		
		List<Material> matItem = new ArrayList<>();
		
		if (itemsConfig.contains("drops"))
			for (String items : itemsConfig.getConfigurationSection("drops").getKeys(false))	{
				
				Material mat = Material.getMaterial(items.toUpperCase());
				int pct = itemsConfig.getInt("drops." + items);
				for (int i = 0; i < pct; i++)	{
					matItem.add(mat);
				}
			}
		
		if (matItem.size() != 100)	{
			Bukkit.broadcastMessage("§cThe rate of total drops is equal to §a" + matItem.size() + " §c(default_drop:blue orchid)§c, please check §aitems.yml");
			matItem = new ArrayList<>();
			ItemStack orchid = new ItemStack(Material.RED_ROSE, 1, (byte)1);
			matItem.add(orchid.getType());
		}
		
		return matItem;
		
	}

	public static List<ItemStack> getDropConfig() {
		
		List<?> drop = itemsConfig.getList("drops_allow");
		List<ItemStack> matDrop = new ArrayList<>();
		
		if (itemsConfig.contains("drops_allow"))	{
			for (Object str : drop)	{
				
				if (str instanceof String)	{
					ItemStack toAdd = new ItemStack(Material.getMaterial(((String) str).toUpperCase()));
					matDrop.add(toAdd);
				}
			}
		}
		
		if (matDrop.size() == 0)	{
			Bukkit.broadcastMessage("§cThe drop list is empty (default_drop:orchid)§c, please check §aitems.yml");
			matDrop = new ArrayList<>();
			ItemStack orchid = new ItemStack(Material.RED_ROSE, 1, (byte) 1);
			matDrop.add(orchid);
		}
		
		return matDrop;
	}
}
