# Release Preparation Guide

This document provides comprehensive instructions for preparing a release version of the English-Hindi Learning app, including code signing, ProGuard configuration, app bundle creation, and submission to the Google Play Store.

## Table of Contents

1. [Introduction](#introduction)
2. [Release Checklist](#release-checklist)
3. [Version Management](#version-management)
4. [Code Signing](#code-signing)
    - [Generating a Signing Key](#generating-a-signing-key)
    - [Configuring Gradle for Signing](#configuring-gradle-for-signing)
    - [Secure Key Storage](#secure-key-storage)
5. [ProGuard Configuration](#proguard-configuration)
    - [Basic Configuration](#basic-configuration)
    - [Custom Rules](#custom-rules)
    - [Testing ProGuard Configuration](#testing-proguard-configuration)
6. [App Bundle Creation](#app-bundle-creation)
    - [Enabling App Bundles](#enabling-app-bundles)
    - [Bundle Configuration](#bundle-configuration)
    - [Dynamic Feature Modules](#dynamic-feature-modules)
7. [Release Variants](#release-variants)
    - [Build Types](#build-types)
    - [Product Flavors](#product-flavors)
    - [Build Variants](#build-variants)
8. [Pre-Release Testing](#pre-release-testing)
    - [Internal Testing](#internal-testing)
    - [Testing on Multiple Devices](#testing-on-multiple-devices)
    - [Pre-Launch Reports](#pre-launch-reports)
9. [Play Store Submission](#play-store-submission)
    - [Store Listing](#store-listing)
    - [Release Tracks](#release-tracks)
    - [App Review Process](#app-review-process)
10. [Continuous Integration](#continuous-integration)
    - [Automated Builds](#automated-builds)
    - [Automated Testing](#automated-testing)
    - [Release Automation](#release-automation)
11. [Post-Release Steps](#post-release-steps)
    - [Monitoring and Analytics](#monitoring-and-analytics)
    - [Crash Reporting](#crash-reporting)
    - [User Feedback](#user-feedback)
12. [Appendix](#appendix)
    - [Release Checklist Template](#release-checklist-template)
    - [Troubleshooting Common Issues](#troubleshooting-common-issues)

## Introduction

Preparing an app for release is a critical process that ensures the application is optimized, secure, and ready for distribution to users. This guide outlines the necessary steps to prepare the English-Hindi Learning app for release to the Google Play Store, covering essential aspects such as code signing, app optimization, and bundle creation.

The release process aims to achieve the following objectives:

- **Optimization**: Reduce app size and improve performance
- **Security**: Protect code from reverse engineering
- **Compatibility**: Ensure proper functioning across supported devices
- **Quality**: Verify app meets quality standards before release
- **Compliance**: Adhere to Play Store policies and guidelines

## Release Checklist

Before starting the release preparation process, ensure all the following items are complete:

- [ ] All planned features for the release are implemented and tested
- [ ] All critical and high-priority bugs are fixed
- [ ] App passes all unit tests, integration tests, and UI tests
- [ ] Performance metrics meet or exceed targets (startup time, memory usage, etc.)
- [ ] Accessibility features are implemented and tested
- [ ] Privacy policy and terms of service are up to date
- [ ] All third-party libraries are updated to the latest stable versions
- [ ] App complies with Google Play policies and guidelines
- [ ] All placeholder content and debug features are removed
- [ ] Version numbers and codes are updated correctly
- [ ] Release notes are prepared
- [ ] App metadata (screenshots, descriptions, etc.) is updated

## Version Management

Proper version management is essential for tracking releases and ensuring users receive the correct updates. The app uses the following versioning scheme:

### versionCode

The `versionCode` is an integer value that represents the version of the application code relative to other versions. Each new release must have a higher `versionCode` than the previous release.

```gradle
android {
    defaultConfig {
        applicationId "com.bhashasetu.app"
        minSdk 21
        targetSdk 33
        
        // Increment for each release
        versionCode 10
        
        // Human-readable version
        versionName "1.3.2"
    }
}
```

### versionName

The `versionName` is a string value that represents the user-visible version name. We follow semantic versioning (MAJOR.MINOR.PATCH):

- MAJOR: Significant changes or redesigns
- MINOR: New features in a backward-compatible manner
- PATCH: Backward-compatible bug fixes

### Version Management Strategy

1. Update both `versionCode` and `versionName` in the app-level `build.gradle` file
2. Commit the version changes to source control
3. Tag the release in Git with the version name:

```bash
git tag -a v1.3.2 -m "Version 1.3.2"
git push origin v1.3.2
```

## Code Signing

Android requires all APKs and App Bundles to be digitally signed with a certificate before installation. Signing is a critical security measure that establishes the app developer's identity for updates and system permissions.

### Generating a Signing Key

If a signing key doesn't already exist, generate one using the following steps:

1. Use the keytool command from the JDK:

```bash
keytool -genkey -v -keystore englishhindi-keystore.jks -alias englishhindi -keyalg RSA -keysize 2048 -validity 10000
```

2. Complete the requested information:
   - Password for the keystore (use a strong password)
   - Name, organizational unit, organization, city, state, and country code
   - Key password (can be the same as the keystore password)

3. Securely store the generated keystore file and passwords

### Configuring Gradle for Signing

Configure the app-level `build.gradle` file to use the signing key:

```gradle
android {
    // Other configuration...
    
    signingConfigs {
        release {
            storeFile file("../keystores/englishhindi-keystore.jks")
            storePassword System.getenv("KEYSTORE_PASSWORD") ?: keystoreProperties['storePassword']
            keyAlias System.getenv("KEY_ALIAS") ?: keystoreProperties['keyAlias']
            keyPassword System.getenv("KEY_PASSWORD") ?: keystoreProperties['keyPassword']
        }
    }
    
    buildTypes {
        release {
            minifyEnabled true
            shrinkResources true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            signingConfig signingConfigs.release
        }
    }
}
```

### Secure Key Storage

To avoid storing sensitive information in the build files, use a properties file or environment variables:

#### Option 1: Using a properties file

1. Create a `keystore.properties` file in the project root (not in source control):

```properties
storePassword=your_keystore_password
keyAlias=englishhindi
keyPassword=your_key_password
storeFile=../keystores/englishhindi-keystore.jks
```

2. Load the properties in the app's `build.gradle`:

```gradle
// Load keystore properties
def keystorePropertiesFile = rootProject.file("keystore.properties")
def keystoreProperties = new Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(new FileInputStream(keystorePropertiesFile))
}

android {
    // Other configuration...
    
    signingConfigs {
        release {
            storeFile file(keystoreProperties['storeFile'] ?: "../keystores/englishhindi-keystore.jks")
            storePassword keystoreProperties['storePassword'] ?: System.getenv("KEYSTORE_PASSWORD")
            keyAlias keystoreProperties['keyAlias'] ?: System.getenv("KEY_ALIAS")
            keyPassword keystoreProperties['keyPassword'] ?: System.getenv("KEY_PASSWORD")
        }
    }
}
```

#### Option 2: Using environment variables

For CI/CD environments, set the following environment variables:

- `KEYSTORE_PASSWORD`: The password to the keystore
- `KEY_ALIAS`: The alias of the key
- `KEY_PASSWORD`: The password for the specific key

The Gradle script above checks for both properties and environment variables, using environment variables if available.

## ProGuard Configuration

ProGuard is a tool that shrinks, optimizes, and obfuscates Java code. It removes unused code, renames classes, methods, and fields, and makes the app harder to reverse engineer.

### Basic Configuration

1. Enable ProGuard in the app's `build.gradle`:

```gradle
android {
    buildTypes {
        release {
            minifyEnabled true
            shrinkResources true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
}
```

2. The default `proguard-rules.pro` file in the app module contains basic configurations. Here's a comprehensive configuration for the English-Hindi Learning app:

```
# Default ProGuard rules for Android
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-dump class_files.txt
-printseeds seeds.txt
-printusage unused.txt
-printmapping mapping.txt
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# Android specific rules
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class * extends androidx.fragment.app.Fragment

# Keep the R class and its fields
-keepclassmembers class **.R$* {
    public static <fields>;
}

# Keep native methods
-keepclasseswithmembers class * {
    native <methods>;
}

# Keep custom View constructors
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Keep activities, services, etc. referenced in AndroidManifest.xml
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Keep Parcelable implementations
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep Serializable classes
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Room Database
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# Entity classes
-keep class com.bhashasetu.app.data.model.** { *; }
-keep class com.bhashasetu.app.data.relation.** { *; }

# Enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Prevent obfuscation of types with annotations
-keepattributes *Annotation*
-keep class androidx.annotation.Keep
-keep @androidx.annotation.Keep class * {*;}
-keepclasseswithmembers class * {
    @androidx.annotation.Keep <methods>;
}
-keepclasseswithmembers class * {
    @androidx.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
    @androidx.annotation.Keep <init>(...);
}

# ViewModels
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}

# Retrofit
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.examples.android.model.** { <fields>; }
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Logging
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}
```

### Custom Rules

For specific app components that need custom ProGuard rules:

#### Room Database

```
# Keep Room Database classes
-keep class com.bhashasetu.app.data.db.AppDatabase
-keep class com.bhashasetu.app.data.dao.** { *; }

# Keep Entity classes and their fields
-keep class com.bhashasetu.app.data.model.** { *; }
-keepclassmembers class com.bhashasetu.app.data.model.** { *; }
```

#### AudioPlayer

```
# Keep AudioPlayer components
-keep class com.bhashasetu.app.audio.AudioPlayer { *; }
-keep class com.bhashasetu.app.audio.AudioPlayerListener { *; }
```

#### Custom Views

```
# Keep custom views
-keep class com.bhashasetu.app.view.** { *; }
-keep class com.bhashasetu.app.ui.components.** { *; }
```

### Testing ProGuard Configuration

Before finalizing the release, test the ProGuard configuration:

1. Build a release variant with ProGuard enabled:

```bash
./gradlew assembleRelease
```

2. Install and test the release APK on a test device:

```bash
adb install -r app/build/outputs/apk/release/app-release.apk
```

3. Review the generated ProGuard files in `app/build/outputs/mapping/release/`:
   - `mapping.txt`: Contains the mapping of original to obfuscated class/method names
   - `seeds.txt`: Classes and members that were not obfuscated
   - `usage.txt`: Code that was removed by ProGuard
   - `dump.txt`: Structure of the class files after optimization

4. Check for any ProGuard warnings in the build output and address them

## App Bundle Creation

Android App Bundles are the preferred publishing format for Android apps, as they allow for smaller, more optimized downloads for users.

### Enabling App Bundles

1. Ensure the Android Gradle Plugin is updated to support App Bundles:

```gradle
plugins {
    id 'com.android.application'
}
```

2. Configure bundle options in the app's `build.gradle`:

```gradle
android {
    // Other configuration...
    
    bundle {
        language {
            enableSplit = true
        }
        density {
            enableSplit = true
        }
        abi {
            enableSplit = true
        }
    }
}
```

### Bundle Configuration

Configure the bundle settings in the `build.gradle` file:

```gradle
android {
    // Other configuration...
    
    bundle {
        language {
            // Include only English and Hindi resources in the base APK
            // All other language resources will be in split APKs
            enableSplit = true
            include = ['en', 'hi']
        }
        density {
            // Enable density-based APK splitting
            enableSplit = true
        }
        abi {
            // Enable ABI-based APK splitting
            enableSplit = true
        }
        
        // Configure Bundle settings        
        buildTypes {
            release {
                // Enable app bundle 
                bundle {
                    // Dynamic feature modules settings
                    storeArchive {
                        // Store feature modules in Google Play App Signing
                        enable = true
                    }
                }
            }
        }
    }
}
```

### Dynamic Feature Modules

For larger apps, consider using dynamic feature modules to reduce the initial download size:

1. Configure the base module (`app/build.gradle`):

```gradle
android {
    // Other configuration...
    
    dynamicFeatures = [":features:pronunciation", ":features:games"]
}
```

2. Create a dynamic feature module:

```gradle
// In features/pronunciation/build.gradle
plugins {
    id 'com.android.dynamic-feature'
}

android {
    compileSdk 33
    
    defaultConfig {
        minSdk 21
    }
    
    // Other configuration...
}

dependencies {
    implementation project(':app')
}
```

3. Configure the `AndroidManifest.xml` for the dynamic feature:

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:dist="http://schemas.android.com/apk/distribution"
    package="com.bhashasetu.app.pronunciation">

    <dist:module
        dist:instant="false"
        dist:title="@string/title_pronunciation">
        <dist:delivery>
            <dist:on-demand />
        </dist:delivery>
        <dist:fusing dist:include="true" />
    </dist:module>
</manifest>
```

## Release Variants

The build system can create different variants of the app for different purposes.

### Build Types

Configure different build types in the app's `build.gradle`:

```gradle
android {
    // Other configuration...
    
    buildTypes {
        debug {
            applicationIdSuffix ".debug"
            versionNameSuffix "-debug"
            debuggable true
            minifyEnabled false
        }
        
        beta {
            applicationIdSuffix ".beta"
            versionNameSuffix "-beta"
            debuggable false
            minifyEnabled true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            signingConfig signingConfigs.release
        }
        
        release {
            debuggable false
            minifyEnabled true
            shrinkResources true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            signingConfig signingConfigs.release
        }
    }
}
```

### Product Flavors

Configure different product flavors for different audiences:

```gradle
android {
    // Other configuration...
    
    flavorDimensions "version"
    
    productFlavors {
        free {
            dimension "version"
            applicationIdSuffix ".free"
            versionNameSuffix "-free"
            // Free version specific configurations
            buildConfigField "boolean", "PREMIUM_FEATURES_ENABLED", "false"
            buildConfigField "boolean", "ADS_ENABLED", "true"
        }
        
        premium {
            dimension "version"
            applicationIdSuffix ".premium"
            versionNameSuffix "-premium"
            // Premium version specific configurations
            buildConfigField "boolean", "PREMIUM_FEATURES_ENABLED", "true"
            buildConfigField "boolean", "ADS_ENABLED", "false"
        }
    }
}
```

### Build Variants

The combination of build types and product flavors creates build variants:

- `freeDebug`: Free version with debugging enabled
- `freeBeta`: Free version for beta testing
- `freeRelease`: Free version for production
- `premiumDebug`: Premium version with debugging enabled
- `premiumBeta`: Premium version for beta testing
- `premiumRelease`: Premium version for production

Build a specific variant:

```bash
./gradlew assemblePremiumRelease
```

Create an App Bundle for a specific variant:

```bash
./gradlew bundlePremiumRelease
```

## Pre-Release Testing

Thorough testing is crucial before releasing the app to users.

### Internal Testing

1. Create an internal testing APK:

```bash
./gradlew assembleBeta
```

2. Distribute to the internal testing team via one of these methods:
   - Firebase App Distribution
   - Google Play Internal Testing track
   - Manual distribution (not recommended for security reasons)

3. Collect feedback and bugs from testers

### Testing on Multiple Devices

1. Test on a range of devices with different:
   - Screen sizes and densities
   - Android versions
   - RAM and processing power
   - Manufacturer customizations

2. Create a device test matrix:

| Device | Android Version | Screen Size | RAM | Priority |
|--------|----------------|-------------|-----|----------|
| Pixel 5 | Android 12 | 6.0" FHD+ | 8GB | High |
| Samsung Galaxy S10 | Android 11 | 6.1" QHD+ | 8GB | High |
| Xiaomi Redmi Note 9 | Android 10 | 6.53" FHD+ | 4GB | Medium |
| Nokia 5.3 | Android 10 | 6.55" HD+ | 3GB | Medium |
| Samsung Galaxy Tab A7 | Android 10 | 10.4" | 3GB | Low |

3. Use Firebase Test Lab or AWS Device Farm for automated testing on multiple devices

### Pre-Launch Reports

1. Upload the APK or App Bundle to the Google Play Console's Pre-Launch Reports

2. Review the reports for:
   - Crashes and ANRs (Application Not Responding)
   - Performance issues
   - Security vulnerabilities
   - Accessibility issues

3. Fix any critical issues before proceeding with the release

## Play Store Submission

### Store Listing

Prepare the following elements for the store listing:

1. **App Details**:
   - Title: "English-Hindi Learning Park"
   - Short description (80 characters max)
   - Full description (4000 characters max)
   - App category: Education
   - Content rating questionnaire completion

2. **Graphic Assets**:
   - App icon (512x512px PNG or JPEG)
   - Feature graphic (1024x500px PNG or JPEG)
   - Promo graphic (180x120px PNG or JPEG)
   - Screenshots for different device types:
     - Phone: Minimum 2, maximum 8 screenshots
     - 7-inch tablet: Optional
     - 10-inch tablet: Optional
     - TV: Not applicable
     - Wear OS: Not applicable

3. **Additional Information**:
   - Contact details (email, website, phone)
   - Privacy policy URL
   - App update information

### Release Tracks

The Google Play Console offers different release tracks:

1. **Internal Testing**:
   - Limited to up to 100 testers
   - Quick review and approval
   - Good for initial testing

2. **Closed Testing (Alpha/Beta)**:
   - Can target specific test groups
   - Allows for collecting feedback
   - Testing with a larger audience

3. **Open Testing**:
   - Available to anyone who joins the testing program
   - Useful for gathering feedback from a broader audience

4. **Production**:
   - Full public release
   - Available to all users on Google Play

### App Review Process

1. Complete the Play Console questionnaires:
   - App content
   - Ads format
   - Target audience and content

2. Submit the app for review

3. Monitor the review status in the Play Console

4. Address any issues raised by the review team

5. Once approved, manage the release:
   - Timed release: Specify a date and time
   - Staged release: Roll out to a percentage of users
   - Full release: Available to all users immediately

## Continuous Integration

Implement a CI/CD pipeline to automate the build, test, and release process.

### Automated Builds

Configure CI/CD to automatically build the app when changes are pushed to the repository:

**GitHub Actions Example**:

```yaml
name: Android CI

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v2
    
    - name: Set up JDK 11
      uses: actions/setup-java@v2
      with:
        java-version: '11'
        distribution: 'adopt'
    
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
    
    - name: Build with Gradle
      run: ./gradlew build
      
    - name: Run Tests
      run: ./gradlew test
      
    - name: Upload APK
      uses: actions/upload-artifact@v2
      with:
        name: app
        path: app/build/outputs/apk/debug/app-debug.apk
```

### Automated Testing

Include automated testing in the CI/CD pipeline:

```yaml
jobs:
  test:
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v2
    
    - name: Set up JDK 11
      uses: actions/setup-java@v2
      with:
        java-version: '11'
        distribution: 'adopt'
    
    - name: Unit Tests
      run: ./gradlew testDebugUnitTest
      
    - name: Instrumentation Tests
      uses: reactivecircus/android-emulator-runner@v2
      with:
        api-level: 29
        script: ./gradlew connectedDebugAndroidTest
        
    - name: Upload Test Reports
      uses: actions/upload-artifact@v2
      with:
        name: test-reports
        path: app/build/reports
```

### Release Automation

Automate the release process:

```yaml
jobs:
  release:
    needs: [build, test]
    runs-on: ubuntu-latest
    if: startsWith(github.ref, 'refs/tags/v')
    
    steps:
    - uses: actions/checkout@v2
    
    - name: Set up JDK 11
      uses: actions/setup-java@v2
      with:
        java-version: '11'
        distribution: 'adopt'
    
    - name: Create keystore file
      run: echo "${{ secrets.KEYSTORE_FILE_BASE64 }}" | base64 -d > keystore.jks
    
    - name: Build Release Bundle
      run: |
        ./gradlew bundleRelease \
          -PKEYSTORE_PASSWORD=${{ secrets.KEYSTORE_PASSWORD }} \
          -PKEY_ALIAS=${{ secrets.KEY_ALIAS }} \
          -PKEY_PASSWORD=${{ secrets.KEY_PASSWORD }}
    
    - name: Upload Release Bundle
      uses: actions/upload-artifact@v2
      with:
        name: release-bundle
        path: app/build/outputs/bundle/release/app-release.aab
        
    - name: Upload to Play Store
      uses: r0adkll/upload-google-play@v1
      with:
        serviceAccountJsonPlainText: ${{ secrets.PLAY_STORE_CONFIG_JSON }}
        packageName: com.bhashasetu.app
        releaseFiles: app/build/outputs/bundle/release/app-release.aab
        track: internal  # Or "alpha", "beta", "production"
        status: draft    # Or "inProgress", "halted", "completed"
```

## Post-Release Steps

### Monitoring and Analytics

Set up monitoring and analytics to track app performance and usage:

1. **Firebase Analytics**:
   - Add Firebase Analytics to the app
   - Create custom events to track user interactions
   - Monitor usage patterns and conversion funnels

2. **Performance Monitoring**:
   - Use Firebase Performance Monitoring to track app startup time, screen load times, etc.
   - Set up alerts for performance regressions

3. **Custom Logging**:
   - Implement custom logging for app-specific events
   - Route logs to a central server for analysis

### Crash Reporting

Set up crash reporting to identify and fix crashes quickly:

1. **Firebase Crashlytics**:
   - Add Crashlytics to the app
   - Configure custom crash attributes for better debugging
   - Set up alerts for critical crashes

2. **ANR Reporting**:
   - Monitor Application Not Responding (ANR) incidents
   - Identify and fix causes of UI freezes

### User Feedback

Collect and analyze user feedback:

1. **In-App Feedback**:
   - Implement a feedback mechanism within the app
   - Prompt users for feedback after specific events

2. **Play Store Reviews**:
   - Monitor and respond to Play Store reviews
   - Address common issues in app updates

3. **User Support**:
   - Set up a support email or help desk
   - Create a knowledge base for common questions

## Appendix

### Release Checklist Template

```
# English-Hindi Learning App Release Checklist

## Version Information
- Version Name: [e.g., 1.3.2]
- Version Code: [e.g., 10]
- Release Date: [e.g., 2023-08-15]

## Pre-Release Checks
- [ ] All planned features implemented
- [ ] All critical bugs fixed
- [ ] Unit tests passing
- [ ] Integration tests passing
- [ ] UI tests passing
- [ ] Performance requirements met
- [ ] Accessibility compliance verified
- [ ] Legal documents updated
- [ ] Third-party licenses included
- [ ] Debug features removed
- [ ] Analytics events verified
- [ ] Privacy policy and terms updated
- [ ] Version numbers updated

## Build and Sign
- [ ] ProGuard configuration tested
- [ ] Release variant built successfully
- [ ] App signed with the correct key
- [ ] App bundle created
- [ ] App size optimized

## Testing
- [ ] Internal testing completed
- [ ] Device compatibility verified
- [ ] Pre-launch report issues addressed
- [ ] User acceptance testing completed

## Store Listing
- [ ] Store listing information updated
- [ ] Screenshots updated
- [ ] Feature graphic updated
- [ ] Release notes prepared
- [ ] Content rating confirmed

## Release
- [ ] App submitted for review
- [ ] Release schedule determined
- [ ] Staged rollout percentage set

## Post-Release
- [ ] Monitor crash reports
- [ ] Monitor user feedback
- [ ] Monitor app performance
- [ ] Prepare for next release

## Notes and Issues
[Add any notes or outstanding issues here]
```

### Troubleshooting Common Issues

**Issue: Signing Fails**

- Check that the keystore file exists and is accessible
- Verify keystore and key passwords
- Ensure key alias is correct
- Check if the keystore path is correct

**Issue: ProGuard Obfuscation Problems**

- Review ProGuard rules for missing keep rules
- Check the mapping file to see what has been obfuscated
- Add specific keep rules for problematic classes
- Test with minifyEnabled true but shrinkResources false to isolate issues

**Issue: App Bundle Generation Fails**

- Update Android Gradle Plugin
- Check for resource conflicts
- Ensure all resources follow Android naming conventions
- Verify that dynamic feature modules are properly configured

**Issue: Play Store Rejection**

- Review the rejection reason in the Play Console
- Check for policy violations
- Update privacy policy and permissions if needed
- Ensure app meets content rating requirements
- Fix crashes and ANRs reported in pre-launch reports