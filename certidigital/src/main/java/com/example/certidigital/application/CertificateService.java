package com.example.certidigital.application;

import com.example.certidigital.domain.CertificateRepository;
import com.example.certidigital.domain.Certificate;

public class CertificateService {

   CertificateRepository certificateRepository;

   public CertificateService(CertificateRepository certificateRepository) {
      this.certificateRepository = certificateRepository;
   }

   public Certificate saveCertificate(Certificate certificate){
      return certificateRepository.save(certificate);
   }

}
