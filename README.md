# Alexa Notes

Much more details coming soon...

For now, some boring build/run/install notes.

## Build-time Settings

When invoking a `package`, `install`, or `spring-boot:run` pass the assigned Amazon Alexa application ID. 

    -DapplicationId=...

When invoking a `package` or `install`, specify the Docker build host

    -DdockerHost=...
    
## Docker run-time

Declare the environment variables `KEY_STORE_PW`. *NOTE:* it assumes a Java keystore file is pre-configured at
`/certs/alexa-keystore.jks`.

## Converting x509 cert chain (i.e. from Let's Encrypt) into Java keystore

    FQDN=YOUR_HOST_HERE
    
    cd /etc/letsencrypt/live/$FQDN
    openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out keystore.p12 -name $FQDN
    
    keytool -importkeystore -srckeystore keystore.p12 -srcstoretype PKCS12 \
        -destkeystore /var/alexa-note-taker/keystore.jks -alias $FQDN

## General Linux service installation

Do the following as `root`...

    useradd alexa
    install -b -m 700 -o alexa -g alexa /root/alexa-note-taker-0.0.1-SNAPSHOT.jar /var/alexa-note-taker
    mkdir /var/alexa-note-taker
    cd /var/alexa-note-taker
    unzip alexa-note-taker-0.0.1-SNAPSHOT.jar 'init/*'

Create the file `application.properties` with contents like

    server.ssl.key-store=/var/alexa-note-taker/keystore.jks
    server.ssl.key-store-password=YOUR_JKS_PASSWORD

And lock it down

    chown alexa:alexa application.properties
    chmod 600 application.properties

## Installation on systemd-base system

Install the service unit

    ln -s /var/alexa-note-taker/init/systemd/alexa-note-taker.service /etc/systemd/system
    systemctl daemon-reload

In the end you should have

    root@vultr:/var/alexa-note-taker# ls -l
    total 36104
    -rwx------ 1 alexa alexa 36956429 Dec 20 16:16 alexa-note-taker-0.0.1-SNAPSHOT.jar
    -rw------- 1 alexa root       138 Dec 20 16:10 application.properties
    drwxr-xr-x 3 root  root      4096 Dec 20 16:10 init
    -rw------- 1 alexa root      3847 Dec 20 16:09 keystore.jks
    
    root@vultr:/var/alexa-note-taker# ls -l /etc/systemd/system/alexa-note-taker.service
    lrwxrwxrwx 1 root root 59 Dec 20 16:21 /etc/systemd/system/alexa-note-taker.service -> /var/alexa-note-taker/init/systemd/alexa-note-taker.service

And start the service

    systemctl start alexa-note-taker

and check that it started up properly

    systemctl status alexa-note-taker
    
which would look like

    root@vultr:/var/alexa-note-taker# systemctl status -l alexa-note-taker
    ● alexa-note-taker.service - Alexa Note Taker
       Loaded: loaded (/var/alexa-note-taker/init/systemd/alexa-note-taker.service; linked; vendor preset: enabled)
       Active: active (running) since Sun 2015-12-20 16:41:05 UTC; 41s ago
     Main PID: 7112 (alexa-note-take)
       CGroup: /system.slice/alexa-note-taker.service
               ├─7112 /bin/bash /var/alexa-note-taker/alexa-note-taker-0.0.1-SNAPSHOT.jar
               └─7126 /usr/bin/java -Dsun.misc.URLClassPath.disableJarChecking=true -jar /var/alexa-note-taker/alexa-note-taker-0.0.1-SNAPSHOT.jar

You can later view its logs with `journalctl`, such as

    journalctl -f -u alexa-note-taker
