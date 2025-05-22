# Gradle Configuration Updates

## 1. Gradle Wrapper Version Update

**File Changed**: `gradle/wrapper/gradle-wrapper.properties`

**Before**:
```properties
distributionUrl=https\://services.gradle.org/distributions/gradle-8.11.1-bin.zip
```

**After**:
```properties
distributionUrl=https\://services.gradle.org/distributions/gradle-8.14-bin.zip
```

**Reason**: Updated Gradle wrapper from version 8.11.1 to 8.14 to address the warning:
> Warning:(3, 17) A newer version of Gradle than 8.11.1 is available: 8.14

**Benefits**:
- Improved build performance
- Access to latest Gradle features and improvements
- Bug fixes from newer Gradle version
- Eliminated version warning

**Note**: This update is backward compatible with the current Android Gradle Plugin version (8.10.0) being used in the project.

## 2. Fixed Deprecated Convention Type Warnings

**File Changed**: `app/build.gradle`

**Before**:
```gradle
compileOptions {
    sourceCompatibility JavaVersion.VERSION_17
    targetCompatibility JavaVersion.VERSION_17
}
```

**After**:
```gradle
// Use Java toolchain API instead of sourceCompatibility/targetCompatibility
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
```

**Reason**: Replaced the deprecated `compileOptions` block that was using the deprecated `org.gradle.api.plugins.Convention` type with the modern Java Toolchain API.

**Benefits**:
- Eliminates the numerous warnings about deprecated `Convention` type
- Makes the project compatible with Gradle 9.0
- Uses the recommended modern API for Java compilation configuration

Additionally, replaced the deprecated `excludes` directive with `pickFirsts`:

**Before**:
```gradle
packaging {
    resources {
        excludes += [
            'META-INF/LICENSE.txt',
            'META-INF/NOTICE.txt',
            'META-INF/DEPENDENCIES'
        ]
    }
}
```

**After**:
```gradle
packaging {
    resources {
        pickFirsts += [
            'META-INF/LICENSE.txt',
            'META-INF/NOTICE.txt',
            'META-INF/DEPENDENCIES'
        ]
    }
}
```

**Reason**: The `excludes` property in the packaging configuration was deprecated, and `pickFirsts` is the recommended alternative for handling duplicate resources.

## 3. Fixed Remaining Build Script Warnings

**File Changed**: `build.gradle` (root project)

**Before**:
```gradle
task clean(type: Delete) {
    delete rootProject.buildDir
}
```

**After**:
```gradle
tasks.register('clean', Delete) {
    delete layout.buildDirectory
}
```

**Reasons for change**:
1. Replaced direct task creation with `tasks.register()` to avoid unnecessary configuration
2. Replaced the deprecated `buildDir` property with `layout.buildDirectory`

**Benefits**:
- Improved build performance by avoiding unnecessary task configuration
- Uses the modern Gradle task registration API
- Eliminates deprecated property warnings
- Makes the project compatible with Gradle 9.0

These changes follow Gradle's task configuration avoidance recommendations, which improve build performance by only configuring tasks when they are actually needed for execution.