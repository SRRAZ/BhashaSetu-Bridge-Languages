# Complete User Flow Diagram

```
┌───────────────────────────────────────────────────────────────────────┐
│                           ONBOARDING FLOW                             │
└───────────────────────────────────────────────────────────────────────┘
          │
          ▼
┌───────────────────┐      ┌─────────────────────┐     ┌─────────────────┐
│   Splash Screen   │───►  │  Language Selection  │───► │ Skill Assessment│
└───────────────────┘      └─────────────────────┘     └─────────────────┘
                                                              │
          ┌───────────────────────────────────────────────────┘
          ▼
┌─────────────────────┐      ┌───────────────────┐     ┌─────────────────┐
│ Learning Preferences│───►  │   Account Setup   │───► │   Home Screen   │
└─────────────────────┘      └───────────────────┘     └─────────────────┘
                                                              │
                                                              ▼
┌───────────────────────────────────────────────────────────────────────┐
│                           MAIN NAVIGATION                             │
└───────────────────────────────────────────────────────────────────────┘
          │                    │                  │                  │
          ▼                    ▼                  ▼                  ▼
┌───────────────┐     ┌──────────────┐    ┌─────────────┐    ┌─────────────┐
│  Home Screen  │     │    Lessons    │    │  Practice   │    │   Profile   │
└───────────────┘     └──────────────┘    └─────────────┘    └─────────────┘
    │   │   │              │   │              │   │  │            │   │
    │   │   │              │   │              │   │  │            │   │
    │   │   └──────────────┘   │              │   │  │            │   │
    │   │                      │              │   │  │            │   │
    │   └──────────────────────┘              │   │  │            │   │
    │                                         │   │  │            │   │
    └─────────────────────────────────────────┘   │  │            │   │
                                                  │  │            │   │
                      ┌─────────────────────────┐│  │            │   │
                      │                         ││  │            │   │
                      ▼                         ▼▼  ▼            ▼   ▼

┌───────────────────────────────────────────────────────────────────────┐
│                           VOCABULARY LEARNING                         │
└───────────────────────────────────────────────────────────────────────┘
          │                    │                  │
          ▼                    ▼                  ▼
┌───────────────┐     ┌──────────────┐    ┌─────────────┐    
│   Word List   │     │  Word Detail  │    │   Add Word  │    
└───────────────┘     └──────────────┘    └─────────────┘    
          │                    │                  
          │                    │                  
          └─────────┬──────────┘                  
                    │                             
                    ▼                             
          ┌───────────────────┐                   
          │Word Search/Filter │                   
          └───────────────────┘                   


┌───────────────────────────────────────────────────────────────────────┐
│                           LESSON LEARNING                             │
└───────────────────────────────────────────────────────────────────────┘
          │                    │                  │
          ▼                    ▼                  ▼
┌───────────────┐     ┌──────────────┐    ┌─────────────┐    
│  Lesson List  │────►│ Lesson Detail │───►│Lesson Content│    
└───────────────┘     └──────────────┘    └─────────────┘    
                                                 │
                                                 ▼
                                          ┌─────────────┐
                                          │ Lesson Quiz │
                                          └─────────────┘
                                                 │
                                                 ▼
                                          ┌─────────────┐
                                          │Quiz Results │
                                          └─────────────┘

┌───────────────────────────────────────────────────────────────────────┐
│                           PRACTICE FLOWS                              │
└───────────────────────────────────────────────────────────────────────┘
                     │
          ┌──────────┼──────────┬───────────┬───────────┐
          ▼          ▼          ▼           ▼           ▼
┌───────────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐
│  Flashcards   │ │ Quizzes │ │ Games   │ │Listening│ │Speaking │
└───────────────┘ └─────────┘ └─────────┘ └─────────┘ └─────────┘
          │          │          │           │           │
          ▼          ▼          ▼           ▼           ▼
┌───────────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐
│   Practice    │ │ Answer  │ │ Play    │ │ Listen  │ │ Record  │
│   Session     │ │Questions│ │ Game    │ │Exercise │ │Practice │
└───────────────┘ └─────────┘ └─────────┘ └─────────┘ └─────────┘
          │          │          │           │           │
          ▼          ▼          ▼           ▼           ▼
┌───────────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐
│    Review     │ │ Results │ │ Results │ │ Results │ │ Feedback│
└───────────────┘ └─────────┘ └─────────┘ └─────────┘ └─────────┘
          │          │          │           │           │
          └──────────┴──────────┴───────────┴───────────┘
                                 │
                                 ▼
                         ┌──────────────┐
                         │Progress Update│
                         └──────────────┘

┌───────────────────────────────────────────────────────────────────────┐
│                           PROFILE & PROGRESS                          │
└───────────────────────────────────────────────────────────────────────┘
          │                    │                  │                  │
          ▼                    ▼                  ▼                  ▼
┌───────────────┐     ┌──────────────┐    ┌─────────────┐    ┌─────────────┐
│ Progress Stats │     │ Achievements  │    │ Settings    │    │  Account    │
└───────────────┘     └──────────────┘    └─────────────┘    └─────────────┘
          │                    │                  │                  │
          ▼                    ▼                  ▼                  ▼
┌───────────────┐     ┌──────────────┐    ┌─────────────┐    ┌─────────────┐
│Learning History│     │Achievement   │    │App Language │    │Login/Logout │
│                │     │Details       │    │             │    │             │
└───────────────┘     └──────────────┘    └─────────────┘    └─────────────┘
          │                                       │                   
          │                                       │                   
          └───────────────────────────────────────┘                   
                               │                                      
                               ▼                                      
                      ┌──────────────────┐                            
                      │Notification Prefs │                            
                      └──────────────────┘                            


┌───────────────────────────────────────────────────────────────────────┐
│                         LANGUAGE SWITCHING                            │
└───────────────────────────────────────────────────────────────────────┘
                            │
              ┌─────────────┴─────────────┐
              ▼                           ▼
      ┌──────────────┐            ┌──────────────┐
      │ English Mode │◄──────────►│  Hindi Mode  │
      └──────────────┘            └──────────────┘
              │                           │
              ▼                           ▼
      ┌──────────────┐            ┌──────────────┐
      │English Primary│            │Hindi Primary │
      │Hindi Secondary│            │English Second│
      └──────────────┘            └──────────────┘


┌───────────────────────────────────────────────────────────────────────┐
│                         QUIZ USER FLOW                                │
└───────────────────────────────────────────────────────────────────────┘
          │
          ▼
┌───────────────────┐      ┌─────────────────────┐     ┌─────────────────┐
│   Quiz Selection  │───►  │  Quiz Introduction   │───► │ Question Screen │
└───────────────────┘      └─────────────────────┘     └─────────────────┘
                                                              │
          ┌───────────────────────────────────────────────────┘
          ▼
┌─────────────────────┐      ┌───────────────────┐     ┌─────────────────┐
│ Answer Submission   │───►  │ Feedback Display  │───► │ Next Question   │
└─────────────────────┘      └───────────────────┘     └─────────────────┘
                                                              │
          ┌───────────────────────────────────────────────────┘
          ▼
┌─────────────────────┐      ┌───────────────────┐     ┌─────────────────┐
│ Quiz Completion     │───►  │ Results Summary   │───► │Recommendations  │
└─────────────────────┘      └───────────────────┘     └─────────────────┘


┌───────────────────────────────────────────────────────────────────────┐
│                      PRACTICE EXERCISE USER FLOW                      │
└───────────────────────────────────────────────────────────────────────┘
          │
          ▼
┌───────────────────┐      ┌─────────────────────┐     ┌─────────────────┐
│ Practice Selection│───►  │  Settings/Options   │───► │ Practice Session│
└───────────────────┘      └─────────────────────┘     └─────────────────┘
                                                              │
          ┌───────────────────────────────────────────────────┘
          ▼
┌─────────────────────┐      ┌───────────────────┐     ┌─────────────────┐
│Self-Assessment      │───►  │Progress Update    │───► │Session Summary  │
└─────────────────────┘      └───────────────────┘     └─────────────────┘
                                                              │
          ┌───────────────────────────────────────────────────┘
          ▼
┌─────────────────────┐      ┌───────────────────┐     
│ Recommendations     │───►  │   Next Steps      │     
└─────────────────────┘      └───────────────────┘     
```

