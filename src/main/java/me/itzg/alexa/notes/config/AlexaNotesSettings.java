package me.itzg.alexa.notes.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Geoff Bourne
 * @since 12/19/2015
 */
@Component
@ConfigurationProperties(prefix = "notes")
public class AlexaNotesSettings {
    @NotNull @Size(min = 1)
    private String cardTimeZoneId;

    @NotNull @Size(min = 1)
    private String cardTimeFormat = "h:mm a, z";

    public String getCardTimeZoneId() {
        return cardTimeZoneId;
    }

    public void setCardTimeZoneId(String cardTimeZoneId) {
        this.cardTimeZoneId = cardTimeZoneId;
    }

    public String getCardTimeFormat() {
        return cardTimeFormat;
    }

    public void setCardTimeFormat(String cardTimeFormat) {
        this.cardTimeFormat = cardTimeFormat;
    }
}
