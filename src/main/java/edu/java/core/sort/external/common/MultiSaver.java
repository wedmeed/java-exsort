package edu.java.core.sort.external.common;

import java.util.Iterator;

public interface MultiSaver<T>{

   void save(Iterator<T> data, Integer number) throws Exception;

}
