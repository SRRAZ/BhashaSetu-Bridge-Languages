# English-Hindi Learning App Navigation Flow

## Overview

This document details the navigation flow between screens in the English-Hindi Learning App, illustrating how users move through the application to access various features and content.

## Main Navigation Structure

The app uses a bottom navigation bar with four primary sections:

```
┌─────────────────────────────────────────────────┐
│                                                 │
│                  Splash Screen                  │
│                        │                        │
│                        ▼                        │
│                 Onboarding Screens              │
│             (First-time users only)             │
│                        │                        │
│                        ▼                        │
│                    Home Screen                  │
│                        │                        │
├─────────┬─────────────┬─────────────┬──────────┤
│         │             │             │          │
│         ▼             ▼             ▼          ▼
│      Home Tab     Lessons Tab   Practice   Profile Tab
│                                   Tab           
└─────────────────────────────────────────────────┘
```

## Detailed Screen Flows

### 1. Initial Launch Flow

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│             │     │             │     │             │
│   Splash    │────►│ Language    │────►│ Difficulty  │
│   Screen    │     │ Selection   │     │ Selection   │
│             │     │             │     │             │
└─────────────┘     └─────────────┘     └─────┬───────┘
                                              │
┌─────────────┐     ┌─────────────┐     ┌─────▼───────┐
│             │     │             │     │             │
│  Home       │◄────│ Profile     │◄────│ Interest    │
│  Screen     │     │ Creation    │     │ Selection   │
│             │     │ (Optional)  │     │             │
└─────────────┘     └─────────────┘     └─────────────┘
```

### 2. Home Tab Flow

```
┌─────────────┐     ┌─────────────┐
│             │     │             │
│  Home       │────►│ Word Detail │
│  Screen     │     │ (Daily Word)│
│             │     │             │
└──────┬──────┘     └─────────────┘
       │
       ├───────────┐     ┌─────────────┐
       │           │     │             │
       ▼           ├────►│ Continue    │
┌─────────────┐    │     │ Learning    │
│             │    │     │ (Lesson)    │
│ Recommended │    │     │             │
│ Sections    │    │     └─────────────┘
│             │    │
└─────────────┘    │     ┌─────────────┐
                   │     │             │
                   └────►│ Recommended │
                         │ New Lesson  │
                         │             │
                         └─────────────┘
```

### 3. Lessons Tab Flow

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│             │     │             │     │             │
│  Lesson     │────►│  Lesson     │────►│  Lesson     │
│  List       │     │  Detail     │     │  Content    │
│             │     │             │     │  Sections   │
└─────────────┘     └─────┬───────┘     └──────┬──────┘
       ▲                  │                    │
       │                  │                    │
       │            ┌─────▼───────┐     ┌──────▼──────┐
       │            │             │     │             │
       └────────────┤  Previous/  │     │  Lesson     │
                    │  Next Lesson│     │  Quiz       │
                    │             │     │             │
                    └─────────────┘     └──────┬──────┘
                                               │
                    ┌─────────────┐            │
                    │             │            │
                    │  Practice   │◄───────────┘
                    │  Words      │
                    │             │
                    └─────────────┘
```

### 4. Practice Tab Flow

```
┌─────────────┐
│             │
│  Practice   │
│  Home       │
│             │
└──────┬──────┘
       │
       ├───────────┐     ┌─────────────┐     ┌─────────────┐
       │           │     │             │     │             │
       ▼           │     │  Flashcard  │────►│  Result     │
┌─────────────┐    │     │  Session    │     │  Summary    │
│             │    │     │             │     │             │
│ Practice    │    ├────►└─────────────┘     └─────────────┘
│ Mode        │    │
│ Selection   │    │     ┌─────────────┐     ┌─────────────┐
│             │    │     │             │     │             │
└─────────────┘    │     │  Multiple   │────►│  Result     │
                   ├────►│  Choice     │     │  Summary    │
                   │     │  Quiz       │     │             │
                   │     │             │     │             │
                   │     └─────────────┘     └─────────────┘
                   │
                   │     ┌─────────────┐     ┌─────────────┐
                   │     │             │     │             │
                   └────►│  Fill-in-   │────►│  Result     │
                         │  Blanks     │     │  Summary    │
                         │             │     │             │
                         └─────────────┘     └─────────────┘
```

