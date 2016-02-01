package com.eHanlin.hLog2.socket

import org.springframework.beans.factory.annotation.{Value, Autowired}
import com.fasterxml.jackson.databind.ObjectMapper
import com.eHanlin.hLog2.service.LogService
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct
import java.net.{Socket, ServerSocket}
import java.io.{BufferedReader, InputStreamReader}

@Service
class LogSocketServer @Autowired()(
  @Value("${logSocket.port}") val port : Integer) extends Runnable
{

  @Autowired
  var objectMapper : ObjectMapper =  null

  @Autowired
  var logService : LogService = null

  var server : ServerSocket = null

  @PostConstruct
  def init() {
    server = new ServerSocket(port)
    new Thread(this).start()
  }

  override def run() {
    while(true){
      val socket = server.accept()
      socket.setKeepAlive(true)
      new Thread(new LogSocketService(objectMapper, logService, socket)).start()
    }
  }

}

class LogSocketService(
  val objectMapper : ObjectMapper,
  val logService : LogService,
  val socket : Socket) extends Runnable
{

  override def run() {
    val in = new BufferedReader(new InputStreamReader(socket.getInputStream, "UTF-8"));
    try{
      var line = in.readLine()
      while(line != null){
        try{
          logService.log(objectMapper.readValue(line, classOf[java.util.Map[String,Object]]))
        } catch {
          case e : Exception => Unit
        }
        line = in.readLine()
      }
    } catch {
      case e : Exception => Unit
    } finally {
      in.close()
      socket.close()
    }
  }

}