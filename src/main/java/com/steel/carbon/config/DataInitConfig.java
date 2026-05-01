package com.steel.carbon.config;

import com.steel.carbon.entity.SysUser;
import com.steel.carbon.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 数据初始化配置
 */
@Component
public class DataInitConfig implements CommandLineRunner {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 检查admin用户是否存在，不存在则创建
        SysUser admin = sysUserMapper.selectByUsername("admin");
        if (admin == null) {
            admin = new SysUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRealName("系统管理员");
            admin.setEmail("admin@steel.com");
            admin.setPhone("13800138000");
            admin.setDeptId(1L);
            admin.setStatus(1);
            sysUserMapper.insert(admin);
            System.out.println("初始化admin用户成功！用户名：admin，密码：admin123");
        } else if (!passwordEncoder.matches("admin123", admin.getPassword())) {
            // 如果密码不对，更新为正确的加密密码
            admin.setPassword(passwordEncoder.encode("admin123"));
            sysUserMapper.update(admin);
            System.out.println("已重置admin用户密码为：admin123");
        }
    }
}
