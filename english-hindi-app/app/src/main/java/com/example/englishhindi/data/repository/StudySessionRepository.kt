package com.example.englishhindi.data.repository

import androidx.lifecycle.LiveData
import com.example.englishhindi.data.dao.StudySessionDao
import com.example.englishhindi.data.model.StudySession
import kotlinx.coroutines.flow.Flow
import java.util.Date

class StudySessionRepository(private val studySessionDao: StudySessionDao) {

    val allStudySessions: Flow<List<StudySession>> = studySessionDao.getAllStudySessions()
    val totalStudyTime: Flow<Int> = studySessionDao.getTotalStudyTime()
    val todayStudySessionCount: Flow<Int> = studySessionDao.getStudySessionCountToday()
    val todayStudyTime: Flow<Int> = studySessionDao.getTodayStudyTime()
    
    suspend fun insert(studySession: StudySession): Long {
        return studySessionDao.insert(studySession)
    }
    
    suspend fun update(studySession: StudySession) {
        studySessionDao.update(studySession)
    }
    
    suspend fun delete(studySession: StudySession) {
        studySessionDao.delete(studySession)
    }
    
    fun getStudySessionById(id: Long): Flow<StudySession> {
        return studySessionDao.getStudySessionById(id)
    }
    
    fun getStudySessionsByDateRange(startDate: Date, endDate: Date): Flow<List<StudySession>> {
        return studySessionDao.getStudySessionsByDateRange(startDate, endDate)
    }
    
    fun getStudyTimeInRange(startDate: Date, endDate: Date): Flow<Int> {
        return studySessionDao.getStudyTimeInRange(startDate, endDate)
    }
}