package pl.edu.agh.student.sspd.classes

/**
  * Created by pingwin on 05.06.17.
  */
abstract class Staff

case class CashierCleaner(var state: State, var occupied: Boolean = false, var queueLength: Int = 0) extends Staff

case class Manicurist(var state: State, var occupied: Boolean = false, var queueLength: Int = 0, var needsLamp: Boolean = false)
  extends Staff

case class Pedicurist(var state: State, var occupied: Boolean = false, var queueLength: Int = 0, var needsLamp: Boolean = false)
  extends Staff

case class Specialist(var state: State, var occupied: Boolean = false, var eyeLashExtensionQueueLength: Int = 0,
                      var earPierceQueueLength: Int = 0) extends Staff

case class Masseur(var state: State, var occupied: Boolean = false, var queueLength: Int = 0) extends Staff

