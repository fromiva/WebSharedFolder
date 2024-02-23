package ru.fromiva.wsf.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * In-memory basic application information holder.
 * Takes properties from application.yaml file.
 * */
@Component
@Scope("singleton")
@Getter
public class ApplicationInfo {

    /** Application name. */
    private final String name;

    /** Application version. */
    private final String version;

    /** Application license type. */
    private final String license;

    /** Application URL. */
    private final String url;

    /**
     * Constructor with all autowired arguments.
     * @param name application name
     * @param version application version
     * @param license application license type
     * @param url application URL
     */
    public ApplicationInfo(
            @Value("${wsf.application.name}")
            final String name,
            @Value("${wsf.application.version}")
            final String version,
            @Value("${wsf.application.license}")
            final String license,
            @Value("${wsf.application.url}")
            final String url) {
        this.name = name;
        this.version = version;
        this.license = license;
        this.url = url;
    }
}
