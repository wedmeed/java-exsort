package edu.java.core.sort.external.processor.impl;

import edu.java.core.sort.external.processor.MultiSource;

import java.io.*;
import java.util.*;

public class LinedFilesSource implements MultiSource<String> {

    private List<LinedFileSource> sources;
    private Collection<String> names;

    public LinedFilesSource(Collection<String> names){
        this.names = names;
    }

    @Override
    public Collection<Iterator<String>> extract() {
        List<Iterator<String>> result = new ArrayList<>(sources.size());
        for (LinedFileSource source : sources) {
            result.add(source.extract());
        }
        return result;
    }

    @Override
    public LinedFilesSource open() throws FileNotFoundException {
        this.sources = new ArrayList<>(names.size());
        for (String name : names) {
            LinedFileSource source = new LinedFileSource(name).open();
            this.sources.add(source);
        }
        return this;
    }

    @Override
    public void close() throws IOException {
        IOException ex = null;
        for (LinedFileSource source : sources) {
            try {
                source.close();
            } catch (IOException e) {
                if (ex == null) {
                    ex = e;
                } else {
                    ex.addSuppressed(e);
                }
            }
        }
        if (ex != null) {
            throw ex;
        }
    }
}
