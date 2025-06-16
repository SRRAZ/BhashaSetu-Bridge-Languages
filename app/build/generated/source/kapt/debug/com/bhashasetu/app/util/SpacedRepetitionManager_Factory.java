package com.bhashasetu.app.util;

import com.bhashasetu.app.data.repository.UserProgressRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class SpacedRepetitionManager_Factory implements Factory<SpacedRepetitionManager> {
  private final Provider<UserProgressRepository> userProgressRepositoryProvider;

  public SpacedRepetitionManager_Factory(
      Provider<UserProgressRepository> userProgressRepositoryProvider) {
    this.userProgressRepositoryProvider = userProgressRepositoryProvider;
  }

  @Override
  public SpacedRepetitionManager get() {
    return newInstance(userProgressRepositoryProvider.get());
  }

  public static SpacedRepetitionManager_Factory create(
      Provider<UserProgressRepository> userProgressRepositoryProvider) {
    return new SpacedRepetitionManager_Factory(userProgressRepositoryProvider);
  }

  public static SpacedRepetitionManager newInstance(UserProgressRepository userProgressRepository) {
    return new SpacedRepetitionManager(userProgressRepository);
  }
}
