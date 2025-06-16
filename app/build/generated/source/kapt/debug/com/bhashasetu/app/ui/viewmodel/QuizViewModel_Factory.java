package com.bhashasetu.app.ui.viewmodel;

import com.bhashasetu.app.data.repository.QuizRepository;
import com.bhashasetu.app.data.repository.UserProgressRepository;
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
public final class QuizViewModel_Factory implements Factory<QuizViewModel> {
  private final Provider<QuizRepository> quizRepositoryProvider;

  private final Provider<UserProgressRepository> userProgressRepositoryProvider;

  public QuizViewModel_Factory(Provider<QuizRepository> quizRepositoryProvider,
      Provider<UserProgressRepository> userProgressRepositoryProvider) {
    this.quizRepositoryProvider = quizRepositoryProvider;
    this.userProgressRepositoryProvider = userProgressRepositoryProvider;
  }

  @Override
  public QuizViewModel get() {
    return newInstance(quizRepositoryProvider.get(), userProgressRepositoryProvider.get());
  }

  public static QuizViewModel_Factory create(Provider<QuizRepository> quizRepositoryProvider,
      Provider<UserProgressRepository> userProgressRepositoryProvider) {
    return new QuizViewModel_Factory(quizRepositoryProvider, userProgressRepositoryProvider);
  }

  public static QuizViewModel newInstance(QuizRepository quizRepository,
      UserProgressRepository userProgressRepository) {
    return new QuizViewModel(quizRepository, userProgressRepository);
  }
}
