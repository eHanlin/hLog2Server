package com.eHanlin.hLog2.transfer

class MockMessageTransfer extends MessageTransfer{
  def send(message: String): Unit = {
    println(s"MockMessageTransfer send $message")
  }
}
