package pl.edu.agh.student.sspd.classes

import pl.edu.agh.student.sspd.simulation.Coordinator

import scala.util.Random

trait Client {
  var coordinator: List[CoordinatorPart]
  var first: CoordinatorPart
  private var previous: State = _

  def nextState(global: Coordinator): Option[State] = {
    val next = first.next(global)
    if (next == previous)
      return None
    next match {
      case OutOfSystem() =>
        if (coordinator.isEmpty) {
          previous = OutOfSystem()
          return Some(OutOfSystem())
        }
        first = coordinator.head
        coordinator = coordinator.tail
        nextState(global)
      case _ =>
        previous = next
        Some(next)
    }
  }
}

case class ManicureClient() extends Client {
  var coordinator: List[CoordinatorPart] =
    List(PaymentCoordinatorPart(new Random))
  var first: CoordinatorPart = ManicureCoordinatorPart(new Random)
}

case class PedicureClient() extends Client {
  var coordinator: List[CoordinatorPart] =
    List(PaymentCoordinatorPart(new Random))
  var first: CoordinatorPart = PedicureCoordinatorPart(new Random)
}

case class WCClient() extends Client {
  var coordinator: List[CoordinatorPart] = List()
  var first: CoordinatorPart = WcCoordinatorPart(new Random)
}

case class EarPierceClient() extends Client {
  var coordinator: List[CoordinatorPart] = List(PaymentCoordinatorPart(new Random))
  var first: CoordinatorPart = EarPiercingCoordinatorPart(new Random)
}

case class MassageClient() extends Client {
  var coordinator: List[CoordinatorPart] = List(PaymentCoordinatorPart(new Random))
  var first: CoordinatorPart = MassageCoordinatorPart(new Random)
}

case class EyeLashClient() extends Client {
  var coordinator: List[CoordinatorPart] = List(PaymentCoordinatorPart(new Random))
  var first: CoordinatorPart = EyeLashExtensionCoordinatorPart(new Random)
}

case class MassageEyeLashClient() extends Client {
  var coordinator: List[CoordinatorPart] = List(EyeLashExtensionCoordinatorPart(new Random), PaymentCoordinatorPart(new Random))
  var first: CoordinatorPart = MassageCoordinatorPart(new Random)
}

case class ManicureMassageClient() extends Client {
  var coordinator: List[CoordinatorPart] = List(MassageCoordinatorPart(new Random),
    PaymentCoordinatorPart(new Random))
  override var first: CoordinatorPart = ManicureCoordinatorPart(new Random)
}

case class PedicureMassageClient() extends Client {
  var coordinator: List[CoordinatorPart] = List(MassageCoordinatorPart(new Random),
    PaymentCoordinatorPart(new Random))
  override var first: CoordinatorPart = PedicureCoordinatorPart(new Random)
}

case class ManicureEyeLashEarCliet() extends Client {
  var coordinator: List[CoordinatorPart] = List(EyeLashExtensionCoordinatorPart(new Random),
    EarPiercingCoordinatorPart(new Random))
  override var first: CoordinatorPart = ManicureCoordinatorPart(new Random)
}

case class PedicureEyeLashWCClient() extends Client {
  var coordinator: List[CoordinatorPart] = List(EyeLashExtensionCoordinatorPart(new Random),
    WcCoordinatorPart(new Random), PaymentCoordinatorPart(new Random))
  override var first: CoordinatorPart = PedicureCoordinatorPart(new Random)
}

case class UniversalClient() extends Client {
  var coordinator: List[CoordinatorPart] = List(
    ManicureCoordinatorPart(new Random),
    MassageCoordinatorPart(new Random),
    EyeLashExtensionCoordinatorPart(new Random),
    EarPiercingCoordinatorPart(new Random)

  )
  override var first: CoordinatorPart = PedicureCoordinatorPart(new Random)
}


trait CoordinatorPart {
  def next(global: Coordinator): State
}

