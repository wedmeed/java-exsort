package edu.java.core.sort.external.processor.impl;

import edu.java.core.sort.external.processor.DataStore;

import java.io.*;

public class LinedFileStore implements DataStore<String> {

    private final String fileName;

    public LinedFileStore(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void accept(Iterable<String> strings) {
        try (Writer wr = new BufferedWriter(new FileWriter(fileName))) {
            for (String entry : strings) {
                wr.write(entry);
                wr.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
