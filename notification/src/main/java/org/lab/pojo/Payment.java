package org.lab.pojo;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Payment {
    private UUID paymentId;
    private Integer userId;
    private Integer cashierId;
    private float mount;
    private String comment;
    private String createdAt;
}
