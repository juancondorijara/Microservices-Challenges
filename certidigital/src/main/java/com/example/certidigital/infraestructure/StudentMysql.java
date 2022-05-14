package com.example.certidigital.infraestructure;

import com.example.certidigital.domain.Student;
import com.example.certidigital.domain.StudentRepository;

public class StudentMysql implements StudentRepository {
   //db connection
   @Override
   public Student save(Student student){
      Student studentResult = new Student();
      studentResult.setId("1");
      studentResult.setName("MySql: " + student.getName());
      return studentResult;
   }

}
