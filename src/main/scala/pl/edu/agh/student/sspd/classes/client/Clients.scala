package pl.edu.agh.student.sspd.classes.client

import pl.edu.agh.student.sspd.classes.ObjectClass
import pl.edu.agh.student.sspd.state.{ActiveState, State}

/**
  * Created by pingwin on 21.05.17.
  */
trait Client extends ObjectClass

trait ClientState extends State

case class PedicureClient(state: PedicureClientState) extends Client

trait PedicureClientState extends ClientState

case class ManicureClient(state: ManicureClientState) extends Client

trait ManicureClientState extends ClientState

case class EarPierceClient(state: EarPierceClientState) extends Client

trait EarPierceClientState extends ClientState

case class MassageClient(state: MassageClientState) extends Client

trait MassageClientState extends ClientState

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
    with ManicureClientState
    with ManicureMassageClientState
    with ManicureEyeLashEarPierceClientState
    with ManicurePedicureMassageClientState

  case class FingerNailPainting(time: Int) extends ActiveState
    with ManicureClientState
    with ManicureMassageClientState
    with ManicureEyeLashEarPierceClientState
    with ManicurePedicureMassageClientState

  case class FingerNailHardening(time: Int) extends ActiveState
    with ManicureClientState
    with ManicureMassageClientState
    with ManicureEyeLashEarPierceClientState
    with ManicurePedicureMassageClientState

  case class PedicureWithdraw(time: Int) extends ActiveState
    with PedicureMassageClientState
    with PedicureClientState
    with PedicureEyeLashWCClientState
    with ManicurePedicureMassageClientState

  case class FeetNailPainting(time: Int) extends ActiveState
    with PedicureMassageClientState
    with PedicureClientState
    with PedicureEyeLashWCClientState
    with ManicurePedicureMassageClientState

  case class FeetNailHardening(time: Int) extends ActiveState
    with PedicureMassageClientState
    with PedicureClientState
    with PedicureEyeLashWCClientState
    with ManicurePedicureMassageClientState

  case class EyeLashExtending(time: Int) extends ActiveState
    with EyeLashClientState
    with MassageEyeLashClientState
    with PedicureEyeLashWCClientState
    with ManicureEyeLashEarPierceClientState

  case class EyeLashExtendingWithdraw(time: Int) extends ActiveState
    with EyeLashClientState
    with MassageEyeLashClientState
    with PedicureEyeLashWCClientState
    with ManicureEyeLashEarPierceClientState

  case class WCOccupation(time: Int) extends ActiveState
    with WCClientState
    with PedicureEyeLashWCClientState

  case class EarPiercing(time: Int) extends ActiveState
    with EarPierceClientState
    with ManicureEyeLashEarPierceClientState

  case class EarPiercingWithdraw(time: Int) extends ActiveState
    with EarPierceClientState
    with ManicureEyeLashEarPierceClientState

  case class Payment(time: Int) extends ActiveState
    with ClientState

}

object DeadStates {

}
