package com.example.studentmanagerfragment.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.studentmanagerfragment.R
import com.example.studentmanagerfragment.models.StudentModel
import com.example.studentmanagerfragment.StudentViewModel

class AddEditStudentFragment : Fragment() {
    private var studentName: String? = null
    private var studentId: String? = null
    private var position: Int? = null
    private val studentViewModel: StudentViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            studentName = it.getString("studentName")
            studentId = it.getString("studentId")
            position = it.getInt("position", -1).takeIf { pos -> pos >= 0 }
        }
        Log.d("AddEditStudentFragment", "onCreate: studentName=$studentName, studentId=$studentId, position=$position")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_edit_student, container, false)
        val title = view.findViewById<TextView>(R.id.fragment_title)
        val editName = view.findViewById<EditText>(R.id.edit_student_name)
        val editId = view.findViewById<EditText>(R.id.edit_student_id)
        val btnSave = view.findViewById<Button>(R.id.btn_save)

        Log.d("AddEditStudentFragment", "onCreateView: Initializing UI components")

        if (position != null) {
            title.text = "Edit Student"
            btnSave.text = "Save Changes"
            editName.setText(studentName)
            editId.setText(studentId)
            Log.d("AddEditStudentFragment", "Editing student: studentName=$studentName, studentId=$studentId, position=$position")
        } else {
            title.text = "Add New Student"
            btnSave.text = "Add Student"
            Log.d("AddEditStudentFragment", "Adding new student")
        }

        btnSave.setOnClickListener {
            val updatedName = editName.text.toString().trim()
            val updatedId = editId.text.toString().trim()
            Log.d("AddEditStudentFragment", "btnSave clicked: updatedName=$updatedName, updatedId=$updatedId")

            if (updatedName.isNotEmpty() && updatedId.isNotEmpty()) {
                val updatedStudent = StudentModel(updatedName, updatedId) // Create a new StudentModel
                Log.d("AddEditStudentFragment", "Saving student: $updatedStudent")
                studentViewModel.saveStudent(updatedStudent, null) // Pass null for position to indicate a new student
                findNavController().navigateUp() // Navigate back after adding
            } else {
                Log.w("AddEditStudentFragment", "Fields are empty. updatedName=$updatedName, updatedId=$updatedId")
            }
        }
        return view
    }
}
