package ru.empat.morewords.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.empat.morewords.data.room.entity.LearningProgressWord

@Dao
interface LearnProgressDao {

    @Update
    fun edit(learningProgressWord: LearningProgressWord)

    @Insert
    fun insert(learningProgressWord: LearningProgressWord)

    @Query("SELECT * FROM learning_progress WHERE id = :id")
    fun getLearn(id: Long): LearningProgressWord

    @Query("DELETE FROM learning_progress WHERE id = :id")
    fun delete(id: Long)
}