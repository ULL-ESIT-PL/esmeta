package esmeta.ir

import esmeta._
import esmeta.ir._
import esmeta.util.FileUtils._

class EvalTinyTest extends IRTest {
  val name: String = "irEvalTest"

  // registration
  def init: Unit = {}

  // for (file <- walkTree(IR_TEST_DIR)) {
  //   val filename = file.getName
  //   if (irFilter(filename)) check(filename, {
  //     val irName = file.toString
  //     // irEvalFile(irName)
  //     ???
  //   })
  // }
  init
}
