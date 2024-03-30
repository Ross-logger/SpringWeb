package com.codelifter.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if (studentByEmail.isPresent()) {
            throw new IllegalStateException("Email is taken!");
        }
        studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(String email) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(email);
        if (!studentByEmail.isPresent()) {
            throw new IllegalStateException("Student does not exist!");
        }
        studentRepository.deleteStudentByEmail(email);
    }
}
