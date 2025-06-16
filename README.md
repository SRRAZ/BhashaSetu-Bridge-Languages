# BhashaSetu - An English Learning App

A comprehensive mobile application for learning English language, designed for Hindi speakers. The app provides vocabulary learning, lessons, quizzes, flashcards, and progress tracking to make language learning engaging and effective.

## Features

### Vocabulary Management
- Extensive English-Hindi word pairs with definitions and examples
- Word categorization by topics (Basic, Food, Numbers, Family, etc.)
- Audio pronunciations for Hindi words
- Favorites system for bookmarking important words

### Learning Content
- Structured lessons with progressive difficulty
- Cultural notes and pronunciation tips
- Interactive exercises and examples
- Bilingual interface (English and Hindi)

### Practice and Testing
- Flashcard-based vocabulary practice
- Quizzes with various question types:
  - Multiple choice
  - Translation
  - Matching
  - Fill in the blanks
  - True/False
- Spaced repetition algorithm for optimized learning

### Progress Tracking
- User progress for individual words
- Quiz and lesson completion statistics
- Daily streaks and study goals
- Achievement system with unlockable badges

### Gamification
- Points system for completed activities
- Achievement badges for milestones
- Daily streaks to encourage regular practice
- Performance statistics and visualization

### User Experience
- Bilingual interface with seamless language switching
- Offline access to all content
- Clean, intuitive UI with smooth animations
- Dark mode support

## Technical Overview

### Architecture
- MVVM (Model-View-ViewModel) architecture
- Repository pattern for data management
- Single Activity with Compose navigation
- Dependency injection with Dagger Hilt

### Database
- Room database for local storage
- Entities for words, categories, user progress, etc.
- Relations between entities (many-to-many, one-to-many)
- Reactive data with Kotlin Flow

### User Interface
- Modern UI built with Jetpack Compose
- Material 3 design implementation
- Support for both light and dark themes
- Responsive layouts for different screen sizes

### Learning Algorithms
- Spaced repetition system (SRS) based on the SM-2 algorithm
- Adaptive learning paths based on user performance
- Progress tracking with proficiency levels

## Development Setup

### Prerequisites
- Android Studio Arctic Fox or newer
- JDK 11 or newer
- Android SDK 31 or newer

### Building the Project
1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Build and run on an emulator or physical device

## Resources

### Learning Content
- Vocabulary categorized by topics:
  - Basic greetings and phrases
  - Food and beverages
  - Numbers and counting
  - Family members
  - (And more to be added)
- Lessons with progressive difficulty
- Audio pronunciations recorded by native speakers

### Future Enhancements
- Community features for language exchange
- Speech recognition for pronunciation practice
- Expanded vocabulary across more categories
- Additional grammar lessons and exercises
- Cloud synchronization for learning progress

## Technical Details

- Minimum SDK: API 21 (Android 5.0 Lollipop)
- Target SDK: API 35 (Android 15)
- Built with Java and Android SDK
- Uses Room Database for local storage

## Project Structure
- `app/src/main/java/com/bhashasetu/app` - Contains all Java code
- `app/src/main/res` - Contains resources like layouts, strings, etc.
- `app/src/main/AndroidManifest.xml` - App configuration file