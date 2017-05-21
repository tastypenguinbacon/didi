package pl.edu.agh.student.sspd.classes.client

import pl.edu.agh.student.sspd.classes.ObjectClass
import pl.edu.agh.student.sspd.state.{ActiveState, State}

/**
  * Created by pingwin on 21.05.17.
  */
trait Client extends ObjectClass

trait ClientState extends State

case class PedicureClient(state: PedicureClientState) extends Client

trait PedicureClientState extends PedicureEyeLashWCClientState with PedicureMassageClientState with ManicurePedicureMassageClientState

case class ManicureClient(state: ManicureClientState) extends Client

trait ManicureClientState extends ManicurePedicureMassageClientState with ManicureMassageClientState with ManicureEyeLashEarPierceClientState

case class EarPierceClient(state: EarPierceClientState) extends Client

trait EarPierceClientState extends ManicureEyeLashEarPierceClientState

case class MassageClient(state: MassageClientState) extends Client

trait MassageClientState extends MassageEyeLashClientState // todo tutaj wątpliwości ;______;

case class EyeLashClient(state: EyeLashClientState) extends Client

trait EyeLashClientState extends ClientState

case class WCClient(state: WCClientState) extends Client

trait WCClientState extends ClientState

case class MassageEyeLashClient(state: MassageEyeLashClientState) extends Client

trait MassageEyeLashClientState extends ClientState

case class ManicureMassageClient(state: ManicureMassageClientState) extends Client

trait ManicureMassageClientState extends ClientState

case class PedicureMassageClient(state: PedicureMassageClientState) extends Client

trait PedicureMassageClientState extends ClientState

case class ManicureEyeLashEarPierceClient(state: ManicureEyeLashEarPierceClientState) extends Client

trait ManicureEyeLashEarPierceClientState extends ClientState

case class PedicureEyeLashWCClient(state: PedicureEyeLashWCClientState) extends Client

trait PedicureEyeLashWCClientState extends ClientState

case class ManicurePedicureMassageClient(state: ManicurePedicureMassageClientState) extends Client

trait ManicurePedicureMassageClientState extends ClientState

case class UniversalClient[T <: ClientState](state: T) extends Client

object ActiveStates {

  case class Arrival(time: Int) extends ActiveState with ClientState

  case class ManicureWithdraw(time: Int) extends ActiveState


  case class FingerNailPainting(time: Int) extends ActiveState

  case class FingerNailHardening(time: Int) extends ActiveState

  case class PedicureWithdraw(time: Int) extends ActiveState

  case class FeetNailPainting(time: Int) extends ActiveState

  case class FeetNailHardening(time: Int) extends ActiveState

  case class EyeLashExtending(time: Int) extends ActiveState

  case class EyeLashExtendingWithdraw(time: Int) extends ActiveState

  case class WCOccupation(time: Int) extends ActiveState

  case class EarPiercing(time: Int) extends ActiveState

  case class EarPiercingWithdraw(time: Int) extends ActiveState

  case class Payment(time: Int) extends ActiveState

}

object DeadStates {
  case class
}
