package me.itzg.alexa.notes.config;

import com.amazon.speech.speechlet.servlet.SpeechletServlet;
import me.itzg.alexa.notes.services.NoteTakerSpeechlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.TimeZone;

/**
 * @author Geoff Bourne
 * @since 12/5/2015
 */
@Configuration
public class AlexaConfig {
    private static Logger LOG = LoggerFactory.getLogger(AlexaConfig.class);

    @Value("classpath:alexa.properties")
    private Resource alexaPropsResource;

    @Autowired
    private NoteTakerSpeechlet noteTakerSpeechlet;

    @Autowired
    private AlexaNotesSettings settings;

    @Bean
    public ServletRegistrationBean alexaNotes() throws IOException {
        final Properties props = new Properties(System.getProperties());
        try (InputStream propsIn = alexaPropsResource.getInputStream()) {
            props.load(propsIn);
            System.setProperties(props);
        }

        SpeechletServlet servlet;
        servlet = new SpeechletServlet();
        servlet.setSpeechlet(noteTakerSpeechlet);

        final ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "/alexaNotes");

        return servletRegistrationBean;
    }

    @Bean
    public TimeZone cardTimeZone() {
        return TimeZone.getTimeZone(settings.getCardTimeZoneId());
    }
}
