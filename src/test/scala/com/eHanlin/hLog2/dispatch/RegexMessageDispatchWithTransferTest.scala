package com.eHanlin.hLog2.dispatch

import com.eHanlin.hLog2.transfer.{MockMessageTransfer, MessageTransfer}
import org.junit.{Assert, Test}
import java.util.concurrent.RejectedExecutionException

class RegexMessageDispatchWithTransferTest {

  val messageTransfer : MessageTransfer = new MockMessageTransfer()

  @Test
  def message() {
    val createAction : String = """{"action":"create", "info":"test"}"""
    val createIntent : String = """{"intent":"create", "info":"test"}"""
    val createActionIntent : String = """{"action":"create", "intent":"create", "info":"test"}"""

    var dispatch : MessageDispatch = new RegexMessageDispatchWithTransfer(
      messageTransfer, """"action":"create"""".r)

    dispatch.message(createAction)
    dispatch.message(createActionIntent)
    try {
      dispatch.message(createIntent)
      Assert.assertTrue(false)
    } catch {
      case ex : RejectedExecutionException => Unit
    }

    dispatch = new RegexMessageDispatchWithTransfer(
      messageTransfer, """"intent":"create"""".r)

    dispatch.message(createIntent)
    dispatch.message(createActionIntent)
    try {
      dispatch.message(createAction)
      Assert.assertTrue(false)
    } catch {
      case ex : RejectedExecutionException => Unit
    }

    dispatch = new RegexMessageDispatchWithTransfer(
      messageTransfer, """"target":"create"""".r)

    try {
      dispatch.message(createIntent)
      Assert.assertTrue(false)
    } catch {
      case ex : RejectedExecutionException => Unit
    }
    try {
      dispatch.message(createAction)
      Assert.assertTrue(false)
    } catch {
      case ex : RejectedExecutionException => Unit
    }
    try {
      dispatch.message(createActionIntent)
      Assert.assertTrue(false)
    } catch {
      case ex : RejectedExecutionException => Unit
    }

    dispatch = new RegexMessageDispatchWithTransfer(
      messageTransfer, """"info":"test"""".r)

    dispatch.message(createIntent)
    dispatch.message(createAction)
    dispatch.message(createActionIntent)

  }
}
