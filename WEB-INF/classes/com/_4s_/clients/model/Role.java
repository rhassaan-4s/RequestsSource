package com._4s_.clients.model;

public enum Role {
    ADMINISTRATOR("ROLE_ADMINISTRATOR"),
    TENANT_ADMIN("ROLE_TENANT_ADMIN"),
    USER("ROLE_USER");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
