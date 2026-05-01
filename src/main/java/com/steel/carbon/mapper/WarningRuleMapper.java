package com.steel.carbon.mapper;

import com.steel.carbon.entity.WarningRule;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface WarningRuleMapper {
    
    int insert(WarningRule warningRule);
    
    int update(WarningRule warningRule);
    
    int deleteById(Long id);
    
    WarningRule selectById(Long id);
    
    List<WarningRule> selectAll();
    
    List<WarningRule> selectByStatus(Integer status);
}
