# Add project specific ProGuard rules here.
-keepattributes Signature
-keepattributes *Annotation*

# Firebase
-keep class com.google.firebase.** { *; }
-keep class com.sktech.messmate.data.model.** { *; }

# Hilt
-keep class dagger.hilt.** { *; }
