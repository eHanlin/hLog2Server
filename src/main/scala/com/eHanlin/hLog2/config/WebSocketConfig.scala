package com.eHanlin.hLog2.config

import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.socket.config.annotation.{WebSocketHandlerRegistry, WebSocketConfigurer, EnableWebSocket}
import org.springframework.web.socket.WebSocketHandler
import com.eHanlin.hLog2.socket.LogWebSocketHandler

@Configuration
@EnableWebSocket
class WebSocketConfig extends WebSocketConfigurer{

  def registerWebSocketHandlers(registry: WebSocketHandlerRegistry): Unit = {
    registry.addHandler(logWebSocketHandler, "/sockJs/log").withSockJS
  }

  @Bean
  def logWebSocketHandler: WebSocketHandler = {
    new LogWebSocketHandler()
  }
}
