package ru.empat.morewords.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.empat.morewords.MainActivity

@ApplicationScope
@Component(
    modules = [
        PresentationModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}