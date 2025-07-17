package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.tablist.player.PlayerSkinProperties;

public interface IFakePlayer {

	String getName();

	int getPingLatency();

	void setPingLatency(int ping);

	void setSkin(PlayerSkinProperties props);

	PlayerSkinProperties getSkin();
}
