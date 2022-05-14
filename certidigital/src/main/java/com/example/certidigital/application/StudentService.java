package com.example.certidigital.application;

import com.example.certidigital.domain.StudentRepository;
import com.example.certidigital.domain.Student;

public class StudentService {

   StudentRepository studentRepository;

   public StudentService(StudentRepository studentRepository) {
      this.studentRepository = studentRepository;
   }

   public Student saveStudent(Student student){
      return studentRepository.save(student);
   }

}
