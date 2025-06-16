# APK/Bundle Generation Process

This section guides you through the process of generating a release-ready APK or Android App Bundle for your English-Hindi Learning app.

## Understanding APK vs Bundle

Before we begin, it's important to understand the difference between an APK and an Android App Bundle:

- **APK (Android Package)**: A traditional package format that contains all resources for all device configurations.
- **Android App Bundle (AAB)**: A newer format that allows Google Play to generate and serve optimized APKs for specific device configurations, resulting in smaller downloads for users.

For the Google Play Store, Android App Bundle (AAB) is the recommended format.

## Prerequisites

Before generating your release build:

1. Ensure your app's version information is updated in `app/build.gradle`:
   ```groovy
   defaultConfig {
       versionCode 2     // Increment this with each release
       versionName "1.1.0"  // Update semantic version as needed
   }
   ```

2. Make sure your keystore is properly set up (see the Keystore Creation section)

3. Verify ProGuard rules in `app/proguard-rules.pro` are appropriate for your app

## Generating a Signed Android App Bundle

![Generate Signed Bundle](generate_signed_bundle.png)

Follow these steps to generate a signed Android App Bundle:

1. In Android Studio, go to **Build → Generate Signed Bundle/APK**
2. Select **Android App Bundle** and click **Next**
3. In the Key store path section:
   - Select your keystore file location
   - Enter your keystore password
   - Enter your key alias
   - Enter your key password
   - Click **Next**
4. Select a destination folder for your bundle
5. Select the build variant:
   - Choose the **release** build variant
   - For product flavor, select either **freeRelease** or **premiumRelease** depending on which version you want to publish
6. Check the signature versions:
   - V1 (Jar Signature)
   - V2 (Full APK Signature)
   - V3 (APK Signature Scheme v3)
   - V4 (APK Signature Scheme v4)
7. Click **Finish**

Android Studio will build your bundle and display a notification when it's complete. The bundle will be saved in the specified destination folder with a `.aab` extension.

## Generating a Signed APK

If you need an APK for distribution outside the Play Store, follow these steps:

1. In Android Studio, go to **Build → Generate Signed Bundle/APK**
2. Select **APK** and click **Next**
3. Complete the keystore information as described above and click **Next**
4. Select a destination folder
5. Choose the **release** build variant and appropriate product flavor
6. Check the signature versions as described above
7. Click **Finish**

The APK will be generated in the specified destination folder with a `.apk` extension.

## Command Line Generation (Optional)

You can also generate release builds using the command line:

### For Android App Bundle:
```
./gradlew bundleRelease
```

### For APK:
```
./gradlew assembleRelease
```

The output will be in:
- `app/build/outputs/bundle/release/` for the AAB file
- `app/build/outputs/apk/release/` for the APK file

## Verifying Your Build

![Build Variants](build_variants.png)

After generating your APK or Bundle:

1. Install the APK on a test device (for APK only):
   - Transfer the APK to your device
   - Enable installation from unknown sources if needed
   - Open the APK to install
   
2. Check the app version in Settings → Apps → English-Hindi Learning

3. Test all functionality to ensure ProGuard hasn't broken anything

4. Verify the app signature using the following command (requires the `apksigner` tool from the Android SDK):
   ```
   apksigner verify --verbose app-release.apk
   ```

The final release APK or Bundle is now ready for distribution.