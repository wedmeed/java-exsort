package edu.java.core.sort.external;

import edu.java.core.sort.external._utils.FileGenerator;
import edu.java.core.sort.external._utils.LinedFileSizeChecker;
import edu.java.core.sort.external._utils.LinedFileSortingChecker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.*;

import static org.junit.Assert.*;

public class FileSorterTest {


    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;


    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void canPrintHelpOnPosixLike() {
        FileSorter.main(new String[]{"-h"});
        assertNotEquals("", outContent.toString());
        assertEquals("", errContent.toString());
    }

    @Test
    public void canPrintHelpOnGnuLike() {
        FileSorter.main(new String[]{"--help"});
        assertNotEquals("", outContent.toString());
        assertEquals("", errContent.toString());
    }

    @Test
    public void canAcceptSourceAndDestination() {
        FileGenerator.run("test_source.txt", 10, System.lineSeparator());
        FileSorter.main(new String[]{"--source=test_source.txt", "--destination=test_dest.txt"});
        LinedFileSortingChecker.run("test_dest.txt", String::compareToIgnoreCase);
        LinedFileSizeChecker.run("test_dest.txt",10);
        //assertEquals("", errContent.toString());
        new File("test_source.txt").delete();
        new File("test_dest.txt").delete();
    }

    @Test
    public void canRunWithoutArgs() {
        FileGenerator.run("source.txt", 10, System.lineSeparator());
        FileSorter.main(new String[]{});
        LinedFileSortingChecker.run("sorted.txt", String::compareToIgnoreCase);
        LinedFileSizeChecker.run("test_dest.txt",10);
        //assertEquals("", errContent.toString());
        new File("test_source.txt").delete();
        new File("test_dest.txt").delete();
    }

    @Test
    public void callsExternalSorter() {
        // can not be tested without Mockito
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

}