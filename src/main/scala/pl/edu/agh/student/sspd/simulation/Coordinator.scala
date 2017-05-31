package pl.edu.agh.student.sspd.simulation

import java.util.function.Consumer

import pl.edu.agh.student.sspd.classes.ClientActiveStates
import pl.edu.agh.student.sspd.classes.ClientActiveStates.MassageWithdrawal
import pl.edu.agh.student.sspd.classes.EquipmentDeadStates
import pl.edu.agh.student.sspd.classes.StaffActiveStates
import pl.edu.agh.student.sspd.classes.StaffDeadStates
import pl.edu.agh.student.sspd.classes.StaffDeadStates.WaitingForPolishToDry
import pl.edu.agh.student.sspd.classes._

import scala.collection.mutable
import scala.util.Random

class Coordinator(val writer: Consumer[Any]) {
  private var pedicurist = Pedicurist(StaffDeadStates.WaitingForPedicureClient())
  private var manicurist = Manicurist(StaffDeadStates.ManicuristIdle())
  private var masseurs = (Masseur(StaffDeadStates.WaitingForMassageClient()),
    Masseur(StaffDeadStates.WaitingForMassageClient()))
  private var specialist = Specialist(StaffDeadStates.SpecialistIdle())
  private var cashierCleaner = CashierCleaner(StaffDeadStates.WaitingForOrders())

  //  private val specialistQueues = Map(
  //    "pedicurist" -> mutable.Queue[(Client, List[ClientState with ActiveState])](),
  //    "manicurist" -> mutable.Queue[(Client, List[ClientState with ActiveState])](),
  //    "masseur_1" -> mutable.Queue[(Client, List[ClientState with ActiveState])](),
  //    "masseur_2" -> mutable.Queue[(Client, List[ClientState with ActiveState])](),
  //    "specialist" -> mutable.Queue[(Client, List[ClientState with ActiveState])](),
  //    "payment" -> mutable.Queue[(Client, List[ClientState with ActiveState])]()
  //  )

  private var deadStates = Map[DeadState with ClientState, mutable.Queue[(Client, List[ClientState with ActiveState])]]()

  private var uVLamp = UVLamp(EquipmentDeadStates.UVLampIdle(), occupied = false, failure = false)
  private var wc = WC(EquipmentDeadStates.ToiletIdle(), needsCleaning = false, occupied = false, 0)
  private var massageBeds =
    (MassageBed(EquipmentDeadStates.MassageBedIdle(), needsCleaning = false, occupied = false, 0),
      MassageBed(EquipmentDeadStates.MassageBedIdle(), needsCleaning = false, occupied = false, 0))

  //  private val equipmentQueues = Map(
  //    "uv_lamp" -> mutable.Queue[(Client, List[ClientState with ActiveState])](),
  //    "wc" -> mutable.Queue[(Client, List[ClientState with ActiveState])](),
  //    "massage_bed_1" -> mutable.Queue[(Client, List[ClientState with ActiveState])](),
  //    "massage_bed_2" -> mutable.Queue[(Client, List[ClientState with ActiveState])]()
  //  )

  private var iteration: Int = 0

  private var scheduled = List.empty[(Int, Client, List[ClientState])]

  def addEvent(event: Event): Unit = event match {
    case _: ToiletGotDirty => wc = WC(wc.state, needsCleaning = true, wc.occupied, wc.queueLength)
    case _: UvLampDoesNotWork => uVLamp = UVLamp(uVLamp.state, uVLamp.occupied, failure = true)
  }

