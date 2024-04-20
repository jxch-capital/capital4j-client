package org.jxch.capital.client.python.register;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.base.Functions;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jxch.capital.client.Capital4JClientApp;
import org.jxch.capital.client.config.AppConfig;
import org.jxch.capital.client.config.JFXConfig;
import org.jxch.capital.client.python.executor.PythonExecutor;
import org.jxch.capital.client.uilt.ReflectionsU;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@NoArgsConstructor
public class PythonBinderRegister implements BeanFactoryPostProcessor, InvocationHandler, ApplicationListener<ContextRefreshedEvent> {
    private PyBindRunnerParamProcessor pyBindRunnerParamProcessor;
    private final Map<String, String> binders = new HashMap<>();
    private volatile Map<String, Resource> resources = null;
    private ResourcePatternResolver resourcePatternResolver;
    private PythonExecutor pythonExecutor;
    private JFXConfig jfxConfig;
    private AppConfig appConfig;

    @Override
    @SneakyThrows
    public Object invoke(Object proxy, Method method, Object[] args) {
        if (Objects.isNull(resources)) {
            resources = Arrays.stream(resourcePatternResolver.getResources(jfxConfig.getPyScan())).collect(Collectors.toMap(Resource::getFilename, Functions.identity()));
        }

        String simpleName = Arrays.stream(proxy.getClass().getInterfaces()).filter(PyBindRunner.class::isAssignableFrom).findFirst().orElseThrow().getSimpleName();
        Resource resource = resources.get(binders.get(simpleName) + ".py");

        if (Objects.isNull(resource)) {
            throw new IllegalArgumentException("No resource found for: " + simpleName);
        }

        File pyFile = resource.getFile();
        File appScriptDir = new File(appConfig.getPythonExecutorAppScriptsAbsolutePath());
        File copyFile = appScriptDir.toPath().resolve(pyFile.getName()).toFile();
        Files.copy(pyFile.toPath(), copyFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return pythonExecutor.run(copyFile, pyBindRunnerParamProcessor.encode(args[0]));
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info("扫描PyBinder并自动注册为Bean");
        for (Class<?> pyBindRunnerClazz : ReflectionsU.scanAllClass(Capital4JClientApp.class.getPackageName(), clazz -> clazz.isInterface() && PyBindRunner.class.isAssignableFrom(clazz))) {
            if (AnnotationUtil.hasAnnotation(pyBindRunnerClazz, PythonBind.class)) {
                PythonBind pythonBind = AnnotationUtil.getAnnotation(pyBindRunnerClazz, PythonBind.class);
                beanFactory.registerSingleton(StringUtils.uncapitalize(pyBindRunnerClazz.getSimpleName()), ReflectionsU.createJDKProxyByInterface(pyBindRunnerClazz, this));
                binders.put(pyBindRunnerClazz.getSimpleName(), pythonBind.pyName());
            }
        }
    }

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        pyBindRunnerParamProcessor = SpringUtil.getBean(PyBindRunnerParamProcessor.class);
        pythonExecutor = SpringUtil.getBean(PythonExecutor.class);
        resourcePatternResolver = event.getApplicationContext();
        jfxConfig = SpringUtil.getBean(JFXConfig.class);
        appConfig = SpringUtil.getBean(AppConfig.class);
    }

}
