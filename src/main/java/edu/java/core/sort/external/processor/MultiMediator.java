package edu.java.core.sort.external.processor;

import edu.java.core.sort.external.common.MultiExtractor;
import edu.java.core.sort.external.common.MultiSaver;

import java.util.Collection;
import java.util.Iterator;

/**
 * The class for temporary data storing.
 * Creation of access session is independent from object creation and from
 * any type of internal activity.
 *
 * An implementation should check that {@code open} method has been called before
 * opening of any internal resource. As well it's guaranteed that any appropriate
 * usage of interface instance will be terminated by {@code close} method.
 *
 * @param <T>
 */
public interface MultiMediator<T> extends AutoCloseable, MultiSaver<T>, MultiExtractor<T> {

    /**
     * Start persistence session.
     *
     * @return object to close persistence session
     */
    MultiMediator<T> open() throws Exception;

}
