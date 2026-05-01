package com.steel.carbon.mapper;

import com.steel.carbon.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SysUserMapper {
    
    int insert(SysUser sysUser);
    
    int update(SysUser sysUser);
    
    int deleteById(Long id);
    
    SysUser selectById(Long id);
    
    SysUser selectByUsername(String username);
    
    List<SysUser> selectAll();
    
    List<SysUser> selectByDeptId(Long deptId);
}
