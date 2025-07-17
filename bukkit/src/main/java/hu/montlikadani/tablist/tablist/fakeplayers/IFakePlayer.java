package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.tablist.player.PlayerSkinProperties;

public interface IFakePlayer {

	String getName();

	String getDisplayName();

	String getHeadIdentifier();

	int getPingLatency();

	void setSkin(PlayerSkinProperties props);

	void setPingLatency(int ping);
}
