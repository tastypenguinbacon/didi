package pl.edu.agh.student.sspd.simulation

import pl.edu.agh.student.sspd.classes._

class Coordinator() {
  val cashierCleaner: CashierCleaner = CashierCleaner(WaitingForOrders())

  val manicurist: Manicurist = Manicurist(WaitingForOrders())
  val pedicurist: Pedicurist = Pedicurist(WaitingForOrders())
  val masseur: Masseur = Masseur(WaitingForOrders())

  val specialist1: Specialist = Specialist(WaitingForOrders())
  val specialist2: Specialist = Specialist(WaitingForOrders())

  val wc: WC = WC(Idle(), needsCleaning = false, occupied = false, 0)
  val lamp: UVLamp = UVLamp(Idle(), occupied = false, failure = false)
  val massageBed1: MassageBed = MassageBed(Idle(), needsCleaning = false, occupied = false, 0)
  val massageBed2: MassageBed = MassageBed(Idle(), needsCleaning = false, occupied = false, 0)

  var iteration: Int = 0
  private var clients: List[Client] = List()

  def add(client: Client): Unit = {

  }

  def add(event: Event): Unit = {

  }

  def next(): Unit = {

  }
}