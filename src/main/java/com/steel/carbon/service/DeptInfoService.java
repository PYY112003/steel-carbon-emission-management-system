package com.steel.carbon.service;

import com.steel.carbon.entity.DeptInfo;
import com.steel.carbon.mapper.DeptInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeptInfoService {

    @Autowired
    private DeptInfoMapper deptInfoMapper;

    public int insert(DeptInfo deptInfo) {
        return deptInfoMapper.insert(deptInfo);
    }

    public int update(DeptInfo deptInfo) {
        return deptInfoMapper.update(deptInfo);
    }

    public int deleteById(Long id) {
        return deptInfoMapper.deleteById(id);
    }

    public DeptInfo selectById(Long id) {
        return deptInfoMapper.selectById(id);
    }

    public List<DeptInfo> selectAll() {
        return deptInfoMapper.selectAll();
    }

    public List<DeptInfo> selectByStatus(Integer status) {
        return deptInfoMapper.selectByStatus(status);
    }
}
