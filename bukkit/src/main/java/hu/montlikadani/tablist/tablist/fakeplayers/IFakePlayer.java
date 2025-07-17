package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.tablist.player.PlayerSkinProperties;

public interface IFakePlayer {

	String getName();

	String getDisplayName();

	int getPingLatency();

	void setPingLatency(int latency);

	PlayerSkinProperties getSkin();

	void setSkin(PlayerSkinProperties props);
}
