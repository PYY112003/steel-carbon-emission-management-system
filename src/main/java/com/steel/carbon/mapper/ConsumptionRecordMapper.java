package com.steel.carbon.mapper;

import com.steel.carbon.entity.ConsumptionRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ConsumptionRecordMapper {
    
    int insert(ConsumptionRecord consumptionRecord);
    
    int update(ConsumptionRecord consumptionRecord);
    
    int deleteById(Long id);
    
    ConsumptionRecord selectById(Long id);
    
    List<ConsumptionRecord> selectAll();
    
    List<ConsumptionRecord> selectByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    List<ConsumptionRecord> selectByProcessId(Long processId);
}
