package com.eHanlin.hLog2.view

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod}
import java.lang.String
import javax.servlet.http.HttpServletRequest

@Controller
class MainView {

  @RequestMapping(value = Array("*.html"), method = Array(RequestMethod.GET))
  def html(request: HttpServletRequest): String = {
    request.getPathInfo
  }

}
