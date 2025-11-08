package ru.empat.morewords.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.empat.morewords.data.room.entity.Language

@Dao
interface LanguageDao {

    @Query("SELECT * from language WHERE id = :id")
    fun getLanguage(id: Long) : Language

    @Insert
    fun insert(language: Language) : Long
}