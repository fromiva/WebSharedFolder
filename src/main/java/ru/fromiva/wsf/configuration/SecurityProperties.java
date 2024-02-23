package ru.fromiva.wsf.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/** Security Configuration properties class. */
@Getter
@Setter
@ConfigurationProperties("app.security")
public class SecurityProperties {

    /** Enables / disables auto activation of the new user's account. */
    private boolean registrationConfirmation = true;
}
