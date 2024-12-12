package growth.tech.spring.easytest.facade;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 11:26
 */

import growth.tech.core.common.rpc.RpcTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;

/**
 * easy test
 */
@Slf4j
@AllArgsConstructor
public class GrowthEasyTestServerFacade implements GrowthTechEasyTestService.Iface {

    /**
     * applicationContext
     */
    private final ApplicationContext applicationContext;

    /**
     * 方法参数 discoverer
     */
    private static final DefaultParameterNameDiscoverer PARAMETER_DISCOVERER = new DefaultParameterNameDiscoverer();

    @Override
    public void ping() {
    }

    @Override
    public ListAllBeanInfosResponse listAllBeanInfos(Context context, ListAllBeanInfosRequest request) {
        return RpcTemplate.execute(request, new RpcCallback<ListAllBeanInfosRequest, ListAllBeanInfosResponse>() {
            @Override
            public String identifier() {
                return "[GROWTH_EASY_TEST][listAllBeanInfos]";
            }

            @Override
            public ListAllBeanInfosResponse execute(ListAllBeanInfosRequest request) {
                ListAllBeanInfosResponse response = new ListAllBeanInfosResponse();
                String[] allBeanNames = applicationContext.getBeanDefinitionNames();
                List<BeanInfo> beanInfoList = Lists.newArrayListWithCapacity(allBeanNames.length);
                for (String beanName : allBeanNames) {
                    Object bean = applicationContext.getBean(beanName);
                    BeanInfo beanInfo = new BeanInfo();
                    beanInfo.setBeanName(beanName);
                    beanInfo.setFullyQualifiedName(bean.getClass().getName());
                    beanInfoList.add(beanInfo);
                }
                response.setBeanInfos(beanInfoList);
                return response;
            }
        });
    }

    @Override
    @SuppressWarnings("ExtractMethodRecommender")
    public ListAllMethodInfosResponse listAllMethodInfos(Context context, ListAllMethodInfosRequest request) {
        return RpcTemplate.execute(request, new RpcCallback<ListAllMethodInfosRequest, ListAllMethodInfosResponse>() {
            @Override
            public String identifier() {
                return "[GROWTH_EASY_TEST][listAllMethodInfos]";
            }

            @Override
            public ListAllMethodInfosResponse execute(ListAllMethodInfosRequest request) {
                ListAllMethodInfosResponse response = new ListAllMethodInfosResponse();
                String beanName = request.getBeanName();
                Object bean = applicationContext.getBean(beanName);
                AssertUtils.isNotNull(bean, SYSTEM_ERROR, "找不到对应bean。beanName=" + beanName);

                Pair<Object, Method[]> meta = EasyTestUtils.getTargetObjectWithMethods(bean);
                Method[] methods = meta.getRight();
                List<MethodInfo> methodInfoList = Lists.newArrayListWithCapacity(methods.length);
                for (int i = 0; i < methods.length; i++) {
                    MethodInfo methodInfo = new MethodInfo();
                    methodInfo.setMethodName(methods[i].getName());
                    methodInfo.setMethodToString(methods[i].toString());
                    methodInfo.setMethodIdx(i);
                    methodInfo.setModifier(methods[i].getModifiers());
                    methodInfoList.add(methodInfo);
                }
                response.setMethodInfos(methodInfoList);
                return response;
            }
        });
    }

