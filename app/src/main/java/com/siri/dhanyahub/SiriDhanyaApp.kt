package com.siri.dhanyahub

import android.app.Application
import com.siri.dhanyahub.data.local.AppDatabase
import com.siri.dhanyahub.data.repository.AppRepository

class SiriDhanyaApp : Application() {
    val database by lazy { AppDatabase.build(this) }
    val repository by lazy { AppRepository(database.recipeDao(), database.priceDao(), database.fpoDao()) }
}
