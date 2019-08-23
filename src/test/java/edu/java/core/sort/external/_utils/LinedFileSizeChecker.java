package edu.java.core.sort.external._utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

public class LinedFileSizeChecker {
    public static void run(String fileName, int size){
        try (BufferedReader read = new BufferedReader(new FileReader(fileName))) {
            if (read.lines().count() != size) throw new RuntimeException("Mismatch items amount!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
