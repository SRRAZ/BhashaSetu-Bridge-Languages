package com.example.englishhindi.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.englishhindi.data.model.StudySession
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface StudySessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(studySession: StudySession): Long

    @Update
    suspend fun update(studySession: StudySession)

    @Delete
    suspend fun delete(studySession: StudySession)

    @Query("SELECT * FROM study_sessions ORDER BY startTime DESC")
    fun getAllStudySessions(): Flow<List<StudySession>>

    @Query("SELECT * FROM study_sessions WHERE id = :id")
    fun getStudySessionById(id: Long): Flow<StudySession>

    @Query("SELECT * FROM study_sessions WHERE startTime >= :startDate AND startTime <= :endDate ORDER BY startTime DESC")
    fun getStudySessionsByDateRange(startDate: Date, endDate: Date): Flow<List<StudySession>>

    @Query("SELECT SUM(durationMinutes) FROM study_sessions")
    fun getTotalStudyTime(): Flow<Int>

    @Query("SELECT SUM(durationMinutes) FROM study_sessions WHERE startTime >= :startDate AND startTime <= :endDate")
    fun getStudyTimeInRange(startDate: Date, endDate: Date): Flow<Int>

    @Query("SELECT COUNT(*) FROM study_sessions WHERE DATE(startTime / 1000, 'unixepoch') = DATE('now')")
    fun getStudySessionCountToday(): Flow<Int>

    @Query("SELECT SUM(durationMinutes) FROM study_sessions WHERE DATE(startTime / 1000, 'unixepoch') = DATE('now')")
    fun getTodayStudyTime(): Flow<Int>
}