package ru.empat.morewords.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.empat.morewords.data.room.entity.LanguageModel
import ru.empat.morewords.domain.entity.Language

@Dao
interface LanguageDao {

    @Query("SELECT * from language WHERE id = :id")
    fun getLanguage(id: Long): Flow<LanguageModel>

    @Query("SELECT * from language")
    fun getAllLanguages(): Flow<List<LanguageModel>>

    @Insert
    suspend fun insert(languageModel: LanguageModel): Long
}