package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.tablist.player.PlayerSkinProperties;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class FakePlayer implements IFakePlayer {

	private final String name;
	private String displayName;
	private String headIdentifier;
	private int ping;
	private UUID uuid;
	private PlayerSkinProperties skin;

	public FakePlayer(String name, String displayName, String headIdentifier, int ping) {
		this.name = name;
		this.displayName = displayName;
		this.headIdentifier = headIdentifier;
		this.ping = ping;
		this.uuid = UUID.nameUUIDFromBytes(("FakePlayer:" + name).getBytes());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public UUID getUniqueId() {
		return uuid;
	}

	@Override
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getDisplayName() {
		return displayName != null ? displayName : name;
	}

	@Override
	public void setPing(int ping) {
		this.ping = ping;
	}

	@Override
	public int getPing() {
		return ping;
	}

	@Override
	public String getHeadIdentifier() {
		return headIdentifier;
	}

	@Override
	public void setSkin(PlayerSkinProperties props) {
		this.skin = props;
	}

	@Override
	public PlayerSkinProperties getSkinProperties() {
		return skin;
	}

	@Override
	public void spawn() {
		// Add code here to create and send the necessary packets
		Bukkit.getLogger().info("Spawning fake player: " + name);
	}

	@Override
	public void remove() {
		// Add code here to remove fake player from tab list
		Bukkit.getLogger().info("Removing fake player: " + name);
	}
}
