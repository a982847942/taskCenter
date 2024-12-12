package edu.util.login.facade.auth;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/26 17:01
 */
public class AuthContextImpl implements AuthContext{
    private Authentication authentication;

    public AuthContextImpl(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public Authentication getAuthentication() {
        return authentication;
    }
}