case class PaymentCoordinatorPart(random: Random) extends CoordinatorPart {
  private var current: State = WaitingForPayment()
  private var nextEvent = -1

  override def next(global: Coordinator): State = {
    if (nextEvent == -1) {
      global.cashierCleaner.queueLength = global.cashierCleaner.queueLength + 1
      nextEvent = 0
    }
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

case class PedicureCoordinatorPart(random: Random) extends CoordinatorPart {
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
          global.pedicurist.queueLength = global.pedicurist.queueLength - 1
          current = PedicureWithdraw(1)
          nextEvent = global.iteration + 1
        } else {
          if (!global.pedicurist.occupied) {
            val time = 30 + random.nextInt(6)
            current = FootNailPainting(time)
            nextEvent = global.iteration + time
            global.pedicurist.state = FootNailPainting(time)
            global.pedicurist.occupied = true
            global.pedicurist.queueLength = global.pedicurist.queueLength - 1
          }
        }
      case PedicureWithdraw(_) =>
        if (global.iteration == nextEvent) {
          current = OutOfSystem()
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
          nextEvent = global.iteration + time
          current = FootNailHardening(time)
          global.lamp.state = FootNailHardening(time)
          global.lamp.occupied = true
          global.pedicurist.state = FootNailHardening(time)
        }
      }
      case FootNailHardening(_) if global.iteration == nextEvent => {
          current = OutOfSystem()
          val time = 10 + random.nextInt(6) + global.iteration
          global.pedicurist.state = CleaningUpAfterPedicure(time)
          global.pedicurist.needsLamp = false
          global.lamp.occupied = false
          global.lamp.state = Idle()
        }
      case _ =>
    }
    current
  }
}

case class ManicureCoordinatorPart(random: Random) extends CoordinatorPart {
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
          global.manicurist.queueLength = global.manicurist.queueLength - 1
          current = ManicureWithdraw(1)
          nextEvent = global.iteration + 1
        } else {
          if (!global.manicurist.occupied) {
            val time = 30 + random.nextInt(6)
            current = FingerNailPainting(time)
            global.manicurist.state = FingerNailPainting(time)
            nextEvent = global.iteration + time
            global.manicurist.occupied = true
            global.manicurist.queueLength = global.manicurist.queueLength - 1
          }
        }
      case ManicureWithdraw(_) =>
        if (global.iteration == nextEvent) {
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
          nextEvent = global.iteration + time
          current = FingerNailHardening(time)
          global.lamp.state = FingerNailHardening(time)
          global.lamp.occupied = true
          global.manicurist.state = FingerNailHardening(time)
        }
      case FingerNailHardening(_) =>
        if (global.iteration == nextEvent) {
          current = OutOfSystem()
          val time = 10 + random.nextInt(6) + global.iteration
          global.manicurist.state = CleaningUpAfterManicure(time)
          global.manicurist.needsLamp = false
          global.lamp.occupied = false
          global.lamp.state = Idle()
        }
      case _ =>
    }
    current
  }
}

case class MassageCoordinatorPart(random: Random) extends CoordinatorPart {
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
          global.masseur.queueLength = global.masseur.queueLength - 1
        } else {
          if (!global.masseur.occupied && !global.massageBed1.occupied
            && !global.massageBed1.needsCleaning) {
            this.bed = global.massageBed1
            val time = 40 + random.nextInt(6)
            nextEvent = time + global.iteration
            current = Massage(time)
            global.massageBed1.occupied = true
            global.massageBed1.state = Massage(time)
            global.masseur.state = Massage(time)
            global.masseur.occupied = true
            global.masseur.queueLength -= 1
          } else if (!global.masseur.occupied && !global.massageBed2.occupied
            && !global.massageBed2.needsCleaning) {
            this.bed = global.massageBed2
            val time = 40 + random.nextInt(6)
            nextEvent = time + global.iteration
            current = Massage(time)
            global.massageBed2.occupied = true
            global.massageBed2.state = Massage(time)
            global.masseur.occupied = true
            global.masseur.state = Massage(time)
            global.masseur.queueLength -= 1
          }
        }
      case MassageWithdraw(_) =>
        current = OutOfSystem()
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

