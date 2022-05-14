package com.example.certidigital.rest;

import com.example.certidigital.application.CertificateService;
import com.example.certidigital.domain.Certificate;
import com.example.certidigital.domain.CertificateRepository;
import com.example.certidigital.infraestructure.CertificateDB;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/certificate")
public class CertificateController {

   //
   CertificateRepository certificateRepository = new CertificateDB();

    CertificateService certificateService = new CertificateService(certificateRepository);

   @PostMapping
   public Certificate saveCertificate(@RequestBody Certificate certificate){
     //Certificate certificate = new Certificate();
     return certificateService.saveCertificate(certificate);
   }

}
