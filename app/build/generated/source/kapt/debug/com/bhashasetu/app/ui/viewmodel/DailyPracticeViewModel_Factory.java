package com.bhashasetu.app.ui.viewmodel;

import com.bhashasetu.app.data.repository.AppSettingsRepository;
import com.bhashasetu.app.data.repository.WordRepository;
import com.bhashasetu.app.util.SpacedRepetitionManager;
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
public final class DailyPracticeViewModel_Factory implements Factory<DailyPracticeViewModel> {
  private final Provider<WordRepository> wordRepositoryProvider;

  private final Provider<AppSettingsRepository> settingsRepositoryProvider;

  private final Provider<SpacedRepetitionManager> spacedRepetitionManagerProvider;

  public DailyPracticeViewModel_Factory(Provider<WordRepository> wordRepositoryProvider,
      Provider<AppSettingsRepository> settingsRepositoryProvider,
      Provider<SpacedRepetitionManager> spacedRepetitionManagerProvider) {
    this.wordRepositoryProvider = wordRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.spacedRepetitionManagerProvider = spacedRepetitionManagerProvider;
  }

  @Override
  public DailyPracticeViewModel get() {
    return newInstance(wordRepositoryProvider.get(), settingsRepositoryProvider.get(), spacedRepetitionManagerProvider.get());
  }

  public static DailyPracticeViewModel_Factory create(
      Provider<WordRepository> wordRepositoryProvider,
      Provider<AppSettingsRepository> settingsRepositoryProvider,
      Provider<SpacedRepetitionManager> spacedRepetitionManagerProvider) {
    return new DailyPracticeViewModel_Factory(wordRepositoryProvider, settingsRepositoryProvider, spacedRepetitionManagerProvider);
  }

  public static DailyPracticeViewModel newInstance(WordRepository wordRepository,
      AppSettingsRepository settingsRepository, SpacedRepetitionManager spacedRepetitionManager) {
    return new DailyPracticeViewModel(wordRepository, settingsRepository, spacedRepetitionManager);
  }
}
