package Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.MrSweeter.DreamCauldron.DreamCauldron;

public class Drop implements Listener	{

	private DreamCauldron pl;
	
	public Drop(DreamCauldron main) {;
		this.pl = main;
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event)	{
		
		BukkitRunnable task = new Tasks.DropChange(pl, event);
		task.runTaskLater(pl, 30);
		
		
	}
}
