package com.fileprocessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileProcessor {
    public static FileStats processFile(File file){
        int lineCount=0;
        int wordCount=0;

        try(Scanner scan = new Scanner(file)) {
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                lineCount++;
                line = line.trim();
                if(!line.isEmpty()){
                    String[] words = line.split("\\s+");
                    wordCount += words.length;
                }

            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            e.printStackTrace();
        }
        return new FileStats(file.getName() , lineCount , wordCount);

    }
}
