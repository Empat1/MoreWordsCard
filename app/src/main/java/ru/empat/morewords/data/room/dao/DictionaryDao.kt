package ru.empat.morewords.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.empat.morewords.data.room.entity.DictionaryModel

@Dao
interface DictionaryDao {
    @Query("SELECT * from dictionary")
    fun getAllDictionary() : List<DictionaryModel>

    @Query("SELECT * from dictionary WHERE id = :id")
    fun getDictionary(id: Long) : DictionaryModel

    @Insert
    fun addDictionaty(dictionaryModel: DictionaryModel) : Long

    @Query("DELETE FROM dictionary WHERE id = :id")
    fun deleteDictionary(id: Long)
}