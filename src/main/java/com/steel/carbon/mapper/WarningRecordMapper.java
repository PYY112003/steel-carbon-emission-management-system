package com.steel.carbon.mapper;

import com.steel.carbon.entity.WarningRecord;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface WarningRecordMapper {
    
    int insert(WarningRecord warningRecord);
    
    int update(WarningRecord warningRecord);
    
    int deleteById(Long id);
    
    WarningRecord selectById(Long id);
    
    List<WarningRecord> selectAll();
    
    List<WarningRecord> selectByStatus(Integer status);
}
