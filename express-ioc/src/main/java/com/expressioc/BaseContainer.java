package com.expressioc;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class BaseContainer implements Container{
    public Container parent;
    public Map<String, Object> beanMap = new HashMap<String, Object>();

    public <T> T getBean(String beanName, Class<T> clazz) {
        return (T)beanMap.get(beanName);
    }

    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }

    public void init(Container parent) {
        this.parent = parent;
    }

    public void collectBeansFromFields() throws IllegalAccessException {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (Modifier.isPublic(field.getModifiers())) {
                beanMap.put(field.getName(), field.get(this));
            }
        }
    }

    public Object ref(String beanName) {
        Object beanInCurrentContainer = getBean(beanName);
        if (beanInCurrentContainer != null) {
            return beanInCurrentContainer;
        }

        return refParent(beanName);
    }

    public Object refParent(String beanName) {
        return parent == null ? null : parent.getBean(beanName);
    }
}
