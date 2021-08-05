package hu.ahomolya.androidbase.di

import javax.inject.Qualifier

object DiQualifiers {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NavigationChannel
}