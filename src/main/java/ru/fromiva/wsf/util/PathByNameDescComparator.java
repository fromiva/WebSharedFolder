package ru.fromiva.wsf.util;

import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Comparator;

/** Comparator for sorting folder content by filename descending. */
@Component
public class PathByNameDescComparator implements Comparator<Path> {

    /**
     * Compares its two arguments for order.
     * @param first  the first object to be compared.
     * @param second the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the second argument
     * is less than, equal to, or greater than the first
     */
    @Override
    public int compare(final Path first, final Path second) {
        return second.getFileName().compareTo(first.getFileName());
    }
}
