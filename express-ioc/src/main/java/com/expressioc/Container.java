package com.expressioc;

public interface Container {
    public Object getBean(String beanName);
    public <T> T getBean(String beanName, Class<T> clazz);
}
