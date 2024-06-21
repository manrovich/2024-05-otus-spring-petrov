package ru.otus.hw.dao;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface DataLoader {

    InputStream load(String source) throws FileNotFoundException;
}
