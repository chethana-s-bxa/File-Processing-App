package com.fileprocessor;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ConcurrentFileProcessor {

    public static FileStats processBatch(List<File> batch) {

        ExecutorService executor = Executors.newFixedThreadPool(4);

        List<Future<FileStats>> futures = new ArrayList<>();

        int batchLines = 0;
        int batchWords = 0;

        for (File file : batch) {

            FileProcessingTask task = new FileProcessingTask(file);

            Future<FileStats> future = executor.submit(task);

            futures.add(future);
        }

        for (Future<FileStats> future : futures) {

            try {

                FileStats data = future.get();

                batchLines += data.getLineCount();
                batchWords += data.getWordCount();

                System.out.println("File: " + data.getFileName());
                System.out.println("Lines: " + data.getLineCount());
                System.out.println("Words: " + data.getWordCount());
                System.out.println();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        return new FileStats("BATCH", batchLines, batchWords);
    }
}
