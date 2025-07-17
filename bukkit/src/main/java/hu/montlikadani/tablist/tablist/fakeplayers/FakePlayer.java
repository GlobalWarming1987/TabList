package hu.montlikadani.tablist.tablist.fakeplayers;

public final class FakePlayer {

    private final String name;
    private final String displayName;
    private final String skin;
    private final int ping;

    public FakePlayer(String name, String displayName, String skin, int ping) {
        this.name = name;
        this.displayName = displayName;
        this.skin = skin;
        this.ping = ping;
    }

    public void spawn() {
        // Simulate fake player spawn
    }

    public void remove() {
        // Simulate fake player removal
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSkin() {
        return skin;
    }

    public int getPing() {
        return ping;
    }
}
