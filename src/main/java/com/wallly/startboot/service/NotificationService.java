package com.wallly.startboot.service;

import com.wallly.startboot.Mapper.NotificationMapper;
import com.wallly.startboot.Model.Notification;
import com.wallly.startboot.Model.User;
import com.wallly.startboot.dto.NotificationDTO;
import com.wallly.startboot.dto.PaginationDTO;
import com.wallly.startboot.enums.NotificationStatusEnum;
import com.wallly.startboot.enums.NotificationTypeEnum;
import com.wallly.startboot.exception.CustomizeErrorCode;
import com.wallly.startboot.exception.CustomizeException;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    public PaginationDTO list(Integer id, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO();
        paginationDTO.setTotalCount(notificationMapper.countByAccountId(id));
        /**
         * 现在totalPage已经算出来了
         */
        if (paginationDTO.getTotalCount() % size == 0) {
            paginationDTO.setTotalPage(paginationDTO.getTotalCount() / size);
        } else {
            paginationDTO.setTotalPage(paginationDTO.getTotalCount() / size + 1);
        }
        /**
         * 这一步确定一下page的取值
         * 验证同一个数据的时候，要从范围较大的开始验证，
         * 然后在验证较小的数据，这样完成之后是有保证的。
         */
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }
        if (page <= 1) {
            page = 1;
        }
        paginationDTO.setpagination(size, page);
        Integer offset = size * (page - 1);
        List<Notification> notificationList = notificationMapper.listProfile(id, offset, size);
        if (notificationList.size() == 0) {
            return paginationDTO;
        }
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notificationList) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setOuterid(notification.getOuterId());
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public Integer unreadCount(Integer id) {
        return notificationMapper.countUnread(id);
    }

    public NotificationDTO read(Integer id, User user) {
        Notification notification = notificationMapper.query(id);
        if (!notification.getReceiver().equals(user.getId()) ){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        if (notification == null){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.update(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setOuterid(notification.getOuterId());
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
