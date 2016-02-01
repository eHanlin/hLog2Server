package com.eHanlin.hLog2.dispatch

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class BasicMessageDispatchServer extends MessageDispatchServer{

  var dispatchMap : Map[String, MessageDispatch] = Map[String, MessageDispatch]()

  def addMessageDispatch(messageDispatch: MessageDispatch): String = {
    if(!hasMessageDispatch(messageDispatch)){
      val id : String = UUID.randomUUID().toString
      dispatchMap += (id -> messageDispatch)
      id
    }else{
      findMessageDispatchId(messageDispatch)
    }
  }

  def removeMessageDispatchById(messageDispatchId: String): Unit = {
    if(dispatchMap.contains(messageDispatchId)){
      dispatchMap -= messageDispatchId
    }
  }

  def messageDispatchMap(): Map[String, MessageDispatch] = {
    dispatchMap
  }

  override def toString: String = {
    s"BasicMessageDispatchServer ( dispatchMap -> $dispatchMap )"
  }
}
