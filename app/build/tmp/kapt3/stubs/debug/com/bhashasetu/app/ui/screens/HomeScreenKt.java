package com.bhashasetu.app.ui.screens;

@kotlin.Metadata(mv = {2, 1, 0}, k = 2, xi = 48, d1 = {"\u0000B\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u001aB\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001aO\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u0007\u00a2\u0006\u0004\b\u0014\u0010\u0015\u001a&\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a&\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u00a8\u0006\u001c"}, d2 = {"HomeScreen", "", "wordViewModel", "Lcom/bhashasetu/app/ui/viewmodel/WordViewModel;", "onNavigateToLessons", "Lkotlin/Function0;", "onNavigateToPractice", "onNavigateToWordList", "ActionButton", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "title", "", "subtitle", "containerColor", "Landroidx/compose/ui/graphics/Color;", "iconTint", "onClick", "modifier", "Landroidx/compose/ui/Modifier;", "ActionButton-ZkgLGzA", "(Landroidx/compose/ui/graphics/vector/ImageVector;Ljava/lang/String;Ljava/lang/String;JJLkotlin/jvm/functions/Function0;Landroidx/compose/ui/Modifier;)V", "PracticeWordCard", "word", "Lcom/bhashasetu/app/data/model/Word;", "isHindiFirst", "", "RecentWordCard", "app_debug"})
public final class HomeScreenKt {
    
    /**
     * Home screen component
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void HomeScreen(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.ui.viewmodel.WordViewModel wordViewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToLessons, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToPractice, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToWordList) {
    }
    
    /**
     * Card component for practice word recommendations
     */
    @androidx.compose.runtime.Composable()
    public static final void PracticeWordCard(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, boolean isHindiFirst, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    /**
     * Card component for recently added words
     */
    @androidx.compose.runtime.Composable()
    public static final void RecentWordCard(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, boolean isHindiFirst, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}