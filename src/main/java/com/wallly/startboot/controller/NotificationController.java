package com.wallly.startboot.controller;

import com.wallly.startboot.Model.Notification;
import com.wallly.startboot.Model.User;
import com.wallly.startboot.dto.NotificationDTO;
import com.wallly.startboot.enums.NotificationStatusEnum;
import com.wallly.startboot.enums.NotificationTypeEnum;
import com.wallly.startboot.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Integer id,
                          HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("githubUser");
        if (user == null) {
            return "redirect: /";
        }
        NotificationDTO notificationDTO = notificationService.read(id, user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType() ||
                NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterid();
        } else {
            return "redirect: /";
        }
    }
}
