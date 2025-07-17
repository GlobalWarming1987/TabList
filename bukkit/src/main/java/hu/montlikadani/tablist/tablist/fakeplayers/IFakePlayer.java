package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.tablist.player.PlayerSkinProperties;

public interface IFakePlayer {

	String getName();

	void setSkin(PlayerSkinProperties props);

	void setPingLatency(int latency);

	int getPingLatency();

	PlayerSkinProperties getSkin();
}
