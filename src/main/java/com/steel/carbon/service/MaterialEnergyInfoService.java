package com.steel.carbon.service;

import com.steel.carbon.entity.MaterialEnergyInfo;
import com.steel.carbon.mapper.MaterialEnergyInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MaterialEnergyInfoService {

    @Autowired
    private MaterialEnergyInfoMapper materialEnergyInfoMapper;

    public int insert(MaterialEnergyInfo materialEnergyInfo) {
        return materialEnergyInfoMapper.insert(materialEnergyInfo);
    }

    public int update(MaterialEnergyInfo materialEnergyInfo) {
        return materialEnergyInfoMapper.update(materialEnergyInfo);
    }

    public int deleteById(Long id) {
        return materialEnergyInfoMapper.deleteById(id);
    }

    public MaterialEnergyInfo selectById(Long id) {
        return materialEnergyInfoMapper.selectById(id);
    }

    public List<MaterialEnergyInfo> selectAll() {
        return materialEnergyInfoMapper.selectAll();
    }

    public List<MaterialEnergyInfo> selectByType(String type) {
        return materialEnergyInfoMapper.selectByType(type);
    }

    public List<MaterialEnergyInfo> selectByStatus(Integer status) {
        return materialEnergyInfoMapper.selectByStatus(status);
    }
}
