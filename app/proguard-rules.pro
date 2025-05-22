# Default ProGuard rules for Android
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-dump class_files.txt
-printseeds seeds.txt
-printusage unused.txt
-printmapping mapping.txt
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# Android specific rules
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class * extends androidx.fragment.app.Fragment

# Keep the R class and its fields
-keepclassmembers class **.R$* {
    public static <fields>;
}

# Keep native methods
-keepclasseswithmembers class * {
    native <methods>;
}

# Keep custom View constructors
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Keep activities, services, etc. referenced in AndroidManifest.xml
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Keep Parcelable implementations
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep Serializable classes
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Room Database
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# Entity classes
-keep class com.example.englishhindi.data.model.** { *; }
-keep class com.example.englishhindi.data.relation.** { *; }

# Enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Prevent obfuscation of types with annotations
-keepattributes *Annotation*
-keep class androidx.annotation.Keep
-keep @androidx.annotation.Keep class * {*;}
-keepclasseswithmembers class * {
    @androidx.annotation.Keep <methods>;
}
-keepclasseswithmembers class * {
    @androidx.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
    @androidx.annotation.Keep <init>(...);
}

# ViewModels
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}

# Retrofit
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.examples.android.model.** { <fields>; }
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Logging - Remove for release
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# Keep Room Database classes
-keep class com.example.englishhindi.data.db.AppDatabase
-keep class com.example.englishhindi.data.dao.** { *; }

# Keep Entity classes and their fields
-keep class com.example.englishhindi.data.model.** { *; }
-keepclassmembers class com.example.englishhindi.data.model.** { *; }

# Keep AudioPlayer components
-keep class com.example.englishhindi.audio.AudioPlayer { *; }
-keep class com.example.englishhindi.audio.AudioPlayerListener { *; }

# Keep custom views
-keep class com.example.englishhindi.view.** { *; }
-keep class com.example.englishhindi.ui.components.** { *; }

# FIXES BASED ON FUNCTIONALITY TESTING

# Audio focus handling - Fix for audio resumption issues
-keep class com.example.englishhindi.audio.AudioFocusHandler { *; }
-keep class com.example.englishhindi.audio.AudioFocusChangeListener { *; }
-keep class * implements android.media.AudioManager$OnAudioFocusChangeListener { *; }

# Chat functionality - Fix for chat crashes
-keep class com.chatprovider.** { *; }
-keepclassmembers class com.chatprovider.** { *; }
-keep class com.example.englishhindi.chat.** { *; }
-keep class com.example.englishhindi.messaging.** { *; }

# JSON Models with complex nesting - Fix for JSON parsing issues
-keep class com.example.englishhindi.data.model.** { *; }
-keep class com.example.englishhindi.data.response.** { *; }
-keep class com.example.englishhindi.network.response.** { *; }

# Additional rules for third-party libraries often used in chat features
-keepattributes EnclosingMethod
-keepattributes InnerClasses
-keep class io.socket.** { *; }
-keep class org.webrtc.** { *; }
-keep class org.json.** { *; }

# Ensure WebView JavaScript interfaces are kept
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Additional rules for dynamic feature loading
-keep class com.example.englishhindi.dynamicfeature.** { *; }
-keep class com.example.englishhindi.module.** { *; }