package me.itzg.alexa.notes.services;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Geoff Bourne
 * @since 12/5/2015
 */
@Service
public class NoteTakerSpeechlet implements Speechlet {
    private static Logger LOG = LoggerFactory.getLogger(NoteTakerSpeechlet.class);

    @Override
    public void onSessionStarted(SessionStartedRequest sessionStartedRequest,
                                 Session session) throws SpeechletException {
        LOG.info("Session started: request={}, session={}", sessionStartedRequest, session);
    }

    @Override
    public SpeechletResponse onLaunch(LaunchRequest launchRequest, Session session) throws SpeechletException {
        LOG.info("onLaunch: request={}, session={}", launchRequest, session);

        return createWelcomeResponse();
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException {
        LOG.info("onIntent: request={}, session={}", request, session);

        final Intent intent = request.getIntent();

        if (intent == null) {
            throw new SpeechletException("Unrecognized intent");
        }

        final String intentName = intent.getName();

        if ("TookMedicine".equals(intentName)) {
            return handleTookMedicine(intent);
        }
        else {
            throw new SpeechletException("Unknown intent");
        }
    }

    @Override
    public void onSessionEnded(SessionEndedRequest sessionEndedRequest, Session session) throws SpeechletException {
        LOG.info("sessionEnded: request={}, session={}", sessionEndedRequest, session);
    }

    protected SpeechletResponse handleTookMedicine(Intent intent) {
        final Map<String, Slot> slots = intent.getSlots();

        LOG.info("tookMedicine: slots={}", slots);

        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText("Got it");

        SimpleCard card = new SimpleCard();

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

    protected SpeechletResponse createWelcomeResponse() {
        // TODO, register these
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText("You can tell me someone took medicine");

        return SpeechletResponse.newTellResponse(outputSpeech);
    }
}
