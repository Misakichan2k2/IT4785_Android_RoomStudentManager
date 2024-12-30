package com.example.studentmanagerfragment.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.studentmanagerfragment.models.StudentModel

@Dao
interface StudentDao {
    @Query("SELECT * FROM students")
    fun getAllStudents(): LiveData<List<StudentModel>>

    @Insert
    suspend fun insert(student: StudentModel): Long

    @Update
    suspend fun update(student: StudentModel)

    @Delete
    suspend fun delete(student: StudentModel)

    @Query("DELETE FROM students WHERE id = :studentId")
    suspend fun deleteById(studentId: Int)
}


