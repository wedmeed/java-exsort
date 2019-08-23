package edu.java.core.sort.external.processor;

public interface DataSource<T> extends AutoCloseable, Iterable<T> {

    DataSource<T> open () throws Exception;

}
