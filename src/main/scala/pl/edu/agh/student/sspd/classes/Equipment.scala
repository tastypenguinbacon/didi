package pl.edu.agh.student.sspd.classes

abstract class Equipment

case class WC(var state: State, var needsCleaning: Boolean, var occupied: Boolean, var queueLength: Int)
  extends Equipment

case class MassageBed(var state: State, var needsCleaning: Boolean, var occupied: Boolean)
  extends Equipment

case class UVLamp(var state: State, var occupied: Boolean, var failure: Boolean)
  extends Equipment
