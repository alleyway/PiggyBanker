# Piggy Banker

Piggy banker is a an open source project which demonstrates the following elements:

 - Gradle
 - Spring Boot
 - Responsive Web (Twitter bootstrap)
 - Web Sockets
 - Android
 - Bluetooth
 - Barcode Scanning
 - Graphing Data Points
 - Continuous Delivery

To test a live demo, you visit [piggybanker.alleywayconsulting.com](http://piggybanker.alleywayconsulting.com)

## Build Instructions


    chmod 755 gradlew

    ./gradlew build

## Run Instructions

After building, you can run the server locally. The companion app be on the same subnet as the host, or the host be publicly accessible.

    server$ java -jar -Dhost.address=192.168.0.99 build/libs/piggygraph-server-VERSION.jar

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

