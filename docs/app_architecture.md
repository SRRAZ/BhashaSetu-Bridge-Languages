# English-Hindi Learning App Architecture

## Overview

The English-Hindi Learning App is designed to help users learn English through Hindi language. The application follows the MVVM (Model-View-ViewModel) architecture pattern, which separates the UI from business logic and data operations.

## Architectural Components

### 1. Model Layer
- **Word Entity**: Represents vocabulary words with English and Hindi translations
- **Lesson Entity**: Represents structured lessons with categories and content
- **Progress Entity**: Tracks user's learning progress
- **Quiz Entity**: Contains quiz questions and answers for testing knowledge

### 2. Database Layer
- **Room Database**: Local SQLite database for storing all data
- **DAOs (Data Access Objects)**: Interfaces for database operations
- **Repository**: Mediates between ViewModels and data sources

### 3. ViewModel Layer
- **WordViewModel**: Manages word-related operations
- **LessonViewModel**: Handles lesson data and operations
- **ProgressViewModel**: Tracks and updates user progress
- **QuizViewModel**: Manages quiz operations and scoring

### 4. View Layer
- **Activities**: Main screens of the application
- **Fragments**: Modular UI components within activities
- **Adapters**: Connect data to RecyclerViews and other list views
- **Layouts**: XML files defining the UI structure

## Data Flow

1. User interacts with the UI (View)
2. View notifies ViewModel of user actions
3. ViewModel retrieves/updates data via Repository
4. Repository accesses local database through DAOs
5. Data changes are observed by ViewModel
6. ViewModel updates the UI with LiveData
7. UI reflects the changes to the user

## Key Features and Implementation

### 1. Word Learning
- Vocabulary cards with translations
- Pronunciation guides
- Categorization by topics
- Favorites system

### 2. Lesson Structure
- Progressive difficulty levels
- Categorized content
- Interactive examples

### 3. Progress Tracking
- Word mastery statistics
- Lesson completion tracking
- Learning streak monitoring
- Achievement system

### 4. Practice and Testing
- Flashcard review system
- Multiple choice quizzes
- Translation exercises
- Pronunciation practice

## Database Schema

### Words Table
- id (PRIMARY KEY)
- englishWord
- hindiWord
- englishPronunciation
- hindiPronunciation
- category
- difficulty (1-5)
- isFavorite (boolean)
- timeAdded
- masteryLevel (0-100%)

### Lessons Table
- id (PRIMARY KEY)
- title
- description
- category
- level (beginner, intermediate, advanced)
- order (sequence number)
- content (lesson text)
- imageUrl
- wordIds (comma-separated list of related words)

### Progress Table
- id (PRIMARY KEY)
- userId (for future multi-user support)
- wordId (FOREIGN KEY -> Words)
- lessonId (FOREIGN KEY -> Lessons)
- completionLevel (0-100%)
- lastPracticed (timestamp)
- reviewDue (timestamp)

### Quiz Table
- id (PRIMARY KEY)
- lessonId (FOREIGN KEY -> Lessons)
- question
- correctAnswer
- wrongAnswer1
- wrongAnswer2
- wrongAnswer3
- explanationText

## Technology Stack

- **Language**: Java
- **Architecture Pattern**: MVVM
- **Database**: Room (SQLite)
- **Reactive Programming**: LiveData
- **UI Components**: Material Design
- **Navigation**: Jetpack Navigation Component
- **Dependency Injection**: Manual (can be upgraded to Dagger/Hilt)
- **Testing**: JUnit, Espresso

## Future Enhancements

- Cloud synchronization for user data
- Audio pronunciation
- Speech recognition for practice
- Online content updates
- Social features for learning together
- Gamification elements for engagement