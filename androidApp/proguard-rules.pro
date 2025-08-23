#############################################
# Entry points – must not be removed/renamed
#############################################

# Android components
-keep class ** extends android.app.Activity
-keep class ** extends android.app.Application
-keep class ** extends android.app.Service
-keep class ** extends android.content.BroadcastReceiver
-keep class ** extends android.content.ContentProvider

# Parcelable CREATOR field
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

#############################################
# Kotlinx Serialization (only if you use it)
#############################################
# If you use kotlinx.serialization with generated serializers (the usual case):
-keep,includedescriptorclasses class **$$serializer { *; }
-keepclassmembers class * {
    @kotlinx.serialization.Serializable *;
}
# If you rely on reflection for serialization, also keep @Serializable classes:
# -keep @kotlinx.serialization.Serializable class ** { *; }

#############################################
# Optional – if you use Gson/Moshi reflection
#############################################
# -keep class com.card.unoshare.model.** { *; }

#############################################
# Keep essential metadata only
#############################################
-keepattributes *Annotation*, InnerClasses, EnclosingMethod, Signature

#############################################
# Remove log calls in release
#############################################
-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
    public static *** wtf(...);
}

#############################################
# Warnings only (ok)
#############################################
-dontwarn kotlin.**
-dontwarn kotlinx.**
-dontwarn org.jetbrains.annotations.**
# If Ktor/OkHttp throw warnings only:
-dontwarn io.ktor.**
-dontwarn okhttp3.**
-dontwarn okio.**