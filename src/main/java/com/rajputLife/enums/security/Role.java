package com.rajputLife.enums.security;

public enum Role {
	USER("User"),
	ADMIN("Admin");

	private final String role;

	Role(final String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return role;
	}
}
