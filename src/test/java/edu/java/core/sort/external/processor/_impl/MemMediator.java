package edu.java.core.sort.external.processor._impl;

import edu.java.core.sort.external.processor.DataMediator;
import edu.java.core.sort.external.processor.DataSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class MemMediator<T> implements DataMediator<T> {
    public List<T> data = new ArrayList<>();
    public boolean cleared;
    public boolean closed;
    public Comparator<T> comparator;

    public MemMediator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void clear() {
        cleared = true;
    }

    @Override
    public DataSource<T> open() throws Exception {
        return this;
    }

    @Override
    public void close() throws Exception {
        closed = true;
    }

    @Override
    public Iterator<T> iterator() {
        data.sort(comparator);
        return data.iterator();
    }

    @Override
    public void accept(Iterable<T> ts) {
        ts.forEach((o)->data.add(o));
    }
}