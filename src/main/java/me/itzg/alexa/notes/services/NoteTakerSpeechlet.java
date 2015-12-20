package me.itzg.alexa.notes.services;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import me.itzg.alexa.notes.types.IntentHandler;
import me.itzg.alexa.notes.types.SpeechletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Geoff Bourne
 * @since 12/5/2015
 */
@Service
public class NoteTakerSpeechlet implements Speechlet {
    private static Logger LOG = LoggerFactory.getLogger(NoteTakerSpeechlet.class);

    @Autowired
    private BeanFactory beanFactory;

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

        final Object bean = beanFactory.getBean(intentName);
        if (bean instanceof IntentHandler) {
            IntentHandler intentHandler = (IntentHandler) bean;

            return intentHandler.handleIntent(request, intent);
        }
        else {
            throw new SpeechletException("Unknown intent");
        }

    }

    @Override
    public void onSessionEnded(SessionEndedRequest sessionEndedRequest, Session session) throws SpeechletException {
        LOG.info("sessionEnded: request={}, session={}", sessionEndedRequest, session);
    }

    protected SpeechletResponse createWelcomeResponse() {
        // TODO, register these
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText("You can tell me someone took medicine or record the time");

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(SpeechletUtils.createTextOutputSpeech("What should I note?"));

        return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
    }
}
