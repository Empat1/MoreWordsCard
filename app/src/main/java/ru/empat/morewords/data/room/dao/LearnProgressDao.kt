package ru.empat.morewords.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.empat.morewords.data.room.entity.LearningProgressWordModel

@Dao
interface LearnProgressDao {

    @Update
    fun edit(learningProgressWordModel: LearningProgressWordModel)

    @Insert
    fun insert(learningProgressWordModel: LearningProgressWordModel)

    @Query("SELECT * FROM learning_progress WHERE id = :id")
    fun getLearn(id: Long): LearningProgressWordModel

    @Query("DELETE FROM learning_progress WHERE id = :id")
    fun delete(id: Long)
}