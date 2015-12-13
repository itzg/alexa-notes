# Alexa Notes

Much more details coming soon...

For now, some boring build/run notes.

## Build-time Settings

When invoking a `package`, `install`, or `spring-boot:run` pass the assigned Amazon Alexa application ID. 

    -DapplicationId=...

When invoking a `package` or `install`, specify the Docker build host

    -DdockerHost=...
    
## Docker run-time

Declare the environment variables `KEY_STORE_PW`. *NOTE:* it assumes a Java keystore file is pre-configured at
`/certs/alexa-keystore.jks`.