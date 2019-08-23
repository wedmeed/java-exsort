package edu.java.core.sort.external.processor._impl;

import edu.java.core.sort.external.processor.DataSource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class MemSource<T> implements DataSource<T> {

    public List<T> data;

    public MemSource(Supplier<T> producer, int size) {
        data = new ArrayList<>(size);
        while(size-- > 0) {
            data.add(producer.get());
        }
    }

    @Override
    public DataSource<T> open(){
        return this;
    }

    @Override
    public void close(){}

    @Override
    public Iterator<T> iterator() {
        return data.iterator();
    }
}