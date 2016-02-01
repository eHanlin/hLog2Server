package com.eHanlin.hLog2.dispatch

import org.junit.{Test, Before, Assert}

class BasicMessageDispatchServerTest {

  var dispatchServer : BasicMessageDispatchServer = null
  var dispatch1 : MessageDispatch = null
  var dispatch1Id : String = null

  @Before
  def before() {
    dispatchServer = new BasicMessageDispatchServer()
    dispatch1 = new MockMessageDispatch()
    dispatch1Id = dispatchServer.addMessageDispatch(dispatch1)
  }

  @Test
  def basicMessageDispatchMethod() {
    Assert.assertTrue(dispatchServer.messageDispatchMap().contains(dispatch1Id))
    Assert.assertTrue(dispatchServer.messageDispatchMap().getOrElse(dispatch1Id, null) == dispatch1)
    Assert.assertTrue(dispatchServer.messageDispatchMap().toList.size == 1)

    val dispatch2 = new MockMessageDispatch()
    val dispatch2Id = dispatchServer.addMessageDispatch(dispatch2)
    Assert.assertTrue(dispatchServer.messageDispatchMap().contains(dispatch2Id))
    Assert.assertTrue(dispatchServer.messageDispatchMap().getOrElse(dispatch2Id, null) == dispatch2)
    Assert.assertTrue(dispatchServer.messageDispatchMap().toList.size == 2)
    Assert.assertTrue(dispatchServer.messageDispatchMap().getOrElse(dispatch2Id, null) != dispatchServer.messageDispatchMap().getOrElse(dispatch1Id, null))

    val dispatch3 = new MockMessageDispatch()
    val dispatch3Id = dispatchServer.addMessageDispatch(dispatch3)
    Assert.assertTrue(dispatchServer.messageDispatchMap().contains(dispatch3Id))
    Assert.assertTrue(dispatchServer.messageDispatchMap().getOrElse(dispatch3Id, null) == dispatch3)
    Assert.assertTrue(dispatchServer.messageDispatchMap().toList.size == 3)
    Assert.assertTrue(dispatchServer.messageDispatchMap().getOrElse(dispatch3Id, null) != dispatchServer.messageDispatchMap().getOrElse(dispatch2Id, null))
    Assert.assertTrue(dispatchServer.messageDispatchMap().getOrElse(dispatch3Id, null) != dispatchServer.messageDispatchMap().getOrElse(dispatch1Id, null))

    dispatchServer.removeMessageDispatchById(dispatch3Id)
    Assert.assertFalse(dispatchServer.messageDispatchMap().contains(dispatch3Id))
    Assert.assertTrue(dispatchServer.messageDispatchMap().toList.size == 2)
    Assert.assertTrue(dispatchServer.messageDispatchMap().getOrElse(dispatch1Id, null) == dispatch1)
    Assert.assertTrue(dispatchServer.messageDispatchMap().getOrElse(dispatch2Id, null) == dispatch2)

    dispatchServer.removeMessageDispatchById(dispatch2Id)
    Assert.assertFalse(dispatchServer.messageDispatchMap().contains(dispatch2Id))
    Assert.assertTrue(dispatchServer.messageDispatchMap().toList.size == 1)
    Assert.assertTrue(dispatchServer.messageDispatchMap().getOrElse(dispatch1Id, null) == dispatch1)
  }

  @Test
  def listMessageDispatchWithId() {
    Assert.assertTrue(dispatchServer.listMessageDispatchId().size == 1)
    Assert.assertTrue(dispatchServer.listMessageDispatchId().contains(dispatch1Id))

    val dispatch2 = new MockMessageDispatch()
    val dispatch2Id = dispatchServer.addMessageDispatch(dispatch2)
    Assert.assertTrue(dispatchServer.listMessageDispatchId().size == 2)
    Assert.assertTrue(dispatchServer.listMessageDispatchId().contains(dispatch1Id))
    Assert.assertTrue(dispatchServer.listMessageDispatchId().contains(dispatch2Id))

    dispatchServer.removeMessageDispatchById(dispatch2Id)
    Assert.assertTrue(dispatchServer.listMessageDispatchId().size == 1)
    Assert.assertTrue(dispatchServer.listMessageDispatchId().contains(dispatch1Id))
    Assert.assertFalse(dispatchServer.listMessageDispatchId().contains(dispatch2Id))
  }

  @Test
  def findMessageDispatchById() {
    Assert.assertTrue(dispatchServer.findMessageDispatchById(dispatch1Id) == dispatch1)

    val dispatch2 = new MockMessageDispatch()
    val dispatch2Id = dispatchServer.addMessageDispatch(dispatch2)
    Assert.assertTrue(dispatchServer.findMessageDispatchById(dispatch2Id) == dispatch2)
    Assert.assertTrue(dispatchServer.findMessageDispatchById(dispatch2Id) != dispatchServer.findMessageDispatchById(dispatch1Id))
  }

  @Test
  def findMessageDispatchId() {
    Assert.assertEquals(dispatchServer.findMessageDispatchId(dispatch1), dispatch1Id)

    val dispatch2 = new MockMessageDispatch()
    val dispatch2Id = dispatchServer.addMessageDispatch(dispatch2)
    Assert.assertEquals(dispatchServer.findMessageDispatchId(dispatch2), dispatch2Id)
    Assert.assertNotEquals(dispatchServer.findMessageDispatchId(dispatch2), dispatchServer.findMessageDispatchId(dispatch1))
  }

