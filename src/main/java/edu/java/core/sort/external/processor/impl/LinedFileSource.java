package edu.java.core.sort.external.processor.impl;

import edu.java.core.sort.external.processor.DataSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;

public class LinedFileSource implements DataSource<String> {

    private final String fileName;
    private BufferedReader reader;

    public LinedFileSource(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public DataSource<String> open() throws Exception {
        if (reader != null) throw new Exception("Wrong source usage");
        reader = new BufferedReader(new FileReader(new File(fileName)));
        return this;
    }

    @Override
    public Iterator<String> iterator() {
        return reader.lines().iterator();
    }

    @Override
    public void close() throws Exception {
        reader.close();
        reader = null;
    }
}
