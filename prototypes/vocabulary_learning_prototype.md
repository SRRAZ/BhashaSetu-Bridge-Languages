# Vocabulary Learning Prototype Specification

## Overview

This document specifies the interactive prototype for the vocabulary learning flow of the English-Hindi Learning Application. The vocabulary learning flow is a core experience that allows users to browse, search, learn, and practice new words in both English and Hindi.

## Screens and States

### 1. Word List Screen

**Default State:**
- Header with title "Words" / "शब्द"
- Search bar
- Category filter tabs
- Sorted word list (alphabetical by default)
- Floating action button to add new words
- Bottom navigation

**Search Active State:**
- Expanded search bar with keyboard active
- Real-time filtered results
- Clear search button
- Recently searched terms (optional)

**Filter Applied State:**
- Selected category tab highlighted
- Filtered word list showing only relevant words
- "Clear filters" option
- Count of displayed words

**Empty State:**
- Illustration indicating no words
- Helpful message: "No words found" / "कोई शब्द नहीं मिला"
- Suggestion to add words or adjust filters

### 2. Word Detail Screen

**Default View State:**
- Word in large typography
- Pronunciation guide
- Language toggle (English/Hindi primary)
- Audio pronunciation button
- Example sentences
- Related words
- Practice options

**English Primary State:**
- English word prominently displayed
- Hindi translation below
- English example sentences with Hindi translations
- English UI elements

**Hindi Primary State:**
- Hindi word prominently displayed
- English translation below
- Hindi example sentences with English translations
- Hindi UI elements

**Edit Mode State:**
- Editable fields for word properties
- Keyboard active for selected field
- Save/cancel options
- Validation feedback

### 3. Add Word Screen

**Empty Form State:**
- Input fields for English and Hindi words
- Pronunciation guide fields
- Category selector
- Difficulty level selection
- Example sentence fields
- Save button (disabled until required fields filled)

**Form Filling State:**
- Validation feedback for each field
- Auto-suggestions for categories
- Real-time character count for fields
- Keyboard active for current field

**Confirmation State:**
- Success message
- Preview of added word
- Options to add another or return to list

### 4. Word Categories Screen

**Default State:**
- Grid of category cards
- Category name in both languages
- Word count per category
- Add category button

**Edit Mode State:**
- Selection checkboxes for categories
- Delete/Edit options
- Drag handles for reordering

**Add Category State:**
- Input field for category name (both languages)
- Color selector
- Icon selector
- Create button

## Interactions and Transitions

### Navigation Interactions

1. **List to Detail Transition:**
   - Tap on word card → Expand card → Transform to detail screen
   - Animation: Card expansion with fade-in of additional details

2. **Detail to List Transition:**
   - Tap back button → Collapse detail → Return to list position
   - Animation: Reverse of entry animation

3. **Add Word Entry:**
   - Tap FAB → Modal slide up from bottom
   - Animation: Modal sheet slides up with backdrop fade

4. **Add Word Exit:**
   - Tap save → Success confirmation → Fade to list with new word highlighted
   - Tap cancel → Confirmation dialog → Dismiss modal
   - Animation: Modal slide down with backdrop fade

### Feature Interactions

1. **Word Card Interaction:**
   - Tap: Open detail view
   - Long press: Show quick actions (favorite, practice, delete)
   - Swipe right: Mark as favorite
   - Swipe left: Archive/Hide

2. **Audio Pronunciation:**
   - Tap speaker icon → Visual audio wave animation → Play audio
   - Animation: Speaker pulse, audio waveform visualization

3. **Language Toggle:**
   - Tap language switch → Switch primary/secondary language display
   - Animation: Flip transition between languages

4. **Category Filter:**
   - Tap category tab → Filter animation → Updated list
   - Animation: Content cross-fade with subtle reordering

5. **Search Interaction:**
   - Tap search → Expand search bar → Keyboard appears
   - Type query → Real-time filtering of results
   - Animation: Results filtering with fade transitions

### Micro-interactions

1. **Favorite Toggling:**
   - Tap star icon → Fill animation → Confirmation feedback
   - Animation: Star fill effect with subtle bounce

2. **Difficulty Level Selection:**
   - Tap difficulty option → Selection highlight → Update difficulty indicator
   - Animation: Selection highlight with color transition

3. **Form Field Focus:**
   - Tap input field → Field expansion → Keyboard appears
   - Animation: Field highlight with subtle expansion

4. **Validation Feedback:**
   - Error state: Shake animation + red highlight + error message
   - Success state: Green check mark animation + success message

5. **Progress Updates:**
   - Word learned: Progress bar increment animation
   - New milestone: Celebration animation with confetti

## Language Switching Mechanism

1. **Global Language Toggle:**
   - Accessible from app header
   - Affects all UI text and primary/secondary language display
   - Remembers preference across sessions

2. **Content-Specific Toggle:**
   - Word detail view includes toggle to switch primary displayed language
   - Affects only content display, not UI elements

