package com.fileprocessor;


import java.io.File;
import java.util.List;

public class SequentialFileProcessor {

    public static FileStats processFiles(List<File> files) {

        int totalLines = 0;
        int totalWords = 0;

        int batchSize = 100;

        for (int i = 0; i < files.size(); i += batchSize) {

            int end = Math.min(i + batchSize, files.size());

            List<File> batch = files.subList(i, end);

            System.out.println("Processing batch: " + (i / batchSize + 1));
            System.out.println("----------------------------------");

            for (File file : batch) {

                FileStats data = FileProcessor.processFile(file);

                totalLines += data.getLineCount();
                totalWords += data.getWordCount();

                System.out.println("File: " + data.getFileName());
                System.out.println("Lines: " + data.getLineCount());
                System.out.println("Words: " + data.getWordCount());
                System.out.println();
            }
        }

        return new FileStats("SEQUENTIAL", totalLines, totalWords);
    }
}