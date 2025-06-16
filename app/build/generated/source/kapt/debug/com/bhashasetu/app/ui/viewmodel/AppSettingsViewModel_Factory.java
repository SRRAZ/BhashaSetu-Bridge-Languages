package com.bhashasetu.app.ui.viewmodel;

import com.bhashasetu.app.data.repository.AppSettingsRepository;
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
public final class AppSettingsViewModel_Factory implements Factory<AppSettingsViewModel> {
  private final Provider<AppSettingsRepository> appSettingsRepositoryProvider;

  public AppSettingsViewModel_Factory(
      Provider<AppSettingsRepository> appSettingsRepositoryProvider) {
    this.appSettingsRepositoryProvider = appSettingsRepositoryProvider;
  }

  @Override
  public AppSettingsViewModel get() {
    return newInstance(appSettingsRepositoryProvider.get());
  }

  public static AppSettingsViewModel_Factory create(
      Provider<AppSettingsRepository> appSettingsRepositoryProvider) {
    return new AppSettingsViewModel_Factory(appSettingsRepositoryProvider);
  }

  public static AppSettingsViewModel newInstance(AppSettingsRepository appSettingsRepository) {
    return new AppSettingsViewModel(appSettingsRepository);
  }
}
