# English-Hindi Learning App User Flow

## Overview

This document outlines the key user flows in the English-Hindi Learning App, showing how users navigate through different features and functions of the application.

## Main Navigation Flow

```
                                 ┌──────────────┐
                                 │  Splash      │
                                 │  Screen      │
                                 └──────┬───────┘
                                        │
                          ┌─────────────▼────────────┐
                          │      Home Screen         │
                          │                          │
                          │  ┌──────┐ ┌──────────┐   │
                          │  │Words │ │ Lessons  │   │
                          │  └──┬───┘ └────┬─────┘   │
                          │     │          │         │
                          └─────┼──────────┼─────────┘
                                │          │
               ┌────────────────┘          └───────────────┐
               │                                           │
    ┌──────────▼───────────┐              ┌───────────────▼──────────┐
    │    Words Section     │              │     Lessons Section      │
    │                      │              │                          │
    │ ┌──────────────────┐ │              │  ┌──────────────────┐   │
    │ │  Word List       │ │              │  │  Lesson List     │   │
    │ └────────┬─────────┘ │              │  └────────┬─────────┘   │
    │          │           │              │           │              │
    │ ┌────────▼─────────┐ │              │  ┌────────▼─────────┐   │
    │ │  Word Detail     │ │              │  │  Lesson Detail   │   │
    │ └────────┬─────────┘ │              │  └────────┬─────────┘   │
    │          │           │              │           │              │
    │ ┌────────▼─────────┐ │              │  ┌────────▼─────────┐   │
    │ │  Practice Word   │ │              │  │  Lesson Quiz     │   │
    │ └──────────────────┘ │              │  └──────────────────┘   │
    └──────────────────────┘              └──────────────────────────┘
                │                                      │
                └──────────────┬───────────────────────┘
                               │
                    ┌──────────▼───────────┐
                    │   Practice Section   │
                    │                      │
                    │ ┌──────────────────┐ │
                    │ │  Flashcards      │ │
                    │ └──────────────────┘ │
                    │                      │
                    │ ┌──────────────────┐ │
                    │ │  Multiple Choice  │ │
                    │ └──────────────────┘ │
                    │                      │
                    │ ┌──────────────────┐ │
                    │ │  Review & Stats  │ │
                    │ └──────────────────┘ │
                    └──────────────────────┘
```

## Detailed User Flows

### 1. First-Time User Flow

```
Splash Screen → Onboarding Screen → Language Preference → Difficulty Selection → 
Home Screen → Recommended First Lesson → Lesson Detail → Practice New Words → 
Quiz → Results → Home Screen (Updated with Progress)
```

### 2. Returning User Flow

```
Splash Screen → Home Screen → Continue Current Lesson or Daily Word → 
Lesson Detail/Word Detail → Practice → Home Screen (Updated with Progress)
```

### 3. Word Learning Flow

#### Browsing Words:
```
Home Screen → Words Tab → Word List → Filter by Category/Search → 
Word Detail → Add to Favorites (Optional) → Practice Word
```

#### Adding Custom Words:
```
Home Screen → Words Tab → Word List → Add Word Button → Add Word Form → 
Save → Word List (Updated) → Word Detail
```

#### Practicing Words:
```
Word Detail → Practice Button → Select Practice Mode (Flashcard/Multiple Choice) → 
Complete Practice → Results → Updated Mastery Level
```

### 4. Lesson Completion Flow

```
Home Screen → Lessons Tab → Lesson List → Select Lesson → Lesson Detail → 
Read Content → Practice Related Words → Take Quiz → 
View Results → Mark Lesson Complete → Next Lesson Recommendation
```

### 5. Practice Session Flow

```
Home Screen → Practice Tab → Select Practice Type → Choose Category → 
Set Difficulty/Number of Items → Complete Practice Session → 
View Results → Save Progress
```

### 6. Review Flow (Based on Spaced Repetition)

```
Home Screen → Due for Review Notification → Review Words Section → 
Complete Reviews → Update Review Schedule → Home Screen
```

### 7. Profile and Progress Flow

```
Home Screen → Profile Tab → View Statistics → View Achievements → 
Set Goals → Adjust Settings → Return to Home
```

## Screen Transitions

### Home Screen to Word List
- Smooth slide transition from right
- Bottom navigation tab indicator animation

### Word List to Word Detail
- Card expansion animation
- Content fade-in

### Word Detail to Practice
- Slide up transition
- Practice options appear with staggered animation

### Lesson List to Lesson Detail
- Page turn animation
- Progress bar animation showing current position

### Quiz Progression
- Slide left transition between questions
- Fade transition for feedback
- Celebration animation for completion

### Practice Session Results
- Results slide up from bottom
- Statistics counters animate to final values
- Progress indicators fill with animation

## Interaction Patterns

### Word Card Interactions
1. Tap: Open word detail
2. Long press: Show quick actions (favorite, practice, delete)
3. Swipe right: Mark as known/mastered
4. Swipe left: Skip/review later

### Lesson Navigation
1. Next/Previous buttons: Navigate between lesson sections
2. Progress bar: Tap to jump to specific section
3. Bottom sheet: Pull up for additional resources
4. Floating button: Mark current section complete

### Quiz Interactions
1. Tap: Select answer
2. Swipe: Navigate between questions (if allowed)
3. Hint button: Reveal partial answer
4. Audio button: Listen to pronunciation

### Practice Mode Interactions
1. Card flip: Reveal answer in flashcard mode
2. Swipe up/down: Categorize difficulty
3. Tap: Select answer in multiple choice
4. Voice button: Record pronunciation for comparison

## Error and Edge Cases

### Offline Mode
- Cached content available
- Sync status indicator
- Auto-sync when connection restored

### No Content Available
- First-time user with no internet
- Friendly error message
- Basic offline content

### Quiz Interruption
- Auto-save progress
- Resume option
- Restart option with new questions

### Data Limits
- Maximum words per category
- Maximum favorite words
- Warning for approaching limits