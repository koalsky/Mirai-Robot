package com.hyq.robot.listener;

import com.hyq.robot.constants.CommonConstant;
import com.hyq.robot.star.RobotStar;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

/**
 * @author nanke
 * @date 2020/7/1 下午12:26
 */
@Service
public class GroupListener extends SimpleListenerHost {

    @EventHandler
    public void onMessage(GroupMessageEvent event) {


    }

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        /**
         * 异常处理方式
         * 给自己发消息
         */
        RobotStar.bot.getFriend(CommonConstant.errorSendId).sendMessage("群聊消息处理错误!" + exception.getMessage());
    }
}
