package com.bhashasetu.app.ui.viewmodel;

import com.bhashasetu.app.data.repository.CategoryRepository;
import com.bhashasetu.app.data.repository.WordRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class WordViewModel_Factory implements Factory<WordViewModel> {
  private final Provider<WordRepository> wordRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  public WordViewModel_Factory(Provider<WordRepository> wordRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider) {
    this.wordRepositoryProvider = wordRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
  }

  @Override
  public WordViewModel get() {
    return newInstance(wordRepositoryProvider.get(), categoryRepositoryProvider.get());
  }

  public static WordViewModel_Factory create(Provider<WordRepository> wordRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider) {
    return new WordViewModel_Factory(wordRepositoryProvider, categoryRepositoryProvider);
  }

  public static WordViewModel newInstance(WordRepository wordRepository,
      CategoryRepository categoryRepository) {
    return new WordViewModel(wordRepository, categoryRepository);
  }
}
