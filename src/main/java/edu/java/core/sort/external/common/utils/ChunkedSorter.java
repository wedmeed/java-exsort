package edu.java.core.sort.external.common.utils;


import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


/**
 * Form any amount of incoming data to chunks of sorted data
 */
public class ChunkedSorter {

    private static Logger log = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * The method implements main class functionality
     *
     * @param source iterator for extracting source data. Can be as deep as needed
     * @param cmp the comparator for sorting
     * @param saver consumer for storing chunks. Will be invoked at least once
     * @param dose size of chunk
     * @return amount of produced chunks
     */
    public static <T> int run(Iterator<T> source, Comparator<T> cmp, Consumer<Iterable<T>> saver, int dose) {
        int parallelismLevel = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                parallelismLevel,
                parallelismLevel,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        CompletionService<String> executionService = new ExecutorCompletionService<>(executor);
        int chunksAmount = 0;


        log.log(Level.INFO, "Start processing with {0} parallelismLevel", parallelismLevel);

        while(source.hasNext()) {

            // 0. wait if there are too many works
            if (chunksAmount >= parallelismLevel){
                try {
                    executionService.take();
                } catch (InterruptedException ignored) {}
            }

            // 1. read to buffer
            List<T> buffer = readBuffer(source, dose);

            // 2. sort buffer
            executionService.submit(()->{
                buffer.sort(cmp);
                // 3. write to output
                saver.accept(buffer);
            }, "");
            chunksAmount++;

            // 4. report state
            log.log(Level.INFO, "Chunk with {0} unique items has been sent to sorting.", buffer.size());
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException ignored) {}

        return chunksAmount;
    }


    /**
     * Read a chunk of data from source to a new buffer.
     *
     * @param si iterator which point to not empty source (at least one si.next() call possible)
     * @param quote maximum amount of entries that can be extracted from source
     * @return list of read entries
     */
    private static <T> List<T> readBuffer(Iterator<T> si, int quote){
        int usedObj = 0;
        ArrayList<T> buffer = new ArrayList<>(quote);

        do{
            T entry = si.next();
            buffer.add(entry);
            usedObj++;
        } while (si.hasNext() && usedObj < quote);

        return buffer;
    }

}
