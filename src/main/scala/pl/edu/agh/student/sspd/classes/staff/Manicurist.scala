package pl.edu.agh.student.sspd.classes.staff

import pl.edu.agh.student.sspd.classes.ObjectClass
import pl.edu.agh.student.sspd.state.{ActiveState, DeadState, State}

/**
  * Created by pingwin on 01.05.17.
  */
case class Manicurist(state: ManicuristState, queueLength: Int, occupied: Boolean, needsLamp: Boolean)
  extends ObjectClass {
  override def getState: ManicuristState = state
}

object ManicuristState {

  object ActiveStates {

    case class PaintingNails(time: Int) extends ActiveState with ManicuristState

    case class HardeningNails(time: Int) extends ActiveState with ManicuristState

    case class RepairingEquipment(time: Int) extends ActiveState with ManicuristState

    case class CleanUp(time: Int) extends ActiveState with ManicuristState

  }

  object DeadStates {

    case class Idle() extends DeadState with ManicuristState

    case class WaitingForClientToLeave() extends DeadState with ManicuristState

    case class WaitingForVarnishToHarden() extends DeadState with ManicuristState

  }

}

trait ManicuristState extends State