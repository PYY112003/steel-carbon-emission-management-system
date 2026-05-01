package com.steel.carbon.mapper;

import com.steel.carbon.entity.ProcessInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ProcessInfoMapper {
    
    int insert(ProcessInfo processInfo);
    
    int update(ProcessInfo processInfo);
    
    int deleteById(Long id);
    
    ProcessInfo selectById(Long id);
    
    List<ProcessInfo> selectAll();
    
    List<ProcessInfo> selectByDeptId(Long deptId);
    
    List<ProcessInfo> selectByStatus(Integer status);
}
