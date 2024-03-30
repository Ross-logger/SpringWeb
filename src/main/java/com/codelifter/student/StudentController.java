package com.codelifter.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping("")
    public String redirectToAll() {
        return "redirect:/students/all"; // Redirect to the /all path
    }

    @GetMapping("/add")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "add_student";
    }

    @PostMapping("/add")
    public String addStudent(@Validated @ModelAttribute("student") Student student, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "add_student"; // Return to the form with validation errors
        }

        try {
            studentService.addNewStudent(student);
            return "redirect:/students/all";
        } catch (IllegalStateException e) {
            model.addAttribute("error", "Email is already taken!");
            return "add_student"; // Return to the form with error message
        }
    }
    @GetMapping("/all")
    public String getAllStudents(Model model) {
        List<Student> students = studentService.getStudents();
        model.addAttribute("students", students);
        return "students";
    }

    @GetMapping("/delete")
    public String showDeleteStudentForm() {
        return "delete_student";
    }

    @PostMapping("/delete")
    public String deleteStudent(@RequestParam("email") String studentEmailToDelete, Model model) {
        try {
            studentService.deleteStudent(studentEmailToDelete);
            return "redirect:/students/all";
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage()); // Pass the error message to the view
            return "delete_student";
        }
    }
}