### 5. Profile Tab Flow

```
┌─────────────┐
│             │
│  Profile    │
│  Home       │
│             │
└──────┬──────┘
       │
       ├───────────┐     ┌─────────────┐
       │           │     │             │
       ▼           │     │  Progress   │
┌─────────────┐    │     │  Details    │
│             │    │     │             │
│ Profile     │    ├────►└─────────────┘
│ Sections    │    │
│             │    │     ┌─────────────┐
│             │    │     │             │
└─────────────┘    │     │  Achievement│
                   ├────►│  Details    │
                   │     │             │
                   │     │             │
                   │     └─────────────┘
                   │
                   │     ┌─────────────┐
                   │     │             │
                   └────►│  Settings   │
                         │             │
                         │             │
                         └──────┬──────┘
                                │
                          ┌─────▼───────┐
                          │             │
                          │  Language   │
                          │  Preferences│
                          │             │
                          └─────────────┘
```

### 6. Word Detail Flow

```
┌─────────────┐
│             │
│  Word       │
│  Detail     │
│             │
└──────┬──────┘
       │
       ├───────────┐     ┌─────────────┐
       │           │     │             │
       ▼           │     │  Audio      │
┌─────────────┐    │     │  Playback   │
│             │    │     │             │
│ Word Detail │    ├────►└─────────────┘
│ Actions     │    │
│             │    │     ┌─────────────┐     ┌─────────────┐
│             │    │     │             │     │             │
└─────────────┘    │     │  Practice   │────►│  Practice   │
                   ├────►│  Options    │     │  Session    │
                   │     │             │     │             │
                   │     └─────────────┘     └─────────────┘
                   │
                   │     ┌─────────────┐
                   │     │             │
                   └────►│  Add to     │
                         │  Favorites  │
                         │             │
                         └─────────────┘
```

### 7. Search Flow

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│             │     │             │     │             │
│  Any        │────►│  Search     │────►│  Search     │
│  Screen     │     │  Input      │     │  Results    │
│             │     │             │     │             │
└─────────────┘     └─────────────┘     └──────┬──────┘
                                               │
                                         ┌─────▼───────┐
                                         │             │
                                         │  Word       │
                                         │  Detail     │
                                         │             │
                                         └─────────────┘
```

### 8. Add New Word Flow

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│             │     │             │     │             │
│  Word       │────►│  Add Word   │────►│  Word       │
│  List       │     │  Form       │     │  Detail     │
│             │     │             │     │  (New Word) │
└─────────────┘     └─────────────┘     └─────────────┘
```

## Modal Flows

### 1. Pronunciation Audio Playback

```
┌─────────────┐     ┌─────────────┐
│             │     │             │
│  Word       │────►│  Audio      │
│  Detail     │     │  Playback   │
│             │     │  (Overlay)  │
└─────────────┘     └─────────────┘
```

### 2. Achievement Notification

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│             │     │             │     │             │
│  Any        │────►│ Achievement │────►│  Profile    │
│  Screen     │     │ Notification│     │  (Achievements)|
│             │     │ (Overlay)   │     │             │
└─────────────┘     └─────────────┘     └─────────────┘
```

### 3. Daily Reminder

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│             │     │             │     │             │
│  System     │────►│  Daily      │────►│  Home       │
│  Notification│    │  Reminder   │     │  Screen     │
│             │     │             │     │             │
└─────────────┘     └─────────────┘     └─────────────┘
```

## Navigation Implementations

### Bottom Navigation

The app uses a BottomNavigationView with four main tabs:

```java
// Set up bottom navigation
BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
bottomNav.setOnItemSelectedListener(item -> {
    switch (item.getItemId()) {
        case R.id.nav_home:
            loadFragment(new HomeFragment());
            return true;
        case R.id.nav_lessons:
            loadFragment(new LessonsFragment());
            return true;
        case R.id.nav_practice:
            loadFragment(new PracticeFragment());
            return true;
        case R.id.nav_profile:
            loadFragment(new ProfileFragment());
            return true;
    }
    return false;
});
```

