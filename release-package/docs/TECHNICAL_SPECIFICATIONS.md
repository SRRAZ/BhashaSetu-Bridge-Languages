# Technical Specifications
## English-Hindi Learning App v1.1.0 (Build 2)

This document provides detailed technical specifications for the English-Hindi Learning App release build.

## Application Details

| Property | Value                     |
|----------|---------------------------|
| Application Name | English-Hindi Learning    |
| Package Name | com.bhashasetu.app        |
| Version Name | 1.1.0                     |
| Version Code | 2                         |
| Minimum SDK | 21 (Android 5.0 Lollipop) |
| Target SDK | 33 (Android 13)           |
| Build Tools Version | 30.0.3                    |
| Gradle Version | 7.4.2                     |
| Kotlin Version | 1.8.0                     |
| NDK Version | 25.1.8937393              |
| Compile SDK | 33                        |

## Build Configuration

### Free Variant

| Property | Value                   |
|----------|-------------------------|
| Application ID | com.bhashasetu.app.free |
| Debuggable | false                   |
| Minify Enabled | true                    |
| Shrink Resources | true                    |
| Obfuscated | true                    |
| Split APKs | true                    |
| ProGuard Enabled | true                    |

### Premium Variant

| Property | Value                      |
|----------|----------------------------|
| Application ID | com.bhashasetu.app.premium |
| Debuggable | false                      |
| Minify Enabled | true                       |
| Shrink Resources | true                       |
| Obfuscated | true                       |
| Split APKs | true                       |
| ProGuard Enabled | true                       |

## Resource Specifications

### APK Sizes

| Variant | APK Size | Installed Size |
|---------|----------|---------------|
| Free | 19.9 MB | 24.3 MB |
| Premium | 21.3 MB | 26.1 MB |

### App Bundle Sizes

| Variant | Bundle Size | Download Size Range |
|---------|------------|---------------------|
| Free | 17.8 MB | 9.2 - 12.5 MB |
| Premium | 19.2 MB | 10.1 - 13.8 MB |

### Resource Counts

| Resource Type | Count |
|---------------|-------|
| Layouts | 42 |
| Drawables | 287 |
| Animations | 23 |
| Raw Resources | 156 |
| String Resources | 1248 |
| Styles | 34 |

## Dependencies

### Core Libraries

| Library | Version | Purpose |
|---------|---------|---------|
| androidx.appcompat | 1.6.1 | Android compatibility |
| androidx.core | 1.9.0 | Core Android functionality |
| androidx.constraintlayout | 2.1.4 | UI layouts |
| com.google.android.material | 1.9.0 | Material Design components |
| androidx.recyclerview | 1.3.0 | Efficient list rendering |
| androidx.fragment | 1.5.5 | Fragment management |

### Database

| Library | Version | Purpose |
|---------|---------|---------|
| androidx.room | 2.5.2 | Local database storage |
| androidx.sqlite | 2.3.1 | SQLite database support |

### Networking

| Library | Version | Purpose |
|---------|---------|---------|
| com.squareup.retrofit2 | 2.9.0 | HTTP client |
| com.squareup.okhttp3 | 4.10.0 | HTTP networking |
| com.squareup.moshi | 1.14.0 | JSON parsing |

### Image Loading

| Library | Version | Purpose |
|---------|---------|---------|
| com.github.bumptech.glide | 4.15.1 | Image loading and caching |

### Architecture Components

| Library | Version | Purpose |
|---------|---------|---------|
| androidx.lifecycle | 2.6.1 | Lifecycle-aware components |
| androidx.navigation | 2.6.0 | Navigation and deep linking |
| androidx.work | 2.8.1 | Background processing |

### Testing Dependencies

| Library | Version | Purpose |
|---------|---------|---------|
| junit | 4.13.2 | Unit testing |
| androidx.test.ext | 1.1.5 | Android testing extensions |
| androidx.test.espresso | 3.5.1 | UI testing |
| androidx.benchmark | 1.1.1 | Performance benchmarking |

## Architecture

The application follows the MVVM (Model-View-ViewModel) architecture pattern with clean architecture principles:

