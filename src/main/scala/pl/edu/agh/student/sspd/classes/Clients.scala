package pl.edu.agh.student.sspd.classes

import pl.edu.agh.student.sspd.state.{ActiveState, DeadState, State}

/**
  * Created by pingwin on 21.05.17.
  */
abstract class Client extends ObjectClass

trait ClientState extends State

case class PedicureClient(state: ClientState) extends Client

case class ManicureClient(state: ClientState) extends Client

case class EarPierceClient(state: ClientState) extends Client

case class MassageClient(state: ClientState) extends Client

case class EyeLashClient(state: ClientState) extends Client

case class WCClient(state: ClientState) extends Client

case class MassageEyeLashClient(state: ClientState) extends Client

case class ManicureMassageClient(state: ClientState) extends Client

case class PedicureMassageClient(state: ClientState) extends Client

case class ManicureEyeLashEarPierceClient(state: ClientState) extends Client

case class PedicureEyeLashWCClient(state: ClientState) extends Client

case class ManicurePedicureMassageClient(state: ClientState) extends Client

case class UniversalClient[T <: ClientState](state: T) extends Client

object ClientActiveStates {

  case class Arrival(time: Int) extends ActiveState with ClientState

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
