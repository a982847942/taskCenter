package edu.util.login.handle.impl;

import edu.util.context.BizType;
import edu.util.login.facade.valobject.MiniAuthentication;
import edu.util.login.handle.Handler;
import edu.util.login.handle.LoginRequest;
import edu.util.login.handle.MiniLogInRequest;
import edu.util.login.handle.ReqHandler;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/26 19:49
 */
@ReqHandler(bizType = BizType.ARIES, support = MiniLogInRequest.class)
public class MiniLoginRequestHandler implements Handler<LoginRequest, MiniAuthentication> {
    @Override
    public MiniAuthentication handle(LoginRequest request) {
        return null;
    }
}
