package pl.edu.agh.student.sspd.gui

import pl.edu.agh.student.sspd.classes._
import pl.edu.agh.student.sspd.simulation.Coordinator

import scalafx.Includes._
import scalafx.application
import scalafx.application.JFXApp
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.GridPane
import scalafx.scene.text.Text

/**
  * Created by pingwin on 06.06.17.
  */
object Gui extends JFXApp {
  val coordinator: Coordinator = new Coordinator

  stage = new application.JFXApp.PrimaryStage {
    title = "Symulacja"

    val firstSpecialist = Label(specialistStatus(coordinator.specialist1))
    val secondSpecialist = Label(specialistStatus(coordinator.specialist2))
    val earPierceQueue = Label(coordinator.specialist1.earPierceQueueLength.toString)
    val eyeLashQueue = Label(coordinator.specialist1.eyeLashExtensionQueueLength.toString)

    val specialists = new GridPane() {
      hgap = 20
      vgap = 6

      add(new Text("Specjaliści"), 0, 0)
      add(new Text("Długość kolejki do\nprzebijania uszu:"), 0, 1)
      add(earPierceQueue, 1, 1)
      add(new Text("Długość kolejki do\nprzedłużania rzęs"), 0, 2)
      add(eyeLashQueue, 1, 2)
      add(new Text("Pierwsza specjalistka:"), 0, 3)
      add(firstSpecialist, 1, 3)
      add(new Text("Druga specjalistka:"), 0, 4)
      add(secondSpecialist, 1, 4)
    }

    val firstMassageBed = Label(massageBedStatus(coordinator.massageBed1))
    val secondMassageBed = Label(massageBedStatus(coordinator.massageBed2))
    val uvLamp = Label(uvLampStatus(coordinator.lamp))
    val toilet = Label(toiletStatus(coordinator.wc))

    val utilities = new GridPane {
      hgap = 20
      vgap = 6
      add(new Text("Wyposażenie i WC"), 0, 0)
      add(new Text("Łóżko do masażu 1:"), 0, 1)
      add(firstMassageBed, 1, 1)
      add(new Text("Łóżko do masażu 2:"), 0, 2)
      add(secondMassageBed, 1, 2)
      add(new Text("Lampa UV:"), 0, 3)
      add(uvLamp, 1, 3)
      add(new Text("WC:"), 0, 4)
      add(toilet, 1, 4)
    }

    val massageQueue = Label(coordinator.masseur.queueLength.toString)
    val masseur = Label(masseurStatus(coordinator.masseur))

    val massage = new GridPane {
      hgap = 20
      vgap = 6
      add(new Text("Masażystka"), 0, 0)
      add(masseur, 1, 0)
      add(new Text("Długość kolejki do masażu"), 0, 1)
      add(massageQueue, 1, 1)
    }

    val pedicureQueue = Label(coordinator.pedicurist.queueLength.toString)
    val pedicurist = Label(pedicuristStatus(coordinator.pedicurist))

    val pedicure = new GridPane {
      hgap = 20
      vgap = 6
      add(new Text("Pedicurzystka:"), 0, 0)
      add(pedicurist, 1, 0)
      add(new Text("Długość kolejki do pedicure"), 0, 1)
      add(pedicureQueue, 1, 1)
    }

    val manicureQueue = Label(coordinator.manicurist.queueLength.toString)
    val manicurist = Label(manicuristStatus(coordinator.manicurist))

    val manicure = new GridPane {
      hgap = 20
      vgap = 6
      add(new Text("Manicurzystka:"), 0, 0)
      add(manicurist, 1, 0)
      add(new Text("Długość kolejki do manicure"), 0, 1)
      add(manicureQueue, 1, 1)
    }

    val paymentQueue = Label(coordinator.cashierCleaner.queueLength.toString)
    val cashierCleaner = Label(cashierCleanerState(coordinator.cashierCleaner))

    val payment = new GridPane {
      hgap = 20
      vgap = 5
      add(new Text("Kasjerko-sprzątaczka"), 0, 0)
      add(cashierCleaner, 1, 0)
      add(new Text("Długość kolejki do kasy"), 0, 1)
      add(paymentQueue, 1, 1)
    }

    val simulation = new GridPane {
      hgap = 20
      vgap = 50
      padding = Insets(40)
      add(specialists, 0, 0)
      add(utilities, 1, 0)
      add(massage, 0, 1)
      add(pedicure, 1, 1)
      add(manicure, 0, 2)
      add(payment, 1, 2)
    }

    val log = new TextArea {
      minHeight = 600
      maxHeight = 600
      wrapText = true
      editable = false
    }

    val logAppender: (String) => (() => Unit) => (MouseEvent) => Unit = (message: String) => (ev: () => Unit) => (_: MouseEvent) => {
      val iteration = coordinator.iteration
      log.text = iteration + "min    " + message + "\n" + log.text.value
      ev()
    }

    val options = new GridPane {
      add(new Button("Klient pedicure") {
        minWidth = 300
        minHeight = 500 / 14
        onMouseClicked = logAppender("Przybycie klienta pedicure")(() => None)
      }, 0, 0)
      add(new Button("Klient manicure") {
        minWidth = 300
        minHeight = 500 / 14
        onMouseClicked = logAppender("Przybycie klienta manicure")(() => None)
      }, 0, 1)
      add(new Button("Klient uszy") {
        minWidth = 300
        minHeight = 500 / 14
        onMouseClicked = logAppender("Przybycie klienta do przebijania uszu")(() => None)
      }, 0, 3)
      add(new Button("Klient masaż") {
        minWidth = 300
        minHeight = 500 / 14
        onMouseClicked = logAppender("Przybycie klienta do masażu")(() => None)
      }, 0, 4)
      add(new Button("Klient rzęsy") {
        minWidth = 300
        minHeight = 500 / 14
        onMouseClicked = logAppender("Przybycie klienta do przedłużania rzęs")(() => None)
      }, 0, 5)
      add(new Button("Klient WC") {
        minWidth = 300
        minHeight = 500 / 14
        onMouseClicked = logAppender("Przybycie klienta WC")(() => None)
      }, 0, 6)
      add(new Button("Klient masażu i rzęs") {
        minWidth = 300
        minHeight = 500 / 14
        onMouseClicked = logAppender("Przybycie klienta masażu i rzęs")(() => None)
      }, 0, 7)
      add(new Button("Klient manicure i masażu") {
        minWidth = 300
        minHeight = 500 / 14
        onMouseClicked = logAppender("Przybycie klienta manicure i masażu")(() => None)
      }, 0, 8)
      add(new Button("Klient pedicure i masażu") {
        minWidth = 300
        minHeight = 500 / 14
        onMouseClicked = logAppender("Przybycie klienta pedicure i masażu")(() => None)
      }, 0, 9)
      add(new Button("Klient manicure, rzęsy i uszy") {
        minWidth = 300
        minHeight = 500 / 14
        onMouseClicked = logAppender("Przybycie klienta manicure, przedłużania rzęs i przebijania uszu")(() => None)
      }, 0, 10)
      add(new Button("Klient pedicure, rzęsy i WC") {
        minWidth = 300
        minHeight = 500 / 14
        onMouseClicked = logAppender("Przybycie klienta pedicure, rzęs i WC")(() => None)
      }, 0, 11)
      add(new Button("Klient uniwersalny") {
        minWidth = 300
        minHeight = 500 / 14
        onMouseClicked = logAppender("Przybycie klienta uniwersalnego")(() => None)
      }, 0, 12)
      add(new Button("Zepsucie lampy UV") {
        minWidth = 300
        minHeight = 500 / 14
        onMouseClicked = logAppender("Zepsuła się lampa UV")(() => None)
      }, 0, 13)
      add(new Button("Zabrudzenie toalety") {
        minWidth = 300
        minHeight = 500 / 14
        onMouseClicked = logAppender("Toaleta wymaga czyszczenia")(() => None)
      }, 0, 14)
      minWidth = 300
      minHeight = 490
      maxHeight = 490
    }

    val nextButton = new Button("Następna\niteracja") {
      minWidth = 300
      minHeight = 110
      maxHeight = 110
      onMouseClicked = (_: MouseEvent) => {
        coordinator.next()
        firstMassageBed.text = massageBedStatus(coordinator.massageBed1)
        secondMassageBed.text = massageBedStatus(coordinator.massageBed2)
        earPierceQueue.text = coordinator.specialist1.earPierceQueueLength.toString
        eyeLashQueue.text = coordinator.specialist1.eyeLashExtensionQueueLength.toString
        firstSpecialist.text = specialistStatus(coordinator.specialist1)
        secondSpecialist.text = specialistStatus(coordinator.specialist2)
        massageQueue.text = coordinator.masseur.queueLength.toString
        masseur.text = masseurStatus(coordinator.masseur)
        manicureQueue.text = coordinator.manicurist.queueLength.toString
        manicurist.text = manicuristStatus(coordinator.manicurist)
        pedicureQueue.text = coordinator.pedicurist.queueLength.toString
        pedicurist.text = pedicuristStatus(coordinator.pedicurist)
        uvLamp.text = uvLampStatus(coordinator.lamp)
        toilet.text = toiletStatus(coordinator.wc)
        paymentQueue.text = coordinator.cashierCleaner.queueLength.toString
        cashierCleaner.text = cashierCleanerState(coordinator.cashierCleaner)
      }
    }

    val leftSideMenu = new GridPane() {
      add(options, 0, 0)
      add(nextButton, 0, 1)
    }

    val gui = new GridPane() {
      add(leftSideMenu, 0, 0)
      add(simulation, 1, 0)
      add(log, 2, 0)
      padding = Insets(0, 20, 0, 0)
    }

    scene = new Scene {
      content = gui
    }
  }

