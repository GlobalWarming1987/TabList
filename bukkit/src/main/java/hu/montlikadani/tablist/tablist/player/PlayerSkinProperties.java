package hu.montlikadani.tablist.tablist.player;

import java.util.Objects;

public final class PlayerSkinProperties {
	private final String texture, signature;

	public PlayerSkinProperties(String texture, String signature) {
		this.texture = texture;
		this.signature = signature;
	}

	public String getTexture() {
		return texture;
	}

	public String getSignature() {
		return signature;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PlayerSkinProperties)) return false;
		PlayerSkinProperties that = (PlayerSkinProperties) o;
		return Objects.equals(texture, that.texture) && Objects.equals(signature, that.signature);
	}

	@Override
	public int hashCode() {
		return Objects.hash(texture, signature);
	}
}
