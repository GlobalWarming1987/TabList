package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.tablist.player.PlayerSkinProperties;

public final class FakePlayer implements IFakePlayer {

	private final String name;
	private final String displayName;
	private final String headIdentifier;
	private int ping;
	private PlayerSkinProperties skin;

	public FakePlayer(String name, String displayName, String headIdentifier, int ping) {
		this.name = name;
		this.displayName = displayName;
		this.headIdentifier = headIdentifier;
		this.ping = ping;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public int getPingLatency() {
		return ping;
	}

	@Override
	public void setPingLatency(int latency) {
		this.ping = latency;
	}

	@Override
	public PlayerSkinProperties getSkin() {
		return skin;
	}

	@Override
	public void setSkin(PlayerSkinProperties props) {
		this.skin = props;
	}

	public String getHeadIdentifier() {
		return headIdentifier;
	}
}
