package com.steel.carbon.service;

import com.steel.carbon.entity.SysUser;
import com.steel.carbon.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public int insert(SysUser sysUser) {
        if (sysUser.getPassword() != null) {
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        }
        return sysUserMapper.insert(sysUser);
    }

    public int update(SysUser sysUser) {
        if (sysUser.getPassword() != null && !sysUser.getPassword().isEmpty()) {
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        }
        return sysUserMapper.update(sysUser);
    }

    public int deleteById(Long id) {
        return sysUserMapper.deleteById(id);
    }

    public SysUser selectById(Long id) {
        return sysUserMapper.selectById(id);
    }

    public SysUser selectByUsername(String username) {
        return sysUserMapper.selectByUsername(username);
    }

    public List<SysUser> selectAll() {
        return sysUserMapper.selectAll();
    }

    public List<SysUser> selectByDeptId(Long deptId) {
        return sysUserMapper.selectByDeptId(deptId);
    }
}
