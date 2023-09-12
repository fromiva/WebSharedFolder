package com.github.fromiva.wsf.util;

import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

/** Comparator for sorting folder content by item type
 * (a directory or a regular file) ascending.
 */
@Component
public class PathByTypeAscComparator implements Comparator<Path> {

    /**
     * Compares its two arguments for order.
     * @param first  the first object to be compared.
     * @param second the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the first argument
     * is less than, equal to, or greater than the second
     */
    @Override
    public int compare(final Path first, final Path second) {
        return Boolean.compare(Files.isDirectory(second), Files.isDirectory(first));
    }
}
