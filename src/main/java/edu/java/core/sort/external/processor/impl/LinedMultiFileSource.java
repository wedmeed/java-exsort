package edu.java.core.sort.external.processor.impl;

import edu.java.core.sort.external.common.ComposedIterator;
import edu.java.core.sort.external.processor.DataSource;

import java.util.*;

public class LinedMultiFileSource implements DataSource<String> {

    private Collection<String> names;
    private List<DataSource<String>> readers;
    private Comparator<String> defaultComparator;

    public LinedMultiFileSource(Collection<String> names, Comparator<String> comparator) {
        this.defaultComparator = comparator;
        this.names = names;
    }

    @Override
    public DataSource<String> open() throws Exception {
        if (readers != null) throw new Exception("Wrong source usage");
        this.readers = new ArrayList<>(names.size());
        for (String name : names) {
            DataSource<String> reader = new LinedFileSource(name).open();
            this.readers.add(reader);
        }
        return this;
    }

    @Override
    public Iterator<String> iterator() {
        List<Iterator<String>> iterators = new ArrayList<>(readers.size());
        for (DataSource<String> rd: readers){
            iterators.add(rd.iterator());
        }
        return new ComposedIterator<>(defaultComparator,iterators);
    }

    @Override
    public void close() throws Exception {
        Exception ex = null;
        for (DataSource reader : readers) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    if (ex == null) {
                        ex = e;
                    } else {
                        ex.addSuppressed(e);
                    }
                }
            }
        }
        if (ex != null) {
            throw ex;
        }
        readers = null;
    }
}
