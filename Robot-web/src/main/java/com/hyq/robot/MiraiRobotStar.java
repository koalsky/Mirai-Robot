package com.hyq.robot;

import com.hyq.robot.helper.ApplicationContextHelper;
import com.hyq.robot.listener.FriendListener;
import com.hyq.robot.listener.FriendRequestListener;
import com.hyq.robot.listener.GroupListener;
import com.hyq.robot.star.RobotStar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author nanke
 * @date 2020/7/14 上午11:49
 */
@SpringBootApplication
public class MiraiRobotStar {

    public static void main(String[] args) {
        // SpringBoot启动
        SpringApplication.run(MiraiRobotStar.class,args);
        // Mirai启动
        RobotStar.star(ApplicationContextHelper.getBean(FriendListener.class),
                ApplicationContextHelper.getBean(GroupListener.class),
                ApplicationContextHelper.getBean(FriendRequestListener.class));
    }
}
