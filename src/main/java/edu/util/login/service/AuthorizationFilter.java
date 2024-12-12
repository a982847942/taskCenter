package edu.util.login.service;

import edu.util.login.LoginRequired;
import edu.util.login.facade.auth.Authentication;
import edu.util.login.facade.exception.AuthorizationException;

/**
 * 处理具体的登录鉴权
 * @author brain
 * @version 1.0
 * @date 2024/10/26 16:52
 */
public interface AuthorizationFilter {
    boolean support(LoginRequired anno, Object[] args);

    Authentication authority (LoginRequired anno, Object[] args) throws AuthorizationException;

}
