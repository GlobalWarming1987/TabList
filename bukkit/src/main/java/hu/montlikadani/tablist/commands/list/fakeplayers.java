package hu.montlikadani.tablist.commands.list;

import hu.montlikadani.tablist.TabList;
import hu.montlikadani.tablist.commands.Commands;
import hu.montlikadani.tablist.tablist.fakeplayers.FakePlayerHandler;
import hu.montlikadani.tablist.tablist.fakeplayers.FakePlayerHandler.EditingResult;
import hu.montlikadani.tablist.tablist.player.TabListPlayer.PlayerSkinProperties;
import hu.montlikadani.tablist.utils.Util;
import org.bukkit.command.CommandSender;

import java.util.Set;

public final class fakeplayers implements Commands.Command {

	@Override
	public void execute(TabList plugin, CommandSender sender, String[] args) {
		if (args.length < 2) {
			sendUsage(sender);
			return;
		}

		FakePlayerHandler handler = plugin.getFakePlayerHandler();
		EditingResult output;

		switch (args[1].toLowerCase()) {
			case "add":
				if (args.length < 3) {
					sender.sendMessage("§cUsage: /tablist fakeplayers add <name>");
					return;
				}

				String name = args[2];

				if ((output = handler.createPlayer(name, name, "", -1)) == EditingResult.ALREADY_EXIST) {
					sender.sendMessage("§cFake player already exists.");
					return;
				}

				if (output == EditingResult.OK) {
					sender.sendMessage("§aFake player added successfully.");
				}

				break;

			case "remove":
				if (args.length < 3) {
					sender.sendMessage("§cUsage: /tablist fakeplayers remove <name>");
					return;
				}

				if ((output = handler.removePlayer(args[2])) == EditingResult.NOT_EXIST) {
					sender.sendMessage("§cFake player does not exist.");
					return;
				}

				if (output == EditingResult.OK) {
					sender.sendMessage("§aFake player removed.");
				}

				break;

			case "rename":
				if (args.length < 4) {
					sender.sendMessage("§cUsage: /tablist fakeplayers rename <oldName> <newName>");
					return;
				}

				String oldName = args[2];
				String newName = args[3];

				if ((output = handler.renamePlayer(oldName, newName)) == EditingResult.NOT_EXIST) {
					sender.sendMessage("§cFake player does not exist.");
					return;
				}

				if (output == EditingResult.OK) {
					sender.sendMessage("§aFake player renamed.");
				}

				break;

			case "list":
				Set<hu.montlikadani.tablist.tablist.fakeplayers.IFakePlayer> list = handler.getAllFakePlayers();
				if (list.isEmpty()) {
					sender.sendMessage("§eNo fake players found.");
				} else {
					sender.sendMessage("§aFake Players:");
					for (var fake : list) {
						sender.sendMessage("§7- " + fake.getName());
					}
				}
				break;

			case "setskin":
				if (args.length < 4) {
					sender.sendMessage("§cUsage: /tablist fakeplayers setskin <name> <value> <signature>");
					return;
				}

				PlayerSkinProperties skinProperties = new PlayerSkinProperties(args[3], args[4]);

				if ((output = handler.setSkin(args[2], skinProperties)) == EditingResult.NOT_EXIST) {
					sender.sendMessage("§cFake player not found.");
				} else {
					sender.sendMessage("§aSkin applied.");
				}

				break;

			case "setping":
				if (args.length < 4) {
					sender.sendMessage("§cUsage: /tablist fakeplayers setping <name> <amount>");
					return;
				}

				int amount = Util.getNumber(args[3], -1);

				if ((output = handler.setPing(args[2], amount)) == EditingResult.NOT_EXIST) {
					sender.sendMessage("§cFake player not found.");
				} else if (output == EditingResult.PING_AMOUNT) {
					sender.sendMessage("§cPing must be at least 1.");
				} else {
					sender.sendMessage("§aPing updated.");
				}

				break;

			case "setdisplayname":
				if (args.length < 4) {
					sender.sendMessage("§cUsage: /tablist fakeplayers setdisplayname <name> <displayName>");
					return;
				}

				StringBuilder builder = new StringBuilder();
				for (int i = 3; i < args.length; i++) {
					builder.append(args[i]).append(' ');
				}

				output = handler.setDisplayName(args[2], builder.toString().trim().replace("\"", ""));

				if (output == EditingResult.NOT_EXIST) {
					sender.sendMessage("§cFake player not found.");
				} else {
					sender.sendMessage("§aDisplay name set.");
				}

				break;

			default:
				sendUsage(sender);
				break;
		}
	}

	private void sendUsage(CommandSender sender) {
		sender.sendMessage("§a/fakeplayers add <name>");
		sender.sendMessage("§a/fakeplayers remove <name>");
		sender.sendMessage("§a/fakeplayers rename <oldName> <newName>");
		sender.sendMessage("§a/fakeplayers list");
		sender.sendMessage("§a/fakeplayers setskin <name> <value> <signature>");
		sender.sendMessage("§a/fakeplayers setping <name> <amount>");
		sender.sendMessage("§a/fakeplayers setdisplayname <name> <displayName>");
	}
}
