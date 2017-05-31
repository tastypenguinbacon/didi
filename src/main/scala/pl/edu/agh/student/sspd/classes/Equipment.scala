package pl.edu.agh.student.sspd.classes

/**
  * Created by pingwin on 02.05.17.
  */
abstract class Equipment extends ObjectClass

trait EquipmentState extends State

case class WC(var state: EquipmentState, var needsCleaning: Boolean, var occupied: Boolean, var queueLength: Int)
  extends Equipment

case class MassageBed(var state: EquipmentState, var needsCleaning: Boolean, var occupied: Boolean, var queueLength: Int)
  extends Equipment

case class UVLamp(var state: EquipmentState, var occupied: Boolean, var failure: Boolean)
  extends Equipment

object EquipmentActiveStates {

  case class WCUse(time: Int) extends ActiveState with EquipmentState

  case class WCCleanup(time: Int) extends ActiveState with EquipmentState

  case class UsedByMasseur(time: Int) extends ActiveState with EquipmentState

  case class MassageBedCleanup(time: Int) extends ActiveState with EquipmentState

  case class EarPiercing(time: Int) extends ActiveState with EquipmentState

  case class FingerNailHardening(time: Int) extends ActiveState with EquipmentState

  case class FootNailHardening(time: Int) extends ActiveState with EquipmentState

  case class Repair(time: Int) extends ActiveState with EquipmentState

}

object EquipmentDeadStates {

  case class ToiletIdle() extends DeadState with EquipmentState

  case class MassageBedIdle() extends DeadState with EquipmentState

  case class WaitingForClientToLeave() extends DeadState with EquipmentState

  case class UVLampIdle() extends DeadState with EquipmentState

}