package edu.java.core.sort.external.common;

import java.util.Iterator;

public interface Saver<T>{

   void save(Iterator<T> data) throws Exception;

}
