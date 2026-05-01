package com.steel.carbon.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeptInfo extends BaseEntity {
    private Long id;
    private String deptName;
    private String managerName;
    private Integer status;
}
