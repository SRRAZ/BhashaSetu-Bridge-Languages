# Build Instructions
## English-Hindi Learning App v1.1.0 (Build 2)

This document provides detailed instructions for building the release version of the English-Hindi Learning App from source code.

## Prerequisites

### Required Software

- **Android Studio**: Arctic Fox (2020.3.1) or newer
- **JDK**: Version 11 (recommended)
- **Gradle**: Version 7.4.2 (included in the project)
- **Git**: For version control

### System Requirements

- **Operating System**: Windows 10/11, macOS 11+, or Linux
- **RAM**: 8GB minimum, 16GB recommended
- **Storage**: 10GB free space
- **CPU**: Quad-core processor recommended

### Access Requirements

- Access to the repository: `https://github.com/SRRAZ/en-hi-learning-park.git`
- Access to the keystore file and credentials for signing
- Firebase configuration file (if using Firebase services)

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/SRRAZ/en-hi-learning-park.git
cd en-hi-learning-park/english-hindi-app
```

### 2. Set Up Signing Configuration

1. **Locate the Keystore File**
   - Obtain the `englishhindi-keystore.jks` file from secure storage
   - Place it in the `keystores` directory (create if it doesn't exist)

2. **Create Keystore Properties File**
   - Create `keystore.properties` file in the project root
   - Add the following content (replace with actual values):

```properties
storeFile=keystores/englishhindi-keystore.jks
keystorePassword=keystorepass
keyAlias=englishhindi
keyPassword=keypass
```

### 3. Set Up Configuration Files

1. **Firebase Configuration** (if applicable)
   - Obtain the `google-services.json` file
   - Place it in the `app` directory

2. **API Configuration**
   - Create `api_keys.properties` file in the project root
   - Add necessary API keys and endpoints

```properties
API_BASE_URL="https://api.englishhindi-app.com/"
ANALYTICS_KEY="your_analytics_key"
```

## Build Instructions

### 1. Using Android Studio

1. **Open the Project**
   - Open Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to and select the `english-hindi-app` directory

2. **Sync Project**
   - Wait for the Gradle sync to complete
   - Resolve any dependency issues if prompted

3. **Select Build Variant**
   - Open the Build Variants panel (View > Tool Windows > Build Variants)
   - Select the desired build variant:
     - `freeRelease` for free version
     - `premiumRelease` for premium version

4. **Build the Project**
   - Select Build > Rebuild Project
   - Wait for the build to complete

5. **Generate Signed APK/Bundle**
   - Select Build > Generate Signed Bundle / APK
   - Select Android App Bundle or APK
   - Choose the existing keystore and enter credentials
   - Select the appropriate destination folder
   - Click Finish to generate the build

### 2. Using Command Line

1. **Clean the Project**
```bash
./gradlew clean
```

2. **Build Free Version**

For APK:
```bash
./gradlew assembleFreeRelease
```

For App Bundle:
```bash
./gradlew bundleFreeRelease
```

3. **Build Premium Version**

For APK:
```bash
./gradlew assemblePremiumRelease
```

For App Bundle:
```bash
./gradlew bundlePremiumRelease
```

4. **Build Both Variants**

For APKs:
```bash
./gradlew assembleRelease
```

For App Bundles:
```bash
./gradlew bundleRelease
```

## Output Files

### APK Files

- Free variant: `app/build/outputs/apk/free/release/app-free-release.apk`
- Premium variant: `app/build/outputs/apk/premium/release/app-premium-release.apk`

### App Bundle Files

- Free variant: `app/build/outputs/bundle/freeRelease/app-free-release.aab`
- Premium variant: `app/build/outputs/bundle/premiumRelease/app-premium-release.aab`

### Mapping Files

- Free variant: `app/build/outputs/mapping/freeRelease/mapping.txt`
- Premium variant: `app/build/outputs/mapping/premiumRelease/mapping.txt`

## Verification Steps

After building, perform these verification steps:

### 1. Verify APK Signature

```bash
apksigner verify --verbose app/build/outputs/apk/free/release/app-free-release.apk
apksigner verify --verbose app/build/outputs/apk/premium/release/app-premium-release.apk
```

### 2. Check APK Contents

```bash
aapt dump badging app/build/outputs/apk/free/release/app-free-release.apk
```

### 3. Test Installation

Install and test the APK on a physical device:

```bash
adb install -r app/build/outputs/apk/free/release/app-free-release.apk
```

### 4. Verify ProGuard Configuration

Check mapping file generation:

```bash
ls -la app/build/outputs/mapping/freeRelease/
```

## Common Issues and Solutions

### 1. Gradle Sync Fails

**Solution:**
- Check internet connection
- Update Gradle plugin version in `build.gradle` files
- Run `./gradlew --refresh-dependencies`

### 2. Signing Configuration Issues

**Solution:**
- Verify keystore file exists in the correct location
- Check keystore.properties file has correct credentials
- Try rebuilding with `./gradlew clean assembleRelease`

### 3. Dependency Conflicts

**Solution:**
- Use `./gradlew app:dependencies` to see dependency tree
- Add resolution strategy in `build.gradle`
- Update conflicting libraries

### 4. Out of Memory Errors

**Solution:**
- Add the following to `gradle.properties`:
  ```
  org.gradle.jvmargs=-Xmx4g -XX:MaxPermSize=512m -XX:+HeapDumpOnOutOfMemoryError
  ```

## CI/CD Build Configuration

### Jenkins Configuration

```groovy
pipeline {
    agent {
        docker {
            image 'thyrlian/android-sdk:latest'
        }
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Setup') {
            steps {
                sh 'mkdir -p keystores'
                withCredentials([file(credentialsId: 'android-keystore', variable: 'KEYSTORE_FILE')]) {
                    sh 'cp $KEYSTORE_FILE keystores/englishhindi-keystore.jks'
                }
                withCredentials([file(credentialsId: 'keystore-properties', variable: 'KEYSTORE_PROPS')]) {
                    sh 'cp $KEYSTORE_PROPS keystore.properties'
                }
            }
        }
        
        stage('Build') {
            steps {
                sh './gradlew clean assembleFreeRelease assemblePremiumRelease bundleFreeRelease bundlePremiumRelease'
            }
        }
        
        stage('Archive') {
            steps {
                archiveArtifacts artifacts: 'app/build/outputs/apk/**/release/*.apk', fingerprint: true
                archiveArtifacts artifacts: 'app/build/outputs/bundle/**/release/*.aab', fingerprint: true
                archiveArtifacts artifacts: 'app/build/outputs/mapping/**/release/mapping.txt', fingerprint: true
            }
        }
    }
}
```

### GitHub Actions Configuration

```yaml
name: Release Build

