package com.fileprocessor;


import java.io.File;
import java.util.List;

public class SequentialFileProcessor {

    public static FileStats processFiles(List<File> files) {

        int totalLines = 0;
        int totalWords = 0;

        for (File file : files) {

            FileStats data = FileProcessor.processFile(file);

            totalLines += data.getLineCount();
            totalWords += data.getWordCount();

            System.out.println("File: " + data.getFileName());
            System.out.println("Lines: " + data.getLineCount());
            System.out.println("Words: " + data.getWordCount());
            System.out.println();
        }

        return new FileStats("SEQUENTIAL", totalLines, totalWords);
    }
}