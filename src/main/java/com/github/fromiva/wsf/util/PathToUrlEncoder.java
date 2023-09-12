package com.github.fromiva.wsf.util;

import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public final class PathToUrlEncoder implements Encoder<String, String> {

    /** The system-dependent file name separator character, represented as a string. */
    private final String filePathSeparator;

    /** The URL name separator character, represented as a string. */
    private final String urlPathSeparator;

    /** Constructor with the system-dependent default separator characters. */
    public PathToUrlEncoder() {
        filePathSeparator = FileSystems.getDefault().getSeparator();
        urlPathSeparator = "/";
    }


    /**
     * Constructor with specified separator characters. For cross-platform testing only.
     * @param filePathSeparator system-dependent file system name separator character
     * @param urlPathSeparator URL name separator character
     */
    PathToUrlEncoder(final String filePathSeparator, final String urlPathSeparator) {
        this.filePathSeparator = filePathSeparator;
        this.urlPathSeparator = urlPathSeparator;
    }

    @Override
    public String encode(final String path) {
        String[] elements = path.split(getRegexSeparator(filePathSeparator));
        String[] result = new String[elements.length];
        for (int i = 0; i < elements.length; i++) {
            result[i] = Arrays.stream(elements[i].split(" "))
                    .map(s -> URLEncoder.encode(s, UTF_8))
                    .collect(Collectors.joining("%20"));
        }
        return String.join(urlPathSeparator, result);
    }

    @Override
    public String decode(final String url) {
        String[] elements = url.split(getRegexSeparator(urlPathSeparator));
        String[] result = new String[elements.length];
        for (int i = 0; i < elements.length; i++) {
            result[i] = URLDecoder.decode(elements[i], UTF_8);
        }
        return String.join(filePathSeparator, result);
    }

    private String getRegexSeparator(final String separator) {
        return switch (separator) {
            case "/" -> "/";
            case "\\" -> "\\\\";
            default -> throw new IllegalArgumentException(
                    "Separator doesn't supported: " + separator);
        };
    }
}
