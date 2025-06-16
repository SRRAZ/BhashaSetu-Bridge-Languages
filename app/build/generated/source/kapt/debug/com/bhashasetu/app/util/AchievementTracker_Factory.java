package com.bhashasetu.app.util;

import com.bhashasetu.app.data.repository.AchievementRepository;
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
public final class AchievementTracker_Factory implements Factory<AchievementTracker> {
  private final Provider<AchievementRepository> achievementRepositoryProvider;

  private final Provider<WordRepository> wordRepositoryProvider;

  private final Provider<UserProgressRepository> userProgressRepositoryProvider;

  private final Provider<LessonRepository> lessonRepositoryProvider;

  private final Provider<QuizRepository> quizRepositoryProvider;

  private final Provider<DailyStreakRepository> dailyStreakRepositoryProvider;

  public AchievementTracker_Factory(Provider<AchievementRepository> achievementRepositoryProvider,
      Provider<WordRepository> wordRepositoryProvider,
      Provider<UserProgressRepository> userProgressRepositoryProvider,
      Provider<LessonRepository> lessonRepositoryProvider,
      Provider<QuizRepository> quizRepositoryProvider,
      Provider<DailyStreakRepository> dailyStreakRepositoryProvider) {
    this.achievementRepositoryProvider = achievementRepositoryProvider;
    this.wordRepositoryProvider = wordRepositoryProvider;
    this.userProgressRepositoryProvider = userProgressRepositoryProvider;
    this.lessonRepositoryProvider = lessonRepositoryProvider;
    this.quizRepositoryProvider = quizRepositoryProvider;
    this.dailyStreakRepositoryProvider = dailyStreakRepositoryProvider;
  }

  @Override
  public AchievementTracker get() {
    return newInstance(achievementRepositoryProvider.get(), wordRepositoryProvider.get(), userProgressRepositoryProvider.get(), lessonRepositoryProvider.get(), quizRepositoryProvider.get(), dailyStreakRepositoryProvider.get());
  }

  public static AchievementTracker_Factory create(
      Provider<AchievementRepository> achievementRepositoryProvider,
      Provider<WordRepository> wordRepositoryProvider,
      Provider<UserProgressRepository> userProgressRepositoryProvider,
      Provider<LessonRepository> lessonRepositoryProvider,
      Provider<QuizRepository> quizRepositoryProvider,
      Provider<DailyStreakRepository> dailyStreakRepositoryProvider) {
    return new AchievementTracker_Factory(achievementRepositoryProvider, wordRepositoryProvider, userProgressRepositoryProvider, lessonRepositoryProvider, quizRepositoryProvider, dailyStreakRepositoryProvider);
  }

  public static AchievementTracker newInstance(AchievementRepository achievementRepository,
      WordRepository wordRepository, UserProgressRepository userProgressRepository,
      LessonRepository lessonRepository, QuizRepository quizRepository,
      DailyStreakRepository dailyStreakRepository) {
    return new AchievementTracker(achievementRepository, wordRepository, userProgressRepository, lessonRepository, quizRepository, dailyStreakRepository);
  }
}
