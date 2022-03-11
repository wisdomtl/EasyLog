# EasyLog
An easy way to customize your log in Android，including output to console, writing log to file in high performance way and so on.

## 1. Initialization
```kotlin
EasyLog.addInterceptor(LogcatInterceptor()) // add logcat intercepter
EasyLog.addInterceptor(OkioLogInterceptor.getInstance(this.filesDir.absolutePath)) // add file interceptor
```

## 2. use it everywhere
```kotlin
EasyLog.v("test message %s","tag","one")
```
