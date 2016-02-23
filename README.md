# PiggyGraph

Piggy graph is a an open source project which demonstrates the following frameworks:

 - Gradle
 - Spring Boot
 - Responsive Web (Twitter bootstrap)
 - Web Sockets
 - Android
 - Bluetooth
 - Barcode Scanning
 - Graphing Data Points
 - Continuous Delivery

## Build Instructions


    chmod 755 gradlew

    ./gradlew build

## Run Instructions

    To run locally, the app must be on the same subnet as the host, or the host be publicly accessable.

    Note: You must execute with Java 8

    server$ java -jar -Dhost.address=192.168.0.99 build/libs/piggygraph-server-0.0.1-SNAPSHOT.jar

## Deployment Instructions

 Put **app.keystore** in app/ (not committed for security reasons)

 Update **app/gradle.properties** to include password:

    storePassword=
    keyPassword=

At this point you should be able to build a release APK for deployment via the web console at https://play.google.com/apps/publish/

    app$ ../gradlew clean assembleRelease

### Continuous Deployment

 Put Google Deployment credentials - **keys.json** - in /app

    app$ ../gradlew clean publishApkRelease



## Development Workflow

To enable loading edits to resources without restart, run with gradle (outside of IntellIJ)

    server$ ../gradlew bootRun

### Tools

JDK 7 *and* 8

Intellij IDEA 15+


Node.js - https://nodejs.org/

Bower

    npm install -g bower

