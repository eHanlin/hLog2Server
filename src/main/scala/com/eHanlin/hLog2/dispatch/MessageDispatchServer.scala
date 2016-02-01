package com.eHanlin.hLog2.dispatch

import java.util.concurrent.RejectedExecutionException

abstract class MessageDispatchServer extends MessageDispatch {
  def addMessageDispatch(messageDispatch : MessageDispatch) : String
  def removeMessageDispatchById(messageDispatchId : String)
  def messageDispatchMap() : Map[String, MessageDispatch]

  def message(info: String): Unit = {
    for(messageDispatch <- listMessageDispatch){
      try {
        messageDispatch.message(info)
      } catch {
        case e : RejectedExecutionException => Unit
      }
    }
  }

  def listMessageDispatchWithId() : List[(String, MessageDispatch)] = {
    messageDispatchMap.toList
  }

  def findMessageDispatchById(messageDispatchId : String) : MessageDispatch = {
    messageDispatchMap.get(messageDispatchId) match {
      case Some(messageDispatch : MessageDispatch) => messageDispatch
      case None => throw new IllegalArgumentException("not exist")
    }
  }

  def findMessageDispatchId(messageDispatch : MessageDispatch) : String = {
    listMessageDispatchWithId.foldLeft("") ((findId : String, item : (String, MessageDispatch)) => {
      if(messageDispatch == item._2) item._1 else findId
    }) match {
      case "" => throw new IllegalArgumentException("not exist")
      case id => id
    }
  }

  def removeMessageDispatch(messageDispatch : MessageDispatch) = {
    removeMessageDispatchById(findMessageDispatchId(messageDispatch))
  }

  def removeAll() = {
    listMessageDispatchId.foreach{removeMessageDispatchById(_)}
  }

  def listMessageDispatch() : List[MessageDispatch] = {
    for((id, item) <- listMessageDispatchWithId) yield item
  }

  def listMessageDispatchId() : List[String] = {
    for((id, item) <- listMessageDispatchWithId) yield id
  }

  def hasMessageDispatch(messageDispatch : MessageDispatch) : Boolean = {
    listMessageDispatch.exists{_ == messageDispatch}
  }

  def hasMessageDispatchById(messageDispatchId : String) : Boolean = {
    listMessageDispatchId.exists{_ == messageDispatchId}
  }
}
