package com.steel.carbon.service;

import com.steel.carbon.entity.ProcessInfo;
import com.steel.carbon.mapper.ProcessInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProcessInfoService {

    @Autowired
    private ProcessInfoMapper processInfoMapper;

    public int insert(ProcessInfo processInfo) {
        return processInfoMapper.insert(processInfo);
    }

    public int update(ProcessInfo processInfo) {
        return processInfoMapper.update(processInfo);
    }

    public int deleteById(Long id) {
        return processInfoMapper.deleteById(id);
    }

    public ProcessInfo selectById(Long id) {
        return processInfoMapper.selectById(id);
    }

    public List<ProcessInfo> selectAll() {
        return processInfoMapper.selectAll();
    }

    public List<ProcessInfo> selectByDeptId(Long deptId) {
        return processInfoMapper.selectByDeptId(deptId);
    }

    public List<ProcessInfo> selectByStatus(Integer status) {
        return processInfoMapper.selectByStatus(status);
    }
}
