package edu.util.context;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/26 14:41
 */
public class DomainContextHolder {
    private static final ThreadLocal<DomainContext> threadLocal = new ThreadLocal<>();

    public static DomainContext getContext() {
        return threadLocal.get();
    }

    public static void setContext(DomainContext context){
        if (context == null){
            return;
        }
        threadLocal.set(context);
    }

    public static void clearContext(){
        threadLocal.remove();
    }
}
