package pl.edu.agh.student.sspd.classes

import org.testng.Assert
import org.testng.annotations.Test
import pl.edu.agh.student.sspd.simulation.Coordinator

import scala.util.Random

/**
  * Created by pingwin on 06.06.17.
  */
class PaymentStateMachineTest {
  val random = new Random() {
    override def nextInt(n: Int): Int = 0
  }

  @Test
  def paymentStateChangeTest(): Unit = {
    val coordinator = new Coordinator()
    coordinator.cashierCleaner.occupied = true
    val stateMachine = PaymentStateMachine(random)
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[WaitingForPayment])
    coordinator.cashierCleaner.occupied = false
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[Payment])
    Assert.assertTrue(coordinator.cashierCleaner.occupied)
    coordinator.iteration = 2
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[OutOfSystem])
    Assert.assertFalse(coordinator.cashierCleaner.occupied)
  }

  @Test
  def clientStopsWaitingForPedicure(): Unit = {
    val coordinator = new Coordinator()
    coordinator.pedicurist.queueLength = 11
    val stateMachine = PedicureStateMachine(random)
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[PedicureWithdraw])
    coordinator.iteration = 1
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[OutOfSystem])
  }

  @Test
  def clientIsReceivedForPedicure(): Unit = {
    val coordinator = new Coordinator()
    coordinator.pedicurist.occupied = true
    val stateMachine = PedicureStateMachine(random)
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[WaitingForPedicurist])
    Assert.assertEquals(coordinator.pedicurist.queueLength, 1)
    coordinator.pedicurist.occupied = false
    coordinator.pedicurist.queueLength = 2
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[FootNailPainting])
    Assert.assertTrue(coordinator.pedicurist.occupied)
    Assert.assertEquals(coordinator.pedicurist.queueLength, 1)
    coordinator.iteration = 29
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[FootNailPainting])
    coordinator.iteration = 30
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[WaitingForVanishToDry])
    Assert.assertTrue(coordinator.pedicurist.needsLamp)
    coordinator.lamp.occupied = true
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[WaitingForVanishToDry])
    coordinator.lamp.occupied = false
    coordinator.lamp.failure = true
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[WaitingForVanishToDry])
    Assert.assertTrue(coordinator.lamp.occupied)
    Assert.assertTrue(coordinator.pedicurist.state.isInstanceOf[EquipmentRepair])
    Assert.assertTrue(coordinator.lamp.state.isInstanceOf[EquipmentRepair])
    coordinator.iteration = coordinator.iteration + 20
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[FootNailHardening])
    Assert.assertTrue(coordinator.lamp.state.isInstanceOf[FootNailHardening])
    Assert.assertTrue(coordinator.pedicurist.state.isInstanceOf[FootNailHardening])
    Assert.assertFalse(coordinator.lamp.failure)
    coordinator.iteration = coordinator.iteration + 10
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[OutOfSystem])
    Assert.assertTrue(coordinator.pedicurist.state.isInstanceOf[WaitingForOrders])
    Assert.assertFalse(coordinator.pedicurist.occupied)
    Assert.assertFalse(coordinator.lamp.occupied)
    Assert.assertTrue(coordinator.lamp.state.isInstanceOf[Idle])
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[OutOfSystem])
  }

  @Test
  def clientStopsWaitingForManicure(): Unit = {
    val coordinator = new Coordinator()
    coordinator.manicurist.queueLength = 11
    val stateMachine = ManicureStateMachine(random)
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[ManicureWithdraw])
    coordinator.iteration = 1
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[OutOfSystem])
  }

  @Test
  def clientIsReceivedForManicure(): Unit = {
    val coordinator = new Coordinator()
    coordinator.manicurist.occupied = true
    val stateMachine = ManicureStateMachine(random)
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[WaitingForManicurist])
    Assert.assertEquals(coordinator.manicurist.queueLength, 1)
    coordinator.manicurist.occupied = false
    coordinator.manicurist.queueLength = 2
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[FingerNailPainting])
    Assert.assertTrue(coordinator.manicurist.occupied)
    Assert.assertEquals(coordinator.manicurist.queueLength, 1)
    coordinator.iteration = 29
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[FingerNailPainting])
    coordinator.iteration = 30
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[WaitingForVanishToDry])
    Assert.assertTrue(coordinator.manicurist.needsLamp)
    coordinator.lamp.occupied = true
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[WaitingForVanishToDry])
    coordinator.lamp.occupied = false
    coordinator.lamp.failure = true
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[WaitingForVanishToDry])
    Assert.assertTrue(coordinator.lamp.occupied)
    Assert.assertTrue(coordinator.manicurist.state.isInstanceOf[EquipmentRepair])
    Assert.assertTrue(coordinator.lamp.state.isInstanceOf[EquipmentRepair])
    coordinator.iteration = coordinator.iteration + 20
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[FingerNailHardening])
    Assert.assertTrue(coordinator.lamp.state.isInstanceOf[FingerNailHardening])
    Assert.assertTrue(coordinator.manicurist.state.isInstanceOf[FingerNailHardening])
    Assert.assertFalse(coordinator.lamp.failure)
    coordinator.iteration = coordinator.iteration + 10
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[OutOfSystem])
    Assert.assertTrue(coordinator.manicurist.state.isInstanceOf[WaitingForOrders])
    Assert.assertFalse(coordinator.manicurist.occupied)
    Assert.assertFalse(coordinator.lamp.occupied)
    Assert.assertTrue(coordinator.lamp.state.isInstanceOf[Idle])
    Assert.assertTrue(stateMachine.next(coordinator).isInstanceOf[OutOfSystem])
  }
}
