package com.bhashasetu.app.ui.viewmodel;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0018J$\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u000f2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020!0\nH\u0086@\u00a2\u0006\u0002\u0010$J$\u0010%\u001a\u00020\u001e2\u0006\u0010\"\u001a\u00020\u000f2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020!0\nH\u0086@\u00a2\u0006\u0002\u0010$J\u000e\u0010&\u001a\u00020\u001e2\u0006\u0010\"\u001a\u00020\u000fJ\u0016\u0010\'\u001a\u00020\u001e2\u0006\u0010(\u001a\u00020!2\u0006\u0010)\u001a\u00020*J\u0014\u0010+\u001a\b\u0012\u0004\u0012\u00020\u000b0,2\u0006\u0010(\u001a\u00020!J\u001a\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\n0,2\u0006\u0010.\u001a\u00020!J\u001a\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\n0,2\u0006\u00100\u001a\u00020\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001d\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u001d\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\rR\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\rR\u001d\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\r\u00a8\u00061"}, d2 = {"Lcom/bhashasetu/app/ui/viewmodel/WordViewModel;", "Landroidx/lifecycle/ViewModel;", "wordRepository", "Lcom/bhashasetu/app/data/repository/WordRepository;", "categoryRepository", "Lcom/bhashasetu/app/data/repository/CategoryRepository;", "<init>", "(Lcom/bhashasetu/app/data/repository/WordRepository;Lcom/bhashasetu/app/data/repository/CategoryRepository;)V", "allWordsWithCategories", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/bhashasetu/app/data/relation/WordWithCategories;", "getAllWordsWithCategories", "()Lkotlinx/coroutines/flow/StateFlow;", "favoriteWords", "Lcom/bhashasetu/app/data/model/Word;", "getFavoriteWords", "recentWords", "getRecentWords", "wordCount", "", "getWordCount", "_searchQuery", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "searchQuery", "getSearchQuery", "searchResults", "getSearchResults", "setSearchQuery", "", "query", "addWord", "", "word", "categoryIds", "(Lcom/bhashasetu/app/data/model/Word;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateWord", "deleteWord", "toggleFavorite", "wordId", "isFavorite", "", "getWordById", "Lkotlinx/coroutines/flow/Flow;", "getWordsByCategoryId", "categoryId", "getRandomWords", "count", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class WordViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.repository.WordRepository wordRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.repository.CategoryRepository categoryRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bhashasetu.app.data.relation.WordWithCategories>> allWordsWithCategories = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bhashasetu.app.data.model.Word>> favoriteWords = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bhashasetu.app.data.model.Word>> recentWords = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> wordCount = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bhashasetu.app.data.model.Word>> searchResults = null;
    
    @javax.inject.Inject()
    public WordViewModel(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.repository.WordRepository wordRepository, @org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.repository.CategoryRepository categoryRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bhashasetu.app.data.relation.WordWithCategories>> getAllWordsWithCategories() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bhashasetu.app.data.model.Word>> getFavoriteWords() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bhashasetu.app.data.model.Word>> getRecentWords() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getWordCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSearchQuery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bhashasetu.app.data.model.Word>> getSearchResults() {
        return null;
    }
    
    /**
     * Updates the search query
     * @param query The search query string
     */
    public final void setSearchQuery(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    /**
     * Adds a new word to the database
     * @param word The word to add
     * @param categoryIds List of category IDs to associate with the word
     * @return The ID of the newly added word
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addWord(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Long> categoryIds, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    /**
     * Updates an existing word
     * @param word The word with updated values
     * @param categoryIds List of category IDs to associate with the word
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateWord(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Long> categoryIds, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Deletes a word
     * @param word The word to delete
     */
    public final void deleteWord(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word) {
    }
    
    /**
     * Toggles the favorite status of a word
     * @param wordId The ID of the word
     * @param isFavorite The new favorite status
     */
    public final void toggleFavorite(long wordId, boolean isFavorite) {
    }
    
    /**
     * Gets a specific word by ID with its categories
     * @param wordId The ID of the word to retrieve
     * @return Flow of WordWithCategories for the specified word
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.relation.WordWithCategories> getWordById(long wordId) {
        return null;
    }
    
    /**
     * Gets words by category ID
     * @param categoryId The ID of the category
     * @return Flow of Word list for the specified category
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getWordsByCategoryId(long categoryId) {
        return null;
    }
    
    /**
     * Gets random words for practice
     * @param count The number of words to retrieve
     * @return Flow of random Word list
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getRandomWords(int count) {
        return null;
    }
}