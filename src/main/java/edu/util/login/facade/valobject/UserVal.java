package edu.util.login.facade.valobject;

import edu.util.context.BizType;

import javax.security.auth.Subject;
import java.security.Principal;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/26 16:25
 */
public class UserVal implements Principal {
    private Long id;
    private String identity;
    private BizType bizType;
    private String email;
    private String nickName;
    private String avatar;
    private String status;
    private Integer credentialStatus;
    @Override
    public String getName() {
        return identity;
    }
    public UserVal from(){
        // 转换
        UserVal userVal = new UserVal();
        return userVal;
    }
}
