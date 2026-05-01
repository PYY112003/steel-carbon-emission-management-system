package com.steel.carbon.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class MaterialEnergyInfo extends BaseEntity {
    private Long id;
    private String name;
    private String type;
    private String unit;
    private BigDecimal emissionFactor;
    private String factorSource;
    private String remark;
    private Integer status;
}
