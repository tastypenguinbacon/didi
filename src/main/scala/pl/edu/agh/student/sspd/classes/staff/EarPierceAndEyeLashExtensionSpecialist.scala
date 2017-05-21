package pl.edu.agh.student.sspd.classes.staff

import pl.edu.agh.student.sspd.classes.ObjectClass
import pl.edu.agh.student.sspd.state.{ActiveState, DeadState, State}

/**
  * Created by pingwin on 01.05.17.
  */
case class EarPierceAndEyeLashExtensionSpecialist
(state: EarPierceAndEyeLashExtensionSpecialistState,
 earPierceQueueLength: Int, eyeLashExtensionQueueLength: Int,
 occupied: Boolean) extends ObjectClass {
}

object EarPierceAndEyeLashExtensionSpecialistState {

  object DeadStates {

    case class WaitingForClientToLeaveAfterEarPiercing()
      extends DeadState with EarPierceAndEyeLashExtensionSpecialistState

    case class WaitingForClientToLeaveAfterEyeLashExtension()
      extends DeadState with EarPierceAndEyeLashExtensionSpecialistState

    case class Idle() extends DeadState with EarPierceAndEyeLashExtensionSpecialistState

  }

  object ActiveStates {

    case class ExtendingEyeLashes(time: Int)
      extends ActiveState with EarPierceAndEyeLashExtensionSpecialistState

    case class PiercingEars(time: Int)
      extends ActiveState with EarPierceAndEyeLashExtensionSpecialistState

    case class CleaningUpAfterPiercing(time: Int)
      extends ActiveState with EarPierceAndEyeLashExtensionSpecialistState

    case class CleaningUpAfterExtension(time: Int)
      extends ActiveState with EarPierceAndEyeLashExtensionSpecialistState

  }

}

trait EarPierceAndEyeLashExtensionSpecialistState extends State