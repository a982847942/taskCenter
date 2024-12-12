package edu.nuaa.initializationbean;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author brain
 * @version 1.0
 * @date 2024/6/8 23:58
 * 等同于 XML中的模板Bean，执行Bean的初始化流程时会判断beanDefinition是否是抽象的bean
 * 因此，即使这里加了@Component也不会报错
 */
@Component
public abstract class AbstractSingletonBean {
    public static final String name = "abstractSingletonBean";

    public static void main(String[] args) {
        ArrayList<Object> temp = new ArrayList<>();
        temp.remove(temp.size() - 1);

    }

}
