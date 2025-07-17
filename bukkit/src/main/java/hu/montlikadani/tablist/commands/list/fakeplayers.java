package hu.montlikadani.tablist.commands.list;

import hu.montlikadani.tablist.TabList;
import hu.montlikadani.tablist.commands.Commands;
import hu.montlikadani.tablist.tablist.fakeplayers.FakePlayer;
import hu.montlikadani.tablist.tablist.fakeplayers.FakePlayerHandler;
import hu.montlikadani.tablist.utils.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class fakeplayers implements Commands.Command {

	@Override
	public String name() {
		return "fakeplayers";
	}

	@Override
	public void perform(CommandSender sender, String label, String[] args) {
		FakePlayerHandler handler = TabList.getInstance().getFakePlayerHandler();

		if (args.length == 1) {
			sender.sendMessage("§eUsage: /" + label + " fakeplayers <add|remove|clear> [name] [displayName] [headId] [ping]");
			return;
		}

		String sub = args[1].toLowerCase();

		switch (sub) {
			case "add": {
				if (args.length < 6) {
					sender.sendMessage("§cUsage: /" + label + " fakeplayers add <name> <displayName> <headId> <ping>");
					return;
				}

				String name = args[2];
				String displayName = args[3];
				String headId = args[4];
				int ping = Util.getNumber(args[5], -1);

				if (ping < 0) {
					sender.sendMessage("§cPing must be a positive number.");
					return;
				}

				FakePlayer fp = new FakePlayer(name, displayName, headId, ping);
				handler.addFakePlayer(fp);

				sender.sendMessage("§aFake player added.");
				break;
			}
			case "remove": {
				if (args.length < 3) {
					sender.sendMessage("§cUsage: /" + label + " fakeplayers remove <name>");
					return;
				}

				boolean removed = handler.removeFakePlayer(args[2]);
				sender.sendMessage(removed ? "§aFake player removed." : "§cFake player not found.");
				break;
			}
			case "clear": {
				handler.clear();
				sender.sendMessage("§aAll fake players removed.");
				break;
			}
			default:
				sender.sendMessage("§cUnknown subcommand. Use add, remove, or clear.");
		}
	}
}
