# Release Instructions

This document provides instructions for building and releasing the English-Hindi Learning app.

## Prerequisites

- Android Studio 2022.2.1 or later
- JDK 11 or later
- Gradle 7.4.2 or later
- Access to the signing keystore (englishhindi-keystore.jks)
- Keystore password and key password

## Build Instructions

### 1. Prepare Environment

Ensure you have the keystore file in the correct location:
```
english-hindi-app/keystores/englishhindi-keystore.jks
```

Verify that the `keystore.properties` file exists in the project root with the correct credentials:
```
keystorePassword=keystorepass
keyAlias=englishhindi
keyPassword=keypass
storeFile=../keystores/englishhindi-keystore.jks
```

### 2. Configure Build Variants

Decide which build variant to release:

- **Premium Release**: Complete version with all features
- **Free Release**: Limited version with basic features

### 3. Build APK

#### From Android Studio:

1. Open the project in Android Studio
2. Select the build variant (e.g., premiumRelease)
3. Go to Build → Generate Signed Bundle / APK
4. Choose APK
5. Select the keystore, key alias, and enter passwords
6. Select destination folder and click Finish

#### From Command Line:

For Premium Release APK:
```bash
./gradlew assemblePremiumRelease
```

For Free Release APK:
```bash
./gradlew assembleFreeRelease
```

The APK will be generated at:
```
app/build/outputs/apk/premium/release/app-premium-release.apk
```
or
```
app/build/outputs/apk/free/release/app-free-release.apk
```

### 4. Build App Bundle (AAB)

#### From Android Studio:

1. Open the project in Android Studio
2. Select the build variant (e.g., premiumRelease)
3. Go to Build → Generate Signed Bundle / APK
4. Choose Android App Bundle
5. Select the keystore, key alias, and enter passwords
6. Select destination folder and click Finish

#### From Command Line:

For Premium Release Bundle:
```bash
./gradlew bundlePremiumRelease
```

For Free Release Bundle:
```bash
./gradlew bundleFreeRelease
```

The AAB will be generated at:
```
app/build/outputs/bundle/premiumRelease/app-premium-release.aab
```
or
```
app/build/outputs/bundle/freeRelease/app-free-release.aab
```

## Testing the Release Build

Before submitting to Google Play, test the release build:

1. Install the release APK on a physical device:
```bash
adb install -r app/build/outputs/apk/premium/release/app-premium-release.apk
```

2. Verify all functionality works as expected
3. Check that ProGuard hasn't broken any features
4. Verify that the app size is optimized
5. Test on multiple device configurations

## Uploading to Google Play

1. Log in to the [Google Play Console](https://play.google.com/console)
2. Select the app "English-Hindi Learning Park"
3. Go to Release → Production
4. Create a new release
5. Upload the AAB file
6. Add release notes from `docs/release/RELEASE_NOTES.md`
7. Review the release and submit for review

## Post-Release Steps

1. Tag the release in Git:
```bash
git tag -a v1.1.0 -m "Version 1.1.0"
git push origin v1.1.0
```

2. Update the version history in `docs/release/VERSION_HISTORY.md`
3. Begin work on the next version

## Troubleshooting

### Signing Issues

If you encounter signing problems:

1. Verify the keystore path is correct
2. Confirm keystore password and key password
3. Check that the key alias matches the one in the keystore

### ProGuard Issues

If the app crashes after ProGuard:

1. Check the ProGuard mapping file at `app/build/outputs/mapping/premiumRelease/mapping.txt`
2. Add additional keep rules in `proguard-rules.pro` for any problematic classes
3. Rebuild and test again

### App Bundle Issues

If there are issues with the App Bundle:

1. Test with the generated APK first to isolate if it's a code issue or bundle issue
2. Use the App Bundle Explorer in Android Studio to inspect the bundle content
3. Verify bundle configuration in the app's build.gradle