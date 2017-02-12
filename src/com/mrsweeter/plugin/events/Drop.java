package com.mrsweeter.plugin.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.mrsweeter.plugin.DreamCauldron;

public class Drop implements Listener	{

	private DreamCauldron pl;
	
	public Drop(DreamCauldron main) {;
		this.pl = main;
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event)	{
		
		BukkitRunnable task = new com.mrsweeter.plugin.tasks.DropChange(pl, event);
		task.runTaskLater(pl, 30);
		
		
	}
}
