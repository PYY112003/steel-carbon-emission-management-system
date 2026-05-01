package com.steel.carbon.task;

import com.steel.carbon.entity.*;
import com.steel.carbon.mapper.*;
import com.steel.carbon.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private EmissionRecordMapper emissionRecordMapper;

    @Autowired
    private WarningRuleMapper warningRuleMapper;

    @Autowired
    private WarningRecordMapper warningRecordMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void dailyStatistics() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        logger.info("开始执行每日统计任务，日期：{}", yesterday);
        
        BigDecimal totalEmission = emissionRecordMapper.selectTotalEmission(yesterday, yesterday);
        logger.info("{} 日碳排放总量：{} 吨CO₂", yesterday, totalEmission);
    }

    @Scheduled(cron = "0 0 2 1 * ?")
    public void monthlyStatistics() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfLastMonth = today.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfLastMonth = today.withDayOfMonth(1).minusDays(1);
        
        int year = lastDayOfLastMonth.getYear();
        int month = lastDayOfLastMonth.getMonthValue();
        
        logger.info("开始执行每月统计任务，统计 {}年{}月数据", year, month);
        
        BigDecimal totalEmission = emissionRecordMapper.selectTotalEmission(firstDayOfLastMonth, lastDayOfLastMonth);
        logger.info("{}年{}月碳排放总量：{} 吨CO₂", year, month, totalEmission);
        
        List<SysUser> users = sysUserMapper.selectAll();
        for (SysUser user : users) {
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                try {
                    emailService.sendReportReminder(user.getEmail(), year, month);
                    logger.info("已发送月报提醒邮件给：{}", user.getEmail());
                } catch (Exception e) {
                    logger.error("发送邮件失败：{}", e.getMessage());
                }
            }
        }
    }

    @Scheduled(cron = "0 0 */6 * * ?")
    public void warningCheck() {
        logger.info("开始执行定时预警检查");
        
        List<WarningRule> rules = warningRuleMapper.selectByStatus(1);
        LocalDate yesterday = LocalDate.now().minusDays(1);
        
        for (WarningRule rule : rules) {
            BigDecimal totalEmission;
            if (rule.getTargetProcessId() != null) {
                totalEmission = new BigDecimal("0");
            } else {
                totalEmission = emissionRecordMapper.selectTotalEmission(yesterday, yesterday);
            }
            
            if (totalEmission.compareTo(rule.getThresholdValue()) > 0) {
                WarningRecord record = new WarningRecord();
                record.setRuleId(rule.getId());
                record.setProcessId(rule.getTargetProcessId());
                record.setWarningContent("排放超标预警：" + rule.getRuleName() + "，当前值：" + totalEmission + "，阈值：" + rule.getThresholdValue());
                record.setLevel(totalEmission.compareTo(rule.getThresholdValue().multiply(new BigDecimal("1.5"))) > 0 ? "高" : "中");
                record.setStatus(0);
                
                warningRecordMapper.insert(record);
                logger.info("生成预警记录：{}", record.getWarningContent());
                
                List<SysUser> users = sysUserMapper.selectAll();
                for (SysUser user : users) {
                    if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                        try {
                            emailService.sendWarningEmail(user.getEmail(), "碳排放预警提醒", record.getWarningContent());
                        } catch (Exception e) {
                            logger.error("发送预警邮件失败：{}", e.getMessage());
                        }
                    }
                }
            }
        }
    }
}
