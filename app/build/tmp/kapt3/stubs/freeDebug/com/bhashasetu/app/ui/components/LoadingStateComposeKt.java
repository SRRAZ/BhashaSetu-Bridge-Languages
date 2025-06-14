package com.bhashasetu.app.ui.components;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000>\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\u001e\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0003\u001aR\u0010\u0007\u001a\u00020\u00012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\b\u0002\u0010\u0011\u001a\u00020\fH\u0007\u001a[\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u0015\u001a\u00020\u00032\b\b\u0002\u0010\u0016\u001a\u00020\u00032\b\b\u0002\u0010\u0017\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u0011\u0010\u0018\u001a\r\u0012\u0004\u0012\u00020\u00010\u0006\u00a2\u0006\u0002\b\u0019H\u0007\u001a\u0010\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u00a8\u0006\u001b"}, d2 = {"EmptyView", "", "message", "", "ErrorView", "onRetry", "Lkotlin/Function0;", "LoadingButton", "onClick", "modifier", "Landroidx/compose/ui/Modifier;", "isLoading", "", "text", "loadingText", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "enabled", "LoadingStateView", "state", "Lcom/bhashasetu/app/ui/components/LoadingState;", "loadingMessage", "errorMessage", "emptyMessage", "content", "Landroidx/compose/runtime/Composable;", "LoadingView", "app_freeDebug"})
public final class LoadingStateComposeKt {
    
    /**
     * Loading state component that shows different states with transitions:
     * - Loading: Shows a loading spinner with optional message
     * - Error: Shows an error message with a retry button
     * - Empty: Shows an empty state message
     * - Content: Shows the actual content
     */
    @androidx.compose.runtime.Composable()
    public static final void LoadingStateView(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.ui.components.LoadingState state, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    java.lang.String loadingMessage, @org.jetbrains.annotations.NotNull()
    java.lang.String errorMessage, @org.jetbrains.annotations.NotNull()
    java.lang.String emptyMessage, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRetry, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> content) {
    }
    
    /**
     * Loading view with spinner and message
     */
    @androidx.compose.runtime.Composable()
    private static final void LoadingView(java.lang.String message) {
    }
    
    /**
     * Error view with message and retry button
     */
    @androidx.compose.runtime.Composable()
    private static final void ErrorView(java.lang.String message, kotlin.jvm.functions.Function0<kotlin.Unit> onRetry) {
    }
    
    /**
     * Empty view with message
     */
    @androidx.compose.runtime.Composable()
    private static final void EmptyView(java.lang.String message) {
    }
    
    /**
     * Loading button that shows loading state and prevents multiple clicks
     */
    @androidx.compose.runtime.Composable()
    public static final void LoadingButton(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, boolean isLoading, @org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    java.lang.String loadingText, @org.jetbrains.annotations.Nullable()
    androidx.compose.ui.graphics.vector.ImageVector icon, boolean enabled) {
    }
}