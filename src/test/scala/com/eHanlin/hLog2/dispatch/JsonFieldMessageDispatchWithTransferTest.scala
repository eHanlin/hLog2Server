package com.eHanlin.hLog2.dispatch

import collection.JavaConversions._
import org.junit.{Assert, Test}
import com.eHanlin.hLog2.transfer.{MessageTransfer, MockMessageTransfer}
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.concurrent.RejectedExecutionException

class JsonFieldMessageDispatchWithTransferTest {

  val messageTransfer : MessageTransfer = new MockMessageTransfer()

  val objectMapper : ObjectMapper = new ObjectMapper()

  @Test
  def message() {

    val createAction : String = """{"action":"create", "info":"test"}"""
    val createIntent : String = """{"intent":"create", "info":"test"}"""
    val createActionIntent : String = """{"action":"create", "intent":"create", "info":"test"}"""

    var dispatch : MessageDispatch = new JsonFieldMessageDispatchWithTransfer(
      messageTransfer, objectMapper, "action", "create")

    dispatch.message(createAction)
    dispatch.message(createActionIntent)
    try {
      dispatch.message(createIntent)
      Assert.assertTrue(false)
    } catch {
      case ex : RejectedExecutionException => Unit
    }

    dispatch = new JsonFieldMessageDispatchWithTransfer(
      messageTransfer, objectMapper, "intent", "create")

    dispatch.message(createIntent)
    dispatch.message(createActionIntent)
    try {
      dispatch.message(createAction)
      Assert.assertTrue(false)
    } catch {
      case ex : RejectedExecutionException => Unit
    }

    dispatch = new JsonFieldMessageDispatchWithTransfer(
      messageTransfer, objectMapper, "target", "create")

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

    dispatch = new JsonFieldMessageDispatchWithTransfer(
      messageTransfer, objectMapper, "info", "test")

    dispatch.message(createIntent)
    dispatch.message(createAction)
    dispatch.message(createActionIntent)
  }
}
