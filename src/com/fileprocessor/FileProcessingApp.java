package com.fileprocessor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileProcessingApp {
    public static void main(String[] args) {


        System.out.println("Scanning directory......");

        File directory = new File("files");
        File[] files = directory.listFiles();
        List<File> textFiles = new ArrayList<>();

        if(files != null){
            for(File file : files){
                if(textFiles.size() == 100) break;
                if(file.isFile() && file.getName().endsWith(".txt")){
                    textFiles.add(file);
                }
            }
        }
        System.out.println("Total files found: "+textFiles.size());
    }
}
