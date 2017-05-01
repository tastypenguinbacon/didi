package pl.edu.agh.student.sspd.classes.utils

import pl.edu.agh.student.sspd.classes.ObjectClass
import pl.edu.agh.student.sspd.state.State

/**
  * Created by pingwin on 01.05.17.
  */
case class MassageBed() extends ObjectClass {
  override def getState: State = null
}

object MassageBedStates {

  object ActiveStates {
    // todo start from here
  }

  object DeadStates {

  }

}

trait MassageBedStates
