package edu.java.core.sort.external.processor.impl;


import edu.java.core.sort.external.processor.DataMediator;
import edu.java.core.sort.external.processor.DataSource;
import edu.java.core.sort.external.processor.DataStore;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LinedMultiFileMediator implements DataMediator<String> {

    private static Logger log = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final Comparator<String> comparator;
    private DataSource<String> reader;
    private final String fileNamePattern;
    private volatile int fileCount = 0;

    public LinedMultiFileMediator(String fileNamePattern, Comparator<String> comparator) {
        this.fileNamePattern = fileNamePattern;
        this.comparator = comparator;
    }

    private synchronized String generateName(){
        return fileNamePattern + fileCount++;
    }

    @Override
    public void accept(Iterable<String> buffer) {
        DataStore<String> store = new LinedFileStore(generateName());
        store.accept(buffer);
    }

    @Override
    public void clear() {
        for (int i = 0; i < fileCount; i++){
            new File(fileNamePattern + i).deleteOnExit();
        }
        log.log(Level.INFO, "{0} {1} files have been deleted",
                new Object[]{fileCount, fileNamePattern});
    }

    @Override
    public DataSource<String> open() throws Exception {
        if (reader != null) throw new Exception("Wrong source usage");
        List<String> names = new ArrayList<>(fileCount);
        for(int i = 0; i< fileCount; i++){
            names.add(fileNamePattern + i);
        }
        reader = new LinedMultiFileSource(names,comparator).open();
        return this;
    }

    @Override
    public void close() throws Exception {
        reader.close();
        reader = null;
    }

    @Override
    public Iterator<String> iterator() {
        return reader.iterator();
    }

}
