package com.eHanlin.hLog2.transfer

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.{Value, Autowired}
import com.rabbitmq.client.{ConnectionFactory, Channel}

class RabbitMessageTransfer(
  val host : String,
  val port : Integer,
  val userName : String,
  val password : String,
  val queue : String) extends MessageTransfer
{
  val factory : ConnectionFactory = new ConnectionFactory()
  factory.setHost(host)
  factory.setPort(port)
  factory.setUsername(userName)
  factory.setPassword(password)

  val channel : Channel = factory.newConnection().createChannel()
  channel.queueDeclare(queue, true, false, false, null)

  def send(message: String): Unit = {
    channel.basicPublish("", queue, null, message.getBytes)
  }

  override def toString: String = {
    s"RabbitMessageTransfer ( host -> $host , port -> $port , userName -> $userName , password -> $password , queue -> $queue )"
  }

}
