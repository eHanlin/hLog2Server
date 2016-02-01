package com.eHanlin.hLog2.control

import collection.JavaConversions._
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestBody, ResponseBody, RequestMethod, RequestMapping}
import scala.Array
import java.util.Date
import javax.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import com.eHanlin.hLog2.service.LogService

@Controller
class LogControl {

  @Autowired
  var logService : LogService = null

  @RequestMapping(value = Array("/LogSessions"), method = Array(RequestMethod.POST))
  @ResponseBody
  def createLogSession(
    @RequestBody info : java.util.Map[String, Object],
    request : HttpServletRequest) =
  {
    info.put("action", "createLogSession")
    createLog(info, request)
  }

  @RequestMapping(value = Array("/Log"), method = Array(RequestMethod.POST))
  @ResponseBody
  def createLog(
    @RequestBody info : java.util.Map[String, Object],
    request : HttpServletRequest) =
  {
    info.put("logServerDate", new Date())
    info.put("remoteAddr", request.getRemoteAddr)
    info.put("remoteHost", request.getRemoteHost)
    info.put("remotePort", request.getRemotePort.asInstanceOf[Integer])
    info.put("remoteUser", request.getRemoteUser)
    info.put("x-forwarded-for", request.getHeader("x-forwarded-for"))
    info.put("Proxy-Client-IP", request.getHeader("Proxy-Client-IP"))
    info.put("WL-Proxy-Client-IP", request.getHeader("WL-Proxy-Client-IP"))
    createLogWithoutRequestInfo(info)
  }

  @RequestMapping(value = Array("/LogWithoutRequestInfo"), method = Array(RequestMethod.POST))
  @ResponseBody
  def createLogWithoutRequestInfo(
    @RequestBody info : java.util.Map[String, Object]) =
  {
    logService.log(info)
    mapAsJavaMap(Map("success" -> true))
  }
}