case class WcCoordinatorPart(random: Random) extends CoordinatorPart {
  private var current: State = WaitingForWC()
  private var nextEvent = -1

  override def next(global: Coordinator): State = {
    if (nextEvent == -1) {
      nextEvent = 0
      global.wc.queueLength += 1
    }
    current match {
      case WaitingForWC() =>
        if (!global.wc.occupied && !global.wc.needsCleaning) {
          val time = 2 + random.nextInt(9)
          nextEvent = global.iteration + time
          current = WCOccupation(time)
          global.wc.state = WCOccupation(time)
          global.wc.occupied = true
          global.wc.queueLength -= 1
        } else {
          if (!global.wc.occupied && !global.cashierCleaner.occupied) {
            val time = 2 + random.nextInt(9) + global.iteration
            global.cashierCleaner.state = WCCleaning(time)
            global.cashierCleaner.occupied = true
            global.wc.state = WCCleaning(time)
          }
        }
      case WCOccupation(_) =>
        if (global.iteration == nextEvent) {
          current = OutOfSystem()
          global.wc.occupied = false
          global.wc.state = Idle()
        }
      case _ =>

    }
    current
  }
}

case class EarPiercingCoordinatorPart(random: Random) extends CoordinatorPart {
  private var current: State = WaitingForSpecialist()
  private var nextEvent = -1
  private var specialist: Specialist = _
  private var bed: MassageBed = _

  override def next(global: Coordinator): State = {
    if (nextEvent == -1) {
      global.specialist1.earPierceQueueLength += 1
      global.specialist2.earPierceQueueLength += 1
      nextEvent = 0
    }
    current match {
      case WaitingForSpecialist() =>
        if (global.specialist1.earPierceQueueLength > 10) {
          global.specialist1.earPierceQueueLength -= 1
          global.specialist2.earPierceQueueLength -= 1
          current = EarPiercingWithdraw(1)
          nextEvent = global.iteration + 1
        } else if (!global.specialist1.occupied || !global.specialist2.occupied) {
          if (!global.massageBed1.occupied || !global.massageBed2.occupied) {
            if (!global.specialist1.occupied)
              specialist = global.specialist1
            else
              specialist = global.specialist2
            if (!global.massageBed1.occupied)
              bed = global.massageBed1
            else
              bed = global.massageBed2
            val time = 2 + random.nextInt(4)
            specialist.occupied = true
            bed.occupied = true
            specialist.state = EarPiercing(time)
            bed.state = EarPiercing(time)
            current = EarPiercing(time)
            nextEvent = time + global.iteration
            global.specialist1.earPierceQueueLength -= 1
            global.specialist2.earPierceQueueLength -= 1
          }
        }
      case EarPiercing(_) =>
        if (global.iteration == nextEvent) {
          current = OutOfSystem()
          bed.needsCleaning = true
          val time = 10 + random.nextInt(6) + global.iteration
          specialist.state = CleaningUpAfterEarPiercing(time)
          bed.state = CleaningUpAfterEarPiercing(time)
        }
      case _ =>
    }
    current
  }
}

case class EyeLashExtensionCoordinatorPart(random: Random) extends CoordinatorPart {
  private var current: State = WaitingForSpecialist()
  private var nextEvent = -1
  private var specialist: Specialist = _

  override def next(global: Coordinator): State = {
    if (nextEvent == -1) {
      global.specialist1.eyeLashExtensionQueueLength += 1
      global.specialist2.eyeLashExtensionQueueLength += 1
      nextEvent = 0
    }
    current match {
      case WaitingForSpecialist() =>
        if (global.specialist1.eyeLashExtensionQueueLength > 10) {
          global.specialist1.eyeLashExtensionQueueLength -= 1
          global.specialist2.eyeLashExtensionQueueLength -= 1
          current = EyeLashExtendingWithdraw(1)
          nextEvent = global.iteration + 1
        } else if (!global.specialist1.occupied || !global.specialist2.occupied) {
          if (!global.specialist1.occupied) {
            specialist = global.specialist1
          } else {
            specialist = global.specialist2
          }
          specialist.occupied = true
          val time = 2 + random.nextInt(4)
          specialist.state = EyeLashExtending(time)
          nextEvent = global.iteration + time
          current = EyeLashExtending(time)
          global.specialist1.eyeLashExtensionQueueLength -= 1
          global.specialist2.eyeLashExtensionQueueLength -= 1
        }
      case EyeLashExtending(_) if nextEvent == global.iteration =>
        val time = 10 + random.nextInt(6) + global.iteration
        specialist.state = CleaningUpAfterEyeLashExtension(time)
        current = OutOfSystem()
      case _ =>
    }
    current
  }
}
