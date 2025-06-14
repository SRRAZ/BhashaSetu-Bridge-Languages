package com.bhashasetu.app.ui.components;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001al\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00052\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00052\u001a\b\u0002\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0007\u001a\b\u0010\r\u001a\u00020\u0001H\u0007\u00a8\u0006\u000e"}, d2 = {"WordCard", "", "word", "Lcom/bhashasetu/app/data/model/Word;", "onCardClick", "Lkotlin/Function1;", "onFavoriteClick", "onPlayPronunciationClick", "Lkotlin/Function2;", "", "isHindiFirst", "modifier", "Landroidx/compose/ui/Modifier;", "WordCardPreview", "app_freeDebug"})
public final class WordCardComposeKt {
    
    /**
     * WordCard component in Jetpack Compose
     * Displays a word with English and Hindi translations
     */
    @androidx.compose.runtime.Composable()
    public static final void WordCard(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.bhashasetu.app.data.model.Word, kotlin.Unit> onCardClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.bhashasetu.app.data.model.Word, kotlin.Unit> onFavoriteClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.bhashasetu.app.data.model.Word, ? super java.lang.Boolean, kotlin.Unit> onPlayPronunciationClick, boolean isHindiFirst, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    @androidx.compose.runtime.Composable()
    public static final void WordCardPreview() {
    }
}