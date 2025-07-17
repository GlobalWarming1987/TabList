package hu.montlikadani.tablist.commands;

import hu.montlikadani.tablist.TabList;
import hu.montlikadani.tablist.commands.list.fakeplayers;
import hu.montlikadani.tablist.commands.list.group;
import hu.montlikadani.tablist.commands.list.player;
import hu.montlikadani.tablist.tablist.fakeplayers.IFakePlayer;
import hu.montlikadani.tablist.utils.Util;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class Commands {

	private final TabList plugin;
	private final List<Command> commands = new ArrayList<>();

	public Commands(TabList plugin) {
		this.plugin = plugin;

		commands.add(new fakeplayers());
		commands.add(new group());
		commands.add(new player());
	}

	public boolean execute(CommandSender sender, String label, String[] args) {
		if (args.length == 0) {
			printInfo(sender, label);
			return true;
		}

		String sub = args[0].toLowerCase();

		for (Command cmd : commands) {
			if (cmd.name().equalsIgnoreCase(sub)) {
				cmd.perform(sender, label, args);
				return true;
			}
		}

		sender.sendMessage("§cUnknown command. Try /" + label + " help");
		return false;
	}

	private void printInfo(CommandSender sender, String label) {
		plugin.getComplement().sendMessage(sender, Util.applyTextFormat("&9&lTab&4&lList"));
		plugin.getComplement().sendMessage(sender, Util.applyTextFormat("&5Version:&a " + plugin.getDescription().getVersion()));
		plugin.getComplement().sendMessage(sender, Util.applyTextFormat("&5Author, created by:&a montlikadani"));
		plugin.getComplement().sendMessage(sender, Util.applyTextFormat("&5List of commands:&7 /" + label + " help"));
		plugin.getComplement().sendMessage(sender, Util.applyTextFormat("&4Report bugs/features here:&e &nhttps://github.com/montlikadani/TabList/issues"));
	}

	public interface Command {
		String name();

		void perform(CommandSender sender, String label, String[] args);
	}
}
