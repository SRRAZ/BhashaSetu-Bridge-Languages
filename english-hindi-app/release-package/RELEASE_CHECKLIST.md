# Release Checklist for English-Hindi Learning App

## Before Building the Release Version

- [ ] Update version code and version name in `app/build.gradle`
- [ ] Update release notes in `docs/release/RELEASE_NOTES.md`
- [ ] Run all unit tests (`./gradlew test`)
- [ ] Run all instrumentation tests (`./gradlew connectedAndroidTest`)
- [ ] Run lint checks (`./gradlew lint`)
- [ ] Verify ProGuard rules in `app/proguard-rules.pro`
- [ ] Ensure keystore and signing configuration is properly set up

## Build Process

- [ ] Generate signed APK for free variant (`./gradlew assembleFreeRelease`)
- [ ] Generate signed APK for premium variant (`./gradlew assemblePremiumRelease`)
- [ ] Generate signed App Bundle for free variant (`./gradlew bundleFreeRelease`)
- [ ] Generate signed App Bundle for premium variant (`./gradlew bundlePremiumRelease`)
- [ ] Verify mapping file is generated for ProGuard (`app/build/outputs/mapping/freeRelease/mapping.txt`)
- [ ] Archive mapping file for future debugging of crash reports

## Testing Release Build

- [ ] Install release APK on test devices
- [ ] Verify app launches correctly
- [ ] Test all core functionalities:
  - [ ] Word lists and vocabulary
  - [ ] Quiz and practice features
  - [ ] Audio pronunciation
  - [ ] Favorites and bookmarks
  - [ ] Progress tracking
  - [ ] Settings and preferences
- [ ] Check for any ProGuard-related issues
- [ ] Verify app size is optimized
- [ ] Run performance tests

## Final Steps

- [ ] Create GitHub release tag
- [ ] Upload signed APK and App Bundle to release-package directory
- [ ] Generate and save performance report
- [ ] Prepare Google Play Store assets (screenshots, descriptions)
- [ ] Update changelogs and release notes for Google Play
- [ ] Final review by team members before submission

## Post-Release

- [ ] Monitor crash reports and user feedback
- [ ] Prepare hotfix plan if critical issues are discovered
- [ ] Document lessons learned for future releases