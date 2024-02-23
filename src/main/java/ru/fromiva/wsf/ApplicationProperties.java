package ru.fromiva.wsf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * In-memory basic application information holder.
 * Takes properties from application.yaml file.
 * */
@Getter
@Setter
@ConfigurationProperties("app")
public class ApplicationProperties {

    /** Application name. */
    private String name = "Web Shared Folder";

    /** Application version. */
    private String version = "0.0.1-SNAPSHOT";

    /** Application license type. */
    private String license = "GPLv3";

    /** Application URL. */
    private String url = "https://github.com/fromiva/WebSharedFolder";
}
