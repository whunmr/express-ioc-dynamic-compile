package com.expressioc.compile;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

public class JavaFileSourceObject extends SimpleJavaFileObject {
    private String content;

    public JavaFileSourceObject(String className, String content) {
        super(URI.create("string:///" + className.replace('.', '/')+ Kind.SOURCE.extension), Kind.SOURCE);
        this.content = content;
    }

    @Override
    public String getCharContent(boolean ignoreEncodingErrors) {
        return content;
    }
}