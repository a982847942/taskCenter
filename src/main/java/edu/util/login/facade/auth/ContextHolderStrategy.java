package edu.util.login.facade.auth;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/26 16:13
 */
public interface ContextHolderStrategy<T> {
    T getContext();

    void setContext(T context);

    void clearContext();

    T createEmptyContext();
}