  def addClient(client: Client, states: List[ClientState with ActiveState]): Unit = client.state match {
    case state: ClientActiveStates.Arrival =>
      writer.accept("Klient przybył")
      if (states.nonEmpty) {
        addClient(Client(states.head), states.tail) //todo
      }
    case state: ClientActiveStates.ManicureWithdraw =>
      writer.accept("Klient rezygnuje z manicure")
      scheduled = (iteration + state.time, client, states) :: scheduled
    case state: ClientActiveStates.FingerNailPainting =>
      if (manicurist.queueLength >= 10) {
        addClient(Client(ClientActiveStates.ManicureWithdraw(randomInRange(2, 5))), states)
      } else {
        writer.accept("Klient ma lakierowane paznokcie u rąk")
        scheduled = (iteration + state.time, client, states) :: scheduled
      }
    case state: ClientActiveStates.FingerNailHardening =>
      writer.accept("Klient ma utwardzane paznokcie u rąk")
      scheduled = (iteration + state.time, client, states) :: scheduled
    case state: ClientActiveStates.PedicureWithdraw =>
      writer.accept("Klient rezygnuje z pedicure")
      scheduled = (iteration + state.time, client, states) :: scheduled
    case state: ClientActiveStates.FeetNailPainting =>
      if (pedicurist.queueLength >= 10) {
        addClient(Client(ClientActiveStates.PedicureWithdraw(randomInRange(2, 5))), states)
      } else {
        writer.accept("Klient ma lakierowane paznokcie u stóp")
        scheduled = (iteration + state.time, client, states) :: scheduled
      }
    case state: ClientActiveStates.FeetNailHardening =>
      writer.accept("Klient ma utwardzane paznokcie u stóp")
      scheduled = (iteration + state.time, client, states) :: scheduled
    case state: ClientActiveStates.EyeLashExtending =>
      if (specialist.eyeLashExtensionQueueLength >= 10) {
        addClient(Client(ClientActiveStates.EyeLashExtendingWithdraw(randomInRange(2, 5))), states)
      } else {
        writer.accept("Klient ma wydłużane rzęsy")
        scheduled = (iteration + state.time, client, states) :: scheduled
      }
    case state: ClientActiveStates.EyeLashExtendingWithdraw =>
      writer.accept("Klient rezygnuje z wydłużania rzęs")
      scheduled = (iteration + state.time, client, states) :: scheduled
    case state: ClientActiveStates.WCOccupation =>
      writer.accept("Klient udaje się do WC")
      scheduled = (iteration + state.time, client, states) :: scheduled
    case state: ClientActiveStates.EarPiercing =>
      if (specialist.earPierceQueueLength >= 10) {
        addClient(Client(ClientActiveStates.EarPiercingWithdraw(randomInRange(2, 5))), states)
      } else {
        writer.accept("Klient ma przebijane uszy")
        scheduled = (iteration + state.time, client, states) :: scheduled
      }
    case state: ClientActiveStates.EarPiercingWithdraw =>
      writer.accept("Klient rezygnuje z przebijania uszu")
      scheduled = (iteration + state.time, client, states) :: scheduled
    case state: ClientActiveStates.Payment =>
      writer.accept("Klient rozpoczyna płacenie")
      scheduled = (iteration + state.time, client, states) :: scheduled
    case state: ClientActiveStates.Massage =>
      if (masseurs._1.queueLength + masseurs._2.queueLength >= 10) {
        addClient(Client(ClientActiveStates.MassageWithdrawal(randomInRange(2, 5))), states)
      }
      writer.accept("Klient rozpoczyna masaż")
      scheduled = (iteration + state.time, client, states) :: scheduled
    case state: ClientActiveStates.MassageWithdrawal =>
      writer.accept("Klient rezygnuje z masażu")
      scheduled = (iteration + state.time, client, states) :: scheduled
  }

  def next(): Unit = {
    iteration += 1
  }

  private def finishedTasks(tuple: (Client, List[ClientState with ActiveState])): Unit = {
    tuple._1.state match {
      case _: ClientActiveStates.ManicureWithdraw =>
        writer.accept("Klient zrezygnował z manicure")
      case _: ClientActiveStates.FingerNailPainting =>
        manicurist.state = StaffDeadStates.WaitingForPolishToDry(randomInRange())
        writer.accept("Klient ma polakierowane paznockie u rąk")
      case _: ClientActiveStates.FingerNailHardening =>
        manicurist.occupied = false
        uVLamp.occupied = false
        writer.accept("Klient ma utwardzone paznokcie u rąk")
      case _: ClientActiveStates.PedicureWithdraw =>
        writer.accept("Klient zrezygnował z pedicure")
      case _: ClientActiveStates.FeetNailPainting =>
        pedicurist.occupied = false
        writer.accept("Klient ma polakierowane paznokcie u stóp")
      case _: ClientActiveStates.FeetNailHardening =>
        pedicurist.occupied = false
        writer.accept("Klient ma utwardzone paznokcie u stóp")
      case _: ClientActiveStates.EyeLashExtending =>
        specialist.occupied = false
        if (massageBeds._1.occupied)
          massageBeds._1.occupied = false
        else
          massageBeds._2.occupied = false
        writer.accept("Klient ma wydłużone rzęsy")
      case _: ClientActiveStates.EyeLashExtendingWithdraw =>
        writer.accept("Klient zrezygnował z wydłużania rzęs")
      case _: ClientActiveStates.WCOccupation =>
        writer.accept("Klient zakończył korzystanie z WC")
      case _: ClientActiveStates.EarPiercing =>
        writer.accept("Klient ma przebite uszy")
      case _: ClientActiveStates.EarPiercingWithdraw =>
        writer.accept("Klient zrezygnował z przebijania uszu")
      case _: ClientActiveStates.Payment =>
        writer.accept("Klient dokonał płatności")
      case _: ClientActiveStates.MassageWithdrawal =>
        writer.accept("Klient zrezygnował z masażu")
      case _: ClientActiveStates.Massage =>
        writer.accept("Klient zakończył masaż")
        if (massageBeds._1.occupied)
          massageBeds._1.occupied = false
        else
          massageBeds._2.occupied = false
        if (masseurs._1.occupied)
          masseurs._1.occupied = false
        else
          masseurs._2.occupied = false
    }
    scheduleNext(tuple._2)
  }

  def scheduleNext(tasksToDo: List[ClientState with ActiveState]): Unit = ???

  private def randomInRange(low: Int, high: Int): Int = Random.nextInt(high - low + 1) + low
}
