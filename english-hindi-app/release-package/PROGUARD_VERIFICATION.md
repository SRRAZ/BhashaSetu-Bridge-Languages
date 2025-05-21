# ProGuard Verification Guide

This document outlines steps to verify that ProGuard optimizations haven't broken any functionality in the release build of the English-Hindi Learning App.

## Understanding ProGuard Output Files

After building the release APK or Bundle, ProGuard generates several output files in the `app/build/outputs/mapping/[flavor]Release/` directory:

- `mapping.txt` - Maps obfuscated class/method names to original names
- `seeds.txt` - Lists classes/members not obfuscated
- `usage.txt` - Lists code removed as unused
- `dump.txt` - Structure of all class files

## Common ProGuard Issues and Verification Steps

### 1. Class not found exceptions
- Check if the class is properly kept in ProGuard rules
- Verify all model classes, especially those used with serialization or reflection

### 2. Reflection issues
- Test all features that use reflection (typically database, JSON parsing)
- Ensure classes accessed via reflection are kept in ProGuard rules

### 3. Resource access problems
- Verify all resources load correctly (images, audio files, etc.)
- Test pronunciation audio functionality thoroughly

### 4. Native method issues
- If using any native code (JNI), verify those functions work correctly

### 5. Third-party library problems
- Test all functionality that depends on external libraries
- Verify database operations with Room
- Check image loading via Glide

## Systematic Testing Approach

1. **Install the release APK** on at least 3 different devices (varying API levels)

2. **Test all app sections:**
   - Word lists and vocabulary browsing
   - Quiz functionality
   - Learning modules
   - Audio pronunciation
   - Search functionality
   - User preferences and settings

3. **Look for specific issues:**
   - Force close/crashes
   - UI rendering problems
   - Missing text or resources
   - Functional features not working

4. **Database verification:**
   - Verify all database operations work
   - Test data persistence across app restarts
   - Check data migration if applicable

5. **Performance checks:**
   - App startup time
   - Memory usage
   - UI responsiveness
   - Battery consumption

## Debugging ProGuard Issues

If issues are found in the release build:

1. Check the `mapping.txt` file to find the obfuscated names for problematic classes
2. Add additional `-keep` rules to `proguard-rules.pro` for affected classes
3. Rebuild and test again
4. Consider using `-dontobfuscate` temporarily for debugging if needed

## Important Areas to Test for English-Hindi Learning App

- Word pronunciation functionality
- Database access for vocabulary
- Quiz generation and scoring
- Progress tracking
- Audio playback
- Image loading for word illustrations
- Preference saving and loading

Always keep a copy of the `mapping.txt` file from each release to be able to deobfuscate crash reports from users.