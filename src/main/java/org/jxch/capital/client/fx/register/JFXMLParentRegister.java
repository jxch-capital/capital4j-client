package org.jxch.capital.client.fx.register;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.spring.SpringUtil;
import javafx.fxml.FXMLLoader;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class JFXMLParentRegister implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {
    public final static String FXML_LOADER_BEAN_NAME_SUFFIX = "FXMLLoader";
    private final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    private Environment environment;

    @Override
    @SneakyThrows
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry registry) throws BeansException {
        log.info("Spring容器启动完毕，开始自动解析装配fxml相关的bean定义, 取controller类名作为bean name");
        String fxmlResources = Objects.requireNonNull(environment.getProperty("spring.fx.fxml-scan", "/**/*.fxml"));
        for (Resource resource : resourcePatternResolver.getResources(fxmlResources)) {
            registry.registerBeanDefinition(getBeanName(resource),  getBeanDefinition(resource));
        }
    }

    @NonNull
    @SneakyThrows
    private String getBeanName(@NonNull Resource resource) {
        IllegalArgumentException exception = new IllegalArgumentException("无法解析fx:controller属性，请检查" + resource.getURL().getPath() + "文件");
        String line = FileUtil.readUtf8Lines(resource.getURL()).stream().filter(fileLine -> fileLine.contains("fx:controller")).findFirst().orElseThrow(() -> exception);
        Matcher matcher = Pattern.compile("fx:controller=\"(.*?)\"").matcher(line);
        if (matcher.find()) {
            String fullClassName = matcher.group(1);
            String[] fullClassPath = fullClassName.split("\\.");
            return StringUtils.uncapitalize(fullClassPath[fullClassPath.length - 1]) + FXML_LOADER_BEAN_NAME_SUFFIX;
        } else {
            throw exception;
        }
    }

    @NonNull
    private GenericBeanDefinition getBeanDefinition(@NonNull Resource resource) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        beanDefinition.setBeanClass(FXMLLoader.class);
        beanDefinition.setInstanceSupplier(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(resource.getURL());
                fxmlLoader.setControllerFactory(SpringUtil::getBean);
                return fxmlLoader;
            } catch (IOException e) {
                log.error("解析fxml文件失败：{}. [{}]", resource.getFilename(), e.getMessage());
                throw new RuntimeException(e);
            }
        });
        return beanDefinition;
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }

}
