# Story App

This is simple Android App, to get dicoding user story with auth

First, clone the repo:

`https://github.com/kouseina/story-app`

### Android Studio (Recommended)

(These instructions were tested with Android Studio version 2022.2.1 Patch 1)

* Open Android Studio and select `File->Open...` or from the Android Launcher select `Import project (Eclipse ADT, Gradle, etc.)` and navigate to the root directory of your project.
* Select the directory or drill in and select the file `build.gradle` in the cloned repo.
* Click 'OK' to open the the project in Android Studio.
* A Gradle sync should start, but you can force a sync and build the 'app' module as needed.

### Gradle (command line)

* Build the APK: `./gradlew build`

### Eclipse

* Download the latest Android SDK from [Maven Central](http://repo1.maven.org/maven2/io/keen/keen-client-api-android)
    * Note: We publish both an AAR and a JAR; you may use whichever is more convenient based on your infrastructure and needs.


## Running the Sample App

Connect an Android device to your development machine.

### Android Studio

* Select `Run -> Run 'app'` (or `Debug 'app'`) from the menu bar
* Select the device you wish to run the app on and click 'OK'