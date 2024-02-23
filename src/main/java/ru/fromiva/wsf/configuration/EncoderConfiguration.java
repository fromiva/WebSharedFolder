package ru.fromiva.wsf.configuration;

import org.springframework.context.annotation.Bean;
import ru.fromiva.wsf.util.Encoder;
import ru.fromiva.wsf.util.PathToUrlEncoder;

public class EncoderConfiguration {

    /**
     * Returns a PathToUrlEncoder bean.
     * @return PathToUrlEncoder bean
     */
    @Bean(name = "pathToUrlEncoder")
    public Encoder<String, String> getPathToUrlEncoder() {
        return new PathToUrlEncoder();
    }
}
