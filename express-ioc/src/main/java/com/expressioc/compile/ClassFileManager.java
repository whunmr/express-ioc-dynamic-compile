package com.expressioc.compile;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import java.io.IOException;
import java.security.SecureClassLoader;

public class ClassFileManager extends ForwardingJavaFileManager {
    private JavaClassObject classObject;

    public ClassFileManager(StandardJavaFileManager standardManager) {
        super(standardManager);
    }

    @Override
    public ClassLoader getClassLoader(Location location) {
        return new SecureClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                byte[] b = classObject.getBytes();
                return super.defineClass(name, b, 0, b.length);
            }
        };
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling)
            throws IOException {
        classObject = new JavaClassObject(className, kind);
        return classObject;
    }
}