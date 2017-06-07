package pl.edu.agh.student.sspd.classes

/**
  * Created by pingwin on 05.06.17.
  */
trait State {
  val message: String
}

trait ActiveState extends State {
  val time: Int
}

trait DeadState extends State

case class Arrival() extends ActiveState {
  override val message: String = "Klient przybył"
  override val time: Int = 1
}

case class ManicureWithdraw(time: Int) extends ActiveState {
  override val message: String = "Klient rezygnuje z manicure"
}

case class FingerNailPainting(time: Int) extends ActiveState {
  override val message: String = "Klient ma malowane paznokcie u rąk"
}

case class FingerNailHardening(time: Int) extends ActiveState {
  override val message: String = "Klient ma utwardzane paznokcie u rąk"
}

case class PedicureWithdraw(time: Int) extends ActiveState {
  override val message: String = "Klient rezygnuje z Pedicure"
}

case class FootNailPainting(time: Int) extends ActiveState {
  override val message: String = "Klient ma malowane paznokcie u stóp"
}

case class FootNailHardening(time: Int) extends ActiveState {
  override val message: String = "Klient ma utwardzane paznokcie u stóp"
}

case class EyeLashExtending(time: Int) extends ActiveState {
  override val message: String = "Klient ma wydłużane rzęsy"
}

case class EyeLashExtendingWithdraw(time: Int) extends ActiveState {
  override val message: String = "Klient rezygnuje z wydłużania rzęs"
}

case class WCOccupation(time: Int) extends ActiveState {
  override val message: String = "Klient zajmuje toaletę"
}

case class EarPiercing(time: Int) extends ActiveState {
  override val message: String = "Klient ma przebijane uszy"
}

case class EarPiercingWithdraw(time: Int) extends ActiveState {
  override val message: String = "Klient rezygnuje z przebijania uszu"
}

case class Massage(time: Int) extends ActiveState {
  override val message: String = "Klient jest masowany"
}

case class MassageWithdraw(time: Int) extends ActiveState {
  override val message: String = "Klient rezygnuje z masażu"
}

case class Payment(time: Int) extends ActiveState {
  override val message: String = "Klient dokonuje płacenia"
}

case class OutOfSystem() extends DeadState {
  override val message: String = "Klient poza systemem"
}

case class WaitingForManicurist() extends DeadState {
  override val message: String = "Klient czeka w kolejce do manicure"
}

case class WaitingForPedicurist() extends DeadState {
  override val message: String = "Klient czeka w kolejce do pedicure"
}

case class WaitingForVanishToDry() extends DeadState {
  override val message: String = "Klient czeka aż lakier przeschnie"
}

case class WaitingForMassage() extends DeadState {
  override val message: String = "Klient czeka w kolejce do masażu"
}

case class WaitingForSpecialist() extends DeadState {
  override val message: String = "Klient czeka w kolejce do specjalistki"
}

case class WaitingForWC() extends DeadState {
  override val message: String =  "Klient w kolejce do WC"
}

case class WaitingForPayment() extends DeadState {
  override val message: String = "Klient w kolejce do zapłaty"
}

case class WCCleaning(time: Int) extends ActiveState {
  override val message: String = "Czyszczenie toalety"
}

case class CleaningUpAfterManicure(time: Int) extends ActiveState {
  override val message: String = "Czyszczenie po manicure"
}

case class EquipmentRepair(time: Int) extends ActiveState {
  override val message: String = "Naprawa sprzętu"
}

case class CleaningUpAfterPedicure(time: Int) extends ActiveState {
  override val message: String = "Sprzątanie po pedicure"
}

case class CleaningUpAfterEarPiercing(time: Int) extends ActiveState {
  override val message: String = "Sprzątanie po przebijaniu uszu"
}

case class CleaningUpAfterEyeLashExtension(time: Int) extends ActiveState {
  override val message: String = "Sprzątanie po przedłużaniu rzęs"
}

case class CleaningUpAfterMassage(time: Int) extends ActiveState {
  override val message: String = "Sprzątanie po masażu"
}

case class WaitingForOrders() extends DeadState {
  override val message: String = "Oczekiwanie na polecenia"
}

case class WaitingForClientToLeave() extends DeadState {
  override val message: String = "Oczekiwanie na odejście klienta"
}

case class WCUse(time: Int) extends ActiveState {
  override val message: String = "Toaleta w użytku"
}

case class Idle() extends DeadState {
  override val message: String = "Nieużywane"
}