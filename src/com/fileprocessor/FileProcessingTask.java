package com.fileprocessor;

import java.io.File;
import java.util.concurrent.Callable;

public class FileProcessingTask  implements Callable<FileStats> {
    private File file;

    public FileProcessingTask(File file) {
        this.file = file;
    }

    @Override
    public FileStats call() {
        return FileProcessor.processFile(file);
    }
}
