package edu.java.core.sort.external.common;

import java.util.Collection;
import java.util.Iterator;

public interface Extractor<T>{

   Iterator<T> extract() throws Exception;

}
