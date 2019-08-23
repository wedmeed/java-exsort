package edu.java.core.sort.external.common;

import java.util.*;

/**
 * the object which allow to iterate through many other iterators
 * the custom alternative for {@link org.apache.commons.collections4.IteratorUtils::collatedIterator}
 */
public class ComposedIterator <T> implements Iterator<T> {

    /**
     * The mechanism for selection of next element. Backed by {@link Bucket}.
     * When some element is extracted and returned to user then bucket with this
     * element will be refilled from related source iterator and returned to queue
     */
    private final PriorityQueue<Bucket<T>> bucketQueue;

    /**
     * Creates composed iterator and fill read buffer
     *
     * @param comparator define order of extraction from source iterators
     * @param iterators source iterators
     */
    public ComposedIterator(Comparator<T> comparator, Collection<Iterator<T>> iterators) {
        this.bucketQueue = new PriorityQueue<>(iterators.size(),(o1, o2)-> comparator.compare(o1.value,o2.value));
        for (Iterator<T> itr: iterators) {
            if (itr.hasNext()) {
                bucketQueue.offer(new Bucket<>(itr.next(), itr));
            }
        }
    }

    /**
     * Method returns next element in iteration. It do not throw exceptions.
     * In case if nothing can be returned then result will be null
     *
     * @return next element from iterators
     */
    public T poll(){
        Bucket<T> bucket = bucketQueue.poll();
        if (bucket != null) {
            T result = bucket.value;
            if (bucket.source.hasNext()) {
                bucket.value = bucket.source.next();
                bucketQueue.offer(bucket);
            }
            return result;
        }
        return null;
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return !bucketQueue.isEmpty();
    }

    /**
     * Method returns next element in iteration.
     *
     * @return next element from iterators
     */
    @Override
    public T next() {
        T result = poll();
        if (result == null) throw new NoSuchElementException();
        return result;
    }

    /**
     * Store for target object with it's source
     */
    private static class Bucket <T>{

        private T value;
        private final Iterator<T> source;

        private Bucket(T value, Iterator<T> source){
            this.value = value;
            this.source = source;
        }
    }

}
