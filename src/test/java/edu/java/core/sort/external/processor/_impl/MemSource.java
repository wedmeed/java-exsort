package edu.java.core.sort.external.processor._impl;

import edu.java.core.sort.external.processor.Source;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class MemSource<T> implements Source<T> {

    public List<T> data;
    public boolean closed;

    public MemSource(Supplier<T> producer, int size) {
        data = new ArrayList<>(size);
        while(size-- > 0) {
            data.add(producer.get());
        }
    }

    @Override
    public Source<T> open(){
        return this;
    }

    @Override
    public void close(){
        closed = true;
    }

    @Override
    public Iterator<T> extract() {
        return data.iterator();
    }
}