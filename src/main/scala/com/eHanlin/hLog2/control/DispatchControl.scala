package com.eHanlin.hLog2.control

import collection.JavaConversions._
import org.springframework.stereotype.Controller
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestParam, RequestMapping, ResponseBody, RequestMethod}
import scala.Array
import com.eHanlin.hLog2.service.DispatchService


@Controller
class DispatchControl {

  @Autowired
  var dispatchService : DispatchService = null

  @RequestMapping(value = Array("/Dispatch"), method = Array(RequestMethod.GET))
  @ResponseBody
  def list() = {
    mapAsJavaMap(Map(
      "success" -> true,
      "value" -> (for((id, dispatch) <- dispatchService.list) yield {
        mapAsJavaMap(Map("id" -> id, "dispatch" -> dispatch.toString))
        }).toArray))
  }

  @RequestMapping(value = Array("/type/all/Dispatch"), method = Array(RequestMethod.POST))
  @ResponseBody
  def add(@RequestParam("queue") queue: String) = {
    mapAsJavaMap(Map("success" -> true, "value" -> dispatchService.addAllMessageDispatch(queue)))
  }

  @RequestMapping(value = Array("/type/regex/Dispatch"), method = Array(RequestMethod.POST))
  @ResponseBody
  def add(
    @RequestParam("queue") queue: String,
    @RequestParam("regex") regex: String) =
  {
    mapAsJavaMap(Map("success" -> true, "value" -> dispatchService.addRegexMessageDispatch(queue, regex.r)))
  }

  @RequestMapping(value = Array("/type/jsonField/Dispatch"), method = Array(RequestMethod.POST))
  @ResponseBody
  def add(
    @RequestParam("queue") queue: String,
    @RequestParam("key") key: String,
    @RequestParam("value") value: Object) =
  {
    mapAsJavaMap(Map("success" -> true, "value" -> dispatchService.addJsonMessageDispatch(queue, key, value)))
  }


  @RequestMapping(value = Array("/Dispatch"), method = Array(RequestMethod.DELETE))
  @ResponseBody
  def remove(@RequestParam("id") dispatchId: String) = {
    dispatchService.remove(dispatchId)
    mapAsJavaMap(Map("success" -> true))
  }
}
