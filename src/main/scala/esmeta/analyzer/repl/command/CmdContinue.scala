package esmeta.analyzer.repl.command

import esmeta.analyzer.*
import esmeta.analyzer.repl.*

// continue command
case object CmdContinue
  extends Command(
    "continue",
    "Continue static analysis.",
  ) {
  // options
  val options: List[String] = Nil

  // run command
  def apply(
    cpOpt: Option[ControlPoint],
    args: List[String],
  ): Unit = REPL.continue = true
}
