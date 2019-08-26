package edu.java.core.sort.external.processor.impl;

import edu.java.core.sort.external.processor.Source;

import java.io.*;
import java.util.*;

public class LinedFileSource implements Source<String> {

    private BufferedReader reader;
    private final String name;

    public LinedFileSource(String name){
        this.name = name;
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }

    @Override
    public Iterator<String> extract() {
        return reader.lines().iterator();
    }

    @Override
    public LinedFileSource open() throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(new File(name)));
        return this;
    }
}
