package ru.empat.morewords.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.empat.morewords.data.room.entity.Word

@Dao
interface WordDao {

    @Query("SELECT * FROM word")
    fun getAllWords() : List<Word>

    @Query("SELECT * FROM word WHERE id = :id")
    fun getWordById(id: Long): Word?

    @Insert
    fun insertWord(word: Word): Long

    @Update
    fun updateWord(word: Word)

    @Delete
    fun deleteWord(word: Word)
}