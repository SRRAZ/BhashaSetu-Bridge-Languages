package com.bhashasetu.app.ui.components;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a8\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0003\u001aF\u0010\f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00072\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007\u001a\b\u0010\u000f\u001a\u00020\u0001H\u0007\u00a8\u0006\u0010"}, d2 = {"CardFace", "", "word", "Lcom/bhashasetu/app/data/model/Word;", "currentLanguage", "", "isFront", "", "onPlayPronunciation", "Lkotlin/Function0;", "modifier", "Landroidx/compose/ui/Modifier;", "FlashcardView", "isFlipped", "onFlip", "FlashcardViewPreview", "app_freeDebug"})
public final class FlashcardViewKt {
    
    /**
     * A flashcard view for displaying and interacting with vocabulary words
     *
     * @param word The word to display
     * @param currentLanguage The current app language setting ("en" or "hi")
     * @param isFlipped Whether the card is showing the front (false) or back (true)
     * @param onFlip Callback when the card is flipped
     * @param onPlayPronunciation Callback when the pronunciation button is clicked
     * @param modifier Optional modifier for the component
     */
    @androidx.compose.runtime.Composable()
    public static final void FlashcardView(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, @org.jetbrains.annotations.NotNull()
    java.lang.String currentLanguage, boolean isFlipped, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onFlip, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPlayPronunciation, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * A single face (front or back) of the flashcard
     */
    @androidx.compose.runtime.Composable()
    private static final void CardFace(com.bhashasetu.app.data.model.Word word, java.lang.String currentLanguage, boolean isFront, kotlin.jvm.functions.Function0<kotlin.Unit> onPlayPronunciation, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    @androidx.compose.runtime.Composable()
    public static final void FlashcardViewPreview() {
    }
}