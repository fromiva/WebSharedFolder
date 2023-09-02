package com.github.fromiva.wsf.configuration;

import com.github.fromiva.wsf.util.Encoder;
import com.github.fromiva.wsf.util.PathToUrlEncoder;
import org.springframework.context.annotation.Bean;

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
