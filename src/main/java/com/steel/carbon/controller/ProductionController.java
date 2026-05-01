package com.steel.carbon.controller;

import com.steel.carbon.entity.ProductionRecord;
import com.steel.carbon.entity.SysUser;
import com.steel.carbon.mapper.ProductionRecordMapper;
import com.steel.carbon.service.ProcessInfoService;
import com.steel.carbon.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/production")
public class ProductionController {

    @Autowired
    private ProductionRecordMapper productionRecordMapper;

    @Autowired
    private ProcessInfoService processInfoService;

    @Autowired
    private SysUserService sysUserService;

    @GetMapping
    public String productionList(Model model) {
        model.addAttribute("records", productionRecordMapper.selectAll());
        return "production-list";
    }

    @GetMapping("/add")
    public String productionAdd(Model model) {
        model.addAttribute("processes", processInfoService.selectByStatus(1));
        return "production-form";
    }

    @PostMapping("/add")
    public String productionAddSubmit(Authentication authentication,
                                     @RequestParam Long processId,
                                     @RequestParam String recordDate,
                                     @RequestParam BigDecimal outputValue,
                                     @RequestParam String unit) {
        String username = authentication.getName();
        SysUser user = sysUserService.selectByUsername(username);
        
        ProductionRecord record = new ProductionRecord();
        record.setRecordDate(LocalDate.parse(recordDate));
        record.setProcessId(processId);
        record.setOutputValue(outputValue);
        record.setUnit(unit);
        record.setInputUserId(user.getId());
        
        productionRecordMapper.insert(record);
        return "redirect:/production";
    }

    @GetMapping("/edit/{id}")
    public String productionEdit(@PathVariable Long id, Model model) {
        model.addAttribute("record", productionRecordMapper.selectById(id));
        model.addAttribute("processes", processInfoService.selectByStatus(1));
        return "production-form";
    }

    @PostMapping("/edit/{id}")
    public String productionEditSubmit(@PathVariable Long id,
                                       @RequestParam Long processId,
                                       @RequestParam String recordDate,
                                       @RequestParam BigDecimal outputValue,
                                       @RequestParam String unit) {
        ProductionRecord record = new ProductionRecord();
        record.setId(id);
        record.setRecordDate(LocalDate.parse(recordDate));
        record.setProcessId(processId);
        record.setOutputValue(outputValue);
        record.setUnit(unit);
        
        productionRecordMapper.update(record);
        return "redirect:/production";
    }

    @GetMapping("/delete/{id}")
    public String productionDelete(@PathVariable Long id) {
        productionRecordMapper.deleteById(id);
        return "redirect:/production";
    }
}
