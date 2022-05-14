package com.example.certidigital.infraestructure;

import com.example.certidigital.domain.Certificate;
import com.example.certidigital.domain.CertificateRepository;

public class CertificateDB implements CertificateRepository {
   //db connection
   public Certificate save(Certificate certificate){
      Certificate certificateResult = new Certificate();
      certificateResult.setId("1");
      certificateResult.setPersona(certificate.getPersona());
      certificateResult.setTipo(certificate.getTipo());
      certificateResult.setFecha(certificate.getFecha());
      return certificateResult;
   }
}