3. **Visual Indicators:**
   - Small flag or language code (EN/HI) indicates current mode
   - Color-coding helps distinguish language modes

4. **Transition Animation:**
   - Smooth cross-fade between language modes
   - Subtle layout adjustments to accommodate text length differences

## Hindi Interface Elements

1. **Typography Considerations:**
   - Hindi text uses Noto Sans Devanagari font
   - Minimum 18px size for Hindi text
   - 1.5× line height for proper rendering

2. **Text Container Adaptations:**
   - Flexible containers to accommodate longer Hindi text
   - Ellipsis handling for overflow
   - Dynamic height adjustment

3. **Button and Label Adaptations:**
   - Wider buttons for Hindi labels
   - Properly aligned Devanagari text
   - Consistent vertical centering

4. **Input Method:**
   - Hindi keyboard layout option
   - Transliteration support (Roman to Devanagari)
   - Voice input option for Hindi words

## User Flow Sequences

### 1. Browse and Learn New Word

```
Word List Screen → Filter by Category → Select Word → 
Word Detail Screen → Listen to Pronunciation → 
View Example Sentences → Mark as Favorite → 
Return to Word List
```

### 2. Add New Vocabulary

```
Word List Screen → Tap Add Word FAB → 
Add Word Form → Enter English Word → 
Enter Hindi Translation → Add Pronunciation → 
Select Category → Add Example Sentence → 
Save Word → Success Confirmation → 
Return to Word List (with new word highlighted)
```

### 3. Search and Edit Word

```
Word List Screen → Tap Search → Enter Search Term → 
View Filtered Results → Select Word → 
Word Detail Screen → Tap Edit → 
Modify Fields → Save Changes → 
Success Confirmation → Updated Word Detail View
```

### 4. Practice Vocabulary

```
Word List Screen → Select Word → 
Word Detail Screen → Tap Practice Button → 
Practice Type Selection → Complete Practice → 
Results Screen → Return to Word Detail → 
Updated Mastery Status
```

## Interactive Elements Specifications

### Word Card Component

**States:**
- Default: Shows English and Hindi words, category indicator
- Selected: Highlighted background, elevated shadow
- Favorite: Star icon filled
- Mastered: Proficiency indicator filled

**Properties:**
- Height: 72dp
- Padding: 16dp
- Corner Radius: 8dp
- Animation: Subtle scale on press (1.02×)

### Language Toggle Component

**States:**
- English Primary: "EN" tab selected
- Hindi Primary: "HI" tab selected

**Properties:**
- Size: 80×36dp
- Animation: 300ms slide transition
- Haptic feedback on toggle

### Audio Button Component

**States:**
- Default: Speaker icon
- Pressed: Animated speaker waves
- Playing: Audio waveform animation
- Completed: Return to default with check mark

**Properties:**
- Size: 48×48dp
- Touch target: 48×48dp minimum
- Animation: 2-second audio wave animation

### Search Component

**States:**
- Collapsed: Search icon only
- Expanded: Full-width search field
- Active: Keyboard visible, clear button present
- Results: Shows result count, filtering animation

**Properties:**
- Collapsed width: 48dp
- Expanded width: 100% of available space
- Animation: 250ms expand/collapse

## Prototype Limitations and Considerations

1. **Audio Simulation:**
   - Prototype will simulate audio playback with visual feedback
   - Actual audio functionality will require implementation in the final app

2. **Keyboard Input:**
   - Prototype will show keyboard visuals but with limited typing simulation
   - Key user flows will have pre-populated content

3. **Database Integration:**
   - Word lists will be simulated with predefined content
   - Search and filter will work on this limited dataset

4. **Performance Considerations:**
   - Limit animations to essential interactions to maintain prototype performance
   - Simplify complex transitions for smoother prototype experience

## Testing Scenarios

1. **Vocabulary Browsing:**
   - User should be able to browse all words
   - Filter by different categories
   - Sort by different criteria
   - Search for specific words

2. **Word Details:**
   - User should access full word information
   - Play pronunciation (simulated)
   - Toggle between language modes
   - Access example sentences and related words

3. **Adding Content:**
   - User should complete the add word form
   - Receive appropriate validation feedback
   - Successfully add a new word
   - See the newly added word in the list

4. **Language Adaptation:**
   - Interface should adapt appropriately when switching languages
   - Hindi text should render properly
   - Layouts should accommodate both languages

## Next Steps After Prototyping

1. **User Testing:**
   - Conduct usability testing with bilingual users
   - Gather feedback on language switching mechanism
   - Evaluate effectiveness of vocabulary learning flow

2. **Refinement:**
   - Iterate on interactions based on testing feedback
   - Optimize transitions and animations
   - Refine Hindi interface elements

3. **Developer Handoff:**
   - Prepare detailed specifications for implementation
   - Create component documentation
   - Annotate key interactions for development team