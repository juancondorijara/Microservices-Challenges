package com.example.certidigital.infraestructure;

import com.example.certidigital.domain.Student;
import com.example.certidigital.domain.StudentRepository;

public class StudentDbTest implements StudentRepository {
   //Connect H2
   @Override
   public Student save(Student student) {
      Student studentResult = new Student();
      studentResult.setId("1");
      studentResult.setName("name Test");
      return studentResult;
   }
}
