package edu.util.login.facade;

import edu.util.context.BizType;
import edu.util.login.Dispatcher;
import edu.util.login.facade.auth.Authentication;
import edu.util.login.handle.LoginRequest;
import edu.util.login.handle.UpdateRequest;

import javax.annotation.Resource;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/26 17:08
 */
public class UserFacade {
    @Resource
    private Dispatcher dispatcher;

    public Authentication login(BizType bizType, LoginRequest loginRequest){
        return dispatcher.dispatch(bizType, loginRequest);
    }

    public void update(BizType bizType, UpdateRequest updateRequest){
        dispatcher.dispatch(bizType, updateRequest);
    }
}