  def massageBedStatus(bed: MassageBed): String = {
    val cleaning: String = if (bed.needsCleaning) "Trzeba wyczyścić" else "Jest czyste"
    val state = bed.state match {
      case Massage(time) => "Masaż trwający: " + time + "min"
      case EarPiercing(time) => "Przebijanie uszu trwające: " + time + "min"
      case CleaningUpAfterMassage(time) => "Sprzątanie po masażu do: " + time + "min"
      case CleaningUpAfterEarPiercing(time) => "Sprzątanie po przebijaniu uszu do" + time + "min"
      case Idle() => "Nieużywane"
      case _ => ""
    }
    val occupied = if (bed.occupied) "Zajęte" else "Wolne"
    String.join("\n", cleaning, state, occupied)
  }

  def specialistStatus(specialist: Specialist): String = {
    val state = specialist.state match {
      case WaitingForOrders() => "Czeka na klienta"
      case EarPiercing(time) => "Przebijanie uszu przez: " + time + "min"
      case EyeLashExtending(time) => "Wydłużanie rzęs przez: " + time + "min"
      case CleaningUpAfterEarPiercing(time) => "Sprzątanie po przebijaniu uszu do: " + time + "min"
      case CleaningUpAfterEyeLashExtension(time) => "Sprzątanie po wydłużaniu rzęs do: " + time + "min"
      case _ => ""
    }
    val occupied = if (specialist.occupied) "Zajęta" else "Wolna"
    String.join("\n", state, occupied)
  }

