package com.steel.carbon.controller;

import com.steel.carbon.common.Result;
import com.steel.carbon.entity.DeptInfo;
import com.steel.carbon.entity.MaterialEnergyInfo;
import com.steel.carbon.entity.ProcessInfo;
import com.steel.carbon.service.DeptInfoService;
import com.steel.carbon.service.MaterialEnergyInfoService;
import com.steel.carbon.service.ProcessInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basic-data")
public class BasicDataController {

    @Autowired
    private DeptInfoService deptInfoService;

    @Autowired
    private ProcessInfoService processInfoService;

    @Autowired
    private MaterialEnergyInfoService materialEnergyInfoService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("depts", deptInfoService.selectAll());
        model.addAttribute("processes", processInfoService.selectAll());
        model.addAttribute("materials", materialEnergyInfoService.selectByType("原料"));
        model.addAttribute("energies", materialEnergyInfoService.selectByType("能源"));
        return "basic-data";
    }

    @GetMapping("/dept")
    public String deptList(Model model) {
        model.addAttribute("depts", deptInfoService.selectAll());
        return "dept-list";
    }

    @GetMapping("/dept/add")
    public String deptAdd() {
        return "dept-form";
    }

    @PostMapping("/dept/add")
    public String deptAddSubmit(DeptInfo deptInfo) {
        deptInfo.setStatus(1);
        deptInfoService.insert(deptInfo);
        return "redirect:/basic-data/dept";
    }

    @GetMapping("/dept/edit/{id}")
    public String deptEdit(@PathVariable Long id, Model model) {
        model.addAttribute("dept", deptInfoService.selectById(id));
        return "dept-form";
    }

    @PostMapping("/dept/edit/{id}")
    public String deptEditSubmit(@PathVariable Long id, DeptInfo deptInfo) {
        deptInfo.setId(id);
        deptInfoService.update(deptInfo);
        return "redirect:/basic-data/dept";
    }

    @PostMapping("/dept/delete/{id}")
    @ResponseBody
    public Result deptDelete(@PathVariable Long id) {
        deptInfoService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/process")
    public String processList(Model model) {
        model.addAttribute("processes", processInfoService.selectAll());
        model.addAttribute("depts", deptInfoService.selectAll());
        return "process-list";
    }

    @GetMapping("/process/add")
    public String processAdd(Model model) {
        model.addAttribute("depts", deptInfoService.selectByStatus(1));
        return "process-form";
    }

    @PostMapping("/process/add")
    public String processAddSubmit(ProcessInfo processInfo) {
        processInfo.setStatus(1);
        processInfoService.insert(processInfo);
        return "redirect:/basic-data/process";
    }

    @GetMapping("/process/edit/{id}")
    public String processEdit(@PathVariable Long id, Model model) {
        model.addAttribute("process", processInfoService.selectById(id));
        model.addAttribute("depts", deptInfoService.selectByStatus(1));
        return "process-form";
    }

    @PostMapping("/process/edit/{id}")
    public String processEditSubmit(@PathVariable Long id, ProcessInfo processInfo) {
        processInfo.setId(id);
        processInfoService.update(processInfo);
        return "redirect:/basic-data/process";
    }

    @PostMapping("/process/delete/{id}")
    @ResponseBody
    public Result processDelete(@PathVariable Long id) {
        processInfoService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/material-energy")
    public String materialEnergyList(Model model, @RequestParam(required = false) String type) {
        if (type == null) type = "原料";
        model.addAttribute("type", type);
        model.addAttribute("items", materialEnergyInfoService.selectByType(type));
        return "material-energy-list";
    }

    @GetMapping("/material-energy/add")
    public String materialEnergyAdd(Model model, @RequestParam(required = false) String type) {
        if (type == null) type = "原料";
        model.addAttribute("type", type);
        return "material-energy-form";
    }

    @PostMapping("/material-energy/add")
    public String materialEnergyAddSubmit(MaterialEnergyInfo info) {
        info.setStatus(1);
        materialEnergyInfoService.insert(info);
        return "redirect:/basic-data/material-energy?type=" + info.getType();
    }

    @GetMapping("/material-energy/edit/{id}")
    public String materialEnergyEdit(@PathVariable Long id, Model model) {
        MaterialEnergyInfo info = materialEnergyInfoService.selectById(id);
        model.addAttribute("item", info);
        model.addAttribute("type", info.getType());
        return "material-energy-form";
    }

    @PostMapping("/material-energy/edit/{id}")
    public String materialEnergyEditSubmit(@PathVariable Long id, MaterialEnergyInfo info) {
        info.setId(id);
        materialEnergyInfoService.update(info);
        return "redirect:/basic-data/material-energy?type=" + info.getType();
    }

    @PostMapping("/material-energy/delete/{id}")
    @ResponseBody
    public Result materialEnergyDelete(@PathVariable Long id) {
        materialEnergyInfoService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/dept/status/{id}")
    @ResponseBody
    public Result<Void> toggleDeptStatus(@PathVariable Long id) {
        DeptInfo dept = deptInfoService.selectById(id);
        dept.setStatus(dept.getStatus() == 1 ? 0 : 1);
        deptInfoService.update(dept);
        return Result.success();
    }

    @PostMapping("/process/status/{id}")
    @ResponseBody
    public Result<Void> toggleProcessStatus(@PathVariable Long id) {
        ProcessInfo process = processInfoService.selectById(id);
        process.setStatus(process.getStatus() == 1 ? 0 : 1);
        processInfoService.update(process);
        return Result.success();
    }

    @PostMapping("/material-energy/status/{id}")
    @ResponseBody
    public Result<Void> toggleMaterialEnergyStatus(@PathVariable Long id) {
        MaterialEnergyInfo info = materialEnergyInfoService.selectById(id);
        info.setStatus(info.getStatus() == 1 ? 0 : 1);
        materialEnergyInfoService.update(info);
        return Result.success();
    }
}
