package com.steel.carbon.mapper;

import com.steel.carbon.entity.EmissionRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmissionRecordMapper {
    
    int insert(EmissionRecord emissionRecord);
    
    int update(EmissionRecord emissionRecord);
    
    int deleteById(Long id);
    
    EmissionRecord selectById(Long id);
    
    List<EmissionRecord> selectAll();
    
    List<EmissionRecord> selectByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    List<EmissionRecord> selectByProcessId(Long processId);
    
    List<Map<String, Object>> selectMonthlyStatistics(@Param("year") Integer year, @Param("month") Integer month);
    
    List<Map<String, Object>> selectByProcessStatistics(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    BigDecimal selectTotalEmission(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
