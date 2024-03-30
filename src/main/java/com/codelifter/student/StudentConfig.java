package com.codelifter.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
            // Check if students exist
            if (repository.findStudentByEmail("sofiyamy@gmail.com").isEmpty()) {
                Student sofia = new Student(
                        "Sofia", "sofiyamy@gmail.com", LocalDate.of(1996, Month.MAY, 15)
                );
                repository.save(sofia);
            }

            if (repository.findStudentByEmail("amirabu@gmail.com").isEmpty()) {
                Student amira = new Student(
                        "Amira", "amirabu@gmail.com", LocalDate.of(2003, Month.JANUARY, 15)
                );
                repository.save(amira);
            }
        };
    }
}
