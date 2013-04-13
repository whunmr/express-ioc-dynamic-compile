package com.expressioc;

import com.expressioc.compile.SourceCompiler;
import com.google.common.io.Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

public class ConfigurationLoader {

    public static Container load(String configFilePath) throws FileNotFoundException {
        return load(configFilePath, null);
    }

    public static Container load(String configFilePath, Container parent) {
        File configFile = getFileOrResource(configFilePath);
        String containerClass = Files.getNameWithoutExtension(configFile.getName());

        try {
            ClassLoader classLoader = SourceCompiler.compile(configFile.getAbsolutePath(), containerClass);
            Class<?> clazz = classLoader.loadClass(containerClass);

            BaseContainer container = (BaseContainer)clazz.newInstance();
            return initContainer(parent, container);
        } catch (Exception e) {
            throw new LoadingException(e);
        }
    }

    private static BaseContainer initContainer(Container parent, BaseContainer container) throws IllegalAccessException {
        container.init(parent);
        container.collectBeansFromFields();
        return container;
    }

    private static File getFileOrResource(String configFilePath) {
        URL resource = ConfigurationLoader.class.getClassLoader().getResource(configFilePath);
        return resource != null ? new File(resource.getFile()) : new File(configFilePath);
    }
}
