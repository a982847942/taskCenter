package edu.util.login.facade.auth;

import java.security.Principal;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/26 16:13
 */
public interface Authentication {

    /**
     * 获取鉴权主体
     * @return
     */
    Principal getPrincipal();

    /**
     * 是否通过鉴权
     * @return
     */
    boolean isAuthenticated();

    /**
     * 获取token
     *
     * @return
     */
    String getToken();
}
