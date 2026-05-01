package com.steel.carbon.mapper;

import com.steel.carbon.entity.ProductionRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ProductionRecordMapper {
    
    int insert(ProductionRecord productionRecord);
    
    int update(ProductionRecord productionRecord);
    
    int deleteById(Long id);
    
    ProductionRecord selectById(Long id);
    
    List<ProductionRecord> selectAll();
    
    List<ProductionRecord> selectByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    List<ProductionRecord> selectByProcessId(Long processId);
}
