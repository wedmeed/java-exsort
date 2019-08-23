package edu.java.core.sort.external._utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.UUID;

public class FileGenerator {
    public static void run(String fileName, int amount, String separator){
        try (Writer wr = new BufferedWriter(new FileWriter(fileName))) {
            while (amount-- > 0) {
                wr.write(UUID.randomUUID().toString());
                wr.write(separator);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
