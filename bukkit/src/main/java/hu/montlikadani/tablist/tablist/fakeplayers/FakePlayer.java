package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.tablist.player.PlayerSkinProperties;

public final class FakePlayer implements IFakePlayer {

	private final String name;
	private int pingLatency = -1;
	private PlayerSkinProperties skin;

	public FakePlayer(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setSkin(PlayerSkinProperties props) {
		this.skin = props;
	}

	@Override
	public void setPingLatency(int latency) {
		this.pingLatency = latency;
	}

	@Override
	public int getPingLatency() {
		return pingLatency;
	}

	@Override
	public PlayerSkinProperties getSkin() {
		return skin;
	}

	public void spawn() {
		// Stub method — actual implementation should spawn the fake player entity.
	}
}
