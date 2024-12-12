package growth.trigger;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 21:44
 */
public interface TaskPointInvoker<C,A,R> {
    String getPointName();
    Class<C> getConfigClazz();
    R trigger(String activityId, String userId, QuizTaskPointConfig<A> config, C configParam, A argument);
}
