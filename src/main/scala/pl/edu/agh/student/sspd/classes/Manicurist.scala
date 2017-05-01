package pl.edu.agh.student.sspd.classes

import pl.edu.agh.student.sspd.classes.Manicurist.{ActiveState, DeadState}

object Manicurist {

  def apply(state: State): Manicurist = state match {
    case s: ActiveState => OccupiedManicurists(s)
    case s: DeadState => UnOccupiedManicurist(s)
  }

  trait ActiveState extends State

  case class PolishingNails(time: Int) extends ActiveState

  case class HardeningNails(time: Int) extends ActiveState

  case class RepairingEquipment(time: Int) extends ActiveState

  case class CleaningUp(time: Int) extends ActiveState

  trait DeadState extends State

  case class Idle() extends DeadState

  case class WaitingForClientToLeave() extends DeadState

  case class WaitingForNailPolishToHarden() extends DeadState

}

trait Manicurist

case class OccupiedManicurists(state: ActiveState) extends Manicurist

case class UnOccupiedManicurist(state: DeadState) extends Manicurist