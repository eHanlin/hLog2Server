package com.eHanlin.hLog2.dispatch

import com.eHanlin.hLog2.transfer.MessageTransfer
import scala.util.matching.Regex
import java.util.concurrent.RejectedExecutionException

class RegexMessageDispatchWithTransfer(
  val messageTransfer : MessageTransfer,
  val regex : Regex) extends MessageDispatch
{
  def message(info: String): Unit = {
    regex.findFirstIn(info) match {
      case Some(m:String) => messageTransfer.send(info)
      case None => throw new RejectedExecutionException()
    }
  }

  override def toString: String = {
    s"RegexMessageDispatchWithTransfer ( messageTransfer -> $messageTransfer , regex = $regex )"
  }
}
