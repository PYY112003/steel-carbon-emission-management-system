package com.steel.carbon.controller;

import com.steel.carbon.common.Result;
import com.steel.carbon.entity.*;
import com.steel.carbon.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class DataController {

    @Autowired
    private ProcessInfoService processInfoService;

    @Autowired
    private MaterialEnergyInfoService materialEnergyInfoService;

    @Autowired
    private EmissionService emissionService;

    @Autowired
    private WarningService warningService;

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/data/basic-data")
    public String basicData(Model model) {
        model.addAttribute("processes", processInfoService.selectAll());
        model.addAttribute("materials", materialEnergyInfoService.selectByType("原料"));
        model.addAttribute("energies", materialEnergyInfoService.selectByType("能源"));
        return "basic-data";
    }

    @GetMapping("/data-entry")
    public String dataEntry(Model model) {
        model.addAttribute("processes", processInfoService.selectByStatus(1));
        model.addAttribute("materials", materialEnergyInfoService.selectByType("原料"));
        model.addAttribute("energies", materialEnergyInfoService.selectByType("能源"));
        return "data-entry";
    }

    @PostMapping("/api/consumption")
    @ResponseBody
    public Result<Void> addConsumption(@RequestBody ConsumptionRecord record, Authentication authentication) {
        String username = authentication.getName();
        SysUser user = sysUserService.selectByUsername(username);
        record.setInputUserId(user.getId());
        record.setRecordDate(LocalDate.now());
        emissionService.addConsumptionAndCalculateEmission(record);
        return Result.success();
    }

    @GetMapping("/statistics")
    public String statistics(Model model, 
                             @RequestParam(required = false) Integer year,
                             @RequestParam(required = false) Integer month) {
        if (year == null) year = LocalDate.now().getYear();
        if (month == null) month = LocalDate.now().getMonthValue();
        
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        
        List<Map<String, Object>> monthlyStats = emissionService.getMonthlyStatistics(year, month);
        List<Map<String, Object>> processStats = emissionService.getProcessStatistics(startDate, endDate);
        List<EmissionRecord> emissions = emissionService.getEmissionByDateRange(startDate, endDate);
        
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("monthlyStats", monthlyStats);
        model.addAttribute("processStats", processStats);
        model.addAttribute("emissions", emissions);
        
        return "statistics";
    }

    @GetMapping("/warnings")
    public String warnings(Model model) {
        model.addAttribute("rules", warningService.getAllRules());
        model.addAttribute("records", warningService.getAllRecords());
        return "warnings";
    }
}
