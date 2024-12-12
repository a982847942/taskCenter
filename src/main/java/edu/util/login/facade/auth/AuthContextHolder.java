package edu.util.login.facade.auth;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/26 16:13
 */
public class AuthContextHolder {
    private static final ContextHolderStrategy<AuthContext> holdStrategy = new ContextHolderStrategyThreadLocalImpl();

    public static AuthContext getContext(){
        return holdStrategy.getContext();
    }

    public static void setContext(AuthContext context){
        if (context == null){
            // 日志
        }
        holdStrategy.setContext(context);
    }

    public static void clearContext(){
        holdStrategy.clearContext();
    }
}
