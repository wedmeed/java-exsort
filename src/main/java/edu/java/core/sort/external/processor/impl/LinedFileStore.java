package edu.java.core.sort.external.processor.impl;

import edu.java.core.sort.external.processor.Store;
import edu.java.core.sort.external.utils.StackTraceExtractor;

import java.io.*;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.logging.LogManager;
import java.util.logging.Logger;


/**
 * An implementation of {@code Store} that put all data to single result file.
 */
public class LinedFileStore implements Store<String> {

    private static Logger log = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final String fileName;
    private Writer writer;

    public LinedFileStore(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public void save(Iterator<String> data) {
        data.forEachRemaining((entry)->{
            try {
                writer.write(entry);
                writer.write(System.lineSeparator());
            } catch (IOException e) {
                log.severe(StackTraceExtractor.process(e));
            }
        });

    }

    @Override
    public Store<String> open() throws IOException {
        this.writer = new BufferedWriter(new FileWriter(fileName,false));
        return this;
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
    }
}
