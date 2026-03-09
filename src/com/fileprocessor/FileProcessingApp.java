package com.fileprocessor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;

public class FileProcessingApp {
    public static void main(String[] args) {
        int totalLines=0;
        int totalWords=0;
        long startTime = System.currentTimeMillis();
        System.out.println("Scanning directory......\n");

        File directory = new File("files");
        File[] files = directory.listFiles();
        List<File> textFiles = new ArrayList<>();
        List<Future<FileStats>> futures = new ArrayList<>();
        if(files != null){
            for(File file : files){
                if(textFiles.size() == 100) break;
                if(file.isFile() && file.getName().endsWith(".txt")){
                    textFiles.add(file);
                }
            }
        }


        System.out.println("Total files found: "+textFiles.size()+"\n");
        System.out.println("Processing files......\n");
        ExecutorService executor = Executors.newFixedThreadPool(4);
        for(File file : textFiles){
            FileProcessingTask task = new FileProcessingTask(file);
            Future<FileStats> future = executor.submit(task);
            futures.add(future);
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

        for(Future<FileStats> future : futures){
            try {
                FileStats data = future.get();

                totalLines += data.getLineCount();
                totalWords += data.getWordCount();

                System.out.println("File: " + data.getFileName());
                System.out.println("Lines: " + data.getLineCount());
                System.out.println("Words: " + data.getWordCount());
                System.out.println();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
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
