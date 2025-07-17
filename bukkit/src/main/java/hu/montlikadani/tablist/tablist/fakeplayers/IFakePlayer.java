package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.tablist.player.TabListPlayer.PlayerSkinProperties;

public interface IFakePlayer {

	String getName();

	String getDisplayName();

	void setDisplayName(String displayName);

	String getHeadIdentifier();

	void setHeadIdentifier(String id);

	int getPing();

	void setPing(int ping);

	void setName(String name);

	void setSkin(PlayerSkinProperties props);

	void spawn();

	void remove();

	default int getPingLatency() {
		return getPing();
	}
}