    @Override
    public GetMethodArgInfosResponse getMethodArgInfos(Context context, GetMethodArgInfosRequest request) {
        return RpcTemplate.execute(request, new RpcCallback<GetMethodArgInfosRequest, GetMethodArgInfosResponse>() {
            @Override
            public String identifier() {
                return "[GROWTH_EASY_TEST][getMethodArgInfos]";
            }

            @Override
            public GetMethodArgInfosResponse execute(GetMethodArgInfosRequest request) {
                GetMethodArgInfosResponse response = new GetMethodArgInfosResponse();
                String beanName = request.getBeanName();
                int methodIdx = request.getMethodIdx();
                Object bean = applicationContext.getBean(beanName);
                AssertUtils.isNotNull(bean, SYSTEM_ERROR, "找不到对应bean。beanName=" + beanName);
                Pair<Object, Method[]> meta = EasyTestUtils.getTargetObjectWithMethods(bean);
                Method[] methods = meta.getRight();
                AssertUtils.isTrue(methodIdx < methods.length, SYSTEM_ERROR,
                        "方法的数量变少了。可能是代码更新导致的，请重新选择方法重试");
                Method method = methods[methodIdx];
                AssertUtils.isNotNull(method, SYSTEM_ERROR,
                        "找不到匹配的方法。可能是代码更新导致的，请重新选择bean和方法重试。methodIdx=" + methodIdx);

                Parameter[] params = method.getParameters();
                if (params == null) {
                    response.setArgPlaceholder(JSON.toJSONString(Maps.newHashMap()));
                    response.setArgInfos(Maps.newHashMap());
                    return response;
                }
                // 获取参数名
                String[] parseParamNames = PARAMETER_DISCOVERER.getParameterNames(method);

                // 参数基本信息
                Map<String, ArgInfo> argInfos = Maps.newLinkedHashMapWithExpectedSize(params.length);
                // 参数placeHolder
                Map<String, Object> placeHolderMap = Maps.newLinkedHashMapWithExpectedSize(params.length);
                for (int i = 0; i < params.length; i++) {
                    Parameter param = params[i];
                    ArgInfo argInfo = new ArgInfo();
                    String paramName = parseParamNames == null ? param.getName() : parseParamNames[i];
                    argInfo.setArgName(paramName);
                    argInfo.setArgType(param.getType().getName());
                    argInfos.put(paramName, argInfo);
                    placeHolderMap.put(paramName, DefaultValueUtils.getDefaultValue(param.getParameterizedType()));
                }
                response.setArgInfos(argInfos);
                response.setArgPlaceholder(JSON.toJSONString(placeHolderMap));

                // 扩展信息
                Map<String, Object> extra = Maps.newHashMap();
                extra.put("duplicateName", Arrays.stream(methods).filter(m -> m.getName().equals(method.getName()))
                        .count() > 1);
                response.setExtra(JSON.toJSONString(extra));
                return response;
            }
        });
    }

