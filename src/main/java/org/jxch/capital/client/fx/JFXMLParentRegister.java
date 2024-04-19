package org.jxch.capital.client.fx;

import cn.hutool.core.io.FileUtil;
import javafx.fxml.FXMLLoader;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jxch.capital.client.config.JFXConfig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class JFXMLParentRegister implements ApplicationListener<ContextRefreshedEvent> {
    public final static String FXML_LOADER_BEAN_NAME_SUFFIX = "FXMLLoader";
    private final ResourcePatternResolver resourcePatternResolver;
    private final JFXConfig jfxConfig;

    @Override
    @SneakyThrows
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        log.info("Spring容器启动完毕，开始自动解析装配fxml相关的bean定义, 取controller类名作为bean name");
        ConfigurableApplicationContext  applicationContext = (ConfigurableApplicationContext) event.getApplicationContext();
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
        for (Resource resource : resourcePatternResolver.getResources(jfxConfig.getFxmlScan())) {
            beanFactory.registerBeanDefinition(getBeanName(resource),  getBeanDefinition(resource, applicationContext));
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
    private GenericBeanDefinition getBeanDefinition(@NonNull Resource resource, @NonNull ConfigurableApplicationContext applicationContext) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        beanDefinition.setBeanClass(FXMLLoader.class);
        beanDefinition.setInstanceSupplier(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(resource.getURL());
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                return fxmlLoader;
            } catch (IOException e) {
                log.error("解析fxml文件失败：{}. [{}]", resource.getFilename(), e.getMessage());
                throw new RuntimeException(e);
            }
        });
        return beanDefinition;
    }

}
