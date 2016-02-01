package com.eHanlin.hLog2.socket

import org.springframework.web.socket.handler.TextWebSocketHandler
import org.springframework.web.socket.{CloseStatus, TextMessage, WebSocketSession}
import org.springframework.beans.factory.annotation.Autowired
import com.eHanlin.hLog2.service.LogService
import com.fasterxml.jackson.databind.ObjectMapper

class LogWebSocketHandler extends TextWebSocketHandler{

  @Autowired
  var objectMapper : ObjectMapper =  null

  @Autowired
  var logService : LogService = null

  override def afterConnectionEstablished(session: WebSocketSession): Unit = {
    super.afterConnectionEstablished(session)
  }

  override def afterConnectionClosed(session: WebSocketSession, status: CloseStatus): Unit = {
    super.afterConnectionClosed(session, status)
  }

  override def handleTextMessage(session: WebSocketSession, message: TextMessage): Unit = {
    logService.log(objectMapper.readValue(message.getPayload, classOf[java.util.Map[String,Object]]))
  }

}