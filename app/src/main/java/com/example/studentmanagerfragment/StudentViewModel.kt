package com.example.studentmanagerfragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.studentmanagerfragment.Dao.StudentDao
import com.example.studentmanagerfragment.models.StudentModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentViewModel(application: Application) : AndroidViewModel(application) {
    private val studentDao: StudentDao
    val students: LiveData<List<StudentModel>>

    init {
        val database = AppDatabase.getDatabase(application)
        studentDao = database.studentDao()
        students = studentDao.getAllStudents()
    }

    fun saveStudent(student: StudentModel, position: Int?) = viewModelScope.launch(Dispatchers.IO) {
        if (student.id > 0) {
            studentDao.update(student)
        } else {
            studentDao.insert(student)
        }
    }

    fun deleteStudent(student: StudentModel) = viewModelScope.launch(Dispatchers.IO) {
        studentDao.delete(student)
    }


    fun deleteStudentById(studentId: Int) = viewModelScope.launch(Dispatchers.IO) {
        studentDao.deleteById(studentId)
    }


}



