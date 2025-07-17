package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.tablist.player.PlayerSkinProperties;

public interface IFakePlayer {

	String getName();

	void setName(String name);

	String getDisplayName();

	void setDisplayName(String displayName);

	String getHeadId();

	void setHeadId(String headId);

	int getPingLatency();

	void setPing(int ping);

	void spawn();

	void remove();

	PlayerSkinProperties getSkinProperties();
}
