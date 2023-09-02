package com.github.fromiva.wsf.util;

/**
 * Interface for encoders.
 * Contract: {@code decode(encode(object)).equals(object) == true}.
 * @param <T> Original object type to encode
 * @param <R> Required encoded object type
 */
public interface Encoder<T, R> {

    /**
     * Encodes objects.
     * @param object to encode
     * @return encoded object
     */
    R encode(T object);

    /**
     * Decodes objects.
     * @param object to decode
     * @return decoded object
     */
    T decode(R object);
}
