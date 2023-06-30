package dev.ftb.mods.ftbchunks.data;

public enum ProtectionOverride {
	CHECK,
	DENY,
	ALLOW;

	public boolean isOverride() {
		return this != CHECK;
	}

	public boolean shouldPreventInteraction() {
		return this == DENY;
	}
}
