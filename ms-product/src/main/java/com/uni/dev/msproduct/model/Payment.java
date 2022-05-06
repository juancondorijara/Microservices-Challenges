package com.uni.dev.msproduct.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;

@Document(collection = "payment")
public class Payment {

    @Id
    private String id;
    private String tipotarjeta;
    private String numerotarjeta;
    private String vencimientotarjeta;
    private Integer cvvtarjeta;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipotarjeta() {
        return tipotarjeta;
    }

    public void setTipotarjeta(String tipotarjeta) {
        this.tipotarjeta = tipotarjeta;
    }

    public String getNumerotarjeta() {
        return numerotarjeta;
    }

    public void setNumerotarjeta(String numerotarjeta) {
        this.numerotarjeta = numerotarjeta;
    }

    public String getVencimientotarjeta() {
        return vencimientotarjeta;
    }

    public void setVencimientotarjeta(String vencimientotarjeta) {
        this.vencimientotarjeta = vencimientotarjeta;
    }

    public Integer getCvvtarjeta() {
        return cvvtarjeta;
    }

    public void setCvvtarjeta(Integer cvvtarjeta) {
        this.cvvtarjeta = cvvtarjeta;
    }

}
