package ru.fromiva.wsf.security;

import org.springframework.stereotype.Component;

import java.util.Comparator;

/** Comparator for sorting user by full name ascending. */
@Component
public class UserByNameAscComparator implements Comparator<User> {

    /**
     * Compares its two arguments for order.
     * @param first  the first object to be compared.
     * @param second the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the first argument
     * is less than, equal to, or greater than the second
     */
    @Override
    public int compare(final User first, final User second) {
        int result = first.getFirstName().compareTo(second.getFirstName());
        return result != 0 ? result : first.getLastName().compareTo(second.getLastName());
    }
}
