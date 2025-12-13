package ru.empat.morewords.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.empat.morewords.data.room.entity.LearningProgressWordModel
import ru.empat.morewords.data.room.entity.WordModel
import ru.empat.morewords.domain.entity.Word
import java.util.Date

@Dao
interface WordDao {

    @Query("SELECT * FROM word")
    fun getAllWords(): Flow<List<WordModel>?>

    @Query("SELECT * FROM word WHERE id = :id")
    fun getWordById(id: Long): Flow<WordModel?>

    @Query("SELECT * FROM word WHERE dictionaryId = :id")
    fun getWordByDictionary(id: Long): Flow<List<WordModel>>

    @Insert
    suspend fun insertWord(wordModel: WordModel): Long

    @Update
    suspend fun updateWord(wordModel: WordModel)

    @Query("DELETE FROM word WHERE id = :id")
    suspend fun deleteWord(id: Long)

    @Query("SELECT * " +
            "FROM word " +
            "WHERE nextReview < :now " +
            "ORDER BY lastReviewed " +
            "LIMIT :limit"
    )
    fun getOldRepeatedWord(limit: Long, now: Long = Date().time): Flow<List<WordModel>?>
}