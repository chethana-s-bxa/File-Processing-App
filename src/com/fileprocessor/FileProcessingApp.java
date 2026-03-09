package com.fileprocessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FileProcessingApp {

    private static FileStats processBatch(List<File> batch) {

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

    public static void main(String[] args) {
        int totalLines=0;
        int totalWords=0;

        long startTime = System.currentTimeMillis();

        System.out.println("Scanning directory......\n");

        File directory = new File("files");
        File[] files = directory.listFiles();

        List<File> textFiles = new ArrayList<>();

//        List<Future<FileStats>> futures = new ArrayList<>();
        if(files != null){
            for(File file : files){
//                if(textFiles.size() == 100) break;
                if(file.isFile() && file.getName().endsWith(".txt")){
                    textFiles.add(file);
                }
            }
        }

        textFiles.sort((f1, f2) -> {
            try {

                BasicFileAttributes attr1 = Files.readAttributes(f1.toPath(), BasicFileAttributes.class);
                BasicFileAttributes attr2 = Files.readAttributes(f2.toPath(), BasicFileAttributes.class);

                int compareTime = attr1.creationTime().compareTo(attr2.creationTime());

                if(compareTime != 0){
                    return compareTime;
                }

                return f1.getName().compareTo(f2.getName());

            } catch(IOException e){
                throw new RuntimeException(e);
            }
        });

        System.out.println("Total files found: "+textFiles.size()+"\n");
        System.out.println("Processing files......\n");

        int batchSize = 100;

        for(int i = 0; i < textFiles.size(); i += batchSize) {

            int end = Math.min(i + batchSize, textFiles.size());

            List<File> batch = textFiles.subList(i, end);
            System.out.println("Processing batch: " + (i / batchSize + 1));
            System.out.println("----------------------------------");
            FileStats batchResult = processBatch(batch);

            totalLines += batchResult.getLineCount();
            totalWords += batchResult.getWordCount();
        }


//        Sequential
//        for(File file : textFiles){
//            FileStats data = FileProcessor.processFile(file);
//            totalLines += data.getLineCount();
//            totalWords += data.getWordCount();
//            System.out.println("File: " +data.getFileName());
//            System.out.println("Lines: "+data.getLineCount());
//            System.out.println("Words: "+data.getWordCount());
//            System.out.println();
//        }


//        Multi - threading

//        List<Future<FileStats>> futures = new ArrayList<>();
//        ExecutorService executor = Executors.newFixedThreadPool(4);
//        for(File file : textFiles){
//            FileProcessingTask task = new FileProcessingTask(file);
//            Future<FileStats> future = executor.submit(task);
//            futures.add(future);
//        }

//        for(Future<FileStats> future : futures){
//            try {
//                FileStats data = future.get();
//
//                totalLines += data.getLineCount();
//                totalWords += data.getWordCount();
//
//                System.out.println("File: " + data.getFileName());
//                System.out.println("Lines: " + data.getLineCount());
//                System.out.println("Words: " + data.getWordCount());
//                System.out.println();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        executor.shutdown();

        long endTime = System.currentTimeMillis();
        System.out.println("---------------------------------------------");
        System.out.println("SUMMARY");
        System.out.println("---------------------------------------------");
        System.out.println("Total files processed: "+textFiles.size());
        System.out.println("Total lines: "+totalLines);
        System.out.println("Total words: "+totalWords);

        System.out.println("\nExecution time: "+(endTime-startTime));
    }
}
