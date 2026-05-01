package com.steel.carbon.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProcessInfo extends BaseEntity {
    private Long id;
    private String processName;
    private Long deptId;
    private String description;
    private Integer status;
    
    private String deptName;
}
