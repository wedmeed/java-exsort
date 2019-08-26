package edu.java.core.sort.external.processor;

import edu.java.core.sort.external.common.ComposedComparingIterator;
import edu.java.core.sort.external.common.ChoppingSorter;

import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ExternalSortProcessor {

    private static Logger log = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static <T> void run(Source<T> source, MultiMediator<T> mediator,
                               Store<T> destination,
                               Comparator<T> comparator,
                               int dose){
        // 0. Open mediator
        try(MultiMediator<T> temp = mediator.open()) {

            // 1. sort source to temporary chunks
            try (Source<T> src = source.open()) {
                ChoppingSorter.run(src, comparator, temp, dose);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error during source processing: {0}", e.getStackTrace());
            }

            // TODO: 2019-07-16
            //   2alt. if there is only one chunk - some mediators should support finalization

            // 2. merge temporary chunks to destination through comparing iterator
            try (Store<T> dest = destination.open()) {
                dest.save(new ComposedComparingIterator<>(comparator, temp.extract()));
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error during result persistence: {0}", e.getStackTrace());
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error during working with temporary storage: {0}", e.getStackTrace());
        }

    }
}
