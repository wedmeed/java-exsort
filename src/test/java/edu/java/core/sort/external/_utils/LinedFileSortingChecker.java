package edu.java.core.sort.external._utils;

import java.io.*;
import java.util.Comparator;

public class LinedFileSortingChecker {
    public static void run(String fileName, Comparator<String> comparator){
        try (BufferedReader read = new BufferedReader(new FileReader(fileName))) {
            SortingChecker.run(read.lines(),comparator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
