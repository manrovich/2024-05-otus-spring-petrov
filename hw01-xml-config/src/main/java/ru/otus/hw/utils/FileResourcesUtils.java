package ru.otus.hw.utils;

import java.io.InputStream;

public class FileResourcesUtils {

    public static InputStream getFileFromResourceAsStream(Class<?> clazz, String fileName) {
        ClassLoader classLoader = clazz.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("File not found! " + fileName);
        } else {
            return inputStream;
        }
    }
}
