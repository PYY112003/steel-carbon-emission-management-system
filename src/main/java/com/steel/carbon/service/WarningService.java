package com.steel.carbon.service;

import com.steel.carbon.entity.WarningRecord;
import com.steel.carbon.entity.WarningRule;
import com.steel.carbon.mapper.WarningRecordMapper;
import com.steel.carbon.mapper.WarningRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WarningService {

    @Autowired
    private WarningRuleMapper warningRuleMapper;

    @Autowired
    private WarningRecordMapper warningRecordMapper;

    public int addRule(WarningRule warningRule) {
        return warningRuleMapper.insert(warningRule);
    }

    public int updateRule(WarningRule warningRule) {
        return warningRuleMapper.update(warningRule);
    }

    public int deleteRule(Long id) {
        return warningRuleMapper.deleteById(id);
    }

    public WarningRule getRuleById(Long id) {
        return warningRuleMapper.selectById(id);
    }

    public List<WarningRule> getAllRules() {
        return warningRuleMapper.selectAll();
    }

    public List<WarningRule> getActiveRules() {
        return warningRuleMapper.selectByStatus(1);
    }

    public int addRecord(WarningRecord warningRecord) {
        return warningRecordMapper.insert(warningRecord);
    }

    public int updateRecord(WarningRecord warningRecord) {
        return warningRecordMapper.update(warningRecord);
    }

    public List<WarningRecord> getAllRecords() {
        return warningRecordMapper.selectAll();
    }

    public List<WarningRecord> getUnprocessedRecords() {
        return warningRecordMapper.selectByStatus(0);
    }
}
