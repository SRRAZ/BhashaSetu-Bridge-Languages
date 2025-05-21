# English-Hindi Learning App Release Package

This directory contains the release artifacts for the English-Hindi Learning App version 1.1.0 (build 2).

## Release Contents

- **APK Files**: Signed release APKs for both free and premium variants
- **AAB Files**: Signed Android App Bundles for Play Store submission
- **ProGuard Mapping**: Mapping files for crash deobfuscation
- **Release Notes**: Detailed release notes and changelogs
- **Test Reports**: Performance and functionality test reports
- **Documentation**: User guides and implementation documentation

## Build Information

- **Version**: 1.1.0
- **Version Code**: 2
- **Build Date**: May 21, 2025
- **Minimum SDK**: 21 (Android 5.0)
- **Target SDK**: 33 (Android 13.0)
- **Build Tools**: 30.0.3
- **Gradle Version**: 7.4.2
- **ProGuard**: Enabled with custom rules

## Release Variants

### Free Variant

- **APK**: `app-free-release.apk`
- **Bundle**: `app-free-release.aab`
- **Features**:
  - Core vocabulary and dictionary
  - Basic learning modules
  - Daily practice with limitations
  - Ad-supported experience

### Premium Variant

- **APK**: `app-premium-release.apk`
- **Bundle**: `app-premium-release.aab`
- **Features**:
  - Complete vocabulary and dictionary
  - All learning modules
  - Unlimited practice sessions
  - Ad-free experience
  - Advanced progress tracking
  - Offline content download
  - Premium support

## Performance Improvements

This release includes significant performance optimizations:

- Cold start time improved by 41.8%
- Memory usage reduced by 19.8%
- Frame rendering time improved by 35.5%
- Battery consumption reduced by 50%
- APK size reduced by 30.2%

For complete details, see the performance reports in the `performance-testing/reports` directory.

## Installation Instructions

1. Download the appropriate APK for your variant (free or premium)
2. Enable installation from unknown sources in your device settings
3. Install the APK by opening the downloaded file
4. Launch the app and follow the first-time setup instructions

## Verification Steps

After installation, verify the following:

1. App launches to the main screen without crashes
2. Navigation through all main screens works correctly
3. Word dictionary search functions properly
4. Audio pronunciation plays correctly
5. Learning modules load and function as expected

## Distribution Channels

The release packages are prepared for distribution through:

1. **Google Play Store**: Using the Android App Bundle (.aab) files
2. **Website Download**: Using the APK files for direct installation
3. **Beta Testing Platforms**: Using either APK or AAB as needed

## ProGuard Optimization

This release uses ProGuard for code shrinking, optimization, and obfuscation. The mapping files are included for crash deobfuscation. Key optimizations include:

- Removal of unused code (27.0% reduction in method count)
- Removal of unused resources (24.8% reduction in resource size)
- Name obfuscation for security and size reduction
- Optimized bytecode for better runtime performance

## Support Information

For any issues with this release:

- **Email**: support@englishhindi-app.com
- **Website**: https://englishhindi-app.com/support
- **Documentation**: See `/docs` directory for detailed guides

## Release Notes

See `RELEASE_NOTES.md` for detailed information about features, improvements, and bug fixes in this release.