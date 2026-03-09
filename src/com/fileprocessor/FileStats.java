package com.fileprocessor;

public class FileStats {

    private String fileName;
    private int lineCount;
    private int wordCount;

    public FileStats(String fileName, int lineCount, int wordCount) {
        this.fileName = fileName;
        this.lineCount = lineCount;
        this.wordCount = wordCount;
    }

    public String getFileName() {
        return fileName;
    }

    public int getLineCount() {
        return lineCount;
    }

    public int getWordCount() {
        return wordCount;
    }

//    @Override
//    public String toString() {
//        return "FileStats{" +
//                "fileName='" + fileName + '\'' +
//                ", lineCount=" + lineCount +
//                ", wordCount=" + wordCount +
//                '}';
//    }
}
