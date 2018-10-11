package com.smartcold.tray.socket;

import com.smartcold.tray.entity.comm.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Copyright (C) SmartCold 版权所有
 *
 * @author MaQiang34
 * @Description: 基类
 * @createDate:2018-09-13 17:49
 * @version:V1.0
 */
@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    //用户标识
    private static final String SESSION_USER = "user";
    //在线用户列表
    private static final Map<Integer, WebSocketSession> users = new HashMap<Integer, WebSocketSession>();

    /**
     * 连接已关闭，移除在Map集合中的记录
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        users.remove(getUserId(session));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        System.out.println("连接出错");
        users.remove(getUserId(session));
    }

    /**
     * 连接建立成功之后，记录用户的连接标识，便于后面发信息
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("成功建立连接");
        Integer userId = getUserId(session);
        System.out.println(userId);
        if (userId != null) {
            users.put(userId, session);
            session.sendMessage(new TextMessage("成功建立socket连接"));
            System.out.println(userId);
            System.out.println(session);
        }
    }

    /**
     * 处理收到的websocket信息
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    }

    /**
     * 发送信息给指定用户
     * @param clientId
     * @param message
     * @return
     */
    public boolean sendMessageToUser(Integer clientId, String message) {
        if (users.get(clientId) == null) {
            return false;
        }

        WebSocketSession session = users.get(clientId);
        System.out.println("sendMessage:" + session);

        if (!session.isOpen()) {
            return false;
        }
        try {
            int count = 1;
            TextMessage textMessage = null;
            String newMessage = "";

            // 循环向客户端发送数据
            while(true) {
                newMessage = message + String.valueOf(count);
                textMessage = new TextMessage(newMessage);
                session.sendMessage(textMessage);
                Thread.sleep(5000);
                newMessage = "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 广播信息
     * @param message
     * @return
     */
    public boolean sendMessageToAllUsers(TextMessage message) {
        boolean allSendSuccess = true;
        Set<Integer> clientIds = users.keySet();
        WebSocketSession session = null;
        for (Integer clientId : clientIds) {
            try {
                session = users.get(clientId);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                allSendSuccess = false;
            }
        }
        return  allSendSuccess;
    }

    /**
     * 获取用户标识
     * @param session
     * @return
     */
    private Integer getUserId(WebSocketSession session) {
        try {
            UserEntity userBean = (UserEntity) session.getAttributes().get(SESSION_USER);
            return userBean.getId();
        } catch (Exception e) {
            return null;
        }
    }
}
