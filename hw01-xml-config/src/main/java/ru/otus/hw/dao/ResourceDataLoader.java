package ru.otus.hw.dao;

import java.io.InputStream;

public class ResourceDataLoader implements DataLoader {

    @Override
    public InputStream load(String source) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(source);

        if (inputStream == null) {
            throw new IllegalArgumentException("Resource not found " + source);
        } else {
            return inputStream;
        }
    }
}
