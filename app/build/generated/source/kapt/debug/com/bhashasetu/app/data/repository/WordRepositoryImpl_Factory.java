package com.bhashasetu.app.data.repository;

import com.bhashasetu.app.data.dao.WordDao;
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
public final class WordRepositoryImpl_Factory implements Factory<WordRepositoryImpl> {
  private final Provider<WordDao> wordDaoProvider;

  public WordRepositoryImpl_Factory(Provider<WordDao> wordDaoProvider) {
    this.wordDaoProvider = wordDaoProvider;
  }

  @Override
  public WordRepositoryImpl get() {
    return newInstance(wordDaoProvider.get());
  }

  public static WordRepositoryImpl_Factory create(Provider<WordDao> wordDaoProvider) {
    return new WordRepositoryImpl_Factory(wordDaoProvider);
  }

  public static WordRepositoryImpl newInstance(WordDao wordDao) {
    return new WordRepositoryImpl(wordDao);
  }
}
