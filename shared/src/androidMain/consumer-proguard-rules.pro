# Keep serialization classes
-keep @kotlinx.serialization.Serializable class ** { *; }
-keep class **$$serializer { *; }

# Coroutines
-dontwarn kotlinx.coroutines.**