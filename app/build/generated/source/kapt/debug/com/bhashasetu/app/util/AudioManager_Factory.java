package com.bhashasetu.app.util;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class AudioManager_Factory implements Factory<AudioManager> {
  @Override
  public AudioManager get() {
    return newInstance();
  }

  public static AudioManager_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static AudioManager newInstance() {
    return new AudioManager();
  }

  private static final class InstanceHolder {
    static final AudioManager_Factory INSTANCE = new AudioManager_Factory();
  }
}
