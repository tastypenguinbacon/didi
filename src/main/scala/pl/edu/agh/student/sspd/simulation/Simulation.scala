package pl.edu.agh.student.sspd.simulation

import pl.edu.agh.student.sspd.classes._

/**
  * Created by pingwin on 29.05.17.
  */
class Simulation {
  var objects: List[ObjectClass] = List()

  def addObjects(objects: ObjectClass*): Unit = {
    this.objects = this.objects ++ objects
  }

  def nextStep(): List[ObjectClass] = {
    objects = objects.map {
      case client: Client => handle(client)
      case staff: Staff => handle(staff)
      case equipment: Equipment => handle(equipment)
    }
    objects
  }

  private def handle(client: Client): Client = client match {
    case pedicureClient: PedicureClient => pedicureClient
    case manicureClient: ManicureClient => manicureClient
    case earPierceClient: EarPierceClient => earPierceClient
    case massageClient: MassageClient => massageClient
    case eyeLashClient: EyeLashClient => eyeLashClient
    case wcClient: WCClient => wcClient
    case massageEyeLashClient: MassageEyeLashClient => massageEyeLashClient
    case manicureMassageClient: ManicureMassageClient => manicureMassageClient
    case pedicureMassageClient: PedicureMassageClient => pedicureMassageClient
    case manicureEyeLashEarPierceClient: ManicureEyeLashEarPierceClient => manicureEyeLashEarPierceClient
    case pedicureEyeLashWcClient: PedicureEyeLashWCClient => pedicureEyeLashWcClient
    case manicurePedicureMassageClient: ManicurePedicureMassageClient => manicurePedicureMassageClient
    case universalClient: UniversalClient => universalClient
  }

  private def handle(staff: Staff): Staff = staff match {
    case cashierCleaner: CashierCleaner => cashierCleaner
    case manicurist: Manicurist => manicurist
    case pedicurist: Pedicurist => pedicurist
    case specialist: Specialist => specialist
    case masseur: Masseur => masseur
  }

  private def handle(equipment: Equipment) = equipment match {
    case wc: WC => wc
    case massageBed: MassageBed => massageBed
    case uvLamp: UVLamp => uvLamp
  }

}
