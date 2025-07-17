package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.tablist.player.PlayerSkinProperties;

public final class FakePlayer implements IFakePlayer {

	private String name;
	private String displayName;
	private String headId;
	private int ping;

	public FakePlayer(String name, String displayName, String headId, int ping) {
		this.name = name;
		this.displayName = displayName;
		this.headId = headId;
		this.ping = ping;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getHeadId() {
		return headId;
	}

	@Override
	public void setHeadId(String headId) {
		this.headId = headId;
	}

	@Override
	public int getPingLatency() {
		return ping;
	}

	@Override
	public void setPing(int ping) {
		this.ping = ping;
	}

	@Override
	public void spawn() {
		// Actual implementation would inject the fake player to the tab list
	}

	@Override
	public void remove() {
		// Actual implementation would remove the fake player from the tab list
	}

	@Override
	public PlayerSkinProperties getSkinProperties() {
		// Stub for now
		return new PlayerSkinProperties("", "");
	}
}
