package com.steel.carbon.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Notification extends BaseEntity {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String type;
    private Integer isRead;
    
    private String userName;
}