### Navigation Component

For advanced navigation flows, the app will use the Android Navigation Component:

```xml
<!-- nav_graph.xml -->
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/nav_graph"
    app:startDestination="@id/homeFragment">

    <fragment
        android:id="@+id/homeFragment"
        android:name="com.example.englishhindi.ui.home.HomeFragment"
        android:label="Home">
        <action
            android:id="@+id/action_home_to_wordDetail"
            app:destination="@id/wordDetailFragment" />
    </fragment>

    <fragment
        android:id="@+id/wordDetailFragment"
        android:name="com.example.englishhindi.ui.word.WordDetailFragment"
        android:label="Word Detail">
        <argument
            android:name="wordId"
            app:argType="integer" />
    </fragment>
    
    <!-- Additional destinations -->
</navigation>
```

### Navigation Activity Implementation

```java
public class MainActivity extends AppCompatActivity implements LanguageChangeListener {
    private LanguageManager languageManager;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Initialize language manager
        languageManager = new LanguageManager(this);
        languageManager.setListener(this);
        
        setContentView(R.layout.activity_main);
        
        // Set up navigation
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(
            findViewById(R.id.bottom_navigation), 
            navController
        );
    }
    
    @Override
    public void onLanguageChanged() {
        // Recreate activity to apply new language
        recreate();
    }
    
    // Handle deep links
    @Override
    public boolean onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        return navController.handleDeepLink(intent) || super.onNewIntent(intent);
    }
}
```

## Navigation Transitions

The app will use the following transitions between screens:

1. **Standard Screen Transitions**:
   - Enter: Slide in from right
   - Exit: Slide out to left
   - Pop Enter: Slide in from left
   - Pop Exit: Slide out to right

2. **Shared Element Transitions**:
   - Word List to Word Detail: Shared transition of word card
   - Lesson List to Lesson Detail: Shared transition of lesson card

3. **Modal Dialogs**:
   - Fade in/out for overlay dialogs
   - Slide up/down for bottom sheets

## Deep Linking

The app will support deep linking to specific screens:

```xml
<!-- AndroidManifest.xml -->
<activity android:name=".MainActivity">
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data
            android:scheme="englishhindi"
            android:host="word" />
    </intent-filter>
</activity>
```

Example deep links:
- `englishhindi://word/123` - Open specific word detail
- `englishhindi://lesson/45` - Open specific lesson
- `englishhindi://practice/flashcards` - Start flashcard practice

## Navigation State Management

The app will preserve navigation state across configuration changes and process death:

1. **SavedStateHandle**: Store and retrieve UI state in ViewModels
2. **RemoteMediator**: Handle pagination state
3. **Saved Instance State**: Preserve navigation stack and UI state
4. **ViewModel**: Retain data across configuration changes

## User Flow Optimization

To optimize the user experience, the app implements these navigation patterns:

1. **Contextual Navigation**: Suggest next actions based on current content
2. **Smart Defaults**: Intelligently select default tabs based on user history
3. **Recent Activity**: Quick access to recently viewed lessons or practiced words
4. **Progressive Disclosure**: Reveal advanced features as users become proficient
5. **Shortcut Actions**: App shortcuts for frequently used features

## Accessibility Navigation

For improved accessibility:

1. **Content Descriptions**: All navigation controls have proper content descriptions
2. **Focus Order**: Logical tab order for keyboard/switch device navigation
3. **Heading Hierarchy**: Proper heading structure for screen readers
4. **Voice Actions**: Support for voice commands to navigate between screens

## Navigation Analytics

The app will track navigation patterns to improve usability:

1. **Screen Views**: Track which screens are most frequently visited
2. **Navigation Paths**: Analyze common navigation sequences
3. **Time on Screen**: Measure engagement time per screen
4. **Abandonment Points**: Identify where users leave tasks incomplete
5. **Feature Discovery**: Track which features users discover and use