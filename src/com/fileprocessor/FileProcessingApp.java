package com.fileprocessor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileProcessingApp {

    public static void main(String[] args) {

        GenerateFile.generateDummyFiles(200);

        long startTime = System.currentTimeMillis();

        File directory = new File("files");
        File[] files = directory.listFiles();

        List<File> textFiles = new ArrayList<>();

        if (files != null) {
            for (File file : files) {

                if (file.isFile() && file.getName().endsWith(".txt")) {
                    textFiles.add(file);
                }
            }
        }

        System.out.println("Total files found: " + textFiles.size());
        System.out.println("Processing files...\n");

        int totalLines = 0;
        int totalWords = 0;



        boolean useConcurrent = false;

        if (!useConcurrent) {

            FileStats result = SequentialFileProcessor.processFiles(textFiles);

            totalLines = result.getLineCount();
            totalWords = result.getWordCount();

        } else {

            int batchSize = 100;

            for (int i = 0; i < textFiles.size(); i += batchSize) {

                int end = Math.min(i + batchSize, textFiles.size());

                List<File> batch = textFiles.subList(i, end);

                System.out.println("Processing batch: " + (i / batchSize + 1));

                FileStats batchResult =
                        ConcurrentFileProcessor.processBatch(batch);

                totalLines += batchResult.getLineCount();
                totalWords += batchResult.getWordCount();
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("----------------------------------");
        System.out.println("SUMMARY");
        System.out.println("----------------------------------");

        System.out.println("Total files processed: " + textFiles.size());
        System.out.println("Total lines: " + totalLines);
        System.out.println("Total words: " + totalWords);

        System.out.println("\nExecution time: " + (endTime - startTime) + " ms");
    }
}