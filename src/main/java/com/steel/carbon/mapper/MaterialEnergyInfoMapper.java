package com.steel.carbon.mapper;

import com.steel.carbon.entity.MaterialEnergyInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface MaterialEnergyInfoMapper {
    
    int insert(MaterialEnergyInfo materialEnergyInfo);
    
    int update(MaterialEnergyInfo materialEnergyInfo);
    
    int deleteById(Long id);
    
    MaterialEnergyInfo selectById(Long id);
    
    List<MaterialEnergyInfo> selectAll();
    
    List<MaterialEnergyInfo> selectByType(String type);
    
    List<MaterialEnergyInfo> selectByStatus(Integer status);
}
