package com.eHanlin.hLog2.service

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.{Value, Autowired}
import com.eHanlin.hLog2.transfer.{RabbitMessageTransfer, MessageTransfer}

@Service
class TransferService @Autowired()(
  @Value("${rabbitMQ.host}") val host : String,
  @Value("${rabbitMQ.port}") val port : Integer,
  @Value("${rabbitMQ.userName}") val userName : String,
  @Value("${rabbitMQ.password}") val password : String)
{
  def create(queueName : String) : MessageTransfer = {
    new RabbitMessageTransfer(host, port, userName, password, queueName)
  }
}
