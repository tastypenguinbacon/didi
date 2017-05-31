package pl.edu.agh.student.sspd.classes

/**
  * Created by pingwin on 31.05.17.
  */

trait Event

case class ToiletGotDirty() extends Event

case class UvLampDoesNotWork() extends Event


