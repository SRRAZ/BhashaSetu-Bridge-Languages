# English-Hindi Learning App UI Style Guide

## Brand Identity

### App Name
- English: **English-Hindi Learning**
- Hindi: **अंग्रेजी-हिंदी शिक्षा**

### Logo
The app logo combines elements from both languages with a stylized combination of the English letter "E" and Hindi letter "ह" to represent the bilingual nature of the app.

### Tagline
- English: "Learn English through Hindi"
- Hindi: "हिंदी के माध्यम से अंग्रेजी सीखें"

## Color Palette

### Primary Colors
- **Primary Blue**: #6750A4
  - Used for app bars, primary buttons, and active states
  - Dark variant: #4F378B
  - Light variant: #D0BCFF

- **Secondary Gold**: #FFC107
  - Used for highlights, accents, and important elements
  - Dark variant: #FF8F00
  - Light variant: #FFE082

### Neutrals
- **Background White**: #FFFFFF
  - Primary background color
- **Card Background**: #F5F7FA
  - Card and component backgrounds
- **Dark Gray**: #212121
  - Primary text color
- **Medium Gray**: #757575
  - Secondary text color
- **Light Gray**: #EEEEEE
  - Dividers and subtle UI elements

### Semantic Colors
- **Success Green**: #4CAF50
  - Used for correct answers and completed items
- **Error Red**: #F44336
  - Used for incorrect answers and alerts
- **Warning Amber**: #FFC107
  - Used for notifications and cautions
- **Info Blue**: #2196F3
  - Used for tips and informational content

## Typography

### Font Families
- **English Text**: Roboto
- **Hindi Text**: Noto Sans Devanagari
- **System Fallbacks**: Sans-serif

### Type Scale
- **App Title**: 24sp, Bold
- **Screen Titles**: 20sp, Medium
- **Section Headers**: 18sp, Medium
- **Primary Content**: 16sp, Regular
- **Secondary Content**: 14sp, Regular
- **Captions/Small Text**: 12sp, Light

