package hu.montlikadani.tablist.utils;

public final class Util {

	private Util() {
	}

	public static int getNumber(String s, int def) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException ex) {
			return def;
		}
	}
}
