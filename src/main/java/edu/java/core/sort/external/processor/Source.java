package edu.java.core.sort.external.processor;

import edu.java.core.sort.external.common.Extractor;

import java.util.Iterator;


/**
 * Universal interface for data extraction.
 * Creation of access session is independent from object creation.
 *
 * @param <T>
 */
public interface Source<T> extends AutoCloseable, Extractor<T> {

    /**
     * Start persistence session.
     *
     * @return object to close persistence session
     */
    Source<T> open() throws Exception;

}