### Style Specifications
- **Line Height Multiplier**:
  - For English: 1.2
  - For Hindi: 1.4 (to accommodate Devanagari's height)
- **Letter Spacing**:
  - Headers: -0.01em
  - Body text: 0.01em
- **Font Weight Usage**:
  - Bold (700): App title, emphasized text
  - Medium (500): Headers, buttons, important content
  - Regular (400): Primary body text
  - Light (300): Secondary information, captions

## Component Library

### Buttons

#### Primary Button
- Background: Primary Blue (#6750A4)
- Text: White (#FFFFFF)
- Height: 48dp
- Corner Radius: 24dp
- Text Size: 16sp
- Padding: 16dp horizontal, 12dp vertical
- Elevation: 2dp (resting state)

```xml
<Button
    android:layout_width="match_parent"
    android:layout_height="48dp"
    android:background="@drawable/button_primary"
    android:textColor="#FFFFFF"
    android:textSize="16sp"
    app:cornerRadius="24dp"
    android:elevation="2dp"
    android:paddingStart="16dp"
    android:paddingEnd="16dp"
    android:text="@string/button_text" />
```

#### Secondary Button
- Border: 1dp Primary Blue (#6750A4)
- Background: Transparent
- Text: Primary Blue (#6750A4)
- Height: 48dp
- Corner Radius: 24dp
- Text Size: 16sp
- Padding: 16dp horizontal, 12dp vertical

#### Text Button
- Background: Transparent
- Text: Primary Blue (#6750A4)
- Height: 36dp
- Text Size: 14sp
- Padding: 8dp horizontal, 4dp vertical

### Cards

#### Standard Card
- Background: White (#FFFFFF)
- Corner Radius: 12dp
- Elevation: 2dp
- Padding: 16dp
- Margin: 8dp

```xml
<com.google.android.material.card.MaterialCardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="8dp"
    app:cardCornerRadius="12dp"
    app:cardElevation="2dp"
    app:contentPadding="16dp">
    
    <!-- Card content here -->
    
</com.google.android.material.card.MaterialCardView>
```

#### Word Card
- Background: White (#FFFFFF)
- Accent Band: 4dp Primary Blue top border
- Corner Radius: 12dp
- Elevation: 2dp
- Division: Upper section for English, lower for Hindi

#### Lesson Card
- Background: White (#FFFFFF)
- Corner Radius: 12dp
- Elevation: 2dp
- Featured Image: Top portion
- Progress Indicator: Bottom of card
- Level Badge: Top-right corner

### Text Fields

#### Standard Text Field
- Background: Light Gray (#EEEEEE)
- Text: Dark Gray (#212121)
- Hint Text: Medium Gray (#757575)
- Border: None (flat)
- Corner Radius: 8dp
- Height: 56dp
- Padding: 16dp

```xml
<com.google.android.material.textfield.TextInputLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="@string/hint_text"
    app:boxBackgroundColor="#EEEEEE"
    app:boxCornerRadiusBottomEnd="8dp"
    app:boxCornerRadiusBottomStart="8dp"
    app:boxCornerRadiusTopEnd="8dp"
    app:boxCornerRadiusTopStart="8dp"
    app:boxStrokeWidth="0dp">

    <com.google.android.material.textfield.TextInputEditText
        android:layout_width="match_parent"
        android:layout_height="56dp"
        android:padding="16dp"
        android:textColor="#212121"
        android:textColorHint="#757575" />
        
</com.google.android.material.textfield.TextInputLayout>
```

#### Search Field
- Background: Semi-transparent White (70% opacity)
- Icon: Search icon in Medium Gray
- Text: Dark Gray (#212121)
- Corner Radius: 24dp
- Height: 48dp

### Navigation

#### Bottom Navigation
- Background: White (#FFFFFF)
- Active Item: Primary Blue (#6750A4)
- Inactive Item: Medium Gray (#757575)
- Labels: Shown alongside icons
- Height: 56dp
- Elevation: 8dp

```xml
<com.google.android.material.bottomnavigation.BottomNavigationView
    android:id="@+id/bottom_navigation"
    android:layout_width="match_parent"
    android:layout_height="56dp"
    android:layout_gravity="bottom"
    android:background="#FFFFFF"
    android:elevation="8dp"
    app:itemIconTint="@color/bottom_nav_selector"
    app:itemTextColor="@color/bottom_nav_selector"
    app:menu="@menu/bottom_navigation_menu" />
```

#### Top App Bar
- Background: Primary Blue (#6750A4)
- Text: White (#FFFFFF)
- Icons: White (#FFFFFF)
- Height: 56dp
- Elevation: 4dp
- Title Text Size: 20sp

#### Tabs
- Background: Primary Blue (#6750A4)
- Selected Tab: White indicator line
- Unselected Tab: 50% opacity
- Height: 48dp
- Indicator Height: 2dp

### Progress Indicators

#### Linear Progress Bar
- Active Color: Primary Blue (#6750A4)
- Background Track: Light variant of Primary (#D0BCFF)
- Height: 4dp
- Corner Radius: 2dp

```xml
<com.google.android.material.progressindicator.LinearProgressIndicator
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:trackColor="#D0BCFF"
    app:indicatorColor="#6750A4"
    app:trackCornerRadius="2dp"
    app:trackThickness="4dp" />
```

#### Circular Progress
- Active Color: Primary Blue (#6750A4)
- Background Track: Light variant of Primary (#D0BCFF)
- Stroke Width: 4dp

#### Mastery Level Indicator
- Low Level (0-33%): #F44336 (Red)
- Medium Level (34-66%): #FFC107 (Amber)
- High Level (67-100%): #4CAF50 (Green)
- Background Track: Light Gray (#EEEEEE)
- Height: 8dp
- Corner Radius: 4dp

### Selection Controls

#### Checkbox
- Selected: Primary Blue (#6750A4)
- Unselected: Medium Gray (#757575)
- Size: 24dp x 24dp

```xml
<com.google.android.material.checkbox.MaterialCheckBox
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@string/checkbox_text"
    app:buttonTint="#6750A4" />
```

#### Radio Button
- Selected: Primary Blue (#6750A4)
- Unselected: Medium Gray (#757575)
- Size: 24dp x 24dp

#### Switch
- On State: Primary Blue (#6750A4)
- Off State: Medium Gray (#757575)
- Track Height: 14dp
- Thumb Diameter: 20dp

### List Items

#### Standard List Item
- Height: 72dp
- Leading Element: 24dp icon (optional)
- Text: Primary text 16sp, Secondary text 14sp
- Trailing Element: Icon or chevron (optional)
- Divider: 1dp Light Gray (#EEEEEE)

#### Word List Item
- Height: 88dp
- English Word: 16sp, Bold
- Hindi Translation: 16sp, Regular
- Pronunciation Guide: 14sp, Light Italic
- Category Tag: Small chip on bottom-left
- Favorite Button: Star icon on right

## Iconography

### Navigation Icons
- Home: Home icon
- Lessons: Book icon
- Practice: Refresh/cycle icon
- Profile: Person icon

### Functional Icons
- Search: Magnifying glass
- Settings: Gear icon
- Add: Plus icon in circle
- Favorite: Star outline/filled
- Listen: Sound wave or speaker
- Back: Left arrow
- Menu: Three dots vertical
- Filter: Funnel icon
- More: Chevron right
- Close: X icon

### Status Icons
- Correct: Checkmark in circle
- Incorrect: X in circle
- Warning: Exclamation in triangle
- Info: i in circle
- Locked: Padlock
- Completed: Checkmark badge

## Layout Specifications

### Spacing System
- **4dp**: Minimal spacing between related elements
- **8dp**: Standard spacing between elements
- **16dp**: Padding within containers, space between related groups
- **24dp**: Larger separation between distinct sections
- **32dp**: Major separations between content blocks
- **48dp+**: Screen margins and major layout divisions

### Component Spacing
- Card Margins: 8dp
- Internal Card Padding: 16dp
- List Item Padding: 16dp horizontal, 8dp vertical
- Section Headers: 16dp top, 8dp bottom
- Button Groups: 8dp between buttons

### Screen Layouts
- Content Container: 16dp margin from screen edges
- Section Dividers: 24dp vertical spacing
- Bottom Navigation Clearance: 16dp bottom margin for content

## Accessibility Guidelines

### Touch Targets
- Minimum Size: 48dp x 48dp
- Padding Between Targets: 8dp minimum

### Color Contrast
- Text on Background: Minimum ratio of 4.5:1
- Large Text on Background: Minimum ratio of 3:1
- UI Components: Minimum ratio of 3:1

### Text Sizes
- Minimum Body Text: 14sp
- Minimum for Interactive Elements: 16sp
- Adjustable Text Size Support: Scale from 0.8x to 1.3x

### Screen Reader Support
- Content Descriptions for all images
- Custom descriptions for complex UI elements
- Proper labeling of interactive elements
- Logical navigation order

## Language-Specific Considerations

### Hindi Text Rendering
- Font: Noto Sans Devanagari
- Line Height: 1.4x for better readability of Devanagari script
- Text Direction: Set to "locale" to support proper rendering
- Word Spacing: Slightly increased (0.05em)

```xml
<TextView
    android:id="@+id/hindiTextView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:fontFamily="@font/noto_sans_devanagari"
    android:textSize="16sp"
    android:lineSpacingMultiplier="1.4"
    android:letterSpacing="0.05"
    android:textDirection="locale"
    android:text="@string/hindi_text_example" />
```

### Bilingual Elements
- Primary Language First: Based on user preference
- Secondary Language: Slightly smaller (0.9x) and lighter color
- Visual Separation: Light divider or spacing between languages
- Toggle Controls: Language preference switcher in settings

### Script-Specific Considerations
- Devanagari Vertical Space: Extra padding for overhead line
- Character Spacing: Slightly increased for Hindi text
- Font Weight: Medium (500) minimum for Hindi to maintain legibility at small sizes

## Animation Guidelines

### Transitions
- Duration: 250ms standard, 300ms for complex transitions
- Easing: Fast out, slow in (standard Android curve)
- Page Transitions: Slide from right for forward navigation
- Card Expansions: Expand from center with elevation increase

### Interactive Feedback
- Button Press: 100ms scale down to 0.95
- Selection: 200ms color change with slight scale
- Error Shake: 300ms left-right with easing
- Success Indicator: 500ms pulse with color change

### Loading States
- Skeleton Screens: Used for content loading
- Progress Indicators: Linear for determinate operations
- Spinner: For indeterminate operations
- Pulse Animation: For waiting states (1200ms cycle)

## Dark Theme

### Dark Theme Colors
- Background: #121212
- Surface: #1E1E1E
- Primary: #D0BCFF (Light variant of primary)
- Secondary: #FFE082 (Light variant of secondary)
- On Background: #EEEEEE
- On Surface: #FFFFFF
- Error: #CF6679

### Dark Theme Adjustments
- Elevation: Add white overlay at 8% opacity per dp of elevation
- Contrast: Increase contrast for text elements
- Shadows: Reduce shadow intensity
- Dividers: Use lighter color (15% white) instead of dark

## Assets and Resources

### Asset Organization
- **drawable-mdpi**: Base density resources
- **drawable-hdpi, xhdpi, xxhdpi, xxxhdpi**: Density variants
- **drawable-night**: Dark theme specific resources
- **raw**: Audio files for pronunciations
- **font**: Custom font files including Noto Sans Devanagari

### Resource Naming
- **ic_[name]_[size]**: Icons (e.g., ic_favorite_24dp)
- **bg_[name]**: Backgrounds (e.g., bg_card)
- **color_[name]**: Color resources (e.g., color_primary)
- **shape_[name]**: Shape resources (e.g., shape_rounded_button)
- **anim_[name]**: Animation resources (e.g., anim_fade_in)

### String Resources
- **English**: Default strings.xml
- **Hindi**: strings-hi.xml with proper translations
- **Format**: snake_case naming (e.g., word_detail_title)
- **Organization**: Grouped by screen or feature