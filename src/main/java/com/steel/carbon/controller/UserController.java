package com.steel.carbon.controller;

import com.steel.carbon.entity.DeptInfo;
import com.steel.carbon.entity.SysUser;
import com.steel.carbon.service.DeptInfoService;
import com.steel.carbon.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DeptInfoService deptInfoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String register(Model model) {
        List<DeptInfo> depts = deptInfoService.selectAll();
        model.addAttribute("depts", depts);
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(Model model,
                             @RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String confirmPassword,
                             @RequestParam String realName,
                             @RequestParam(required = false) String email,
                             @RequestParam(required = false) String phone,
                             @RequestParam(required = false) Long deptId) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "两次输入的密码不一致");
            model.addAttribute("depts", deptInfoService.selectAll());
            return "register";
        }
        if (sysUserService.selectByUsername(username) != null) {
            model.addAttribute("error", "该用户名已存在");
            model.addAttribute("depts", deptInfoService.selectAll());
            return "register";
        }
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setRealName(realName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setDeptId(deptId);
        user.setStatus(1);
        sysUserService.insert(user);
        model.addAttribute("success", "注册成功，请登录");
        model.addAttribute("depts", deptInfoService.selectAll());
        return "register";
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        String username = authentication.getName();
        SysUser user = sysUserService.selectByUsername(username);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model, Authentication authentication) {
        String username = authentication.getName();
        SysUser user = sysUserService.selectByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("depts", deptInfoService.selectAll());
        return "edit-profile";
    }

    @PostMapping("/profile/edit")
    public String doEditProfile(Model model, Authentication authentication,
                                @RequestParam String realName,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) String phone,
                                @RequestParam(required = false) Long deptId) {
        String username = authentication.getName();
        SysUser user = sysUserService.selectByUsername(username);
        user.setRealName(realName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setDeptId(deptId);
        sysUserService.update(user);
        model.addAttribute("success", "信息修改成功");
        model.addAttribute("user", sysUserService.selectByUsername(username));
        return "profile";
    }

    @GetMapping("/profile/change-password")
    public String changePassword() {
        return "change-password";
    }

    @PostMapping("/profile/change-password")
    public String doChangePassword(Model model, Authentication authentication,
                                   @RequestParam String oldPassword,
                                   @RequestParam String newPassword,
                                   @RequestParam String confirmPassword) {
        String username = authentication.getName();
        SysUser user = sysUserService.selectByUsername(username);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            model.addAttribute("error", "当前密码不正确");
            return "change-password";
        }
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "两次输入的新密码不一致");
            return "change-password";
        }
        user.setPassword(newPassword);
        sysUserService.update(user);
        model.addAttribute("success", "密码修改成功");
        return "change-password";
    }
}
