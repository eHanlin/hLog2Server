package com.eHanlin.hLog2.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.eHanlin.hLog2.dispatch.MessageDispatchServer
import com.fasterxml.jackson.databind.ObjectMapper

@Service
class LogService {

  @Autowired
  var jsonObjectMapper : ObjectMapper = null

  @Autowired
  var messageDispatchServer : MessageDispatchServer = null

  def log(info : java.util.Map[String, Object]) {
    messageDispatchServer.message(jsonObjectMapper.writeValueAsString(info))
  }
}