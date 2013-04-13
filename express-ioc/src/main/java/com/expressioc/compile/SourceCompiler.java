package com.expressioc.compile;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SourceCompiler {
    public static ClassLoader compile(String configFile, String configClassName) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        JavaFileManager fileManager = new ClassFileManager(compiler.getStandardFileManager(diagnostics, Locale.ENGLISH, Charset.forName("UTF-8")));

        compiler.getTask(null, fileManager, diagnostics, null, null, getJavaFileObjects(configFile, configClassName)).call();

        printDiagnostics(diagnostics);
        return fileManager.getClassLoader(null);
    }

    private static List<JavaFileObject> getJavaFileObjects(String configFilePath, String configClassName) throws IOException {
        String src = Files.toString(new File(configFilePath), Charsets.UTF_8);
        return Arrays.asList((JavaFileObject) new JavaFileSourceObject(configClassName, src));
    }

    private static void printDiagnostics(DiagnosticCollector<JavaFileObject> diagnostics) {
        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            System.out.format("Error on line %d:%d in %s \n %s", diagnostic.getLineNumber(),
                diagnostic.getColumnNumber(), diagnostic.getSource().toString(), diagnostic.getMessage(Locale.ENGLISH));
        }
    }
}
