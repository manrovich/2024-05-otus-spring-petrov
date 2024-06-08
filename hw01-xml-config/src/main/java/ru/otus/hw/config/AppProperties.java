package ru.otus.hw.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AppProperties implements TestFileNameProvider, TestFileSkipLinesProvider{
    private String testFileName;
    private Integer testFileSkipLines;
}
