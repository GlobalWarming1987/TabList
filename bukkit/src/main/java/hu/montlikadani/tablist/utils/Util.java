package hu.montlikadani.tablist.utils;

import hu.montlikadani.tablist.TabList;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public final class Util {

	private Util() {
	}

	public static void consolePrint(Level level, TabList plugin, String msg, Object... args) {
		if (plugin == null || msg == null || msg.isEmpty()) {
			return;
		}

		String formattedMsg = java.text.MessageFormat.format(msg, args);
		plugin.getLogger().log(level, formattedMsg);
	}

	public static void printTrace(Level level, TabList plugin, String message, Throwable ex) {
		if (plugin == null || message == null || ex == null) {
			return;
		}

		plugin.getLogger().log(level, message, ex);
	}

	public static String legacyNmsVersion() {
		String version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName();
		return version.substring(version.lastIndexOf('.') + 1);
	}

	public static String applyTextFormat(String text) {
		if (text == null || text.isEmpty()) {
			return "";
		}

		return org.bukkit.ChatColor.translateAlternateColorCodes('&', text);
	}

	public static Optional<UUID> tryParseId(String input) {
		try {
			return Optional.of(UUID.fromString(input));
		} catch (IllegalArgumentException e) {
			return Optional.empty();
		}
	}

	public static int getNumber(String input, int defaultValue) {
		if (input == null) {
			return defaultValue;
		}

		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
}
