package ru.empat.morewords.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.empat.morewords.data.WorkRepositoryImpl
import ru.empat.morewords.data.room.UserDatabase
import ru.empat.morewords.domain.repository.WordRepository

@Module
interface DataModule {

    @[ApplicationScope Binds]
    fun bindRepository(impl: WorkRepositoryImpl) : WordRepository

    companion object {
        @[ApplicationScope Provides]
        fun provideUserDatabase(context: Context) : UserDatabase{
            return UserDatabase.newInstance(context)
        }
    }
}