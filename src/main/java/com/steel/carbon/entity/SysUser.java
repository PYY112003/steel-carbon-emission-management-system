package com.steel.carbon.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {
    private Long id;
    private String username;
    private String password;
    private String realName;
    private String email;
    private String phone;
    private Long deptId;
    private Integer status;
    
    private String deptName;
}
