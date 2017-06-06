package pl.edu.agh.student.sspd.classes

import pl.edu.agh.student.sspd.simulation.Coordinator

import scala.util.Random

/**
  * Created by pingwin on 05.06.17.
  */
case class Client(var stateMachines: List[StateMachine]) {
  def nextState(global: Coordinator): Unit = {

  }
}

trait StateMachine {
  def next(global: Coordinator): State
}

case class PaymentStateMachine(random: Random) extends StateMachine {
  private var current: State = WaitingForPayment()
  private var nextEvent = -1

  override def next(global: Coordinator): State = {
    current match {
      case WaitingForPayment() => global.cashierCleaner match {
        case CashierCleaner(_, false, _) =>
          val time = 2 + random.nextInt(4)
          current = Payment(time)
          global.cashierCleaner.queueLength = global.cashierCleaner.queueLength - 1
          global.cashierCleaner.occupied = true
          global.cashierCleaner.state = Payment(time)
          nextEvent = global.iteration + time
        case _ =>
          if (nextEvent == -1)
            global.cashierCleaner.queueLength = global.cashierCleaner.queueLength + 1
      }
      case Payment(_) =>
        if (global.iteration == nextEvent) {
          current = OutOfSystem()
          global.cashierCleaner.occupied = false
          global.cashierCleaner.state = WaitingForOrders()
        }
      case _ =>
    }
    current
  }
}

case class PedicureStateMachine(random: Random) extends StateMachine {
  private var current: State = WaitingForPedicurist()
  private var nextEvent = -1

  override def next(global: Coordinator): State = {
    current match {
      case WaitingForPedicurist() =>
        if (nextEvent == -1) {
          nextEvent = 0
          global.pedicurist.queueLength = global.pedicurist.queueLength + 1
        }
        if (global.pedicurist.queueLength > 10) {
          current = PedicureWithdraw(1)
          nextEvent = global.iteration + 1
        } else {
          if (!global.pedicurist.occupied) {
            val time = 30 + random.nextInt(6)
            current = FootNailPainting(time)
            nextEvent = global.iteration + time
            global.pedicurist.occupied = true
            global.pedicurist.queueLength = global.pedicurist.queueLength - 1
          }
        }
      case PedicureWithdraw(_) =>
        if (global.iteration == nextEvent) {
          current = OutOfSystem()
          global.pedicurist.queueLength = global.pedicurist.queueLength - 1
        }
      case FootNailPainting(_) =>
        if (global.iteration == nextEvent) {
          current = WaitingForVanishToDry()
          global.pedicurist.needsLamp = true
        }
      case WaitingForVanishToDry() => {
        if (global.lamp.failure) {
          if (!global.lamp.state.isInstanceOf[EquipmentRepair]) {
            val time = 20 + random.nextInt(5)
            global.lamp.occupied = true
            global.pedicurist.state = EquipmentRepair(time)
            global.lamp.state = EquipmentRepair(time)
            nextEvent = global.iteration + time
          } else {
            if (global.pedicurist.state.isInstanceOf[EquipmentRepair] && nextEvent == global.iteration) {
              val time = 10 + random.nextInt(6)
              nextEvent = nextEvent + time
              current = FootNailHardening(time)
              global.lamp.state = FootNailHardening(time)
              global.lamp.failure = false
              global.pedicurist.state = FootNailHardening(time)
            }
          }
        } else if (!global.lamp.occupied) {
          val time = 10 + random.nextInt(6)
          nextEvent = nextEvent + time
          current = FootNailHardening(time)
          global.lamp.state = FootNailHardening(time)
          global.lamp.occupied = true
          global.pedicurist.state = FootNailHardening(time)
        }
      }
      case FootNailHardening(_) =>
        if (global.iteration == nextEvent) {
          current = OutOfSystem()
          global.pedicurist.state = WaitingForOrders()
          global.pedicurist.occupied = false
          global.lamp.occupied = false
          global.lamp.state = Idle()
        }
      case _ =>
    }
    current
  }
}

