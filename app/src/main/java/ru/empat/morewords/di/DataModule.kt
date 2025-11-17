package ru.empat.morewords.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.empat.morewords.data.WorkRepositoryImpl
import ru.empat.morewords.data.room.UserDatabase
import ru.empat.morewords.data.room.dao.DictionaryDao
import ru.empat.morewords.data.room.dao.LanguageDao
import ru.empat.morewords.data.room.dao.LearnProgressDao
import ru.empat.morewords.data.room.dao.WordDao
import ru.empat.morewords.domain.repository.WordRepository

@Module
interface DataModule {

    @[ApplicationScope Binds]
    fun bindRepository(impl: WorkRepositoryImpl): WordRepository

    companion object {
        @[ApplicationScope Provides]
        fun provideUserDatabase(context: Context): UserDatabase {
            return UserDatabase.newInstance(context)
        }

        @[ApplicationScope Provides]
        fun provideDictionaryDao(database: UserDatabase): DictionaryDao {
            return database.dictionaryDao()
        }

        @[ApplicationScope Provides]
        fun provideLanguageDao(database: UserDatabase): LanguageDao {
            return database.languageDao()
        }

        @[ApplicationScope Provides]
        fun provideLearnProgressDao(database: UserDatabase): LearnProgressDao {
            return database.learnProgressDao()
        }

        @[ApplicationScope Provides]
        fun provideWordDao(database: UserDatabase): WordDao {
            return database.wordDao()
        }
    }
}