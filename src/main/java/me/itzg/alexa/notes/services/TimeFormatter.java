package me.itzg.alexa.notes.services;

import com.amazon.speech.speechlet.IntentRequest;
import me.itzg.alexa.notes.config.AlexaNotesSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Geoff Bourne
 * @since 12/19/2015
 */
@Service
public class TimeFormatter {

    @Autowired
    private TimeZone cardTimeZone;

    private SimpleDateFormat dateFormat;

    @Autowired
    public void setSettings(AlexaNotesSettings settings) {
        dateFormat = new SimpleDateFormat(settings.getCardTimeFormat());
        dateFormat.setTimeZone(cardTimeZone);
    }

    public String formatTimestampForCard(IntentRequest request) {
        final Date timestamp = request.getTimestamp();

        return dateFormat.format(timestamp);
    }
}
