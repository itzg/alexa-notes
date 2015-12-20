package me.itzg.alexa.notes.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import me.itzg.alexa.notes.types.IntentHandler;
import me.itzg.alexa.notes.types.SpeechletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Geoff Bourne
 * @since 12/19/2015
 */
@Component
public class TookMedicine implements IntentHandler {
    private static Logger LOG = LoggerFactory.getLogger(TookMedicine.class);

    @Override
    public SpeechletResponse handleIntent(IntentRequest request, Intent intent) {
        final Map<String, Slot> slots = intent.getSlots();

        LOG.info("tookMedicine: slots={}", slots);

        PlainTextOutputSpeech outputSpeech = SpeechletUtils.createTextOutputSpeech("Got it");

        SimpleCard card = new SimpleCard();
        card.setTitle("Took Medicine");

        Slot defaultName = Slot.builder()
                .withName("name").withValue("Someone")
                .build();
        Slot defaultType = Slot.builder()
                .withName("typeOfMedicine")
                .withValue("some")
                .build();
        Slot defaultWhen = Slot.builder()
                .withName("timeWhen")
                .withValue("the current time")
                .build();
        final String leadingLine = String.format("%s took %s medicine at %s.",
                slots.getOrDefault("name", defaultName).getValue(),
                slots.getOrDefault("typeOfMedicine", defaultType).getValue(),
                slots.getOrDefault("timeWhen", defaultWhen).getValue());

        StringBuilder sb = new StringBuilder(leadingLine);
        final Slot takeNextIn = slots.get("takeNextIn");
        if (takeNextIn.getValue() != null) {
            sb.append("\n");
            sb.append("Next dose due in ");
            sb.append(takeNextIn.getValue());
        }

        card.setContent(sb.toString());

        return SpeechletResponse.newTellResponse(outputSpeech, card);
    }
}
