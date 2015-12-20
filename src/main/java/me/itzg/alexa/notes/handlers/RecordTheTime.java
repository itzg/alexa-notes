package me.itzg.alexa.notes.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import me.itzg.alexa.notes.services.TimeFormatter;
import me.itzg.alexa.notes.types.IntentHandler;
import me.itzg.alexa.notes.types.SpeechletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Geoff Bourne
 * @since 12/19/2015
 */
@Component
public class RecordTheTime implements IntentHandler {

    @Autowired
    private TimeFormatter timeFormatter;

    @Override
    public SpeechletResponse handleIntent(IntentRequest request, Intent intent) {
        final Date timestamp = request.getTimestamp();

        SimpleCard notedCard = SpeechletUtils.createSimpleCard("Recorded the Time", "The time has been recorded as " +
                timeFormatter.formatTimestampForCard(request));
        PlainTextOutputSpeech noted = SpeechletUtils.createTextOutputSpeech("Recorded");

        return SpeechletResponse.newTellResponse(noted, notedCard);
    }
}
