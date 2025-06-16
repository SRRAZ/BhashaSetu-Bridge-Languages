# Development Environment Setup

This section will guide you through setting up your development environment to prepare your English-Hindi Learning app for release.

## Android Studio Installation

Android Studio is the official Integrated Development Environment (IDE) for Android app development. Follow these steps to install it:

1. Visit the [Android Studio download page](https://developer.android.com/studio)
2. Download the appropriate version for your operating system (Windows, macOS, or Linux)
3. Follow the installation wizard:
   - For Windows: Run the downloaded exe file and follow the prompts
   - For macOS: Drag Android Studio to the Applications folder
   - For Linux: Unpack the downloaded archive and run the `studio.sh` script

4. During first launch, the Android Studio Setup Wizard will guide you through the installation of:
   - Android SDK
   - Android SDK Platform-Tools
   - Android Emulator
   - Android SDK Build-Tools

5. After installation, ensure you have the following components from the SDK Manager (Tools > SDK Manager):
   - Android SDK Platform 33 (Android 13.0) or higher
   - Android SDK Build-Tools 33.0.0 or higher
   - Android SDK Command-line Tools
   - Android Emulator

![Android Studio Installation](project_structure.png)

## Setting Up JDK

Android Studio comes bundled with the OpenJDK, but you can verify the installation:

1. Open Android Studio
2. Go to File > Settings (or Android Studio > Preferences on macOS)
3. Navigate to Build, Execution, Deployment > Build Tools > Gradle
4. Ensure that Gradle JDK is set to the embedded JDK or a compatible version (JDK 11 or higher is recommended)

## Cloning Your Repository

To work with your English-Hindi Learning app, you need to clone the repository:

1. Open Android Studio
2. Select "Get from Version Control" on the welcome screen
3. In the URL field, enter: `https://github.com/SRRAZ/en-hi-learning-park.git`
4. Choose a directory for the project
5. Click "Clone"
6. Wait for the repository to be cloned and the project to be indexed

## Project Structure

After cloning, familiarize yourself with the project structure:

```
en-hi-learning-park/
├── design/                 # Design documents and resources
├── english-hindi-app/      # Main Android project
│   ├── app/                # App module
│   │   ├── src/            # Source code
│   │   ├── build.gradle    # App-level build configuration
│   │   └── proguard-rules.pro # ProGuard rules for release
│   ├── build.gradle        # Project-level build configuration
│   ├── keystores/          # Directory for keystores
│   └── settings.gradle     # Gradle settings
├── prototypes/             # App prototypes
└── README.md               # Project overview
```

## Configure Gradle Properties

For optimal build performance, create or modify the `gradle.properties` file in the project root:

```properties
# Project-wide Gradle settings
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
android.useAndroidX=true
android.enableJetifier=true
org.gradle.parallel=true
org.gradle.caching=true
android.enableR8.fullMode=true
```

These settings will:
- Allocate more memory to Gradle
- Enable AndroidX libraries
- Enable parallel and cached builds
- Enable full R8 optimization mode

## Setting Up an Emulator (Optional)

While testing on physical devices is recommended, an emulator is also useful:

1. In Android Studio, go to Tools > Device Manager
2. Click "Create device"
3. Select a device definition (e.g., Pixel 4)
4. Select a system image (recommend Android 11 or higher)
5. Name your virtual device and click "Finish"

## Preparing Physical Devices for Testing

Before releasing, you should test on actual devices:

1. On your Android device, enable Developer options:
   - Go to Settings > About phone
   - Tap "Build number" seven times
   - You'll see a message that you are now a developer

2. Enable USB debugging:
   - Go to Settings > System > Developer options
   - Turn on "USB debugging"

3. Connect your device to your computer:
   - Install any required USB drivers for your device
   - Accept the "Allow USB debugging" prompt on your device
   - Verify the connection in Android Studio's Device Manager

## Updating Dependencies

Before release, ensure all dependencies are up-to-date:

1. Open the `build.gradle` files for the project and app module
2. Review the dependencies section and update library versions as needed
3. Look for any deprecation warnings in the code
4. Click "Sync Now" after any changes to the build files

## Building the Project

Verify that the project builds correctly:

1. In Android Studio, select Build > Make Project
2. Fix any compilation errors
3. Run the app in debug mode to ensure it functions correctly:
   - Select Run > Run 'app'
   - Choose a connected device or emulator

Once you've completed this setup, your development environment is ready for creating a release build of the English-Hindi Learning app.