case class ManicureStateMachine(random: Random) extends StateMachine {
  private var current: State = WaitingForManicurist()
  private var nextEvent = -1

  override def next(global: Coordinator): State = {
    current match {
      case WaitingForManicurist() =>
        if (nextEvent == -1) {
          nextEvent = 0
          global.manicurist.queueLength = global.manicurist.queueLength + 1
        }
        if (global.manicurist.queueLength > 10) {
          current = ManicureWithdraw(1)
          nextEvent = global.iteration + 1
        } else {
          if (!global.manicurist.occupied) {
            val time = 30 + random.nextInt(6)
            current = FingerNailPainting(time)
            nextEvent = global.iteration + time
            global.manicurist.occupied = true
            global.manicurist.queueLength = global.manicurist.queueLength - 1
          }
        }
      case ManicureWithdraw(_) =>
        if (global.iteration == nextEvent) {
          global.manicurist.queueLength = global.manicurist.queueLength - 1
          current = OutOfSystem()
        }
      case FingerNailPainting(_) =>
        if (global.iteration == nextEvent) {
          current = WaitingForVanishToDry()
          global.manicurist.needsLamp = true
        }
      case WaitingForVanishToDry() =>
        if (global.lamp.failure) {
          if (!global.lamp.state.isInstanceOf[EquipmentRepair]) {
            val time = 20 + random.nextInt(5)
            global.lamp.occupied = true
            global.manicurist.state = EquipmentRepair(time)
            global.lamp.state = EquipmentRepair(time)
            nextEvent = global.iteration + time
          } else {
            if (global.manicurist.state.isInstanceOf[EquipmentRepair] && nextEvent == global.iteration) {
              val time = 10 + random.nextInt(6)
              nextEvent = nextEvent + time
              current = FingerNailHardening(time)
              global.lamp.state = FingerNailHardening(time)
              global.lamp.failure = false
              global.manicurist.state = FingerNailHardening(time)
            }
          }
        } else if (!global.lamp.occupied) {
          val time = 10 + random.nextInt(6)
          nextEvent = nextEvent + time
          current = FingerNailHardening(time)
          global.lamp.state = FingerNailHardening(time)
          global.lamp.occupied = true
          global.manicurist.state = FingerNailHardening(time)
        }
      case FingerNailHardening(_) =>
        if (global.iteration == nextEvent) {
          current = OutOfSystem()
          global.manicurist.state = WaitingForOrders()
          global.manicurist.occupied = false
          global.lamp.occupied = false
          global.lamp.state = Idle()
        }
      case _ =>
    }
    current
  }
}

case class MassageStateMachine(random: Random) extends StateMachine {
  private var current: State = WaitingForMassage()
  private var nextEvent = -1
  private var bed: MassageBed = _

  override def next(global: Coordinator): State = {
    current match {
      case WaitingForMassage() =>
        if (nextEvent == -1) {
          global.masseur.queueLength = global.masseur.queueLength + 1
          nextEvent = 0
        }
        if (global.masseur.queueLength > 10) {
          current = MassageWithdraw(1)
          nextEvent = global.iteration + 1
        } else {
          if (!global.masseur.occupied && !global.massageBed1.occupied
            && !global.massageBed1.needsCleaning) {
            this.bed = global.massageBed1
            val time = 40 + random.nextInt(6)
            nextEvent = time + global.iteration
            current = Massage(time)
            global.massageBed1.occupied = true
            global.massageBed1.state = Massage(time)
          }
          if (!global.masseur.occupied && !global.massageBed2.occupied
            && !global.massageBed2.needsCleaning) {
            this.bed = global.massageBed2
            val time = 40 +random.nextInt(6)
            nextEvent = time + global.iteration
            current = Massage(time)
            global.massageBed2.occupied = true
            global.massageBed2.state = Massage(time)
          }
        }
      case MassageWithdraw(_) =>
        current = OutOfSystem()
        global.masseur.queueLength = global.masseur.queueLength - 1
      case Massage(_) =>
        if (global.iteration == nextEvent) {
          current = OutOfSystem()
          val time = 10 + random.nextInt(6) + global.iteration
          global.masseur.state = CleaningUpAfterMassage(time)
          this.bed.state = CleaningUpAfterMassage(time)
          this.bed.needsCleaning = true
        }
      case _ =>
    }
    current
  }
}