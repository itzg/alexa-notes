package me.itzg.alexa.notes.types;

import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;

/**
 * @author Geoff Bourne
 * @since 12/19/2015
 */
public class SpeechletUtils {
    public static PlainTextOutputSpeech createTextOutputSpeech(String outputText) {
        PlainTextOutputSpeech noted = new PlainTextOutputSpeech();
        noted.setText(outputText);
        return noted;
    }

    public static SimpleCard createSimpleCard(String title, String content) {
        SimpleCard notedCard = new SimpleCard();
        notedCard.setTitle(title);
        notedCard.setContent(content);
        return notedCard;
    }
}
