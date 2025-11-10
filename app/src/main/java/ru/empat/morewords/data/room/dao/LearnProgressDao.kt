package ru.empat.morewords.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.empat.morewords.data.room.entity.LearningProgressWordModel

@Dao
interface LearnProgressDao {

    @Update
    suspend fun edit(learningProgressWordModel: LearningProgressWordModel)

    @Insert
    suspend fun insert(learningProgressWordModel: LearningProgressWordModel)

    @Query("SELECT * FROM learning_progress WHERE id = :id")
    fun getLearn(id: Long): Flow<LearningProgressWordModel>

    @Query("DELETE FROM learning_progress WHERE id = :id")
    suspend fun delete(id: Long)
}