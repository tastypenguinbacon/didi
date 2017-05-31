package pl.edu.agh.student.sspd.classes

/**
  * Created by pingwin on 04.05.17.
  */

abstract class Staff extends ObjectClass

trait StaffState extends State

case class CashierCleaner(var state: StaffState, var occupied: Boolean = false, var queueLength: Int = 0) extends Staff

case class Manicurist(var state: StaffState, var occupied: Boolean = false, var queueLength: Int = 0, var needsLamp: Boolean = false)
  extends Staff

case class Pedicurist(var state: StaffState, var occupied: Boolean = false, var queueLength: Int = 0, var needsLamp: Boolean = false)
  extends Staff

case class Specialist(var state: StaffState, var occupied: Boolean = false, var eyeLashExtensionQueueLength: Int = 0,
                      var earPierceQueueLength: Int = 0) extends Staff

case class Masseur(var state: StaffState, var occupied: Boolean = false, var queueLength: Int = 0) extends Staff

object StaffActiveStates {

  case class Payment(time: Int) extends ActiveState with StaffState

  case class WCCleaning(time: Int) extends ActiveState with StaffState

  case class FingerNailHardening(time: Int) extends ActiveState with StaffState

  case class FingerNailPainting(time: Int) extends ActiveState with StaffState

  case class CleaningUpAfterManicure(time: Int) extends ActiveState with StaffState

  case class EquipmentRepair(time: Int) extends ActiveState with StaffState

  case class FootNailHardening(time: Int) extends ActiveState with StaffState

  case class CleaningUpAfterPedicure(time: Int) extends ActiveState with StaffState

  case class FootNailPainting(time: Int) extends ActiveState with StaffState

  case class EarPiercing(time: Int) extends ActiveState with StaffState

  case class CleaningUpAfterEarPiercing(time: Int) extends ActiveState with StaffState

  case class EyeLashExtending(time: Int) extends ActiveState with StaffState

  case class CleaningUpAfterEyeLashExtension(time: Int) extends ActiveState with StaffState

  case class Massaging(time: Int) extends ActiveState with StaffState

  case class CleaningUpAfterMassage(time: Int) extends ActiveState with StaffState

}

object StaffDeadStates {

  case class WaitingForOrders() extends DeadState with StaffState

  case class ManicuristIdle() extends DeadState with StaffState

  case class WaitingForPolishToDry() extends DeadState with StaffState

  case class WaitingForClientToLeave() extends DeadState with StaffState

  case class WaitingForPedicureClient() extends DeadState with StaffState

  case class SpecialistIdle() extends DeadState with StaffState

  case class WaitingForMassageClient() extends DeadState with StaffState

  case class WaitingForMassageClientToLeave() extends DeadState with StaffState

}