package cn.jxufe.websocket;

import cn.jxufe.bean.SystemCode;
import cn.jxufe.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @create: 2022-05-25 10:35
 * @author: lwz
 * @description:
 **/
public class SystemWebsocketHandler extends TextWebSocketHandler {
    private final static Logger log = LoggerFactory.getLogger(WebSocketHandler.class);
    private static final ArrayList<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        /*message.getPayload()获取String类型的数据*/
        /*session.getId()获取连接对象的sessionId*/
    }

    @Override
    /*发布webSocket会话时，在会话管理列表中注册该webSocket会话*/
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionList.add(session);
        User user = (User) session.getHandshakeAttributes().get(SystemCode.WEBSOCKET_SESSION_NAME);
        log.info("用户 " + user.getUsername() + " 成功建立连接");
    }

    @Override
    /*发生webSocket会话主动关闭事件时，清理会话管理列表*/
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        User user = (User) session.getHandshakeAttributes().get(SystemCode.WEBSOCKET_SESSION_NAME);
        log.info("用户 " + user.getUsername() + " 连接关闭。状态: " + status);
        sessionList.remove(session);
    }

    @Override
    /*发生传输错误时关闭该用户的webSocket会话，并清理会话管理列表*/
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        User user = (User) session.getHandshakeAttributes().get(SystemCode.WEBSOCKET_SESSION_NAME);
        if (session.isOpen()) {
            session.close();
        }
        log.debug("用户: " + user.getUsername() + " 连接由于传输错误被关闭......");
        sessionList.remove(session);
    }

    /**
     * 向全部用户发消息
     *
     * @param message
     */
    public void sendMessageToAll(TextMessage message) {
        for (WebSocketSession session : sessionList) {
            try {
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 向单一用户发消息
     *
     * @param username
     * @param message
     */
    public void sendMessageToOne(String username, TextMessage message) {
        for (WebSocketSession session : sessionList) {
            User User = (User) session.getHandshakeAttributes().get(SystemCode.WEBSOCKET_SESSION_NAME);
            if (User == null) continue;
            if (User.getUsername().equals(username)) {
                try {
                    if (session.isOpen()) {
                        session.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
