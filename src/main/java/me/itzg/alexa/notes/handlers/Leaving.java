package me.itzg.alexa.notes.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import me.itzg.alexa.notes.services.TimeFormatter;
import me.itzg.alexa.notes.types.IntentHandler;
import me.itzg.alexa.notes.types.SpeechletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Geoff Bourne
 * @since 12/19/2015
 */
@Component
public class Leaving implements IntentHandler {

    @Autowired
    private TimeFormatter timeFormatter;

    @Override
    public SpeechletResponse handleIntent(IntentRequest request, Intent intent) {
        final SpeechletResponse response = new SpeechletResponse();
        response.setCard(SpeechletUtils.createSimpleCard("Leaving","You told me you left at "+
                timeFormatter.formatTimestampForCard(request)));
        response.setOutputSpeech(SpeechletUtils.createTextOutputSpeech("Goodbye"));
        return response;
    }
}