    @Override
    public InvokeResponse invokeWithJsonString(Context context, InvokeWithJsonStringRequest request) {
        return RpcTemplate.execute(request, new RpcCallback<InvokeWithJsonStringRequest, InvokeResponse>() {
            @Override
            public String identifier() {
                return "[GROWTH_EASY_TEST][invokeWithJsonString]";
            }

            @Override
            public InvokeResponse execute(InvokeWithJsonStringRequest request) {
                InvokeResponse response = new InvokeResponse();
                String beanName = request.getBeanName();
                String methodName = request.getMethodName();
                int methodIdx = request.getMethodIdx();

                // 校验
                Object bean = applicationContext.getBean(beanName);
                AssertUtils.isNotNull(bean, SYSTEM_ERROR, "找不到对应bean。beanName=" + beanName);
                Pair<Object, Method[]> meta = EasyTestUtils.getTargetObjectWithMethods(bean);
                Object beanTarget = meta.getLeft();
                Method[] methods = meta.getRight();
                AssertUtils.isTrue(methodIdx < methods.length, SYSTEM_ERROR,
                        "方法的数量变少了。可能是代码更新导致的，请重新选择方法重试");
                Method method = methods[methodIdx];
                AssertUtils.isEquals(method.getName(), methodName, SYSTEM_ERROR,
                        "找不到匹配的方法。可能是代码更新导致的，请重新选择bean和方法重试。methodName=" + methodName);

                // 通过fastjson将args反序列化成具体参数
                Parameter[] parameters = method.getParameters();
                String[] parseParameterNames = PARAMETER_DISCOVERER.getParameterNames(method);
                Object[] args = new Object[parameters.length];
                JSONObject parameterJsonObject = JSON.parseObject(request.getArgsJsonString());
                for (int i = 0; i < parameters.length; i++) {
                    Parameter parameter = parameters[i];
                    String parameterName = parseParameterNames == null ? parameter.getName() : parseParameterNames[i];
                    // 注意 这里是根据参数名字来映射参数的
                    String argString = JSON.toJSONString(MapUtils.getObject(parameterJsonObject, parameterName));
                    args[i] = JSON.parseObject(argString, parameters[i].getParameterizedType());
                }
                StringBuilder logStr = new StringBuilder();
                logStr.append("appName: ").append(request.getAppName()).append('\n');
                logStr.append("beanName: ").append(beanName).append('\n');
                logStr.append("methodName: ").append(methodName).append('\n');
                logStr.append("methodIdx: ").append(methodIdx).append('\n');
                logStr.append("originArgsJson:\n").append(request.getArgsJsonString()).append('\n');
                logStr.append("actualArgs: ").append(Arrays.stream(args).map(arg -> "[" + arg + "]")
                        .collect(Collectors.toList())).append('\n');

                try {
                    // 调用方法执行。走到这里，无论调用method成功失败与否，外层方法都是success的
                    method.setAccessible(true);
                    Object invokeResult = method.invoke(beanTarget, args);
                    response.setInvokeSuccess(true);
                    response.setInvokeResult(JSON.toJSONString(invokeResult));
                    logStr.append("invokeResult: ").append(invokeResult);
                    log.info("{} debug biz logic success.\n{}", identifier(), logStr);
                } catch (Throwable bizEx) {
                    log.warn("{} debug biz logic error.\n:{}", identifier(), logStr, bizEx);
                    response.setInvokeSuccess(false);
                    response.setInvokeResult(ExceptionUtils.getStackTrace(bizEx));
                }
                return response;
            }
        });
    }

    @Override
    public InvokeResponse evalGroovyScript(Context context, EvalGroovyScriptRequest request) {
        return RpcTemplate.execute(request, new RpcCallback<EvalGroovyScriptRequest, InvokeResponse>() {
            @Override
            public String identifier() {
                return "[GROWTH_EASY_TEST][evalGroovyScript]";
            }

            @Override
            public InvokeResponse execute(EvalGroovyScriptRequest request) {
                Object instance = GroovySpringFactory.loadNewInstance(request.getScript());
                // 目前脚本只支持实现 Runnable 和 Callable
                String inter;
                if (Runnable.class.isAssignableFrom(instance.getClass())) {
                    inter = Runnable.class.getName();
                } else if (Callable.class.isAssignableFrom(instance.getClass())) {
                    inter = Callable.class.getName();
                } else {
                    throw new CommonException(PARAMETER_ILLEGAL, "groovy脚本需要实现 Runnable 或 Callable 接口");
                }

                StringBuilder logStr = new StringBuilder();
                logStr.append("appName: ").append(request.getAppName()).append('\n');
                logStr.append("interface: ").append(inter).append('\n');
                logStr.append("script:\n").append(request.getScript()).append('\n');

                InvokeResponse response = new InvokeResponse();
                try {
                    // 执行groovy脚本。走到这里，无论调用脚本执行成功失败与否，外层方法都是success的
                    if (Runnable.class.isAssignableFrom(instance.getClass())) {
                        ((Runnable) instance).run();
                        logStr.append("invokeResult: Void");
                    } else {
                        Object invokeResult = ((Callable<?>) instance).call();
                        response.setInvokeResult(JSON.toJSONString(invokeResult));
                        logStr.append("invokeResult: ").append(invokeResult);
                    }
                    response.setInvokeSuccess(true);
                    log.info("{} eval script success.\n{}", identifier(), logStr);
                } catch (Throwable bizEx) {
                    log.warn("{} eval script error.\n:{}", identifier(), logStr, bizEx);
                    response.setInvokeSuccess(false);
                    response.setInvokeResult(ExceptionUtils.getStackTrace(bizEx));
                }
                return response;
            }
        });
    }
}