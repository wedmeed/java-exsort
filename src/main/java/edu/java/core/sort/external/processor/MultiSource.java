package edu.java.core.sort.external.processor;

import edu.java.core.sort.external.common.MultiExtractor;

import java.util.Collection;
import java.util.Iterator;


/**
 * Universal interface for data extraction.
 * Creation of access session is independent from object creation.
 *
 * @param <T>
 */
public interface MultiSource<T> extends AutoCloseable, MultiExtractor<T> {

    /**
     * Start persistence session.
     *
     * @return object to close persistence session
     */
    MultiSource<T> open() throws Exception;

}
