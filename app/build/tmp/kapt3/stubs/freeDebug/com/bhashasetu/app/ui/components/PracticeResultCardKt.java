package com.bhashasetu.app.ui.components;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u001aH\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0007\u001a\b\u0010\r\u001a\u00020\u0001H\u0007\u001a0\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\bH\u0003\u00a8\u0006\u0014"}, d2 = {"PracticeResultCard", "", "correctCount", "", "totalCount", "streakDays", "pointsEarned", "currentLanguage", "", "onContinue", "Lkotlin/Function0;", "modifier", "Landroidx/compose/ui/Modifier;", "PracticeResultCardPreview", "StatItem", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "value", "labelEn", "labelHi", "app_freeDebug"})
public final class PracticeResultCardKt {
    
    /**
     * A card component for displaying practice session results
     *
     * @param correctCount Number of correct answers
     * @param totalCount Total number of questions
     * @param streakDays Current streak days
     * @param pointsEarned Points earned in this session
     * @param currentLanguage The current app language setting ("en" or "hi")
     * @param onContinue Callback when the continue button is clicked
     * @param modifier Optional modifier for the component
     */
    @androidx.compose.runtime.Composable()
    public static final void PracticeResultCard(int correctCount, int totalCount, int streakDays, int pointsEarned, @org.jetbrains.annotations.NotNull()
    java.lang.String currentLanguage, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onContinue, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * A single stat item with icon, value, and label
     */
    @androidx.compose.runtime.Composable()
    private static final void StatItem(androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String value, java.lang.String labelEn, java.lang.String labelHi, java.lang.String currentLanguage) {
    }
    
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    @androidx.compose.runtime.Composable()
    public static final void PracticeResultCardPreview() {
    }
}