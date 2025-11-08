package ru.empat.morewords.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.empat.morewords.data.room.entity.Dictionary

@Dao
interface DictionaryDao {
    @Query("SELECT * from dictionary")
    fun getAllDictionary() : List<Dictionary>

    @Query("SELECT * from dictionary WHERE id = :id")
    fun getDictionary(id: Long) : Dictionary

    @Insert
    fun addDictionaty(dictionary: Dictionary) : Long

    @Query("DELETE FROM dictionary WHERE id = :id")
    fun deleteDictionary(id: Long)
}