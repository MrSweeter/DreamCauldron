package com.mrsweeter.dreamcauldron.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.mrsweeter.dreamcauldron.DreamCauldron;

public class Commands implements CommandExecutor, TabCompleter {
	
	private DreamCauldron pl;
	
	public Commands(DreamCauldron main)	{
		this.pl = main;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)	{
		
		if (args.length == 0)	{
			switch (commandLabel)	{
			case "dcreload":
				if (sender.hasPermission("dreamCauldron.reload"))	{
					pl.reloadConfig();
					sender.sendMessage("§c[§aDreamCauldron§c] §7Reload complete");
				} else	{
					sender.sendMessage("§cYou aren't allow do to this");
				}
				return true;
			}
		} else if (args.length >= 1)	{
			switch (commandLabel)	{
			case "dcremove":
				if (sender.hasPermission("dreamCauldron.modify"))	{
					CommandExecute.removeCauldron((Player)sender, pl, args[0].trim());
				} else	{
					sender.sendMessage("§cYou aren't allow do to this");
				}
				return true;
			case "dccreate":
				if (sender.hasPermission("dreamCauldron.modify") && sender instanceof Player)	{
					CommandExecute.createCauldron((Player)sender, pl, args);
				} else	{
					sender.sendMessage("§cYou aren't allow do to this");
				}
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String commandLabel, String[] args) {
		
		List <String> fList = new ArrayList<String>();
		
		if (commandLabel.equals("dccreate"))	{
			List<String> arg = Arrays.asList("decoration", "disc", "nether", "ores", "redstone");
			
			if (args.length == 2)	{
				for (String str : arg)	{
					fList.add(str);
				}
			}
		} else if (commandLabel.equals("dcremove"))	{
			Set<String> arg = pl.getConfig().getKeys(false);
			
			if (args.length == 1)	{
				for (String str : arg)	{
					fList.add(str);
				}
			}
		}
		return	fList;
	}
}
