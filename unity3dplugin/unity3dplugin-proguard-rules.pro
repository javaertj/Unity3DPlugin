# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# 保持native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
# 保持Parcelable不被混淆
-keep class * implements Android.os.Parcelable {
    public static final Android.os.Parcelable$Creator *;
}
# 保持enum不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#保持com.ykbjson.lib.unity3dplugin下所有类不被混淆
-keep class com.ykbjson.lib.unity3dplugin.**
-keep class com.ykbjson.lib.unity3dplugin.** { *; }
-keep class com.ykbjson.lib.unity3dplugin.* { *; }
-keepclassmembers class com.ykbjson.lib.unity3dplugin.CallInfo$Builder {
    *;
}

#unity3d
-keep class bitter.jnibridge.* { *; }
-keep class com.unity3d.player.* { *; }
-keep class org.fmod.* { *; }
-ignorewarnings

#fastjson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.**{*;}
#忽略范型
-keepattributes Signature
#忽略序列化，实体类实现Serializable接口即可不需单独添加到这里混淆
-keepclassmembers class * implements java.io.Serializable { *; }

#butterknife
# Retain generated class which implement Unbinder.
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }

# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinding.
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }



