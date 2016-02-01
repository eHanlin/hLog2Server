package com.eHanlin.hLog2.dispatch

import com.eHanlin.hLog2.transfer.MessageTransfer
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.concurrent.RejectedExecutionException

class JsonFieldMessageDispatchWithTransfer(
  val messageTransfer : MessageTransfer,
  val objectMapper : ObjectMapper,
  val key : String,
  val value : Object) extends MessageDispatch
{
  def message(info: String): Unit = {
    val map = objectMapper.readValue(info, classOf[java.util.Map[String, Object]])
    if(map.containsKey(key) && map.get(key).equals(value)){
      messageTransfer.send(info)
    }else{
      throw new RejectedExecutionException()
    }
  }

  override def toString: String = {
    s"JsonFieldMessageDispatchWithTransfer ( messageTransfer -> $messageTransfer , key -> $key , value -> $value )"
  }

}
