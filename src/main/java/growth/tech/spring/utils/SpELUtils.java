package growth.tech.spring.utils;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 21:53
 */

import lombok.SneakyThrows;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * SpEL工具类
 */
public class SpELUtils {

    /** 方法参数 discoverer */
    private static final DefaultParameterNameDiscoverer PARAMETER_NAME_DISCOVERER =
            new DefaultParameterNameDiscoverer();

    /** SPEl上下文解析器 */
    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    /**
     * 根据一个方法调用<h2>解析EL表达式</h2>
     *
     * @param elExpression EL表达式
     * @param resultType   结果类型
     * @param args         方法参数
     * @param method       方法
     * @param <T>          结果类型
     * @return 解析结果
     */
    public static <T> T parseElExpression(String elExpression, Class<T> resultType, Object[] args, Method method) {
        String[] paraNameArr = PARAMETER_NAME_DISCOVERER.getParameterNames(method);
        if (paraNameArr == null || paraNameArr.length == 0) {
            return null;
        }

        StandardEvaluationContext elContext = new StandardEvaluationContext();
        // 把方法参数放入SPEl上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            Object paraValue = args[i];
            elContext.setVariable(paraNameArr[i], paraValue);
        }
        // 解析SPEl表达式
        return EXPRESSION_PARSER.parseExpression(elExpression).getValue(elContext, resultType);
    }

    /**
     * 根据某个POJO对象<h2>解析EL表达式</h2>
     *
     * @param elExpression EL表达式
     * @param resultType   结果类型
     * @param pojo         pojo
     * @param <T>          结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> T parseElExpression(String elExpression, Class<T> resultType, Object pojo) {
        Field[] fields = pojo.getClass().getDeclaredFields();
        if (fields.length == 0) {
            return null;
        }

        StandardEvaluationContext elContext = new StandardEvaluationContext();
        // 把DTO的所有字段放入SPEl上下文中
        for (Field field : fields) {
            field.setAccessible(true);
            elContext.setVariable(field.getName(), field.get(pojo));
        }

        // 解析SPEl表达式
        return EXPRESSION_PARSER.parseExpression(elExpression).getValue(elContext, resultType);
    }

    /**
     * 根据多个POJO对象<h2>解析EL表达式</h2>
     *
     * @param pojos(s)     pojo(s)
     * @param elExpression EL表达式
     * @param resultType   结果类型
     * @param <T>          结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> T parseElExpression(String elExpression, Class<T> resultType, Object... pojos) {
        if (pojos == null || pojos.length == 0) {
            return null;
        }

        StandardEvaluationContext elContext = new StandardEvaluationContext();

        for (Object pojo : pojos) {
            Field[] fields = pojo.getClass().getDeclaredFields();
            // 把每个pojo的所有字段放入SPEl上下文中
            for (Field field : fields) {
                field.setAccessible(true);
                elContext.setVariable(field.getName(), field.get(pojo));
            }
        }

        // 解析SPEl表达式
        return EXPRESSION_PARSER.parseExpression(elExpression).getValue(elContext, resultType);
    }

}