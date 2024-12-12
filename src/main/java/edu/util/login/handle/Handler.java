package edu.util.login.handle;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/26 19:30
 */
public interface Handler<R, T> {
    T handle(R request);
}
