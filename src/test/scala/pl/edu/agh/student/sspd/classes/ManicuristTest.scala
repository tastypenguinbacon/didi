package pl.edu.agh.student.sspd.classes

import org.testng.annotations.Test

/**
  * Created by pingwin on 01.05.17.
  */
class ManicuristTest {
  @Test
  def test(): Unit = {
    val a: Manicurist.ActiveState = Manicurist.PolishingNails(3)
    val b: Manicurist.DeadState = Manicurist.Idle()
  }
}
