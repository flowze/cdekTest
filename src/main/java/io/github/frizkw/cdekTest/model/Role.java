package io.github.frizkw.cdekTest.model;

public enum Role {
    USER, ADMIN;
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
