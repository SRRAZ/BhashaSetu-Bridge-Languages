package com.bhashasetu.app.util;

import com.bhashasetu.app.data.repository.AchievementRepository;
import com.bhashasetu.app.data.repository.AppSettingsRepository;
import com.bhashasetu.app.data.repository.CategoryRepository;
import com.bhashasetu.app.data.repository.DailyStreakRepository;
import com.bhashasetu.app.data.repository.LessonRepository;
import com.bhashasetu.app.data.repository.QuizRepository;
import com.bhashasetu.app.data.repository.UserProgressRepository;
import com.bhashasetu.app.data.repository.WordRepository;
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
public final class DataInitializer_Factory implements Factory<DataInitializer> {
  private final Provider<WordRepository> wordRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<LessonRepository> lessonRepositoryProvider;

  private final Provider<QuizRepository> quizRepositoryProvider;

  private final Provider<AchievementRepository> achievementRepositoryProvider;

  private final Provider<UserProgressRepository> userProgressRepositoryProvider;

  private final Provider<AppSettingsRepository> appSettingsRepositoryProvider;

  private final Provider<DailyStreakRepository> dailyStreakRepositoryProvider;

  public DataInitializer_Factory(Provider<WordRepository> wordRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<LessonRepository> lessonRepositoryProvider,
      Provider<QuizRepository> quizRepositoryProvider,
      Provider<AchievementRepository> achievementRepositoryProvider,
      Provider<UserProgressRepository> userProgressRepositoryProvider,
      Provider<AppSettingsRepository> appSettingsRepositoryProvider,
      Provider<DailyStreakRepository> dailyStreakRepositoryProvider) {
    this.wordRepositoryProvider = wordRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.lessonRepositoryProvider = lessonRepositoryProvider;
    this.quizRepositoryProvider = quizRepositoryProvider;
    this.achievementRepositoryProvider = achievementRepositoryProvider;
    this.userProgressRepositoryProvider = userProgressRepositoryProvider;
    this.appSettingsRepositoryProvider = appSettingsRepositoryProvider;
    this.dailyStreakRepositoryProvider = dailyStreakRepositoryProvider;
  }

  @Override
  public DataInitializer get() {
    return newInstance(wordRepositoryProvider.get(), categoryRepositoryProvider.get(), lessonRepositoryProvider.get(), quizRepositoryProvider.get(), achievementRepositoryProvider.get(), userProgressRepositoryProvider.get(), appSettingsRepositoryProvider.get(), dailyStreakRepositoryProvider.get());
  }

  public static DataInitializer_Factory create(Provider<WordRepository> wordRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<LessonRepository> lessonRepositoryProvider,
      Provider<QuizRepository> quizRepositoryProvider,
      Provider<AchievementRepository> achievementRepositoryProvider,
      Provider<UserProgressRepository> userProgressRepositoryProvider,
      Provider<AppSettingsRepository> appSettingsRepositoryProvider,
      Provider<DailyStreakRepository> dailyStreakRepositoryProvider) {
    return new DataInitializer_Factory(wordRepositoryProvider, categoryRepositoryProvider, lessonRepositoryProvider, quizRepositoryProvider, achievementRepositoryProvider, userProgressRepositoryProvider, appSettingsRepositoryProvider, dailyStreakRepositoryProvider);
  }

  public static DataInitializer newInstance(WordRepository wordRepository,
      CategoryRepository categoryRepository, LessonRepository lessonRepository,
      QuizRepository quizRepository, AchievementRepository achievementRepository,
      UserProgressRepository userProgressRepository, AppSettingsRepository appSettingsRepository,
      DailyStreakRepository dailyStreakRepository) {
    return new DataInitializer(wordRepository, categoryRepository, lessonRepository, quizRepository, achievementRepository, userProgressRepository, appSettingsRepository, dailyStreakRepository);
  }
}
