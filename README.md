# 钢铁企业碳排放数据管理与统计分析系统

基于 Spring Boot 2.7 + MyBatis + Thymeleaf + Spring Security 开发的碳排放管理系统。

## 技术栈

- Spring Boot 2.7.18
- MyBatis
- Spring Security (密码加密)
- Thymeleaf
- MySQL 8.0
- Maven
- 可选：Redis、RocketMQ

## 项目结构

```
src/main/java/com/steel/carbon/
├── config/          # 配置类
├── controller/      # 控制器
├── entity/          # 实体类
├── mapper/          # MyBatis Mapper
├── service/         # 业务层
└── CarbonEmissionApplication.java  # 启动类

src/main/resources/
├── mapper/          # MyBatis XML
├── templates/       # Thymeleaf页面
└── application.yml  # 配置文件
```

## 核心功能

1. **用户与权限管理**：登录、密码加密
2. **基础数据管理**：工序、原料、能源、排放因子
3. **数据录入与自动计算**：录入消耗数据，自动计算碳排放量
4. **统计分析**：按时间、工序统计，可视化展示
5. **预警管理**：预警规则、预警记录
6. **定时任务**：日报月报生成、预警检测

## 快速开始

### 1. 数据库初始化

在 MySQL 中执行 `数据库文件.sql` 脚本，创建数据库和表，并初始化测试数据。

### 2. 修改配置

编辑 `src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/carbon_emission_db?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
```

### 3. 启动项目

```bash
mvn clean install
mvn spring-boot:run
```

### 4. 访问系统

打开浏览器访问：http://localhost:8080

**默认登录账号**：
- 用户名：admin
- 密码：admin123

## 使用说明

1. **数据录入**：选择工序、原料/能源，输入消耗量，提交后自动计算碳排放量
2. **统计分析**：选择月份查看月度碳排放数据和工序统计
3. **预警管理**：查看预警规则和预警记录

## 数据库表说明

- `dept_info`：部门表
- `sys_user`：用户表
- `process_info`：工序表
- `material_energy_info`：原料/能源表（含排放因子）
- `production_record`：产量记录表
- `consumption_record`：消耗记录表
- `emission_record`：排放记录表
- `warning_rule`：预警规则表
- `warning_record`：预警记录表
- `notification`：通知表

## 必做功能完成情况

✅ 用户登录/退出
✅ 工序管理
✅ 原料管理
✅ 能源管理
✅ 排放因子管理
✅ 原料/能源数据录入
✅ 自动排放计算
✅ 排放数据查询
✅ 按月统计分析
✅ 预警记录查看
