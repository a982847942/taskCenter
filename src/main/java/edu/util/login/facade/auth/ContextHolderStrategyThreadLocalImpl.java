package edu.util.login.facade.auth;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/26 16:14
 */
public class ContextHolderStrategyThreadLocalImpl implements ContextHolderStrategy<AuthContext>{
    private final ThreadLocal<AuthContext> threadLocal = new ThreadLocal<>();
    @Override
    public AuthContext getContext() {
        AuthContext authContext = threadLocal.get();
        if (authContext == null){
            AuthContext empty = createEmptyContext();
            setContext(empty);
            return empty;
        }
        return authContext;
    }

    @Override
    public void setContext(AuthContext context) {
        threadLocal.set(context);
    }

    @Override
    public void clearContext() {
        threadLocal.remove();
    }

    @Override
    public AuthContext createEmptyContext() {
        return new AuthContext() {
            @Override
            public Authentication getAuthentication() {
                return null;
            }
        };
    }
}
