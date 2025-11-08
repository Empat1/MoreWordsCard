package ru.empat.morewords.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.empat.morewords.data.room.entity.WordModel

@Dao
interface WordDao {

    @Query("SELECT * FROM word")
    fun getAllWords() : List<WordModel>

    @Query("SELECT * FROM word WHERE id = :id")
    fun getWordById(id: Long): WordModel?

    @Insert
    fun insertWord(wordModel: WordModel): Long

    @Update
    fun updateWord(wordModel: WordModel)

    @Delete
    fun deleteWord(wordModel: WordModel)
}