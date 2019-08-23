package edu.java.core.sort.external.processor._impl;

import edu.java.core.sort.external.processor.DataStore;

import java.util.ArrayList;
import java.util.List;

public class MemStore<T> implements DataStore<T> {
    public List<T> data = new ArrayList<>();

    @Override
    public void accept(Iterable<T> ts) {
        ts.forEach(data::add);
    }
}
