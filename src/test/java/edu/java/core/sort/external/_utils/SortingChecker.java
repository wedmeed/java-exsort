package edu.java.core.sort.external._utils;

import java.util.Comparator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SortingChecker {
    public static <T> void run(Iterable<T> source, Comparator<T> comparator){
        run(StreamSupport.stream(source.spliterator(), false), comparator);
    }

    public static <T> void run(Stream<T> source, Comparator<T> comparator){
        source.reduce((a, b)->{
            if (comparator.compare(a,b)>0)
                throw new RuntimeException("File isn't sorted!");
            return b;
        });
    }
}
