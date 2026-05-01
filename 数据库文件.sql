-- =========================
-- 钢铁企业碳排放数据管理与统计分析系统
-- MySQL 8.0 建表脚本
-- =========================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- -------------------------
-- 1. 部门表
-- -------------------------
DROP TABLE IF EXISTS dept_info;
CREATE TABLE dept_info (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    dept_name       VARCHAR(100) NOT NULL COMMENT '部门名称',
    manager_name    VARCHAR(50) DEFAULT NULL COMMENT '负责人',
    status          TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用，0停用',
    create_time     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_dept_name (dept_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';


-- -------------------------
-- 2. 用户表
-- -------------------------
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    username        VARCHAR(50) NOT NULL COMMENT '登录名',
    password        VARCHAR(100) NOT NULL COMMENT '密码',
    real_name       VARCHAR(50) NOT NULL COMMENT '真实姓名',
    email           VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    phone           VARCHAR(20) DEFAULT NULL COMMENT '电话',
    dept_id         BIGINT DEFAULT NULL COMMENT '所属部门ID',
    status          TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1正常，0禁用',
    create_time     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_username (username),
    UNIQUE KEY uk_email (email),
    KEY idx_user_dept_id (dept_id),
    CONSTRAINT fk_user_dept
        FOREIGN KEY (dept_id) REFERENCES dept_info(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';


-- -------------------------
-- 3. 工序表
-- -------------------------
DROP TABLE IF EXISTS process_info;
CREATE TABLE process_info (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    process_name    VARCHAR(50) NOT NULL COMMENT '工序名称',
    dept_id         BIGINT NOT NULL COMMENT '所属部门ID',
    description     VARCHAR(255) DEFAULT NULL COMMENT '描述',
    status          TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用，0停用',
    create_time     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_process_name (process_name),
    KEY idx_process_dept_id (dept_id),
    CONSTRAINT fk_process_dept
        FOREIGN KEY (dept_id) REFERENCES dept_info(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工序表';


-- -------------------------
-- 4. 原料/能源表
-- -------------------------
DROP TABLE IF EXISTS material_energy_info;
CREATE TABLE material_energy_info (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name                VARCHAR(100) NOT NULL COMMENT '名称',
    type                VARCHAR(20) NOT NULL COMMENT '类型：原料/能源',
    unit                VARCHAR(20) NOT NULL COMMENT '单位',
    emission_factor     DECIMAL(10,4) NOT NULL DEFAULT 0.0000 COMMENT '排放因子',
    factor_source       VARCHAR(100) DEFAULT NULL COMMENT '因子来源',
    remark              VARCHAR(255) DEFAULT NULL COMMENT '备注',
    status              TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用，0停用',
    create_time         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_material_energy_name_type (name, type),
    KEY idx_material_energy_type (type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='原料/能源基础信息表';


-- -------------------------
-- 5. 产量记录表
-- -------------------------
DROP TABLE IF EXISTS production_record;
CREATE TABLE production_record (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    record_date     DATE NOT NULL COMMENT '记录日期',
    process_id      BIGINT NOT NULL COMMENT '工序ID',
    output_value    DECIMAL(12,2) NOT NULL DEFAULT 0.00 COMMENT '产量',
    unit            VARCHAR(20) NOT NULL COMMENT '单位',
    input_user_id   BIGINT NOT NULL COMMENT '录入人ID',
    create_time     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_production_date (record_date),
    KEY idx_production_process_id (process_id),
    KEY idx_production_input_user_id (input_user_id),
    CONSTRAINT fk_production_process
        FOREIGN KEY (process_id) REFERENCES process_info(id),
    CONSTRAINT fk_production_user
        FOREIGN KEY (input_user_id) REFERENCES sys_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产量记录表';


-- -------------------------
-- 6. 消耗记录表
-- -------------------------
DROP TABLE IF EXISTS consumption_record;
CREATE TABLE consumption_record (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    record_date         DATE NOT NULL COMMENT '记录日期',
    process_id          BIGINT NOT NULL COMMENT '工序ID',
    material_energy_id  BIGINT NOT NULL COMMENT '原料/能源ID',
    consume_value       DECIMAL(12,2) NOT NULL DEFAULT 0.00 COMMENT '消耗量',
    unit                VARCHAR(20) NOT NULL COMMENT '单位',
    input_user_id       BIGINT NOT NULL COMMENT '录入人ID',
    create_time         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_consumption_date (record_date),
    KEY idx_consumption_process_id (process_id),
    KEY idx_consumption_material_energy_id (material_energy_id),
    KEY idx_consumption_input_user_id (input_user_id),
    CONSTRAINT fk_consumption_process
        FOREIGN KEY (process_id) REFERENCES process_info(id),
    CONSTRAINT fk_consumption_material_energy
        FOREIGN KEY (material_energy_id) REFERENCES material_energy_info(id),
    CONSTRAINT fk_consumption_user
        FOREIGN KEY (input_user_id) REFERENCES sys_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消耗记录表';


-- -------------------------
-- 7. 排放记录表
-- -------------------------
DROP TABLE IF EXISTS emission_record;
CREATE TABLE emission_record (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    record_date         DATE NOT NULL COMMENT '记录日期',
    process_id          BIGINT NOT NULL COMMENT '工序ID',
    material_energy_id  BIGINT NOT NULL COMMENT '原料/能源ID',
    consume_value       DECIMAL(12,2) NOT NULL DEFAULT 0.00 COMMENT '消耗量',
    emission_factor     DECIMAL(10,4) NOT NULL DEFAULT 0.0000 COMMENT '排放因子',
    emission_value      DECIMAL(12,4) NOT NULL DEFAULT 0.0000 COMMENT '排放量',
    production_id       BIGINT DEFAULT NULL COMMENT '关联产量记录ID',
    input_user_id       BIGINT NOT NULL COMMENT '录入人ID',
    audit_status        TINYINT NOT NULL DEFAULT 0 COMMENT '审核状态：0未审核，1已审核，2驳回',
    create_time         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_emission_date (record_date),
    KEY idx_emission_process_id (process_id),
    KEY idx_emission_material_energy_id (material_energy_id),
    KEY idx_emission_production_id (production_id),
    KEY idx_emission_input_user_id (input_user_id),
    CONSTRAINT fk_emission_process
        FOREIGN KEY (process_id) REFERENCES process_info(id),
    CONSTRAINT fk_emission_material_energy
        FOREIGN KEY (material_energy_id) REFERENCES material_energy_info(id),
    CONSTRAINT fk_emission_production
        FOREIGN KEY (production_id) REFERENCES production_record(id),
    CONSTRAINT fk_emission_user
        FOREIGN KEY (input_user_id) REFERENCES sys_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='排放记录表';


-- -------------------------
-- 8. 预警规则表
-- -------------------------
DROP TABLE IF EXISTS warning_rule;
CREATE TABLE warning_rule (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    rule_name           VARCHAR(100) NOT NULL COMMENT '规则名称',
    warning_type        VARCHAR(50) NOT NULL COMMENT '预警类型',
    target_process_id   BIGINT DEFAULT NULL COMMENT '目标工序ID',
    threshold_value     DECIMAL(12,2) NOT NULL DEFAULT 0.00 COMMENT '阈值',
    status              TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用，0停用',
    create_time         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_warning_rule_process_id (target_process_id),
    CONSTRAINT fk_warning_rule_process
        FOREIGN KEY (target_process_id) REFERENCES process_info(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预警规则表';


-- -------------------------
-- 9. 预警记录表
-- -------------------------
DROP TABLE IF EXISTS warning_record;
CREATE TABLE warning_record (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    rule_id             BIGINT NOT NULL COMMENT '规则ID',
    process_id          BIGINT DEFAULT NULL COMMENT '工序ID',
    warning_content     VARCHAR(255) NOT NULL COMMENT '预警内容',
    level               VARCHAR(20) NOT NULL COMMENT '预警级别：低/中/高',
    status              TINYINT NOT NULL DEFAULT 0 COMMENT '处理状态：0未处理，1已处理',
    create_time         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_warning_record_rule_id (rule_id),
    KEY idx_warning_record_process_id (process_id),
    CONSTRAINT fk_warning_record_rule
        FOREIGN KEY (rule_id) REFERENCES warning_rule(id),
    CONSTRAINT fk_warning_record_process
        FOREIGN KEY (process_id) REFERENCES process_info(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预警记录表';


-- -------------------------
-- 10. 通知表
-- -------------------------
DROP TABLE IF EXISTS notification;
CREATE TABLE notification (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id         BIGINT NOT NULL COMMENT '接收人ID',
    title           VARCHAR(100) NOT NULL COMMENT '通知标题',
    content         VARCHAR(255) NOT NULL COMMENT '通知内容',
    type            VARCHAR(50) NOT NULL COMMENT '通知类型',
    is_read         TINYINT NOT NULL DEFAULT 0 COMMENT '是否已读：0否，1是',
    create_time     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_notification_user_id (user_id),
    KEY idx_notification_is_read (is_read),
    CONSTRAINT fk_notification_user
        FOREIGN KEY (user_id) REFERENCES sys_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知表';

SET FOREIGN_KEY_CHECKS = 1;

-- =========================
-- 初始化测试数据
-- =========================

-- 插入部门数据
INSERT INTO dept_info (dept_name, manager_name, status) VALUES
('烧结车间', '张三', 1),
('炼铁车间', '李四', 1),
('炼钢车间', '王五', 1),
('轧钢车间', '赵六', 1);

-- 插入用户数据（密码都是 admin123，BCrypt加密后）
INSERT INTO sys_user (username, password, real_name, email, phone, dept_id, status) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '系统管理员', 'admin@steel.com', '13800138000', 1, 1);

-- 插入工序数据
INSERT INTO process_info (process_name, dept_id, description, status) VALUES
('原料烧结', 1, '将铁矿粉烧结成烧结矿', 1),
('高炉炼铁', 2, '使用高炉将铁矿石还原为生铁', 1),
('转炉炼钢', 3, '将生铁炼成钢水', 1),
('连铸', 3, '将钢水铸成钢坯', 1),
('热轧', 4, '将钢坯热轧成钢材', 1);

-- 插入原料数据
INSERT INTO material_energy_info (name, type, unit, emission_factor, factor_source, status) VALUES
('铁矿石', '原料', '吨', 0.25, 'IPCC指南', 1),
('焦炭', '原料', '吨', 3.0, 'IPCC指南', 1),
('石灰石', '原料', '吨', 0.5, 'IPCC指南', 1),
('废钢', '原料', '吨', 0.1, 'IPCC指南', 1);

-- 插入能源数据
INSERT INTO material_energy_info (name, type, unit, emission_factor, factor_source, status) VALUES
('电力', '能源', 'kWh', 0.581, '中国区域电网平均排放因子', 1),
('天然气', '能源', '立方米', 2.0, 'IPCC指南', 1),
('蒸汽', '能源', '吨', 0.1, '企业实测', 1),
('高炉煤气', '能源', '立方米', 0.2, '企业实测', 1);

-- 插入预警规则
INSERT INTO warning_rule (rule_name, warning_type, target_process_id, threshold_value, status) VALUES
('高炉日排放超限', '排放', 2, 1000.00, 1),
('转炉能耗过高', '能耗', 3, 500.00, 1);