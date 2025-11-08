package ru.empat.morewords.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.empat.morewords.data.room.entity.LanguageModel

@Dao
interface LanguageDao {

    @Query("SELECT * from language WHERE id = :id")
    fun getLanguage(id: Long) : LanguageModel

    @Insert
    fun insert(languageModel: LanguageModel) : Long
}