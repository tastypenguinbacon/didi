package pl.edu.agh.student.sspd.classes

import pl.edu.agh.student.sspd.classes.ClientActiveStates.Arrival

/**
  * Created by pingwin on 01.05.17.
  */

case class Client(state: ClientState = Arrival()) extends ObjectClass

trait ClientState extends State

object ClientActiveStates {

  case class Arrival() extends ActiveState with ClientState {
    val time = 1
  }

  case class ManicureWithdraw(time: Int) extends ActiveState with ClientState

  case class FingerNailPainting(time: Int) extends ActiveState with ClientState

  case class FingerNailHardening(time: Int) extends ActiveState with ClientState

  case class PedicureWithdraw(time: Int) extends ActiveState with ClientState

  case class FeetNailPainting(time: Int) extends ActiveState with ClientState

  case class FeetNailHardening(time: Int) extends ActiveState with ClientState

  case class EyeLashExtending(time: Int) extends ActiveState with ClientState

  case class EyeLashExtendingWithdraw(time: Int) extends ActiveState with ClientState

  case class WCOccupation(time: Int) extends ActiveState with ClientState

  case class EarPiercing(time: Int) extends ActiveState with ClientState

  case class EarPiercingWithdraw(time: Int) extends ActiveState with ClientState

  case class Payment(time: Int) extends ActiveState with ClientState

  case class Massage(time: Int) extends ActiveState with ClientState

  case class MassageWithdrawal(time: Int) extends ActiveState with ClientState

}

object ClientDeadStates {

  case class OutOfSystem() extends DeadState with ClientState

  case class WaitingForPedicurist() extends DeadState with ClientState

  case class WaitingForTheVarnishToDry() extends DeadState with ClientState

  case class WaitingForManicurist() extends DeadState with ClientState

  case class WaitingForMassage() extends DeadState with ClientState

  case class WaitingForSpecialist() extends DeadState with ClientState

  case class WaitingForWC() extends DeadState with ClientState

  case class WaitingForPayment() extends DeadState with ClientState

}
