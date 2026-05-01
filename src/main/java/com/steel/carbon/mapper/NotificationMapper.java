package com.steel.carbon.mapper;

import com.steel.carbon.entity.Notification;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface NotificationMapper {
    
    int insert(Notification notification);
    
    int update(Notification notification);
    
    int deleteById(Long id);
    
    Notification selectById(Long id);
    
    List<Notification> selectByUserId(Long userId);
    
    List<Notification> selectUnreadByUserId(Long userId);
    
    int countUnreadByUserId(Long userId);
}
