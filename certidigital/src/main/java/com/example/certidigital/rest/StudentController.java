package com.example.certidigital.rest;

import com.example.certidigital.application.StudentService;
import com.example.certidigital.domain.Student;
import com.example.certidigital.domain.StudentRepository;
import com.example.certidigital.infraestructure.StudentDbTest;
import com.example.certidigital.infraestructure.StudentMysql;
import com.example.certidigital.infraestructure.StudentOracle;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

   // Oracle
   StudentRepository studentRepository = new StudentOracle();

   //Mysql
   //StudentRepository studentRepository = new StudentMysql();

   StudentService studentService = new StudentService(studentRepository);

   @PostMapping
   public Student saveStudent(@RequestBody Student student){
     //Student student = new Student();
     return studentService.saveStudent(student);
   }

}
