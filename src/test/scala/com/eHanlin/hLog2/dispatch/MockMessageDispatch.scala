package com.eHanlin.hLog2.dispatch

class MockMessageDispatch extends MessageDispatch {
  def message(info: String): Unit = {

  }

  override def toString: String = {
    s"MockMessageDispatch ( )"
  }
}
