package com.emgcy.core.common.websocket;


import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.emgcy.core.common.util.SpringContextHolder;
import com.emgcy.core.system.sys.service.IUserService;

@ServerEndpoint(value = "/websocket/{params}")
@Component
public class WsHandler {

	private static final Logger logger = LoggerFactory.getLogger(WsHandler.class);
	private static int onlineCount = 0;

	/**
	 * 多例
	 */
	long userId;

	private static IUserService userService = SpringContextHolder.getBean(IUserService.class);

	/**
	 * 连接token
	 */
	@OnOpen
	public void onOpen(@PathParam("params")String userIdStr,Session session) {

	}

	/**
	 * 关闭token
	 */
	@OnClose
	public void onClose() {

	}

	@OnMessage
	public void onMessage(String message, Session session) {

	}

	/**
	 * 连接错误则关闭连接
	 * @param error
	 */
	@OnError
	public void onError(Throwable error) {
		onClose();
		logger.error("连接错误!", error);
	}




}
