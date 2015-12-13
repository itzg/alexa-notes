package me.itzg.alexa.notes.config;

import com.amazon.speech.speechlet.servlet.SpeechletServlet;
import me.itzg.alexa.notes.services.NoteTakerSpeechlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Geoff Bourne
 * @since 12/5/2015
 */
@Configuration
public class AlexaConfig {

    @Value("classpath:alexa.properties")
    private Resource alexaPropsResource;

    @Autowired
    private NoteTakerSpeechlet noteTakerSpeechlet;

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

        final ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "/alexaMedTracker");

        return servletRegistrationBean;
    }
}
