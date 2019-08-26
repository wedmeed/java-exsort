package edu.java.core.sort.external.processor._impl;

import edu.java.core.sort.external.processor.Store;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MemStore<T> implements Store<T> {
    public List<T> data = new ArrayList<>();
    public boolean closed;

    @Override
    public Store<T> open() {
        return this;
    }

    @Override
    public void save(Iterator<T> data) {
        data.forEachRemaining(this.data::add);
    }

    @Override
    public void close(){
        closed = true;
    }
}
