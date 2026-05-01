package com.steel.carbon.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WarningRecord extends BaseEntity {
    private Long id;
    private Long ruleId;
    private Long processId;
    private String warningContent;
    private String level;
    private Integer status;
    
    private String ruleName;
    private String processName;
}
