package com.eHanlin.hLog2.dispatch

import com.eHanlin.hLog2.transfer.MessageTransfer

class AllMessageDispatchWithTransfer(
  val messageTransfer : MessageTransfer) extends MessageDispatch
{
  def message(info: String): Unit = {
    messageTransfer.send(info)
  }

  override def toString: String = {
    s"AllMessageDispatchWithTransfer ( messageTransfer -> $messageTransfer )"
  }
}
