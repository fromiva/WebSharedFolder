package ru.fromiva.wsf.folder;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Configurable comparator for sorting folder content. */
public class DelegatingPathComparator implements Comparator<Path> {

    /** List of comparators to delegate. */
    private final List<Comparator<Path>> comparators = new ArrayList<>();

    /**
     * Compares its two arguments for order by consistent application
     * of the comparators from list. List order matters for the result.
     * @param first  the first object to be compared.
     * @param second the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the first argument
     * is less than, equal to, or greater than the second
     */
    @Override
    public int compare(final Path first, final Path second) {
        int result = 0;
        for (Comparator<Path> comparator : comparators) {
            result = comparator.compare(first, second);
            if (result != 0) {
                return result;
            }
        }
        return result;
    }

    /**
     * Adds a comparator to the list of comparators to be applied.
     * The order of addition matters for comparison results.
     * @param comparator to add to the list of comparators
     */
    public void addComparator(final Comparator<Path> comparator) {
        comparators.add(comparator);
    }
}
