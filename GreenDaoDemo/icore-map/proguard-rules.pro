# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\android\SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#指定代码的压缩级别
-optimizationpasses 5
#包明不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
 #优化  不优化输入的类文件
-dontoptimize
 #预校验
-dontpreverify
 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#保护注解
-keepattributes *Annotation*

# 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
#如果有引用v4包可以添加下面这行
#-keep public class * extends android.support.v4.app.Fragment
#-keep public class * extends android.support.** { *; }
#如果引用了v4或者v7包
-dontwarn android.support.*
#忽略警告
-ignorewarning

-keep class com.google.inject.Binder
        -keepclassmembers class * {
            @com.google.inject.Inject <init>(...);
        }
# There's no way to keep all @Observes methods, so use the On*Event convention to identify event handlers
-keepclassmembers class * {
            void *(**On*Event);
        }
-keep public class * extends android.view.View {
            public <init>(android.content.Context);
            public <init>(android.content.Context, android.util.AttributeSet);
            public <init>(android.content.Context, android.util.AttributeSet, int);
            public void set*(...);
        }
-keep public class roboguice.**
-keep class com.google.inject.internal.util.$Finalizer { *; }

#####################记录生成的日志数据,gradle build时在本项目根目录输出################
 #混淆时是否记录日志
-verbose
#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt

# 混淆包
-keep class com.google.gson.examples.android.model.** { *; }
#dialog
-keep class me.drakeet.materialdialog.** { *; }
#加载框
-keep class com.kaopiz.kprogresshud.** { *; }
#下拉刷新
-keep class in.srain.cube.views.ptr.** { *; }
#实体类不混淆

-keep class com.ousrslook.shimao.commen.ioc.** { *;} #不能混淆 否则注解无效
-keep class com.ousrslook.shimao.model.** { *; } #不能混淆
-keep class com.ousrslook.shimao.net.XaResult{ *; }#统一返回的实体类泛型不能混淆
#-keep class com.ousrslook.shimao.net.** { *; }

####混淆保护自己项目的部分代码以及引用的第三方jar包library-end####
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepattributes SourceFile,SourceDir,InnerClasses
#写死两个调用native的包
-keep class com.commit451.nativestackblur.NativeStackBlur{*;}
-keep class com.enrique.stackblur.NativeBlurProcess{*;}
#保持 native 方法不被混淆 JNI不混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable
#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}
#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}

 -keep class **.R$* { *; }
#避免混淆泛型 如果混淆报错建议关掉
-keepattributes Signature
#反射
-keepattributes EnclosingMethod
#不混淆实体类
-keep class com.xinsteel.oa.base.** {*;}
-keep class com.xinsteel.oa.module.main.bean.** {*;}
-keep class com.xinsteel.oa.module.association.bean.** {*;}
-keep class com.xinsteel.oa.module.message.bean.** {*;}
-keep class com.xinsteel.oa.module.work.bean.** {*;}
-keep class com.xinsteel.oa.module.myCalendar.bean.** {*;}
-keep class com.xinsteel.oa.module.email.bean.** {*;}
-keep class com.xinsteel.oa.module.filebox.bean.** {*;}
-keep class com.xinsteel.oa.module.travel.bean.** {*;}
-keep class com.xinsteel.oa.module.task.bean.** {*;}
-keep class com.xinsteel.oa.module.meeting.bean.** {*;}
-keep class com.xinsteel.oa.module.message.bean.** {*;}
-keep class com.xinsteel.oa.module.report.bean.** {*;}
-keep class com.xinsteel.oa.module.forum.bean.** {*;}
-keep class com.xinsteel.oa.module.fileflow.bean.** {*;}
-keep class com.xinsteel.oa.module.car.bean.** {*;}
-keep class com.xinsteel.oa.module.office.bean.** {*;}
-keep class com.xinsteel.oa.module.statistics.bean.** {*;}
-keep class com.xinsteel.oa.module.knowledge.bean.** {*;}
-keep class com.xinsteel.oa.module.property.bean.** {*;}
-keep class com.xinsteel.oa.module.flow.bean.** {*;}
#------------环信混淆----------------------
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**
-keep class android.app.** {*;}
-keep class com.easemob.** {*;}

-keep class com.superrtc.** {*;}

