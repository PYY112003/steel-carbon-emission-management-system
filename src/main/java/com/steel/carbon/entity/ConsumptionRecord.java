package com.steel.carbon.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class ConsumptionRecord extends BaseEntity {
    private Long id;
    private LocalDate recordDate;
    private Long processId;
    private Long materialEnergyId;
    private BigDecimal consumeValue;
    private String unit;
    private Long inputUserId;
    
    private String processName;
    private String materialEnergyName;
    private String materialEnergyType;
    private String inputUserName;
}
