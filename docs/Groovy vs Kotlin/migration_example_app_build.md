# Example App Build.gradle.kts Conversion

Below is an example of how the app module's `build.gradle` file would look if converted to Kotlin DSL. This example is based on the current `app/build.gradle` file in the project.

## Before (Groovy DSL - build.gradle)

```groovy
plugins {
    id 'com.android.application'
    id 'kotlin-android'
    id 'kotlin-kapt'
}

// Load keystore properties if available
def keystorePropertiesFile = rootProject.file("keystore.properties")
def keystoreProperties = new Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(new FileInputStream(keystorePropertiesFile))
}

android {
    namespace "com.bhashasetu.app"
    compileSdk 34
    
    defaultConfig {
        applicationId "com.bhashasetu.app"
        minSdk 21
        targetSdk 34
        
        // Updated for release
        versionCode 2
        versionName "1.1.0"
        
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        
        // Set vectors for backward compatibility
        vectorDrawables.useSupportLibrary = true
        
        // Multidex support
        multiDexEnabled true
        // Example: Only include English and Hindi string resources
        resConfigs "en", "hi"
    }
    
    // Signing configuration
    signingConfigs {
        release {
            storeFile keystoreProperties.getProperty('storeFile') ? file(keystoreProperties.getProperty('storeFile')) : null
            storePassword keystoreProperties.getProperty('keystorePassword')
            keyAlias keystoreProperties.getProperty('keyAlias')
            keyPassword keystoreProperties.getProperty('keyPassword')
        }
    }
    
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
    
    // Product flavors for different app versions
    flavorDimensions += ["version"]
    productFlavors {
        free {
            dimension "version"
            applicationIdSuffix ".free"
            versionNameSuffix "-free"
            // Free version specific configurations
            buildConfigField "boolean", "PREMIUM_FEATURES_ENABLED", "false"
        }
        
        premium {
            dimension "version"
            applicationIdSuffix ".premium"
            versionNameSuffix "-premium"
            // Premium version specific configurations
            buildConfigField "boolean", "PREMIUM_FEATURES_ENABLED", "true"
        }
    }
    
    // Java toolchain setup
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }
    
    // Packaging configuration
    packaging {
        resources {
            pickFirsts += [
                'META-INF/LICENSE.txt',
                'META-INF/NOTICE.txt',
                'META-INF/DEPENDENCIES'
            ]
        }
    }
}

dependencies {
    implementation 'androidx.core:core-ktx:1.16.0'
    
    // Jetpack Compose
    implementation platform('androidx.compose:compose-bom:2025.05.01')
    implementation 'androidx.compose.ui:ui'
    implementation 'androidx.compose.material3:material3'
    // More dependencies...
}
```

## After (Kotlin DSL - build.gradle.kts)

```kotlin
import org.gradle.api.JavaVersion
import org.gradle.jvm.toolchain.JavaLanguageVersion
import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

// Load keystore properties if available
val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    namespace = "com.bhashasetu.app"
    compileSdk = 34
    
    defaultConfig {
        applicationId = "com.bhashasetu.app"
        minSdk = 21
        targetSdk = 34
        
        // Updated for release
        versionCode = 2
        versionName = "1.1.0"
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        // Set vectors for backward compatibility
        vectorDrawables.useSupportLibrary = true
        
        // Multidex support
        multiDexEnabled = true
        // Example: Only include English and Hindi string resources
        resourceConfigurations.addAll(listOf("en", "hi"))
    }
    
    // Signing configuration
    signingConfigs {
        create("release") {
            storeFile = keystoreProperties.getProperty("storeFile")?.let { file(it) }
            storePassword = keystoreProperties.getProperty("keystorePassword")
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
        }
    }
    
    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isDebuggable = true
            isMinifyEnabled = false
        }
        
        create("beta") {
            applicationIdSuffix = ".beta"
            versionNameSuffix = "-beta"
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
        
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }
    
    // Product flavors for different app versions
    flavorDimensions += listOf("version")
    productFlavors {
        create("free") {
            dimension = "version"
            applicationIdSuffix = ".free"
            versionNameSuffix = "-free"
            // Free version specific configurations
            buildConfigField("boolean", "PREMIUM_FEATURES_ENABLED", "false")
        }
        
        create("premium") {
            dimension = "version"
            applicationIdSuffix = ".premium"
            versionNameSuffix = "-premium"
            // Premium version specific configurations
            buildConfigField("boolean", "PREMIUM_FEATURES_ENABLED", "true")
        }
    }
    
    // Jetpack Compose configuration
    buildFeatures {
        compose = true
        buildConfig = true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    
    // Java toolchain setup
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
    
    // Packaging configuration
    packaging {
        resources {
            pickFirsts.add("META-INF/LICENSE.txt")
            pickFirsts.add("META-INF/NOTICE.txt")
            pickFirsts.add("META-INF/DEPENDENCIES")
        }
    }
    
    // Lint options
    lint {
        abortOnError = false
        checkReleaseBuilds = true
        disable.add("MissingTranslation")
    }
}

dependencies {
    // Core libraries
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("androidx.multidex:multidex:2.0.1")
    
    // Kotlin Core
    implementation("androidx.core:core-ktx:1.16.0")
    
    // Jetpack Compose
    implementation(platform("androidx.compose:compose-bom:2025.05.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.runtime:runtime-livedata")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.0")
    implementation("androidx.navigation:navigation-compose:2.9.0")
    implementation("io.coil-kt:coil-compose:2.5.0")
    
    // More dependencies...
}
```

## Key Differences and Conversion Notes

1. **Imports Added**: Required imports are added at the top of the file.

2. **Plugin Syntax**: Changed from `id 'plugin'` to `id("plugin")` and `kotlin("plugin")`.

3. **Variable Declarations**: Changed from `def variable` to `val variable`.

4. **Property Assignments**: 
   - Changed from `property value` to `property = value` in blocks.
   - All property assignments use the equals sign in Kotlin DSL.

5. **Collection Handling**: 
   - Changed from `collection += [item1, item2]` to `collection.addAll(listOf("item1", "item2"))` or individual `collection.add("item")` calls.

6. **Build Type & Flavor Handling**:
   - Changed from direct declarations to `getByName()` and `create()` methods.
   - Changed property names to match Kotlin naming conventions (e.g., `minifyEnabled` to `isMinifyEnabled`).

7. **String Literals**: Changed from single quotes to double quotes.

8. **Method Calls**: Added parentheses to all method calls.

9. **Boolean Properties**: Properties with boolean values use `is` prefix in Kotlin (e.g., `isDebuggable`).

10. **Type Safety**: Kotlin DSL enforces type safety, requiring explicit types in many places.

This example demonstrates the major differences between Groovy DSL and Kotlin DSL for Android build scripts. When migrating, pay close attention to syntax changes, property assignments, and collection handling, as these are the areas where most errors occur during conversion.