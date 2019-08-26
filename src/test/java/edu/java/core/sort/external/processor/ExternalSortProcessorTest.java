package edu.java.core.sort.external.processor;

import edu.java.core.sort.external._utils.FileGenerator;
import edu.java.core.sort.external._utils.LinedFileSizeChecker;
import edu.java.core.sort.external._utils.LinedFileSortingChecker;
import edu.java.core.sort.external._utils.SortingChecker;
import edu.java.core.sort.external.processor._impl.MemMediator;
import edu.java.core.sort.external.processor._impl.MemSource;
import edu.java.core.sort.external.processor._impl.MemStore;
import edu.java.core.sort.external.processor.impl.LinedFilesMediator;
import edu.java.core.sort.external.processor.impl.LinedFileSource;
import edu.java.core.sort.external.processor.impl.LinedFileStore;
import org.junit.Test;

import java.io.File;
import java.util.*;

import static org.junit.Assert.*;

public class ExternalSortProcessorTest {

    @Test
    public void canSortFromFile() {
        try {
            FileGenerator.run("test_source.txt", 20, System.lineSeparator());

            Source<String> source = new LinedFileSource("test_source.txt");
            MultiMediator<String> mediator = new LinedFilesMediator("tmp");
            Store<String> store = new LinedFileStore("test_dest.txt");
            ExternalSortProcessor.run(source,mediator,store,String::compareToIgnoreCase,10);

            LinedFileSortingChecker.run("test_dest.txt", String::compareToIgnoreCase);
            LinedFileSizeChecker.run("test_dest.txt",20);
        }
        finally {
            assertTrue(new File("test_source.txt").delete());
            assertTrue(new File("test_dest.txt").delete());
        }
    }


    @Test
    public void canSortFromMemory() {

        MemSource<String> source = new MemSource<>(()-> UUID.randomUUID().toString(), 20);
        MemMediator<String> mediator = new MemMediator<>();
        MemStore<String> store = new MemStore<>();
        ExternalSortProcessor.run(source,mediator,store,String::compareToIgnoreCase,10);

        SortingChecker.run(store.data, String::compareToIgnoreCase);
        assertEquals(20, store.data.size());

    }

    @Test
    public void canSortSmallAmounts() {

        MemSource<String> source = new MemSource<>(()-> UUID.randomUUID().toString(), 10);
        MemMediator<String> mediator = new MemMediator<>();
        MemStore<String> store = new MemStore<>();
        ExternalSortProcessor.run(source,mediator,store,String::compareToIgnoreCase,20);

        SortingChecker.run(store.data, String::compareToIgnoreCase);
        assertEquals(10, store.data.size());

    }

    @Test
    public void tolerantToZeroInputs() {

        MemSource<String> source = new MemSource<>(()-> UUID.randomUUID().toString(), 0);
        MemMediator<String> mediator = new MemMediator<>();
        MemStore<String> store = new MemStore<>();
        ExternalSortProcessor.run(source,mediator,store,String::compareToIgnoreCase,20);

        SortingChecker.run(store.data, String::compareToIgnoreCase);
        assertEquals(0, store.data.size());

    }


    @Test
    public void canSortNonString() {

        Random rand = new Random();

        MemSource<Integer> source = new MemSource<>(rand::nextInt, 20);
        MemMediator<Integer> mediator = new MemMediator<>();
        MemStore<Integer> store = new MemStore<>();
        ExternalSortProcessor.run(source,mediator,store,Integer::compare,10);

        SortingChecker.run(store.data, Integer::compare);
        assertEquals(20, store.data.size());

    }


    @Test
    public void closesResources() {

        MemSource<String> source = new MemSource<>(()-> UUID.randomUUID().toString(), 10);
        MemMediator<String> mediator = new MemMediator<>();
        MemStore<String> store = new MemStore<>();
        ExternalSortProcessor.run(source,mediator,store,String::compareToIgnoreCase,20);

        assertTrue(mediator.closed);
        assertTrue(store.closed);
        assertTrue(source.closed);

    }






}