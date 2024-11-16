package org.iesharia.composeroomapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Query("SELECT * FROM tasks ORDER BY id ASC")
    fun getAllTasks(): Flow<List<Task>>

    @Update
    suspend fun update(task: Task)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun delete(taskId: Int)
}

