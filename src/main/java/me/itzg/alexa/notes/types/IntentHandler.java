package me.itzg.alexa.notes.types;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;

/**
 * @author Geoff Bourne
 * @since 12/19/2015
 */
public interface IntentHandler {
    SpeechletResponse handleIntent(IntentRequest request, Intent intent);
}
