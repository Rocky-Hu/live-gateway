package com.live.gateway.util;

import com.live.gateway.config.AbstractConfig;
import com.live.gateway.exception.ProxyException;
import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/10/13 14:28
 */
@Slf4j
public final class PropertiesUtil {

    public static Properties loadProperties(String configFile) {
        final Properties properties = new Properties();
        try (final InputStream in = new BufferedInputStream(new FileInputStream(configFile))) {
            properties.load(in);
        } catch (Exception ex) {
            throw new ProxyException("Load properties error.", ex);
        }

        return properties;
    }

    public static Properties loadResourceProperties(String configFile) {
        final Properties properties = new Properties();
        try (final InputStream stream = PropertiesUtil.class.getClassLoader().getResourceAsStream(configFile)) {
            properties.load(stream);
        } catch (Exception ex) {
            throw new ProxyException("Load properties error.", ex);
        }

        return properties;
    }

    public static void properties2Config(final Properties properties, final AbstractConfig config) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(config.getClass());
            PropertyDescriptor pds[] = beanInfo.getPropertyDescriptors();
            Method setterMethod = null;
            for (PropertyDescriptor pd : pds) {
                setterMethod = pd.getWriteMethod();
                if (setterMethod == null)
                    continue;
                String methodName = setterMethod.getName();
                String propertyName = methodName.substring(3,4).toLowerCase() + methodName.substring(4);
                String property = properties.getProperty(config.getConfigPrefix() + propertyName);
                if (property != null) {
                    Class<?>[] parameterTypes = setterMethod.getParameterTypes();
                    if (parameterTypes != null && parameterTypes.length > 0) {
                        String parameterType = parameterTypes[0].getSimpleName();
                        Object arg = null;
                        if (parameterType.equals("int") || parameterType.equals("Integer")) {
                            arg = Integer.parseInt(property);
                        } else if (parameterType.equals("long") || parameterType.equals("Long")) {
                            arg = Long.parseLong(property);
                        } else if (parameterType.equals("double") || parameterType.equals("Double")) {
                            arg = Double.parseDouble(property);
                        } else if (parameterType.equals("boolean") || parameterType.equals("Boolean")) {
                            arg = Boolean.parseBoolean(property);
                        } else if (parameterType.equals("float") || parameterType.equals("Float")) {
                            arg = Float.parseFloat(property);
                        } else if (parameterType.equals("String")) {
                            arg = property;
                        } else {
                            continue;
                        }
                        setterMethod.invoke(config, arg);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ProxyException("Parse properties to config error: {}.", ex);
        }

    }

}
