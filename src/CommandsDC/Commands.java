package CommandsDC;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.MrSweeter.DreamCauldron.DreamCauldron;

public class Commands implements CommandExecutor {
	
	private DreamCauldron pl;
	
	public Commands(DreamCauldron main)	{
		this.pl = main;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)	{
		
		if (sender.hasPermission("dreamCauldron.reload"))	{
			if (commandLabel.toLowerCase().equals("dcreload"))	{
				pl.reloadConfig();
				sender.sendMessage("§c[§aDreamCauldron§c] §7Reload complete");
				return true;
			}
		} else	{
			sender.sendMessage("§cVous n'avez pas la permission d'executer cette commande");
		}
		
		return false;
	}
}
