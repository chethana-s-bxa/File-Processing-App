package com.fileprocessor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateFile {
    public static void generateDummyFiles(int numberOfFiles) {

        File directory = new File("files");

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String[] sampleLines = {
                "Java is a powerful programming language",
                "Multithreading improves performance",
                "ExecutorService manages thread pools",
                "Concurrency allows parallel task execution",
                "File processing is a common backend task",
                "Spring Boot is widely used for microservices",
                "Data structures improve algorithm efficiency",
                "Artificial Intelligence is transforming industries"
        };

        Random random = new Random();

        for (int i = 4; i <= numberOfFiles; i++) {

            File file = new File(directory, "file" + i + ".txt");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

                int numberOfLines = random.nextInt(5) + 3;

                for (int j = 0; j < numberOfLines; j++) {

                    String line = sampleLines[random.nextInt(sampleLines.length)];

                    writer.write(line);
                    writer.newLine();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(numberOfFiles - 3 + " dummy files generated successfully.");
    }
}
