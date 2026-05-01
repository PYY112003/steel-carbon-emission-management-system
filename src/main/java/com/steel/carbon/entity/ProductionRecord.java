package com.steel.carbon.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductionRecord extends BaseEntity {
    private Long id;
    private LocalDate recordDate;
    private Long processId;
    private BigDecimal outputValue;
    private String unit;
    private Long inputUserId;
    
    private String processName;
    private String inputUserName;
}
