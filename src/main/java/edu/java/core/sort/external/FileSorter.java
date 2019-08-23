package edu.java.core.sort.external;

import edu.java.core.sort.external.processor.ExternalSortProcessor;
import edu.java.core.sort.external.processor.impl.LinedFileSource;
import edu.java.core.sort.external.processor.impl.LinedFileStore;
import edu.java.core.sort.external.processor.impl.LinedMultiFileMediator;
import edu.java.core.sort.external.utils.Sleeper;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class FileSorter {

    private static Logger log = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static final String SOURCE_FILE_ARG_NAME = "--source";
    private static final String DEFAULT_SOURCE_FILE = "source.txt";

    private static final String DEST_FILE_ARG_NAME = "--destination";
    private static final String DEFAULT_DEST_FILE = "sorted.txt";

    private static final String TMP_FILE_PATTERN_ARG_NAME = "--tmp-file-pattern";
    private static final String DEFAULT_TMP_FILE_PATTERN = "tmp";

    private static final String CHUNK_SIZE_ARG_NAME = "--chunk-size";
    private static final int DEFAULT_CHUNK_SIZE = 300000;


    private static final String HELP_MESSAGE = "The sorter accepts GNU-like options as follow:" + System.lineSeparator() +
            SOURCE_FILE_ARG_NAME + "=" + DEFAULT_SOURCE_FILE + System.lineSeparator()+
            DEST_FILE_ARG_NAME + "=" + DEFAULT_DEST_FILE + System.lineSeparator()+
            TMP_FILE_PATTERN_ARG_NAME + "=" + DEFAULT_TMP_FILE_PATTERN + System.lineSeparator()+
            CHUNK_SIZE_ARG_NAME + "=" + DEFAULT_CHUNK_SIZE + System.lineSeparator();

    public static void main(String[] args) {
        String source = DEFAULT_SOURCE_FILE;
        String destination = DEFAULT_DEST_FILE;
        String tmpPattern = DEFAULT_TMP_FILE_PATTERN;
        int chunkSize = DEFAULT_CHUNK_SIZE;

        // 0. parse arguments
        if (args.length > 0){
            if ((args.length==1) && (args[0].equals("-h") || args[0].equals("--help"))){
                System.out.println(HELP_MESSAGE);
                return;
            } else {
                int index = 0;
                while (index < args.length){
                    int sepPos = args[index].indexOf('=');
                    switch (args[index].substring(0,sepPos)){
                        case SOURCE_FILE_ARG_NAME:
                            source = args[index].substring(sepPos+1);
                            break;
                        case DEST_FILE_ARG_NAME:
                            destination = args[index].substring(sepPos+1);
                            break;
                        case TMP_FILE_PATTERN_ARG_NAME:
                            tmpPattern = args[index].substring(sepPos+1);
                            break;
                        case CHUNK_SIZE_ARG_NAME:
                            chunkSize = Integer.valueOf(args[index].substring(sepPos+1));
                            break;
                        default:
                            throw new RuntimeException("unknown cli argument");
                    }
                    index++;
                }
            }
        }

        // 00. Generate Source file
        //LinedFileGenerator.run("source.txt",5000000);

        // 1. wait a little bit to connect from profilers and etc.
        Sleeper.countDown(5,2000);

        // 2.
        log.log(Level.INFO, "GO!!!!");
        Instant start = Instant.now();

        // 3. run the file sorter
        ExternalSortProcessor.run(
                new LinedFileSource(source),
                new LinedMultiFileMediator(tmpPattern,String.CASE_INSENSITIVE_ORDER),
                new LinedFileStore(destination),
                String.CASE_INSENSITIVE_ORDER,
                chunkSize);

	    // 4. print statistic
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        log.log(Level.INFO, "DONE in {0} ms!!!",timeElapsed);

    }


}
