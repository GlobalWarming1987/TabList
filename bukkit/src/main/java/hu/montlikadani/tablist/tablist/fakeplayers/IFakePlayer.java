package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.tablist.player.TabListPlayer.PlayerSkinProperties;

public interface IFakePlayer {
	String getName();

	String getDisplayName();

	String getHeadId();

	int getPing();

	void remove();

	void display();

	void setSkin(PlayerSkinProperties props);

	void setPing(int ping);

	void setDisplayName(String displayName);

	final class Factory {
		public static IFakePlayer create(String name, String displayName, String headIdentifier, int ping) {
			return new FakePlayer(name, displayName, headIdentifier, ping);
		}
	}
}
