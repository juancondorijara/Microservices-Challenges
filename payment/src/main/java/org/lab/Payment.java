package org.lab;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@AllArgsConstructor 
@NoArgsConstructor
public class Payment {
    @Id
    private UUID paymentId;
    private Integer userId;
    private Integer cashierId;
    private float mount;
    private String comment;
    private String createdAt;
}