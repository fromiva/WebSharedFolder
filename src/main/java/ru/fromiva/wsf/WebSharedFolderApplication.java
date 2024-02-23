package ru.fromiva.wsf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
@SpringBootApplication
public class WebSharedFolderApplication {

    /**
     * Application entry point.
     * @param args array of the command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(WebSharedFolderApplication.class, args);
    }

}