-keep class org.apache.** {*;}
-dontwarn  com.easemob.**
-keep class org.jivesoftware.** {*;}
-keep class com.easemob.chatuidemo.utils.SmileUtils {*;}
#注意前面的包名，如果把这个类复制到自己的项目底下，比如放在com.example.utils底下，应该这么写（实际要去掉#）
#-keep class com.example.utils.SmileUtils {*;}
#如果使用EaseUI库，需要这么写
-keep class com.easemob.easeui.utils.EaseSmileUtils {*;}

#2.0.9后加入语音通话功能，如需使用此功能的API，加入以下keep
-dontwarn ch.imvs.**
-dontwarn org.slf4j.**
-keep class org.ice4j.** {*;}
-keep class net.java.sip.** {*;}
-keep class org.webrtc.voiceengine.** {*;}
-keep class org.bitlet.** {*;}
-keep class org.slf4j.** {*;}
-keep class ch.imvs.** {*;}

#---------百度地图混淆------------------------
#-libraryjars libs/BaiduLBS_Android.jar
-keep class com.baidu.** { *; }
-keep class vi.com.gdi.bgl.android.**{*;}
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-keep class com.sinovoice.** {*;}
-keep class pvi.com.** {*;}
-dontwarn com.baidu.**
-dontwarn vi.com.**
-dontwarn pvi.com.**

# ----AndroidEventBus
-keep class org.simple.** { *; }
-keep interface org.simple.** { *; }
-keepclassmembers class * {
    @org.simple.eventbus.Subscriber <methods>;
}
# EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
#---ButterKnife-------------
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#---------------------------Gson--------------------------
#-keepattributes Signature-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# 使用Gson时需要配置Gson的解析对象及变量都不混淆。不然Gson会找不到变量。
# 将下面替换成自己的实体类
#-keep class com.example.bean.** { *; }

# OrmLite
-keepattributes *DatabaseField*
-keepattributes *DatabaseTable*
-keepattributes *SerializedName*
-keep class com.j256.**
-keepclassmembers class com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }

#--------U-meng----------------
#避免混淆了友盟的包
-dontwarn com.umeng.**
-keep class com.umeng*.** {*; }
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class com.xinsteel.oa_customer.R$*{
public static final int *;
}
#保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


#保证zxing框架不被混淆
-keep class com.google.zxing.** {*; }


#-------------------glide图片框架------------------------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#=====================okhttputils框架=====================
#====okhttputils====
-dontwarn com.zhy.http.**
-keep class com.zhy.http.**{*;}
-keep interface com.zhy.http.**{*;}

#====okhttp====
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-keep interface okhttp3.**{*;}
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.**{*;}
-keep interface com.squareup.okhttp.**{*;}

#====gson====
#====jackson===========
-keep class sun.misc.Unsafe{*;}
-dontwarn com.google.gson.**
-keep class com.google.gson.**{*;}
-keep class com.google.gson.stream.**{*;}
-keep class com.google.gson.examples.android.model.**{*;}

#===========shareSDK===================
# ShareSDK 混淆
-dontwarn cn.sharesdk.**
-keep class cn.sharesdk.** { *; }

#===========科大讯飞SDK===================
-keep class com.iflytek.**{*;}
-keepattributes Signature

#===========邮箱===================
-keep class javax.mail.**{*;}
-keep class com.sun.mail.**{*;}
-keep class javax.activation.**{*;}
-keep class com.sun.activation.registries.**{*;}
-dontwarn com.sun.mail.handlers.handler_base

#===========融云===================
-keepattributes Exceptions,InnerClasses

-keepattributes Signature

# RongCloud SDK
-keep class io.rong.** {*;}
-keep class * implements io.rong.imlib.model.MessageContent {*;}
-dontwarn io.rong.push.**
-dontnote com.xiaomi.**
-dontnote com.google.android.gms.gcm.**
-dontnote io.rong.**

# VoIP
-keep class io.agora.rtc.** {*;}

# Location
-keep class com.amap.api.**{*;}
-keep class com.amap.api.services.**{*;}

# 红包
-keep class com.uuhelper.Application.** {*;}
-keep class net.sourceforge.zbar.** { *; }
-keep class com.google.android.gms.** { *; }
-keep class com.alipay.** {*;}
-keep class com.jrmf360.rylib.** {*;}

-ignorewarnings
#低版本eventbus
-keep class de.greenrobot.event.** {*;}
-keepclassmembers class ** {
    public void onEvent*(**);
    void onEvent*(**);
}