package edu.java.core.sort.external.processor;

/**
 * The class for temporary data storing. Can act as both {@code DataSource}
 * and {@code DataStore}. It has method for the cleaning of all temporary resources.
 *
 * @param <T>
 */
public interface DataMediator<T> extends DataSource<T>,DataStore<T> {

    void clear();

}
