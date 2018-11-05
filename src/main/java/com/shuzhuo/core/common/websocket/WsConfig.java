package com.shuzhuo.core.common.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 
 * @author chenshuzhuo
 * @since 2017-12-25
 *
 */
@Configuration
@Profile("dev")
public class WsConfig {
	
	@Bean
    public ServerEndpointExporter serverEndpointExporter() {  
        return new ServerEndpointExporter();  
    }
	
	
}

