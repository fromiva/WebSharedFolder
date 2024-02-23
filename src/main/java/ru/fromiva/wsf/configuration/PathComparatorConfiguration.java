package ru.fromiva.wsf.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.fromiva.wsf.util.DelegatingPathComparator;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;

@Configuration
public class PathComparatorConfiguration {

    /** Produces the delegating path comparator to use as a main configurable path
     * comparator for sorting folder content. Automatically aggregates all available
     * path comparator beans and choose specified in the setting application file.
     * @param comparators map of available comparator beans
     * @param setting specifies application order of the comparators
     * @return a negative integer, zero, or a positive integer as the first argument
     * is less than, equal to, or greater than the second
     */
    @Bean
    @Primary
    public Comparator<Path> getDelegatingPathComparator(
            final Map<String, Comparator<Path>> comparators,
            @Value("${wsf.application.settings.filesystem.sorting:''}") final String setting) {
        String[] list = setting.split(",");
        DelegatingPathComparator result = new DelegatingPathComparator();
        for (String item : list) {
            Comparator<Path> comparator = comparators.get(item.strip());
            if (comparator != null) {
                result.addComparator(comparator);
            }
        }
        return result;
    }
}