  def masseurStatus(masseur: Masseur): String = {
    val state = masseur.state match {
      case WaitingForOrders() => "Czeka na klienta"
      case Massage(time) => "Masaż przez " + time + "min"
      case CleaningUpAfterMassage(time) => "Sprzątanie po masażu do: " + time + "min"
      case _ => ""
    }
    val occupied = if (masseur.occupied) "Zajęta" else "Wolna"
    String.join("\n", state, occupied)
  }

  def manicuristStatus(manicurist: Manicurist): String = {
    val needsLamp = if (manicurist.needsLamp) "Potrzebuje lampy" else "Nie potrzebuje lampy"
    val state = manicurist.state match {
      case WaitingForOrders() => "Czeka na klienta"
      case FingerNailPainting(time) => "Malowanie paznokci przez " + time + "min"
      case FingerNailHardening(time) => "Utwardzanie paznokci przez " + time + "min"
      case WaitingForVanishToDry() => "Oczekiwanie na przeschnięcie lakieru"
      case _ => ""
    }
    val occupied = if (manicurist.occupied) "Zajęta" else "Wolna"
    String.join("\n", needsLamp, state, occupied)
  }

  def pedicuristStatus(pedicurist: Pedicurist): String = {
    val needsLamp = if (pedicurist.needsLamp) "Potrzebuje lampy" else "Nie potrzebuje lampy"
    val state = pedicurist.state match {
      case WaitingForOrders() => "Czeka na klienta"
      case FingerNailPainting(time) => "Malowanie paznokci przez " + time + "min"
      case FingerNailHardening(time) => "Utwardzanie paznokci przez " + time + "min"
      case WaitingForVanishToDry() => "Oczekiwanie na przeschnięcie lakieru"
      case _ => ""
    }
    val occupied = if (pedicurist.occupied) "Zajęta" else "Wolna"
    String.join("\n", needsLamp, state, occupied)
  }

  def uvLampStatus(uVLamp: UVLamp): String = {
    val needsRepair = if (uVLamp.failure) "Wymaga naprawy" else "Działa"
    val state = uVLamp.state match {
      case FingerNailHardening(time) => "Utwardzanie paznokci u dłoni przez " + time + "min"
      case FootNailHardening(time) => "Utwardzanie paznokci u stóp przez " + time + "min"
      case EquipmentRepair(time) => "Jest naprawiane przez" + time + "min"
      case Idle() => "nieużywana"
      case _ => ""
    }
    val occupied = if (uVLamp.occupied) "Zajęta" else "Wolna"
    String.join("\n", needsRepair, state, occupied)
  }

  def toiletStatus(wc: WC): String = {
    val needsCleaning = if (wc.needsCleaning) "Wymaga sprzątnia" else "Czysta"
    val state = wc.state match {
      case WCUse(time) => "Toaleta używana przez " + time + "min"
      case WCCleaning(time) => "Toaleta sprzątana przez " + time + "min"
      case Idle() => "Toaleta nieużywana"
      case _ => ""
    }
    val queueLength = "Długość kolejki: " + wc.queueLength
    val occupied = if (wc.occupied) "Zajęte" else "Wolne"
    String.join("\n", needsCleaning, state, queueLength, occupied)
  }

  def cashierCleanerState(cashierCleaner: CashierCleaner): String = {
    val state = cashierCleaner.state match {
      case Payment(time) => "Obsługiwanie płacenia przez " + time + "min"
      case WCCleaning(time) => "Sprzątanie toalety przez " + time + "min"
      case WaitingForOrders() => "Oczekuje na zadanie"
      case _ => ""
    }
    val occupied = if (cashierCleaner.occupied) "Zajęta" else "Wolna"
    String.join("\n", state, occupied)
  }
}
