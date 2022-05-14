package com.example.certidigital.domain;

import lombok.Data;

@Data
public class Payment {

   private String id;
   private String tipo;
   private String numero;
   private String vencimiento;
   private String cvv;

}
