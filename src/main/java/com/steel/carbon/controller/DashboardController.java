package com.steel.carbon.controller;

import com.steel.carbon.entity.SysUser;
import com.steel.carbon.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {

    @Autowired
    private EmissionService emissionService;

    @Autowired
    private SysUserService sysUserService;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        SysUser user = sysUserService.selectByUsername(username);
        
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        LocalDate firstDayOfYear = today.withDayOfYear(1);
        
        BigDecimal totalEmissionMonth = emissionService.getTotalEmission(firstDayOfMonth, today);
        BigDecimal totalEmissionYear = emissionService.getTotalEmission(firstDayOfYear, today);
        List<Map<String, Object>> processStats = emissionService.getProcessStatistics(firstDayOfMonth, today);
        
        model.addAttribute("user", user);
        model.addAttribute("totalEmissionMonth", totalEmissionMonth);
        model.addAttribute("totalEmissionYear", totalEmissionYear);
        model.addAttribute("processStats", processStats);
        
        return "dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