## Detailed Screen-to-Screen Navigation

### First-Time User Flow
1. **Splash Screen** → **Language Selection** → **Skill Assessment** → **Learning Preferences** → **Account Setup (Optional)** → **Home Screen**

### Returning User Flow
1. **Splash Screen** → **Home Screen**

### Vocabulary Learning Flow
1. **Home Screen** → **Word List** → **Word Detail** → **Practice Word** / **Add to Favorites**
2. **Word List** → **Search/Filter** → **Word Detail**
3. **Home Screen** → **Add Word** → **Word List**

### Lesson Learning Flow
1. **Home Screen** / **Lessons Tab** → **Lesson List** → **Lesson Detail** → **Lesson Content**
2. **Lesson Content** → **Lesson Quiz** → **Quiz Results** → **Next Lesson Recommendation**

### Practice Flow
1. **Home Screen** / **Practice Tab** → **Practice Type Selection**
2. **Practice Type Selection** → **Flashcards** / **Quizzes** / **Games** / **Listening** / **Speaking**
3. **Practice Session** → **Review/Results** → **Progress Update** → **Home Screen** / **New Practice**

### Profile & Progress Flow
1. **Home Screen** / **Profile Tab** → **Progress Statistics** / **Achievements** / **Settings** / **Account**
2. **Progress Statistics** → **Learning History** → **Detailed Stats**
3. **Achievements** → **Achievement Details** → **Related Learning Content**
4. **Settings** → **App Language** / **Notification Preferences** / **Theme Settings**

### Language Switching Flow
1. **Any Screen** → **Language Toggle** → **Switch Interface Language**
2. **Settings** → **App Language** → **English Primary** / **Hindi Primary**

### Quiz Flow
1. **Quiz Selection** → **Quiz Introduction** → **Question Screen**
2. **Question Screen** → **Answer Submission** → **Feedback Display**
3. **Final Question** → **Quiz Completion** → **Results Summary** → **Recommendations**

### Practice Exercise Flow
1. **Practice Selection** → **Settings/Options** → **Practice Session**
2. **Practice Item** → **Self-Assessment** → **Next Item**
3. **Session Completion** → **Progress Update** → **Session Summary** → **Recommendations**