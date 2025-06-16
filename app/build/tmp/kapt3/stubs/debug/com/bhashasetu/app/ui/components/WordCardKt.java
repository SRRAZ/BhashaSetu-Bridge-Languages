package com.bhashasetu.app.ui.components;

@kotlin.Metadata(mv = {2, 1, 0}, k = 2, xi = 48, d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aV\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\rH\u0007\u001a\b\u0010\u000e\u001a\u00020\u0001H\u0007\u00a8\u0006\u000f"}, d2 = {"WordCard", "", "word", "Lcom/bhashasetu/app/data/model/Word;", "currentLanguage", "", "onFavoriteClick", "Lkotlin/Function0;", "onPlayPronunciationClick", "onClick", "showDetails", "", "modifier", "Landroidx/compose/ui/Modifier;", "WordCardPreview", "app_debug"})
public final class WordCardKt {
    
    /**
     * A card component for displaying a vocabulary word in both English and Hindi.
     *
     * @param word The Word object to display
     * @param currentLanguage The currently selected language code ("en" or "hi")
     * @param onFavoriteClick Callback when the favorite button is clicked
     * @param onPlayPronunciationClick Callback when the pronunciation button is clicked
     * @param onClick Callback when the card is clicked
     * @param showDetails Whether to show detailed information about the word
     * @param modifier Optional modifier for styling
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void WordCard(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, @org.jetbrains.annotations.NotNull()
    java.lang.String currentLanguage, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onFavoriteClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPlayPronunciationClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, boolean showDetails, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    @androidx.compose.runtime.Composable()
    public static final void WordCardPreview() {
    }
}