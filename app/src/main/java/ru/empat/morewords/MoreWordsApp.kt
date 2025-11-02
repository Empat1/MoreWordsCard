package ru.empat.morewords

import android.app.Application
import ru.empat.morewords.di.ApplicationComponent
import ru.empat.morewords.di.DaggerApplicationComponent

class MoreWordsApp : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}