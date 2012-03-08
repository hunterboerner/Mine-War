package me.hunterboerner.war;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
public class Commands implements CommandExecutor {
	
	private War plugin;
	
	public Commands(War war) {
		this.plugin	=	war;
	}


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) { 
		if(cmd.getName().equalsIgnoreCase("war")){
			if(!(sender instanceof Player)){
				plugin.logMessage("Only a player may use the war command.");
			}else{
				Player player = (Player) sender;
				if(player.hasPermission("war.commands")){
					if(args.length==0){
						player.sendMessage(ChatColor.RED + "No argument specified");
						return false;
					}
					
					if(args[0].equals("declare")){
						if(args.length==2){
							Player target	=	player.getServer().getPlayer(args[1]);
							if(target==null){
								player.sendMessage(ChatColor.RED + "Player "+args[1]+" does not exist or is not online.");
								return true;
							}
							if(!plugin.addWar(player, target)){
								player.sendMessage(ChatColor.RED + "You were already at war with that person!");
								return true;
							}
							player.sendMessage(ChatColor.DARK_PURPLE + "You have declared war on " + target.getDisplayName());
							player.getServer().broadcastMessage(ChatColor.DARK_PURPLE + player.getDisplayName() + " has declared war on " + target.getDisplayName());
							target.sendMessage(ChatColor.DARK_PURPLE +player.getDisplayName()+" has declared war upon you!");
							plugin.logMessage(player.getDisplayName() + " used "  + "/war " + args[0] + " on " + args[1]);
						}else{
							player.sendMessage(ChatColor.RED +"A target must be specified when declaring war!");
							return false;
						}
					}else if(args[0].equals("world")){
						player.sendMessage(ChatColor.DARK_PURPLE + "You have started a world war!");
						player.getServer().broadcastMessage(ChatColor.DARK_PURPLE + player.getDisplayName() + " has started a world war!");
						plugin.logMessage(player.getDisplayName() + " used " + "/war " + args[0]);
					}else if(args[0].equals("truce")){
						if(args.length==2){
							Player target	=	player.getServer().getPlayer(args[1]);
							if(target==null){
								player.sendMessage(ChatColor.RED + "Player "+args[1]+" does not exist or is not online.");
								return true;
							}
							if(!plugin.removeWar(player, target)){
								player.sendMessage(ChatColor.RED + "You were not at war with that person!");
								return true;
							}
							player.sendMessage(ChatColor.GREEN + "You have made peace with " + target.getDisplayName());
							player.getServer().broadcastMessage(ChatColor.GREEN + player.getDisplayName() + " has made peace with " + target.getDisplayName());
							target.sendMessage(ChatColor.GREEN +player.getDisplayName()+" has made peace with you!");
							plugin.logMessage(player.getDisplayName() + " used "  + "/war " + args[0] + " on " + args[1]);
						}else{
							player.sendMessage(ChatColor.RED +"A target must be specified when declaring a truce!");
							return false;
						}
					}
				}else{
					player.sendMessage(ChatColor.RED + "Permission denied" );
				}
							
			}
		}
		return true;
		
	}
}