on:
  push:
    tags:
      - 'v*'

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 11
      uses: actions/setup-java@v3
      with:
        java-version: '11'
        distribution: 'adopt'
        
    - name: Setup Android SDK
      uses: android-actions/setup-android@v2
        
    - name: Setup keystore
      run: |
        mkdir -p keystores
        echo "${{ secrets.KEYSTORE_FILE_BASE64 }}" | base64 --decode > keystores/englishhindi-keystore.jks
        echo "storeFile=keystores/englishhindi-keystore.jks" > keystore.properties
        echo "keystorePassword=${{ secrets.KEYSTORE_PASSWORD }}" >> keystore.properties
        echo "keyAlias=${{ secrets.KEY_ALIAS }}" >> keystore.properties
        echo "keyPassword=${{ secrets.KEY_PASSWORD }}" >> keystore.properties
    
    - name: Build with Gradle
      run: ./gradlew assembleRelease bundleRelease
      
    - name: Upload APK artifacts
      uses: actions/upload-artifact@v3
      with:
        name: release-apks
        path: app/build/outputs/apk/**/release/*.apk
        
    - name: Upload Bundle artifacts
      uses: actions/upload-artifact@v3
      with:
        name: release-bundles
        path: app/build/outputs/bundle/**/release/*.aab
        
    - name: Upload Mapping files
      uses: actions/upload-artifact@v3
      with:
        name: mapping-files
        path: app/build/outputs/mapping/**/release/mapping.txt
```

## Appendix

### Build Variants Matrix

| Build Type | Flavor  | Variant Name    | Features                               |
|------------|---------|-----------------|----------------------------------------|
| debug      | free    | freeDebug       | Debug build with ads, debug features   |
| debug      | premium | premiumDebug    | Debug build, no ads, all features      |
| beta       | free    | freeBeta        | Beta build with ads, crash reporting   |
| beta       | premium | premiumBeta     | Beta build, no ads, crash reporting    |
| release    | free    | freeRelease     | Release build with ads, optimized      |
| release    | premium | premiumRelease  | Release build, no ads, all optimized   |

### Gradle Tasks Reference

| Task                   | Description                                         |
|------------------------|-----------------------------------------------------|
| `clean`                | Clean build directory                               |
| `assembleFreeDebug`    | Build free debug APK                                |
| `assembleFreeRelease`  | Build free release APK                              |
| `assemblePremiumDebug` | Build premium debug APK                             |
| `assemblePremiumRelease` | Build premium release APK                         |
| `bundleFreeRelease`    | Build free release AAB                              |
| `bundlePremiumRelease` | Build premium release AAB                           |
| `lintFreeRelease`      | Run lint checks on free release variant             |
| `lintPremiumRelease`   | Run lint checks on premium release variant          |
| `testFreeReleaseUnitTest` | Run unit tests for free release variant          |
| `testPremiumReleaseUnitTest` | Run unit tests for premium release variant    |

### File Structure Reference

```
english-hindi-app/
├── app/
│   ├── src/
│   │   ├── main/
│   │   ├── free/
│   │   ├── premium/
│   │   ├── debug/
│   │   ├── release/
│   │   ├── test/
│   │   └── androidTest/
│   ├── build.gradle
│   └── proguard-rules.pro
├── keystores/
│   └── englishhindi-keystore.jks
├── gradle/
│   └── wrapper/
├── build.gradle
├── keystore.properties
├── gradle.properties
└── settings.gradle
```