package com.steel.carbon.mapper;

import com.steel.carbon.entity.DeptInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface DeptInfoMapper {
    
    int insert(DeptInfo deptInfo);
    
    int update(DeptInfo deptInfo);
    
    int deleteById(Long id);
    
    DeptInfo selectById(Long id);
    
    List<DeptInfo> selectAll();
    
    List<DeptInfo> selectByStatus(Integer status);
}
