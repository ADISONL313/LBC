package com.cqcskj.lbc.controller;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/bysj/message")
public class MessageController {
    // 用于存储所有连接的会话
    private static final CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<>();
    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("新连接：" + session.getId());
    }
    @OnMessage
    public void onMessage(String message, Session session) {
        // 处理接收到的消息，这里简单地将消息发送给所有连接的客户端
        for (Session s : sessions) {
            try {
                s.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("连接关闭：" + session.getId());
    }
}
