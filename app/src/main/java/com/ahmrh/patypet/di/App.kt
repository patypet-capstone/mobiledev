package com.ahmrh.patypet.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

val Context.dataStore by preferencesDataStore(name = "app_preferences")
@HiltAndroidApp
class App: Application()