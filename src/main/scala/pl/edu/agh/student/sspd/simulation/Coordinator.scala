package pl.edu.agh.student.sspd.simulation

import pl.edu.agh.student.sspd.classes._

class Coordinator(consumer: (String) => Unit = (_: String) => _) {
  val cashierCleaner: CashierCleaner = CashierCleaner(WaitingForOrders())

  val manicurist: Manicurist = Manicurist(WaitingForOrders())
  val pedicurist: Pedicurist = Pedicurist(WaitingForOrders())
  val masseur: Masseur = Masseur(WaitingForOrders())

  val specialist1: Specialist = Specialist(WaitingForOrders())
  val specialist2: Specialist = Specialist(WaitingForOrders())

  val wc: WC = WC(Idle(), needsCleaning = false, occupied = false, 0)
  val lamp: UVLamp = UVLamp(Idle(), occupied = false, failure = false)
  val massageBed1: MassageBed = MassageBed(Idle(), needsCleaning = false, occupied = false)
  val massageBed2: MassageBed = MassageBed(Idle(), needsCleaning = false, occupied = false)

  var iteration: Int = 0
  private var clients: List[Client] = List()
  private var events: List[Event] = List()

  def add(client: Client): Unit = {
    clients = client :: clients
  }

  def add(event: Event): Unit = {
    events = event :: events
  }

  def next(): Unit = {
    iteration += 1
    finishUngratefulWork()

    clients.map(c => c.nextState(this))
      .filter(_.isInstanceOf[Some[Any]])
      .map(_.get)
      .map(_.message)
      .foreach(consumer)
  }

  private def finishUngratefulWork(): Unit = {
    cashierCleaner.state match {
      case WCCleaning(time) if time == iteration =>
      case _ =>
    }

    masseur.state match {
      case CleaningUpAfterMassage(time) if time == iteration =>
        masseur.state = WaitingForOrders()
        masseur.occupied = false
        List(massageBed2, massageBed1).foreach(mb => mb.state match {
          case CleaningUpAfterMassage(_) =>
            mb.state = Idle()
            mb.occupied = false
            mb.needsCleaning = false
          case _ =>
        })
      case _ =>
    }

    manicurist.state match {
      case CleaningUpAfterManicure(time) if time == iteration =>
        manicurist.state = WaitingForOrders()
        manicurist.occupied = false
        manicurist.needsLamp = false
      case _ =>
    }

    manicurist.state match {
      case EquipmentRepair(time) if time == iteration =>
        manicurist.state = manicurist.onHold
        lamp.state = Idle()
        lamp.occupied = false
        lamp.failure = false
      case _ =>
    }

    pedicurist.state match {
      case CleaningUpAfterPedicure(time) if time == iteration =>
        pedicurist.state = WaitingForOrders()
        pedicurist.occupied = false
        pedicurist.needsLamp = false
      case _ =>
    }

    pedicurist.state match {
      case EquipmentRepair(time) if time == iteration =>
        pedicurist.state = pedicurist.onHold
        lamp.state = Idle()
        lamp.occupied = false
        lamp.failure = false
      case _ =>
    }

    List(specialist1, specialist2).foreach(s => s.state match {
      case CleaningUpAfterEarPiercing(time) if time == iteration =>
        s.state = WaitingForOrders()
        s.occupied = false
        List(massageBed1, massageBed2).foreach(mb => mb.state match {
          case CleaningUpAfterEarPiercing(t) if t == iteration =>
            mb.state = Idle()
            mb.needsCleaning = false
            mb.occupied = false
          case _ =>
        })
      case CleaningUpAfterEyeLashExtension(time) if time == iteration =>
        s.state = WaitingForOrders()
        s.occupied =false
      case _ =>
    })

    cashierCleaner.state match {
      case WCCleaning(time) if time == iteration =>
        wc.occupied = false
        wc.state = Idle()
        wc.needsCleaning = false
        cashierCleaner.occupied = false
        cashierCleaner.state = WaitingForOrders()
      case _ =>
    }
  }
}