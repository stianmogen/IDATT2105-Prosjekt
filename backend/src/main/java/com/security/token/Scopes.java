package com.security.token;


public enum Scopes {

    REFRESH_TOKEN;

    public String scope() {
        return "ROLE_" + this.name();
    }

}
