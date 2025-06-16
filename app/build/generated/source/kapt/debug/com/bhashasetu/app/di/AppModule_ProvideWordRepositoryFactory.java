package com.bhashasetu.app.di;

import com.bhashasetu.app.data.db.AppDatabase;
import com.bhashasetu.app.data.repository.WordRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideWordRepositoryFactory implements Factory<WordRepository> {
  private final Provider<AppDatabase> databaseProvider;

  public AppModule_ProvideWordRepositoryFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public WordRepository get() {
    return provideWordRepository(databaseProvider.get());
  }

  public static AppModule_ProvideWordRepositoryFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new AppModule_ProvideWordRepositoryFactory(databaseProvider);
  }

  public static WordRepository provideWordRepository(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideWordRepository(database));
  }
}
