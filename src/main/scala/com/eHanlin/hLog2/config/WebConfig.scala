package com.eHanlin.hLog2.config

import org.springframework.context.annotation.{ComponentScan, Bean, Configuration}
import org.springframework.web.servlet.config.annotation._
import org.springframework.context.support.{ReloadableResourceBundleMessageSource, PropertySourcesPlaceholderConfigurer}
import org.springframework.core.io.ClassPathResource
import org.thymeleaf.templateresolver.ServletContextTemplateResolver
import org.thymeleaf.spring4.SpringTemplateEngine
import org.springframework.web.servlet.i18n.{LocaleChangeInterceptor, CookieLocaleResolver}
import java.util.Locale
import org.springframework.web.servlet.ViewResolver
import org.thymeleaf.spring4.view.ThymeleafViewResolver
import com.fasterxml.jackson.databind.ObjectMapper

@Configuration
@EnableWebMvc
@ComponentScan (basePackages = Array ("com.eHanlin.hLog2") )
class WebConfig extends WebMvcConfigurerAdapter {

  override def addResourceHandlers(registry: ResourceHandlerRegistry): Unit = {
    super.addResourceHandlers(registry)
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/")
  }

  override def configureDefaultServletHandling(configurer: DefaultServletHandlerConfigurer): Unit = {
    super.configureDefaultServletHandling(configurer)
    configurer.enable
  }

  override def addInterceptors(registry: InterceptorRegistry): Unit = {
    super.addInterceptors(registry)
    registry.addInterceptor(localeChangeInterceptor)
  }

  @Bean
  def properties: PropertySourcesPlaceholderConfigurer = {
    val propertySourcesPlaceholderConfigurer: PropertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer
    propertySourcesPlaceholderConfigurer.setLocation(new ClassPathResource("environment.properties"))
    propertySourcesPlaceholderConfigurer
  }

  @Bean
  def templateResolver: ServletContextTemplateResolver = {
    val resolver: ServletContextTemplateResolver = new ServletContextTemplateResolver
    resolver.setPrefix("/WEB-INF/view/")
    resolver.setSuffix(".html")
    resolver.setTemplateMode("HTML5")
    resolver.setCharacterEncoding("UTF-8")
    resolver.setCacheable(false)
    resolver
  }

  @Bean
  def viewResolver: ViewResolver = {
    val viewResolver: ThymeleafViewResolver = new ThymeleafViewResolver
    viewResolver.setTemplateEngine(templateEngine)
    viewResolver.setOrder(1)
    viewResolver.setViewNames(Array[String]("*"))
    viewResolver.setCache(false)
    viewResolver.setCharacterEncoding("UTF-8")
    viewResolver
  }

  @Bean
  def templateEngine: SpringTemplateEngine = {
    val engine: SpringTemplateEngine = new SpringTemplateEngine
    engine.setTemplateResolver(templateResolver)
    engine
  }

  @Bean
  def messageSource: ReloadableResourceBundleMessageSource = {
    val source: ReloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource
    source.setBasename("classpath:i18n/messages")
    source.setDefaultEncoding("UTF-8")
    source
  }

  @Bean
  def localeResolver: CookieLocaleResolver = {
    val localeResolver: CookieLocaleResolver = new CookieLocaleResolver
    localeResolver.setCookiePath("/")
    localeResolver.setCookieName("lang")
    localeResolver.setCookieMaxAge(Integer.MAX_VALUE)
    localeResolver.setDefaultLocale(Locale.TAIWAN)
    localeResolver
  }

  @Bean
  def localeChangeInterceptor: LocaleChangeInterceptor = {
    val localeChangeInterceptor: LocaleChangeInterceptor = new LocaleChangeInterceptor
    localeChangeInterceptor.setParamName("lang")
    localeChangeInterceptor
  }

  @Bean
  def jsonObjectMapper : ObjectMapper = {
    new ObjectMapper()
  }
}
