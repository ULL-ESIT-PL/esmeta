package esmeta.phase

import esmeta.*
import esmeta.cfg.CFG
import esmeta.interp.*
import esmeta.util.*
import esmeta.util.BaseUtils.*
import esmeta.util.SystemUtils.*
import esmeta.js.*
import esmeta.test262.*

/** `test262test` phase */
case object Test262Test extends Phase[CFG, Option[String]] {
  val name = "test262test"
  val help = "test a Test262 program with harness files."
  def apply(
    cfg: CFG,
    globalConfig: GlobalConfig,
    config: Config,
  ): Option[String] = try {
    // TODO support directory
    val filename = getFirstFilename(globalConfig, this.name)
    val initSt = Initialize.fromFile(cfg, filename, test262 = true)
    val st = Interp(initSt, timeLimit = None)

    // check final state
    st(GLOBAL_RESULT) match
      case Undef => None
      case v     => Some(s"return not undefined: $v")
  } catch {
    case e: Throwable => Some(s"unexpected error: $e")
  }
  def defaultConfig: Config = Config()
  val options: List[PhaseOption[Config]] = List()
  case class Config()
}