```
app/
├── data/
│   ├── model/       # Data models
│   ├── repository/  # Data sources and repositories
│   ├── local/       # Local database
│   └── remote/      # API services
├── domain/
│   ├── usecase/     # Business logic
│   └── repository/  # Repository interfaces
├── ui/
│   ├── home/        # Home screen UI components
│   ├── dictionary/  # Dictionary UI components
│   ├── learning/    # Learning modules UI components
│   └── settings/    # Settings UI components
└── util/            # Utility classes
```

## Database Schema

The app uses Room database with the following main entities:

1. **Word**
   - Primary table for vocabulary items
   - Contains Hindi and English translations, usage examples, etc.

2. **Category**
   - Word categories and learning modules 

3. **Progress**
   - User learning progress tracking

4. **User**
   - User profile and preferences

5. **Quiz**
   - Quiz questions and answers

## Performance Specifications

| Metric | Target | Actual (v1.1.0) |
|--------|--------|----------------|
| Cold Start Time | < 2000ms | 1886ms |
| Warm Start Time | < 1000ms | 1013ms |
| Hot Start Time | < 500ms | 322ms |
| Memory Usage | < 70MB | 65.4MB |
| Frame Rate | > 55fps | 57fps |
| Battery Usage (Active) | < 8%/hr | 6%/hr |
| Battery Usage (Background) | < 2%/hr | 2%/hr |
| Network Data (Daily) | < 5MB | 3.2MB |

## Security Features

1. **Data Encryption**
   - SQLCipher for database encryption (AES-256)
   - Encrypted SharedPreferences for sensitive settings

2. **Network Security**
   - Certificate pinning for API communication
   - TLS 1.3 for all network communications
   - Network Security Configuration enforcing HTTPS

3. **Code Protection**
   - ProGuard code obfuscation
   - Tamper detection
   - Root detection (optional)

4. **Authentication**
   - Email/password with proper hashing
   - OAuth 2.0 support for social login
   - Biometric authentication option

## Hardware Requirements

### Minimum Requirements

| Component | Specification |
|-----------|---------------|
| Processor | 1.4 GHz Quad-core |
| RAM | 1.5 GB |
| Storage | 100 MB free space |
| Screen | 4.5" WVGA (480 x 800) |
| Android Version | 5.0 (API 21) |

### Recommended Requirements

| Component | Specification |
|-----------|---------------|
| Processor | 2.0 GHz Octa-core |
| RAM | 4 GB |
| Storage | 500 MB free space |
| Screen | 5.5" FHD (1080 x 1920) |
| Android Version | 10.0+ (API 29+) |

## Permissions

| Permission | Purpose | Required |
|------------|---------|----------|
| INTERNET | Network access for content | Yes |
| ACCESS_NETWORK_STATE | Check network connectivity | Yes |
| RECORD_AUDIO | Pronunciation practice | No (Optional) |
| CAMERA | Scan text for translation | No (Optional) |
| READ_EXTERNAL_STORAGE | Import custom word lists | No (Optional) |
| WRITE_EXTERNAL_STORAGE | Export progress data | No (Optional) |

## Build Instructions

Detailed build instructions are available in the `instructions/BUILD_INSTRUCTIONS.md` file.

## ProGuard Configuration

The app uses custom ProGuard rules defined in `app/proguard-rules-updated.pro`. Key rules include:

- Keeping model classes for proper serialization
- Preserving Room database classes
- Special handling for audio focus components
- Protection for third-party libraries using reflection

## App Bundle Configuration

The Android App Bundle is configured to:

- Enable language-based splitting (en, hi)
- Enable density-based splitting
- Enable ABI-based splitting
- Include only essential languages in base APK

## Testing Coverage

| Component | Unit Test Coverage | Instrumentation Test Coverage |
|-----------|-------------------|------------------------------|
| Data Layer | 87% | 78% |
| Domain Layer | 92% | N/A |
| UI Layer | 63% | 72% |
| Overall | 79% | 74% |

## Future Technical Considerations

1. **Architecture Evolution**
   - Migration to Jetpack Compose for UI
   - Implementation of Kotlin Flow for reactive streams

2. **Performance Enhancements**
   - Further warm start optimization
   - Enhanced image loading pipeline

3. **Feature Expansion**
   - Offline machine learning for pronunciation
   - Expanded language support beyond Hindi/English