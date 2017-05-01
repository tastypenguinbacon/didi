package pl.edu.agh.student.sspd.classes.utils

import pl.edu.agh.student.sspd.classes.ObjectClass
import pl.edu.agh.student.sspd.state.{ActiveState, DeadState, State}

/**
  * Created by pingwin on 01.05.17.
  */
case class UvLamp(state: UVLampState, occupied: Boolean, needsRepair: Boolean)
  extends ObjectClass {
  override def getState: UVLampState = state
}

object UVLampState {

  object ActiveStates {

    case class HardeningHandNails(time: Int) extends ActiveState with UVLampState

    case class HardeningFootNails(time: Int) extends ActiveState with UVLampState

    case class EquipmentRepair(time: Int) extends ActiveState with UVLampState

  }


  object DeadStates {

    case class Unused() extends DeadState with UVLampState

  }

}

trait UVLampState extends State