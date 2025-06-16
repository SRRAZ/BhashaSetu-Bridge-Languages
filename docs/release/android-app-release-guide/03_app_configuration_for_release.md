# App Configuration for Release

Before generating your release APK or App Bundle, you need to properly configure your English-Hindi Learning app for production deployment. This section covers all necessary configurations to ensure your app is optimized, secure, and ready for the Google Play Store.

## Version Configuration

In your `app/build.gradle` file, make sure your version information is properly set:

```groovy
defaultConfig {
    applicationId "com.example.englishhindi"
    minSdk 21
    targetSdk 33
    
    // Update these values for each release
    versionCode 2        // Integer that must increase with each update
    versionName "1.1.0"  // Semantic version visible to users
    
    // ...
}
```

- **versionCode**: An integer value that must be increased with each update you publish
- **versionName**: A string value visible to users (follow semantic versioning: major.minor.patch)

![Gradle Configuration](gradle_config.png)

## ProGuard Configuration

ProGuard/R8 is the code shrinker and obfuscator for Android apps. It makes your app smaller and harder to reverse engineer. The English-Hindi Learning app already has a ProGuard configuration in `app/proguard-rules.pro`.

Key ProGuard settings for this app include:

```
# Keep model classes
-keep class com.example.englishhindi.data.model.** { *; }

# Keep Room Database classes
-keep class com.example.englishhindi.data.db.AppDatabase
-keep class com.example.englishhindi.data.dao.** { *; }

# Keep AudioPlayer components
-keep class com.example.englishhindi.audio.AudioPlayer { *; }
-keep class com.example.englishhindi.audio.AudioPlayerListener { *; }
```

These rules ensure that classes essential to your app's functionality aren't improperly obfuscated or removed.

## Build Types Configuration

Your app has multiple build types configured:

```groovy
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
        shrinkResources true
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
```

For release builds, ensure:
- `debuggable` is set to `false`
- `minifyEnabled` is set to `true` for code shrinking
- `shrinkResources` is set to `true` for resource shrinking
- ProGuard configuration is properly linked
- Signing configuration is properly set

## Product Flavors

Your app includes product flavors for free and premium versions:

```groovy
flavorDimensions "version"
productFlavors {
    free {
        dimension "version"
        applicationIdSuffix ".free"
        versionNameSuffix "-free"
        buildConfigField "boolean", "PREMIUM_FEATURES_ENABLED", "false"
    }
    
    premium {
        dimension "version"
        applicationIdSuffix ".premium"
        versionNameSuffix "-premium"
        buildConfigField "boolean", "PREMIUM_FEATURES_ENABLED", "true"
    }
}
```

When releasing, decide which flavor to publish:
- For a freemium model, publish the free version with in-app purchases
- For a paid app model, publish the premium version

## App Bundle Configuration

The app is configured for Android App Bundle, which is Google's recommended publishing format:

```groovy
bundle {
    language {
        // Include only English and Hindi resources in the base APK
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
}
```

This configuration ensures:
- Only English and Hindi language resources are included in the base APK
- Google Play will generate optimized APKs for different screen densities
- Google Play will generate optimized APKs for different CPU architectures (ABIs)

## Removing Debug Features

Before creating a release build, ensure:

1. All debug logs are removed or disabled
2. Debug features or developer menus are disabled
3. Test accounts or credentials are removed
4. Internal URLs point to production servers, not development/staging

The ProGuard configuration already includes rules to strip out logging:

```
# Logging - Remove for release
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}
```

## Resources Optimization

Additional resource optimizations to consider:

1. Compress all images if not already done
2. Remove unused resources
3. Ensure audio files are efficiently compressed

These are handled by the `shrinkResources true` setting in your release build type.

## Permissions Review

Before release, review the app's permissions in the AndroidManifest.xml file:

1. Remove any unnecessary permissions
2. Ensure each permission has a clear purpose that users will understand
3. Consider implementing runtime permission requests with clear explanations

For a language learning app, typical permissions might include:
- `INTERNET` (for downloading content)
- `RECORD_AUDIO` (for pronunciation features)
- `ACCESS_NETWORK_STATE` (to check connectivity)