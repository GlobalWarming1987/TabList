package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.tablist.player.PlayerSkinProperties;

import java.util.UUID;

public interface IFakePlayer {

	String getName();

	UUID getUniqueId();

	void setDisplayName(String displayName);

	String getDisplayName();

	void setPing(int ping);

	int getPing();

	String getHeadIdentifier();

	void setSkin(PlayerSkinProperties props);

	PlayerSkinProperties getSkinProperties();

	void spawn();

	void remove();
}
