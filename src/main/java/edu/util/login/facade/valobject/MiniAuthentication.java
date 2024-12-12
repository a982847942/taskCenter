package edu.util.login.facade.valobject;

import edu.util.login.facade.auth.Authentication;

import java.security.Principal;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/26 16:24
 */
public class MiniAuthentication implements Authentication {
    private UserVal userVal;
    private boolean authenticated;

    private String token;
    @Override
    public Principal getPrincipal() {
        return userVal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public String getToken() {
        return token;
    }
}
