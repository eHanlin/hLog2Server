package com.eHanlin.hLog2.config

import org.springframework.core.annotation.Order
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer
import javax.servlet.{Filter, FilterRegistration, ServletContext}
import ro.isdc.wro.http.WroFilter
import org.springframework.web.filter.CharacterEncodingFilter

@Order(2)
class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  def getRootConfigClasses: Array[Class[_]] = {
    return new Array[Class[_]](0)
  }


  def getServletConfigClasses: Array[Class[_]] = {
    return Array[Class[_]](classOf[WebConfig], classOf[WebSocketConfig])
  }

  def getServletMappings: Array[String] = {
    return Array[String]("/")
  }

  override def registerDispatcherServlet(servletContext: ServletContext): Unit = {
    super.registerDispatcherServlet(servletContext)

    val wroFilter: WroFilter = new WroFilter
    val registration: FilterRegistration.Dynamic = servletContext.addFilter("wroFilter", wroFilter)
    registration.addMappingForUrlPatterns(null, false, "/wro/*")
  }

  override def getServletFilters: Array[Filter] = {
    val characterEncodingFilter: CharacterEncodingFilter = new CharacterEncodingFilter
    characterEncodingFilter.setEncoding("UTF-8")
    return Array[Filter](characterEncodingFilter)
  }
}