  @Test
  def removeMessageDispatch() {
    val dispatch2 = new MockMessageDispatch()
    val dispatch2Id = dispatchServer.addMessageDispatch(dispatch2)
    val dispatch3 = new MockMessageDispatch()
    val dispatch3Id = dispatchServer.addMessageDispatch(dispatch3)
    Assert.assertTrue(dispatchServer.messageDispatchMap().toList.size == 3)

    dispatchServer.removeMessageDispatch(dispatch3)
    Assert.assertTrue(dispatchServer.messageDispatchMap().toList.size == 2)
    Assert.assertFalse(dispatchServer.messageDispatchMap().contains(dispatch3Id))
    Assert.assertTrue(dispatchServer.messageDispatchMap().getOrElse(dispatch2Id, null) == dispatch2)
    Assert.assertTrue(dispatchServer.messageDispatchMap().getOrElse(dispatch1Id, null) == dispatch1)

    dispatchServer.removeMessageDispatch(dispatch2)
    Assert.assertTrue(dispatchServer.messageDispatchMap().toList.size == 1)
    Assert.assertFalse(dispatchServer.messageDispatchMap().contains(dispatch2Id))
    Assert.assertTrue(dispatchServer.messageDispatchMap().getOrElse(dispatch1Id, null) == dispatch1)
  }

  @Test
  def removeAll() {
    val dispatch2 = new MockMessageDispatch()
    val dispatch2Id = dispatchServer.addMessageDispatch(dispatch2)
    val dispatch3 = new MockMessageDispatch()
    val dispatch3Id = dispatchServer.addMessageDispatch(dispatch3)
    Assert.assertTrue(dispatchServer.messageDispatchMap().toList.size == 3)

    dispatchServer.removeAll()
    Assert.assertTrue(dispatchServer.messageDispatchMap().toList.size == 0)
    Assert.assertFalse(dispatchServer.messageDispatchMap().contains(dispatch1Id))
    Assert.assertFalse(dispatchServer.messageDispatchMap().contains(dispatch2Id))
    Assert.assertFalse(dispatchServer.messageDispatchMap().contains(dispatch3Id))
  }

  @Test
  def listMessageDispatch() {
    Assert.assertTrue(dispatchServer.listMessageDispatch().size == 1)
    Assert.assertTrue(dispatchServer.listMessageDispatch().contains(dispatch1))

    val dispatch2 = new MockMessageDispatch()
    val dispatch2Id = dispatchServer.addMessageDispatch(dispatch2)
    Assert.assertTrue(dispatchServer.listMessageDispatch().size == 2)
    Assert.assertTrue(dispatchServer.listMessageDispatch().contains(dispatch1))
    Assert.assertTrue(dispatchServer.listMessageDispatch().contains(dispatch2))

    dispatchServer.removeMessageDispatchById(dispatch2Id)
    Assert.assertTrue(dispatchServer.listMessageDispatch().size == 1)
    Assert.assertTrue(dispatchServer.listMessageDispatch().contains(dispatch1))
  }

  @Test
  def listMessageDispatchId() {
    Assert.assertTrue(dispatchServer.listMessageDispatchId().size == 1)
    Assert.assertTrue(dispatchServer.listMessageDispatchId().contains(dispatch1Id))

    val dispatch2 = new MockMessageDispatch()
    val dispatch2Id = dispatchServer.addMessageDispatch(dispatch2)
    Assert.assertTrue(dispatchServer.listMessageDispatchId().size == 2)
    Assert.assertTrue(dispatchServer.listMessageDispatchId().contains(dispatch1Id))
    Assert.assertTrue(dispatchServer.listMessageDispatchId().contains(dispatch2Id))

    dispatchServer.removeMessageDispatchById(dispatch2Id)
    Assert.assertTrue(dispatchServer.listMessageDispatchId().size == 1)
    Assert.assertTrue(dispatchServer.listMessageDispatchId().contains(dispatch1Id))
  }

  @Test
  def hasMessageDispatch() {
    val dispatch2 = new MockMessageDispatch()
    Assert.assertTrue(dispatchServer.hasMessageDispatch(dispatch1))
    Assert.assertFalse(dispatchServer.hasMessageDispatch(dispatch2))

    val dispatch2Id = dispatchServer.addMessageDispatch(dispatch2)
    Assert.assertTrue(dispatchServer.hasMessageDispatch(dispatch1))
    Assert.assertTrue(dispatchServer.hasMessageDispatch(dispatch2))

    dispatchServer.removeMessageDispatchById(dispatch2Id)
    Assert.assertTrue(dispatchServer.hasMessageDispatch(dispatch1))
    Assert.assertFalse(dispatchServer.hasMessageDispatch(dispatch2))
  }

  @Test
  def hasMessageDispatchById() {
    val dispatch2 = new MockMessageDispatch()
    Assert.assertTrue(dispatchServer.hasMessageDispatchById(dispatch1Id))

    val dispatch2Id = dispatchServer.addMessageDispatch(dispatch2)
    Assert.assertTrue(dispatchServer.hasMessageDispatchById(dispatch1Id))
    Assert.assertTrue(dispatchServer.hasMessageDispatchById(dispatch2Id))

    dispatchServer.removeMessageDispatchById(dispatch2Id)
    Assert.assertTrue(dispatchServer.hasMessageDispatchById(dispatch1Id))
    Assert.assertFalse(dispatchServer.hasMessageDispatchById(dispatch2Id))
  }

}
