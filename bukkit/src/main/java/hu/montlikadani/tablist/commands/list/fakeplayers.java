package hu.montlikadani.tablist.commands.list;

import hu.montlikadani.tablist.TabList;
import hu.montlikadani.tablist.commands.Commands;
import hu.montlikadani.tablist.tablist.fakeplayers.FakePlayerHandler;
import hu.montlikadani.tablist.tablist.fakeplayers.FakePlayerHandler.EditingResult;
import hu.montlikadani.tablist.tablist.player.PlayerSkinProperties;
import hu.montlikadani.tablist.tablist.fakeplayers.IFakePlayer;
import hu.montlikadani.tablist.utils.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public final class fakeplayers implements Commands.Command {

	@Override
	public void perform(CommandSender sender, String[] args) {
		if (args.length < 2) {
			sendUsage(sender);
			return;
		}

		FakePlayerHandler handler = TabList.getInstance().getFakePlayerHandler();
		EditingResult output;

		String sub = args[1].toLowerCase();

		switch (sub) {
			case "create":
				if (args.length < 3) {
					sendUsage(sender);
					return;
				}

				String name = args[2];
				if ((output = handler.createPlayer(name, name, "", -1)) == EditingResult.ALREADY_EXIST) {
					sender.sendMessage("Fake player already exists.");
					return;
				}

				if (output == EditingResult.OK) {
					sender.sendMessage("Fake player created.");
				}
				break;

			case "remove":
				if (args.length < 3) {
					sendUsage(sender);
					return;
				}

				if ((output = handler.removePlayer(args[2])) == EditingResult.NOT_EXIST) {
					sender.sendMessage("Fake player not found.");
					return;
				}

				if (output == EditingResult.OK) {
					sender.sendMessage("Fake player removed.");
				}
				break;

			case "rename":
				if (args.length < 4) {
					sendUsage(sender);
					return;
				}

				String oldName = args[2], newName = args[3];

				if ((output = handler.renamePlayer(oldName, newName)) == EditingResult.NOT_EXIST) {
					sender.sendMessage("Fake player not found.");
					return;
				}

				if (output == EditingResult.OK) {
					sender.sendMessage("Fake player renamed.");
				}
				break;

			case "list":
				Set<IFakePlayer> list = handler.getAllFakePlayers();

				if (list.isEmpty()) {
					sender.sendMessage("There are no fake players.");
					return;
				}

				for (IFakePlayer fakePlayer : list) {
					sender.sendMessage("- " + fakePlayer.getName());
				}
				break;

			case "setskin":
				if (args.length < 5) {
					sendUsage(sender);
					return;
				}

				PlayerSkinProperties skinProperties = new PlayerSkinProperties(args[3], args[4]);

				if ((output = handler.setSkin(args[2], skinProperties)) == EditingResult.NOT_EXIST) {
					sender.sendMessage("Fake player not found.");
					return;
				}

				if (output == EditingResult.OK) {
					sender.sendMessage("Skin updated.");
				}
				break;

			case "setping":
				if (args.length < 4) {
					sendUsage(sender);
					return;
				}

				int amount = Util.getNumber(args[3], -1);
				if ((output = handler.setPing(args[2], amount)) == EditingResult.NOT_EXIST) {
					sender.sendMessage("Fake player not found.");
				} else if (output == EditingResult.PING_AMOUNT) {
					sender.sendMessage("Invalid ping amount.");
				} else {
					sender.sendMessage("Ping updated.");
				}
				break;

			case "setdisplayname":
				if (args.length < 4) {
					sendUsage(sender);
					return;
				}

				StringBuilder builder = new StringBuilder();
				for (int i = 3; i < args.length; i++) {
					builder.append(args[i]).append(" ");
				}

				output = handler.setDisplayName(args[2], builder.toString().trim().replace("\"", ""));

				if (output == EditingResult.NOT_EXIST) {
					sender.sendMessage("Fake player not found.");
				} else {
					sender.sendMessage("Display name updated.");
				}
				break;

			default:
				sendUsage(sender);
				break;
		}
	}

	private void sendUsage(CommandSender sender) {
		sender.sendMessage("/tablist fakeplayers create <name>");
		sender.sendMessage("/tablist fakeplayers remove <name>");
		sender.sendMessage("/tablist fakeplayers rename <old> <new>");
		sender.sendMessage("/tablist fakeplayers setskin <name> <value> <signature>");
		sender.sendMessage("/tablist fakeplayers setping <name> <ping>");
		sender.sendMessage("/tablist fakeplayers setdisplayname <name> <text>");
		sender.sendMessage("/tablist fakeplayers list");
	}
}
