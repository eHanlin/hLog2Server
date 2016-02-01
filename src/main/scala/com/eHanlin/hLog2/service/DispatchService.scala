package com.eHanlin.hLog2.service

import com.eHanlin.hLog2.dispatch._
import org.springframework.beans.factory.annotation.{Value, Autowired}
import org.springframework.stereotype.Service
import scala.collection.JavaConversions._
import scala.util.matching.Regex
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.io.ClassPathResource
import au.com.bytecode.opencsv.{CSVWriter, CSVReader}
import java.io.{FileWriter, FileReader}
import javax.annotation.PostConstruct

@Service
class DispatchService @Autowired()(
  @Value("${dispatchService.csvFilePath}") val csvFilePath : String = "dispatchService.csv")
{
  @Autowired
  var objectMapper : ObjectMapper = null

  @Autowired
  var transferService : TransferService = null

  @Autowired
  var messageDispatchServer : MessageDispatchServer = null

  val csvFileResource : ClassPathResource = new ClassPathResource(csvFilePath)
  val reader : CSVReader = new CSVReader(new FileReader(csvFileResource.getFile))
  val csvLines : java.util.List[Array[String]] = reader.readAll()
  val csvLinesId : java.util.List[String] = new java.util.ArrayList[String]()
  reader.close()

  @PostConstruct
  def init() {
    for(csvLine <- csvLines){
      csvLine match {
        case Array("all", queue, _*) => {
          csvLinesId.add(addAllMessageDispatchWithOutCsv(queue))
        }
        case Array("regex", queue, regex, _*) => {
          csvLinesId.add(addRegexMessageDispatchWithOutCsv(queue, regex.r))
        }
        case Array("jsonField", queue, key, value, _*) => {
          csvLinesId.add(addJsonMessageDispatchWithOutCsv(queue, key, value))
        }
        case _ => csvLinesId.add(null)
      }
    }
  }

  def list : List[(String, MessageDispatch)] = {
    messageDispatchServer.listMessageDispatchWithId()
  }

  def addAllMessageDispatchWithOutCsv(queue : String) : String = {
    messageDispatchServer.addMessageDispatch(
      new AllMessageDispatchWithTransfer(transferService.create(queue)))
  }

  def addRegexMessageDispatchWithOutCsv(queue : String, regex : Regex) : String = {
    messageDispatchServer.addMessageDispatch(
      new RegexMessageDispatchWithTransfer(transferService.create(queue), regex))
  }

  def addJsonMessageDispatchWithOutCsv(queue : String, key : String, value : Object) : String = {
    messageDispatchServer.addMessageDispatch(
      new JsonFieldMessageDispatchWithTransfer(transferService.create(queue), objectMapper, key, value))
  }

  def removeWithOutCsv(messageDispatchId : String) = {
    messageDispatchServer.removeMessageDispatchById(messageDispatchId)
  }

  def addAllMessageDispatch(queue : String) : String = {
    addCsvLine(addAllMessageDispatchWithOutCsv(queue), Array[String]("all", queue))
  }

  def addRegexMessageDispatch(queue : String, regex : Regex) : String = {
    addCsvLine(addRegexMessageDispatchWithOutCsv(queue, regex), Array[String]("regex", queue, regex.toString))
  }

  def addJsonMessageDispatch(queue : String, key : String, value : Object) : String = {
    addCsvLine(addJsonMessageDispatchWithOutCsv(queue, key, value), Array[String]("jsonField", queue, key, value.toString))
  }

  def remove(messageDispatchId : String) = {
    removeCsvLine(messageDispatchId)
    removeWithOutCsv(messageDispatchId)
  }

  def removeCsvLine(id : String) {
    val index = csvLinesId.indexOf(id)
    csvLines.remove(index)
    csvLinesId.remove(index)
    updateCsvFile()
  }

  def addCsvLine(id : String, line : Array[String]) : String = {
    csvLines.add(line)
    csvLinesId.add(id)
    updateCsvFile()
    id
  }

  def updateCsvFile() {
    val writer : CSVWriter = new CSVWriter(new FileWriter(csvFileResource.getFile))
    writer.writeAll(csvLines)
    writer.flush()
    writer.close()
  }
}
