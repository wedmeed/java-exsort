package edu.java.core.sort.external.processor;

import edu.java.core.sort.external.common.Saver;

import java.util.Iterator;


/**
 * The interface for data storing. Should be ready to accept any amount of data storing requests.
 * Creation of persistence session is independent from object creation.
 *
 * @param <T>
 */
public interface Store<T> extends AutoCloseable, Saver<T> {

    /**
     * Start persistence session.
     *
     * @return object to close persistence session
     */
    Store<T> open() throws Exception;
}
