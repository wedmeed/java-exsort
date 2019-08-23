package edu.java.core.sort.external.processor;

import edu.java.core.sort.external._utils.FileGenerator;
import edu.java.core.sort.external._utils.LinedFileSizeChecker;
import edu.java.core.sort.external._utils.LinedFileSortingChecker;
import edu.java.core.sort.external._utils.SortingChecker;
import edu.java.core.sort.external.processor._impl.MemMediator;
import edu.java.core.sort.external.processor._impl.MemSource;
import edu.java.core.sort.external.processor._impl.MemStore;
import edu.java.core.sort.external.processor.impl.LinedFileSource;
import edu.java.core.sort.external.processor.impl.LinedFileStore;
import edu.java.core.sort.external.processor.impl.LinedMultiFileMediator;
import org.junit.Test;

import java.io.File;
import java.util.*;
import java.util.function.Supplier;

import static org.junit.Assert.*;

public class ExternalSortProcessorTest {

    @Test
    public void canSortFromFile() {
        FileGenerator.run("test_source.txt", 10, System.lineSeparator());

        DataSource<String> source = new LinedFileSource("test_source.txt");
        DataMediator<String> mediator = new LinedMultiFileMediator("tmp",String::compareToIgnoreCase);
        DataStore<String> store = new LinedFileStore("test_dest.txt");
        ExternalSortProcessor.run(source,mediator,store,String::compareToIgnoreCase,20);

        LinedFileSortingChecker.run("test_dest.txt", String::compareToIgnoreCase);
        LinedFileSizeChecker.run("test_dest.txt",10);
        new File("test_source.txt").delete();
        new File("test_dest.txt").delete();
    }

    @Test
    public void canSortFromMemory() {

        MemSource<String> source = new MemSource<>(()-> UUID.randomUUID().toString(), 10);
        MemMediator<String> mediator = new MemMediator<>(String::compareToIgnoreCase);
        MemStore<String> store = new MemStore<>();
        ExternalSortProcessor.run(source,mediator,store,String::compareToIgnoreCase,20);

        SortingChecker.run(store.data, String::compareToIgnoreCase);
        assertEquals(10, store.data.size());

    }

    @Test
    public void canSortNonString() {

        Random rand = new Random();

        MemSource<Integer> source = new MemSource<>(rand::nextInt, 10);
        MemMediator<Integer> mediator = new MemMediator<>(Integer::compare);
        MemStore<Integer> store = new MemStore<>();
        ExternalSortProcessor.run(source,mediator,store,Integer::compare,20);

        SortingChecker.run(store.data, Integer::compare);
        assertEquals(10, store.data.size());

    }

    @Test
    public void closesAndCleansResources() {

        MemSource<String> source = new MemSource<>(()-> UUID.randomUUID().toString(), 10);
        MemMediator<String> mediator = new MemMediator<>(String::compareToIgnoreCase);
        MemStore<String> store = new MemStore<>();
        ExternalSortProcessor.run(source,mediator,store,String::compareToIgnoreCase,20);

        assertTrue(mediator.cleared);
        assertTrue(mediator.closed);

    }






}