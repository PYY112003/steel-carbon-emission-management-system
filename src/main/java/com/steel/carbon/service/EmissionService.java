package com.steel.carbon.service;

import com.steel.carbon.entity.ConsumptionRecord;
import com.steel.carbon.entity.EmissionRecord;
import com.steel.carbon.entity.MaterialEnergyInfo;
import com.steel.carbon.mapper.ConsumptionRecordMapper;
import com.steel.carbon.mapper.EmissionRecordMapper;
import com.steel.carbon.mapper.MaterialEnergyInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class EmissionService {

    @Autowired
    private ConsumptionRecordMapper consumptionRecordMapper;

    @Autowired
    private EmissionRecordMapper emissionRecordMapper;

    @Autowired
    private MaterialEnergyInfoMapper materialEnergyInfoMapper;

    @Transactional
    public int addConsumptionAndCalculateEmission(ConsumptionRecord consumptionRecord) {
        int result = consumptionRecordMapper.insert(consumptionRecord);
        
        MaterialEnergyInfo me = materialEnergyInfoMapper.selectById(consumptionRecord.getMaterialEnergyId());
        if (me != null && me.getEmissionFactor() != null) {
            BigDecimal emissionValue = consumptionRecord.getConsumeValue().multiply(me.getEmissionFactor());
            
            EmissionRecord emissionRecord = new EmissionRecord();
            emissionRecord.setRecordDate(consumptionRecord.getRecordDate());
            emissionRecord.setProcessId(consumptionRecord.getProcessId());
            emissionRecord.setMaterialEnergyId(consumptionRecord.getMaterialEnergyId());
            emissionRecord.setConsumeValue(consumptionRecord.getConsumeValue());
            emissionRecord.setEmissionFactor(me.getEmissionFactor());
            emissionRecord.setEmissionValue(emissionValue);
            emissionRecord.setInputUserId(consumptionRecord.getInputUserId());
            emissionRecord.setAuditStatus(0);
            
            emissionRecordMapper.insert(emissionRecord);
        }
        
        return result;
    }

    public List<EmissionRecord> getEmissionByDateRange(LocalDate startDate, LocalDate endDate) {
        return emissionRecordMapper.selectByDateRange(startDate, endDate);
    }

    public List<Map<String, Object>> getMonthlyStatistics(Integer year, Integer month) {
        return emissionRecordMapper.selectMonthlyStatistics(year, month);
    }

    public List<Map<String, Object>> getProcessStatistics(LocalDate startDate, LocalDate endDate) {
        return emissionRecordMapper.selectByProcessStatistics(startDate, endDate);
    }

    public BigDecimal getTotalEmission(LocalDate startDate, LocalDate endDate) {
        return emissionRecordMapper.selectTotalEmission(startDate, endDate);
    }

    public List<ConsumptionRecord> getAllConsumption() {
        return consumptionRecordMapper.selectAll();
    }

    public List<EmissionRecord> getAllEmission() {
        return emissionRecordMapper.selectAll();
    }
}
