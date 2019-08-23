package edu.java.core.sort.external.processor;

import edu.java.core.sort.external.common.utils.ChunkedSorter;

import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ExternalSortProcessor {

    private static Logger log = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static <T> void run(DataSource<T> source, DataMediator<T> mediator,
                               DataStore<T> destination,
                               Comparator<T> comparator,
                               int dose){

        // 1. sort source to temporary chunks
        try(DataSource<T> src = source.open()){
            ChunkedSorter.run(src.iterator(), comparator, mediator, dose);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error during source processing: {0}", e.getStackTrace());
        }

        // TODO: 2019-07-16
        //   2alt. if there is only one chunk - some mediators should support finalization

        // 2. merge temporary chunks to destination
        try(DataSource<T> src = mediator.open()){
            destination.accept(src);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error during result preparation: {0}", e.getStackTrace());
        }

        // 3. clear temporary resources
        mediator.clear();

    }
}
