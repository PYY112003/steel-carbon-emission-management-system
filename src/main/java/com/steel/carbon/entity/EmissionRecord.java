package com.steel.carbon.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmissionRecord extends BaseEntity {
    private Long id;
    private LocalDate recordDate;
    private Long processId;
    private Long materialEnergyId;
    private BigDecimal consumeValue;
    private BigDecimal emissionFactor;
    private BigDecimal emissionValue;
    private Long productionId;
    private Long inputUserId;
    private Integer auditStatus;
    
    private String processName;
    private String materialEnergyName;
    private String materialEnergyType;
    private String inputUserName;
}
