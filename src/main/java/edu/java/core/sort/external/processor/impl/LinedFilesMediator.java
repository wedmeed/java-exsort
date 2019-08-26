package edu.java.core.sort.external.processor.impl;


import edu.java.core.sort.external.processor.MultiMediator;
import edu.java.core.sort.external.processor.Store;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LinedFilesMediator implements MultiMediator<String> {

    private static Logger log = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final String fileNamePattern;
    private volatile int fileCount = 0;
    private volatile int lastUnreadFile = 0;
    private List<LinedFilesSource> readers = new LinkedList<>();

    public LinedFilesMediator(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    private synchronized String generateName(){
        return fileNamePattern + fileCount++;
    }
    private synchronized List<String> getNames(){
        List<String> result = new ArrayList<>(fileCount-lastUnreadFile);
        for (;lastUnreadFile<fileCount;lastUnreadFile++)
            result.add(fileNamePattern + lastUnreadFile);
        return result;
    }

    @Override
    public Collection<Iterator<String>> extract() throws FileNotFoundException {
        LinedFilesSource reader = new LinedFilesSource(getNames()).open();
        readers.add(reader);
        return reader.extract();
    }

    @Override
    public void save(Iterator<String> data, Integer chunk) throws Exception {
        try(Store<String> store = new LinedFileStore(generateName()).open()) {
            store.save(data);
        }
    }

    @Override
    public LinedFilesMediator open(){
        return this;
    }

    @Override
    public synchronized void close() throws Exception {
        for (LinedFilesSource reader : readers) {
            reader.close();
        }
        for (int i = 0; i < fileCount; i++){
            new File(fileNamePattern + i).deleteOnExit();
        }
        log.log(Level.INFO, "{0} {1} files have been deleted",
                new Object[]{fileCount, fileNamePattern});

    }

}
