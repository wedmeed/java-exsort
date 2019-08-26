package edu.java.core.sort.external.processor._impl;

import edu.java.core.sort.external.processor.MultiMediator;

import java.util.*;

public class MemMediator<T> implements MultiMediator<T> {
    public List<List<T>> data = new ArrayList<>();
    public boolean closed;

    @Override
    public MultiMediator<T> open() {
        return this;
    }

    @Override
    public void close() {
        closed = true;
    }

    @Override
    public Collection<Iterator<T>> extract() {
        ArrayList<Iterator<T>> result = new ArrayList<>();
        for (List<T> datum : data) {
            result.add(datum.iterator());
        }
        return result;
    }

    @Override
    public void save(Iterator<T> data, Integer number) {
        ArrayList<T> store = new ArrayList<>();
        data.forEachRemaining(store::add);
        this.data.add(store);
    }
}