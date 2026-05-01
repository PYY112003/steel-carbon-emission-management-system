package com.steel.carbon.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class WarningRule extends BaseEntity {
    private Long id;
    private String ruleName;
    private String warningType;
    private Long targetProcessId;
    private BigDecimal thresholdValue;
    private Integer status;
    
    private String processName;
}
