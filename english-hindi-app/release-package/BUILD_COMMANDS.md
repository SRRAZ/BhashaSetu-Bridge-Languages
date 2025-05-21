# Build Commands for Release APK and Bundle

This document contains the commands to generate signed APK and App Bundle for the English-Hindi Learning App.

## Prerequisites
- Android Studio or Gradle command line
- JDK 8 or higher
- Keystore file (`keystores/englishhindi-keystore.jks`) with proper credentials

## Generating Signed APK

### Command Line
```bash
# For free variant
./gradlew assembleFreeRelease

# For premium variant
./gradlew assemblePremiumRelease
```

### Android Studio
1. Open Android Studio
2. Go to Build > Generate Signed Bundle/APK
3. Select APK
4. Select the keystore file and enter credentials
5. Select release build type and desired flavor (free/premium)
6. Click Finish

## Generating Signed App Bundle

### Command Line
```bash
# For free variant
./gradlew bundleFreeRelease

# For premium variant
./gradlew bundlePremiumRelease
```

### Android Studio
1. Open Android Studio
2. Go to Build > Generate Signed Bundle/APK
3. Select Android App Bundle
4. Select the keystore file and enter credentials
5. Select release build type and desired flavor (free/premium)
6. Click Finish

## Verification Steps
After generating the APK or Bundle, perform these verification steps:

1. Install the APK on a test device
2. Verify all app features work correctly
3. Check app size and performance
4. Ensure ProGuard optimizations haven't broken any functionality

## Output Files
- APK: `app/build/outputs/apk/free/release/app-free-release.apk` or `app/build/outputs/apk/premium/release/app-premium-release.apk`
- Bundle: `app/build/outputs/bundle/freeRelease/app-free-release.aab` or `app/build/outputs/bundle/premiumRelease/app-premium-release.aab`