package edu.java.core.sort.external.common;

import java.util.Collection;
import java.util.Iterator;

public interface MultiExtractor<T>{

    Collection<Iterator<T>> extract() throws Exception;

}
